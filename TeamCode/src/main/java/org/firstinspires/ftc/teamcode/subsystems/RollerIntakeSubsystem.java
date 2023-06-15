package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RollerIntakeSubsystem {
    public CRServo servomotor1;
    public CRServo servomotor2;
    public RollerIntakeSubsystem(HardwareMap hardwareMap) {
        servomotor1 = hardwareMap.get(CRServo.class, "servomotor1");
        servomotor1.setDirection(CRServo.Direction.FORWARD);
        servomotor2 = hardwareMap.get(CRServo.class, "servomotor2");
        servomotor2.setDirection(CRServo.Direction.REVERSE);
    }
    public void move(double power) {
        servomotor1.setPower(power);
        servomotor2.setPower(power);
    }
}
