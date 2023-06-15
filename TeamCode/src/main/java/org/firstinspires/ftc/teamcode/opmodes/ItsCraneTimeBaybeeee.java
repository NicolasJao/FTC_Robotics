package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.RollerIntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelsSubsystem;

import java.lang.Math;

@TeleOp(name = "ItsCraneTimeBaybeeee")
public class ItsCraneTimeBaybeeee extends OpMode {
    private RollerIntakeSubsystem intake;
    private boolean previousButtonState;
    private boolean currentButtonState;
    private boolean servosMoving = false;
    private double RPM = 0.0;
    private WheelsSubsystem drive;
    @Override
    public void init() {
        intake = new RollerIntakeSubsystem(hardwareMap);
        currentButtonState = gamepad1.right_stick_button;
        drive = new WheelsSubsystem(hardwareMap);
    }
    @Override
    public void loop() {
        previousButtonState = currentButtonState;
        currentButtonState = gamepad1.right_stick_button;
        if (previousButtonState && !currentButtonState) {
            servosMoving = !servosMoving;
            if (servosMoving) {
                intake.move(1.0);
            } else {
                intake.move(0.0);
            }
        }
        drive.move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
    }
}



/*
double yeet = intakeServo1.servomotor1.getController().getServoPosition(intakeServo1.servomotor1.getPortNumber());

RPM = (ticksFinal - ticksInitial) * 240 * Math.PI;
telemetry.addData("RPM", RPM);
telemetry.update();

telemetry.addData("stuff", yeet);

*/
