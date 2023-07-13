package org.firstinspires.ftc.teamcode.utilities;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Pipeline extends OpenCvPipeline {
    public enum CubePosition {
        LEFT,
        MIDDLE,
        RIGHT,
        NONE
    }
    public  Pipeline.CubePosition position = null;
    private Mat matrix = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(matrix);
        if (matrix.empty()) {
            position = CubePosition.NONE;
            return input;
        }
        Imgproc.cvtColor(matrix, matrix, Imgproc.COLOR_RGB2YCrCb);

        Mat matLeft = matrix.submat(120, 160, 40, 80);
        Mat matCenter = matrix.submat(120, 160, 160, 200);
        Mat matRight = matrix.submat(120, 160, 280, 320);

        double leftTotalCr = Core.sumElems(matLeft).val[1];
        double centerTotalCr = Core.sumElems(matCenter).val[1];
        double rightTotalCr = Core.sumElems(matRight).val[1];

        double leftTotalCb = Core.sumElems(matLeft).val[2];
        double centerTotalCb = Core.sumElems(matCenter).val[2];
        double rightTotalCb = Core.sumElems(matRight).val[2];

        final Scalar Fill = new Scalar(0, 0, 255);

        // Left Rectangle
        Imgproc.rectangle(matrix, new Point(40, 120), new Point(80, 160), Fill, 2);

        // Center Rectangle
        Imgproc.rectangle(matrix, new Point(160, 120), new Point(200, 160), Fill, 2);

        // Right Rectangle
        Imgproc.rectangle(matrix, new Point(280, 120), new Point(320, 160), Fill, 2);

        return matrix;
    }
}
