package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "First Program")
public class FirstProgram extends OpMode {
    //creating our motor variables
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    //creating our continuous servo variable
    private CRServo continuousServo;

    //creating our position servo variable
    private Servo positionServo;

    //a move forward function to control both motors at the same time
    public void moveForward(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    @Override
    public void init() {
        //initializing our motor variables
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        //initializing our continuous servo variable
        continuousServo = hardwareMap.get(CRServo.class, "continuousServo");

        //initializing our position servo variable
        positionServo = hardwareMap.get(Servo.class, "positionServo");

        //saying we will run our motors with encoders
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //stating the spin directions of each motor
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //saying that at zero power, our motors will brake
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //stating the spin direction of our continuous servo
        continuousServo.setDirection(DcMotorSimple.Direction.FORWARD);

        //stating the spin direction of our position servo, setting its scale range, resetting its position
        positionServo.setDirection(Servo.Direction.FORWARD);
        positionServo.scaleRange(0, 1);
        positionServo.setPosition(0.5);
    }

    @Override
    public void loop() {
        //moving the robot according to the left joystick
        moveForward(-gamepad1.left_stick_y);

        //moving the continuous servo according to the right trigger
        continuousServo.setPower(gamepad1.right_trigger);

        //moving our position servo according to A and B buttons
        if (gamepad1.a) {
            positionServo.setPosition(0.0);
        }
        else if (gamepad1.b) {
            positionServo.setPosition(1.0);
        }

        //outputting telemetry data to the driver station
        telemetry.addLine("Telemetry Data:");
        telemetry.addData("Motor Power", gamepad1.right_trigger);
        telemetry.addData("Position Servo's Position", positionServo.getPosition());
        telemetry.update();
    }
}
