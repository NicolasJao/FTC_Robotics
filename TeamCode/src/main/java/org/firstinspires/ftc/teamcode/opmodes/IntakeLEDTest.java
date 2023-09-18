package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ClawIntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ColourSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LEDSubsystem;

@TeleOp(name = "IntakeLEDTest")
public class IntakeLEDTest extends OpMode {
    //setting up the components of the intake LED system
    private ClawIntakeSubsystem arm;
    private ColourSensorSubsystem intakeSensor;
    private LEDSubsystem indicator;

    //setting up the range values for the colour yellow for the sensor
    private int minRed = 0;
    private int maxRed = 0;
    private int minGreen = 0; //find these values with testing
    private int maxGreen = 0;
    private int minBlue = 0;
    private int maxBlue = 0;

    @Override
    public void init() {
        //setting up intake colour sensor
        intakeSensor = new ColourSensorSubsystem(hardwareMap);

        //setting up the claw arm
        arm = new ClawIntakeSubsystem(hardwareMap);

        //setting up the LED indicator subsystem
        indicator = new LEDSubsystem(hardwareMap);
    }

    @Override
    public void loop() {
        //nothing in intake default: red, something yellow in intake: green
        if (intakeSensor.getColour().red() > minRed && intakeSensor.getColour().red() < maxRed &&
                intakeSensor.getColour().green() > minGreen && intakeSensor.getColour().green() < maxGreen &&
                intakeSensor.getColour().blue() > minBlue && intakeSensor.getColour().blue() < maxBlue) {
            indicator.setGreen();
        }
        else {
            indicator.setRed();
        }

        //telemetry from the colour sensor
        telemetry.addData("Red", intakeSensor.getColour().red());
        telemetry.addData("Green", intakeSensor.getColour().green());
        telemetry.addData("Blue", intakeSensor.getColour().blue());
        telemetry.addData("Alpha", intakeSensor.getColour().alpha());
        telemetry.update();
    }
}
