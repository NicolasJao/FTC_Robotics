package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Test")
public class Test extends OpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private CRServo continuousServo;
    private Servo positionServo;

    public void moveForward(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    int add(int firstNumber, int secondNumber) {
        int sum = firstNumber + secondNumber;
        return sum;
    }

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor =  hardwareMap.get(DcMotor.class, "rightMotor");
        continuousServo = hardwareMap.get(CRServo.class, "continuousServo");
        positionServo = hardwareMap.get(Servo.class, "positionServo");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        continuousServo.setDirection(DcMotorSimple.Direction.FORWARD);

        positionServo.setDirection(Servo.Direction.FORWARD);
        positionServo.scaleRange(0, 1);
        positionServo.setPosition(0.5);
    }

    @Override
    public void loop() {
        moveForward(-gamepad1.left_stick_y);
        continuousServo.setPower(gamepad1.right_trigger);
        if (gamepad1.a) {
            positionServo.setPosition(0.0);
        }
        else if (gamepad1.b) {
            positionServo.setPosition(1.0);
        }
        telemetry.addLine("Telemetry Data:");
        telemetry.addData("Motor Power", gamepad1.right_trigger);
        telemetry.addData("Position Servo's Position", positionServo.getPosition());
        telemetry.update();
    }
}
