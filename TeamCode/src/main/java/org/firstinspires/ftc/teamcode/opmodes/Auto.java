package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    public void moveForwardByDistance(double distance, double radius) {
        int ticksToMove = (int) (1440 * (distance / (2 * Math.PI * radius)));
        leftMotor.setTargetPosition(ticksToMove);
        rightMotor.setTargetPosition(ticksToMove);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void moveForwardByTime(double power, long milliseconds) throws InterruptedException {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
        Thread.sleep(milliseconds);
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // moving robot 1m based on distance
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        moveForwardByDistance(100, 5);
        Thread.sleep(2000);
        moveForwardByDistance(50, 5);
        Thread.sleep(2000);
        // moving robot 1m based on time
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        moveForwardByTime(1.0, 2000);
        Thread.sleep(2000);
        moveForwardByTime(1.0, 2000);
        waitForStart();
    }
}
