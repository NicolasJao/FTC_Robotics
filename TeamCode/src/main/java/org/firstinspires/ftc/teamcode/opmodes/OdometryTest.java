package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.OdometrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelsSubsystem;
import org.firstinspires.ftc.teamcode.threadopmode.TaskThread;
import org.firstinspires.ftc.teamcode.threadopmode.ThreadOpMode;

@TeleOp(name = "OdometryTest")
public class OdometryTest extends ThreadOpMode {
    // create mecanum drive wheels subsystem object and odometry subsystem object
    private WheelsSubsystem drive;
    private OdometrySubsystem odometry;

    @Override
    public void mainInit() {
        // initialize mecanum drive wheels and odometry objects
        drive = new WheelsSubsystem(hardwareMap);
        odometry = new OdometrySubsystem(hardwareMap);

        // reset encoders and position
        odometry.reset();

        // creating a new thread to separately run our odometry
        registerThread(new TaskThread(new TaskThread.Actions() {
            @Override
            public void loop() {
                // update our odometry position constantly
                odometry.update();
            }
        }));
    }
    @Override
    public void mainLoop() {
        // gamepad inputs for our mecanum wheels
        drive.move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        // telemetry for our odometry
        telemetry.addData("x", odometry.getx());
        telemetry.addData("y", odometry.gety());
        telemetry.addData("heading in DEG", odometry.getHeading() * 180 / Math.PI);
        telemetry.addData("RightTicks", odometry.getRightTicks());
        telemetry.addData("LeftTicks", odometry.getLeftTicks());
        telemetry.addData("AuxTicks", odometry.getAuxTicks());
        telemetry.update();
    }
}
