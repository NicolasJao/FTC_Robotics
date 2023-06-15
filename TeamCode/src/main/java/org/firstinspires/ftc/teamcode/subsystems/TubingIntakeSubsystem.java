package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TubingIntakeSubsystem {
    public CRServo servomotor;
    public TubingIntakeSubsystem(HardwareMap hardwareMap) {
        servomotor = hardwareMap.get(CRServo.class, "servomotor");
        servomotor.setDirection(CRServo.Direction.FORWARD);
    }
    public void move(double power) {
        servomotor.setPower(power);
    }
}

