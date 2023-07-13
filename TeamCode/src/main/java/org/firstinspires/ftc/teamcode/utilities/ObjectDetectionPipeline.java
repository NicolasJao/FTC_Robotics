package org.firstinspires.ftc.teamcode.utilities;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ObjectDetectionPipeline extends OpenCvPipeline {

    //what we use
    Mat hsvMat = new Mat();
    Mat filteredHSV = new Mat();
    List<MatOfPoint> contours = new ArrayList<>();
    Mat hierarchy = new Mat();
    final MatOfInt hull = new MatOfInt();
    List<MatOfPoint> outputContours = new ArrayList<>();

    Rect[] boundingBoxes;
    float xPosition;

    @Override
    //this member method "constructor" processes a frame for the pipeline
    public Mat processFrame(Mat input) {
        //if camera input is empty, return that input
        if (input.empty()) {
            return input;
        }

        //convert colour scale from RGB to HSV
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        //make an hsv threshold
        Scalar lowHSV = new Scalar(40, 60, 41);
        Scalar highHSV = new Scalar(77, 155, 155);
        Core.inRange(hsvMat, lowHSV, highHSV, filteredHSV);

        //find contours
        contours.clear();
        int mode = Imgproc.RETR_LIST;
        int method = Imgproc.CHAIN_APPROX_SIMPLE;
        Imgproc.findContours(filteredHSV, contours, hierarchy, mode, method);

        //filter contours, set parameters to filter out
        double minArea = 500.0;
        double minPerimeter = 0;
        double minWidth = 0.0;
        double maxWidth = 100000000.0;
        double minHeight = 0.0;
        double maxHeight = 100000000.0;
        double[] solidity = {0.0, 100.0};
        double maxVertexCount = 1000000.0;
        double minVertexCount = 0.0;
        double minRatio = 0.0;
        double maxRatio = 1000.0;
        outputContours.clear();

        //put contours that match into the output list
        for (int i = 0; i < contours.size(); i++) {
            final MatOfPoint contour = contours.get(i);
            final Rect bb = Imgproc.boundingRect(contour);
            if (bb.width < minWidth || bb.width > maxWidth) {
                continue;
            }
            if (bb.height < minHeight || bb.height > maxHeight) {
                continue;
            }
            final double area = Imgproc.contourArea(contour);
            if (area < minArea) {
                continue;
            }
            if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter) {
                continue;
            }
            Imgproc.convexHull(contour, hull);
            MatOfPoint mopHull = new MatOfPoint();
            mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
            for (int j = 0; j < hull.size().height; j++) {
                int index = (int) hull.get(j, 0)[0];
                double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1]};
                mopHull.put(j, 0, point);
            }
            final double solid = 100 * area / Imgproc.contourArea(mopHull);
            if (solid < solidity[0] || solid > solidity[1]) {
                continue;
            }
            if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount) {
                continue;
            }
            final double ratio = bb.width / (double) bb.height;
            if (ratio < minRatio || ratio > maxRatio) {
                continue;
            }
            outputContours.add(contour);
        }

        //draw bounding boxes around the final outputContours
        final Scalar Fill = new Scalar(0.5, 76.9, 89.8);
        boundingBoxes = new Rect[outputContours.size()];
        for (int i = 0; i < outputContours.size(); i++) {
            boundingBoxes[i] = Imgproc.boundingRect(outputContours.get(i));
            if (boundingBoxes[i] != null) {
                Imgproc.rectangle(hsvMat, boundingBoxes[i], Fill);
            }
        }

        //copy the output contours into a target list and create a list of xPositions of contours
        List<MatOfPoint> targets = outputContours;
        float[] xPositions = new float[targets.size()];
        int i = 0;
        if (targets != null) {
            for (MatOfPoint target: targets) {
                float location = Imgproc.boundingRect(target).x;
                xPositions[i] = location;
                i++;
            }
        }
        if (xPositions.length != 0) {
            xPosition = xPositions[0];
        }

        //finally we return the HSV matrix
        return hsvMat;
    }

    //this member method "getter" returns the xPosition we found
    public float getTargetXPos() {
        return xPosition;
    }
}
