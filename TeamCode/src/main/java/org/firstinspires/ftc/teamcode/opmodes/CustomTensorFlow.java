package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.R;

@TeleOp(name = "CustomTensorFlow")
public class CustomTensorFlow extends OpMode {
    //declare TensorFlow variables
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCOM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    //declare Vuforia info
    private static final String VUFORIA_KEY = "(paste vuforia key here)";

    //declare Vuforia and TensorFlow objects
    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;

    //initialize Vuforia
    private void initVuforia() {
        //configure Vuforia by creating a Parameter object and passing it to the Vuforia engine
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "NicoWebcam");

        //instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        //loading trackables is not necessary for the TensorFlow Object Detection engine
    }

    //initialize tfod
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id",
                hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    @Override
    public void init() {
        //set up TensorFlow
        initVuforia();
        initTfod();

        //check if tfod is working then activate it and set the zoom
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2.5, 16.0/9.0);
        }

        //if you want to use the dashboard declare this:
        FtcDashboard.getInstance().startCameraStream(this.tfod, 0);
    }

    @Override
    public void loop() {
        //an example of using tfod in loop() is my PIDservo class
    }
}
