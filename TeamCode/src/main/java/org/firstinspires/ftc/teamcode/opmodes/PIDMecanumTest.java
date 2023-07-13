package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.PIDMecanumSubsystem;

@TeleOp(name = "PIDMecanumTest")
public class PIDMecanumTest extends OpMode {
    private PIDMecanumSubsystem drive;
    private final ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    public double integral = 0.0;
    public double derivative = 0.0;
    public double lastError = 0.0;
    private double PID(double setpoint, double feedback) {
        double error = setpoint - feedback;
        integral += error * timer.time();
        derivative = (error - lastError) / timer.time();
        lastError = error;
        timer.reset();
        double kP = 0.0;
        double kI = 0.0;
        double kD = 0.0;
        return kP * error + kI * integral + kD * derivative;
    }
    @Override
    public void init() {
        drive = new PIDMecanumSubsystem(hardwareMap);
    }

    @Override
    public void loop() {
        drive.move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
    }
}
