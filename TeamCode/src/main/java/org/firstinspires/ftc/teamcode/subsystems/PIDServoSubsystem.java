package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

//this class controls a servo with a PID to make the camera find the centre of an object
public class PIDServoSubsystem {
    //set PID variables
    public float error = 0;
    double kP = 0.1;
    double kD = 0.3;//0.0001;
    double kI = 0;//0.0000000012;
    private ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    double integral = 0.0;
    double derivative = 0.0;
    double lastError = 0.0;
    float pixelsToServoMv = (float) 0.000666;

    //set camera tracking variables
    double servoPosition = 0.5;
    float centeredLine = 310;
    /*
    public void CenterCamera() {
        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                int i = 0;
                for (Recognition recognition: updatedRecognitions) {
                    //find center of current object
                    float leftSide = recognition.getLeft();
                    float rightSide = recognition.getRight();
                    float middleObject = (leftSide + rightSide) / 2;

                    //set error from center
                    double servoSpeed = PID(centeredLine, middleObject);

                    //servoPosition is changed by adding the servoSpeed
                    servoPosition -= servoSpeed;

                    //then we can reset the servoPosition
                    trackingServo.setPosition(servoPosition);
                }
            }
        }
    }
    */
    public double PID(float setpoint, float feedback) {
        //finding proportional, derivative, and integral
        error = (setpoint - feedback) * pixelsToServoMv;
        derivative = (error - lastError) / timer.time();
        integral += error * timer.time();

        //setting things up for the next PID loop
        lastError = error;
        timer.reset();

        //calculating the adjustment value to reach setpoint value
        double adjustment = kP * error + kI * integral + kD * derivative;

        //returning the adjustment value to reach the setpoint value
        return adjustment;
    }
}
