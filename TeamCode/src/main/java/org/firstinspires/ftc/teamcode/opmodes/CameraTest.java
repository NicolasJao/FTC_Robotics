package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;

@TeleOp(name = "CameraTest")
public class CameraTest extends OpMode {
    private CameraSubsystem camera;
    @Override
    public void init() {
        camera = new CameraSubsystem(hardwareMap);
    }

    @Override
    public void loop() {
        FtcDashboard.getInstance().startCameraStream(camera.camera, 30);
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
    }
}
