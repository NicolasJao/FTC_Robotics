package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColourSensorSubsystem {
    private ColorSensor colour;
    public ColourSensorSubsystem(HardwareMap hardwareMap) {
        colour = hardwareMap.get(ColorSensor.class, "colourSensor");
    }
    public ColorSensor getColour() {
        return colour;
    }
}
