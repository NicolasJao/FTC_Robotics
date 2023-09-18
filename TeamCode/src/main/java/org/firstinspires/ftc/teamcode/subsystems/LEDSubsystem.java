package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LEDSubsystem {
    private DigitalChannel greenLED;
    private DigitalChannel redLED;
    public LEDSubsystem(HardwareMap hardwareMap) {
        //setting up green LED, true for off, false for on
        greenLED = hardwareMap.get(DigitalChannel.class, "greenLED");
        greenLED.setMode(DigitalChannel.Mode.OUTPUT);
        greenLED.setState(true);

        //setting up red LED, true for off, false for on
        redLED = hardwareMap.get(DigitalChannel.class, "redLED");
        redLED.setMode(DigitalChannel.Mode.OUTPUT);
        redLED.setState(true);
    }
    public void setGreen() {
        greenLED.setState(false);
        redLED.setState(true);
    }
    public void setRed() {
        redLED.setState(false);
        greenLED.setState(true);
    }
}
