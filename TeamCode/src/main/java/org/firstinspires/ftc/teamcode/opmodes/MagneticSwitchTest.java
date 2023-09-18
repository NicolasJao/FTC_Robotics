package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "MagneticSwitchTest")
public class MagneticSwitchTest extends LinearOpMode {
    private DigitalChannel limit1;
    @Override
    public void runOpMode() throws InterruptedException {
        limit1 = hardwareMap.get(DigitalChannel.class, "limit1");
        limit1.setMode(DigitalChannel.Mode.INPUT);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Limit 1", limit1.getState());
            telemetry.update();
        }
    }
}
