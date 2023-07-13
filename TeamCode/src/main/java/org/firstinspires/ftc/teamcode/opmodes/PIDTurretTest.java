package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.PIDTurretSubsystem;

@TeleOp(name = "PIDTurretTest")
public class PIDTurretTest extends OpMode {
    int targetPosition;
    int currentPosition;
    private PIDTurretSubsystem turret;
    double kP = 0.01;
    double kI = 0.0;
    double kD = 0.0;
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
        return kP * error + kI * integral + kD * derivative;
    }
    @Override
    public void init() {
        turret = new PIDTurretSubsystem(hardwareMap);
        targetPosition = 0;
    }
    @Override
    public void loop() {
        currentPosition = turret.getPosition();
        turret.spin(PID(targetPosition, currentPosition));
        if (gamepad1.a) {
            targetPosition += 50;
        }
        else if (gamepad1.b) {
            targetPosition -= 50;
        }
    }
}
