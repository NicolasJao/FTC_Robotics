package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "First Auto")
public class FirstAuto extends LinearOpMode {
    //creating our motor variables
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    //a movement function by distance
    public void moveForwardByDistance(double distance, double radius) {
        //the equation that finds the ticks to move according to distance and radius
        int ticksToMove = (int) (1440 * (distance / (2 * Math.PI * radius)));

        //sets the target ticks position for our motors
        leftMotor.setTargetPosition(ticksToMove);
        rightMotor.setTargetPosition(ticksToMove);

        //makes the motors run to that set target ticks position
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //a movement function by time
    public void moveForwardByTime(double power, long milliseconds) throws InterruptedException {
        //sets the motor's power to the power parameter
        leftMotor.setPower(power);
        rightMotor.setPower(power);

        //makes the motors run for the amount of time as the milliseconds parameter
        Thread.sleep(milliseconds);

        //stops the motors by setting their power to 0
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //initializing our motor variables
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        //saying we will run our motors with encoders
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //stating the spin directions of each motor
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //saying that at zero power, our motors will brake
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //code won't run until you press start on the driver station
        waitForStart();

        //moving robot 1m based on distance
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //example autonomous test run by distance
        moveForwardByDistance(100, 5);
        Thread.sleep(2000);
        moveForwardByDistance(50, 5);
        Thread.sleep(2000);

        //moving robot 1m based on time
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //example autonomous test run by time
        moveForwardByTime(1.0, 2000);
        Thread.sleep(2000);
        moveForwardByTime(1.0, 2000);
    }
}
