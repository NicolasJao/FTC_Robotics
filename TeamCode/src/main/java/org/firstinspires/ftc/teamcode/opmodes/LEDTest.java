package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "LEDTest")
public class LEDTest extends OpMode {
    private DigitalChannel LED;
    @Override
    public void init() {
        LED = hardwareMap.get(DigitalChannel.class, "LED");
        LED.setMode(DigitalChannel.Mode.OUTPUT);
        LED.setState(false);
        telemetry.addData("State", LED.getState());
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("State", LED.getState());
        telemetry.update();
    }
}
