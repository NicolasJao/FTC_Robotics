package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class WheelsSubsystem {
    private final double SPEED_MULTIPLIER = 0.2;
    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;
    private double CLIP_MIN = -1.0;
    private final double CLIP_MAX = 1.0;
    private double frontLeftPower;
    private double frontRightPower;
    private double backLeftPower;
    private double backRightPower;
    public WheelsSubsystem(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void move(double x, double y, double z) {
        frontLeftPower = Range.clip(x + y + z, CLIP_MIN, CLIP_MAX);
        frontRightPower = Range.clip(-x + y - z, CLIP_MIN, CLIP_MAX);
        backLeftPower = Range.clip(-x + y + z, CLIP_MIN, CLIP_MAX);
        backRightPower = Range.clip(x + y - z, CLIP_MIN, CLIP_MAX);

        frontLeft.setPower(frontLeftPower * SPEED_MULTIPLIER);
        frontRight.setPower(frontRightPower * SPEED_MULTIPLIER);
        backLeft.setPower(backLeftPower * SPEED_MULTIPLIER);
        backRight.setPower(backRightPower * SPEED_MULTIPLIER);
    }
}
