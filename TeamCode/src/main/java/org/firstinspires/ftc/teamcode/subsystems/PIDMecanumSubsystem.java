package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class PIDMecanumSubsystem {
    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;
    public PIDMecanumSubsystem(HardwareMap hardwareMap) {
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
        double CLIP_MIN = -1.0;
        double CLIP_MAX = 1.0;
        double frontLeftPower = Range.clip(x + y + z, CLIP_MIN, CLIP_MAX);
        double frontRightPower = Range.clip(-x + y - z, CLIP_MIN, CLIP_MAX);
        double backLeftPower = Range.clip(-x + y + z, CLIP_MIN, CLIP_MAX);
        double backRightPower = Range.clip(x + y - z, CLIP_MIN, CLIP_MAX);

        double SPEED_MULTIPLIER = 1.0;
        frontLeft.setPower(frontLeftPower * SPEED_MULTIPLIER);
        frontRight.setPower(frontRightPower * SPEED_MULTIPLIER);
        backLeft.setPower(backLeftPower * SPEED_MULTIPLIER);
        backRight.setPower(backRightPower * SPEED_MULTIPLIER);
    }
}
