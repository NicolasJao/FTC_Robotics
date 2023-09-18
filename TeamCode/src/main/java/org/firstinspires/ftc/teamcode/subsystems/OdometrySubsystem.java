package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OdometrySubsystem {
    // constants that define the geometry of the robot
    private final double L1 = 19.73;
    private final double L2 = 20.34;
    public static final double B = 19.88;
    public static final double r = 1.75;
    public static final double TPR = 8192;
    public static final double GEAR_RATIO = 1;
    private final double CM_PER_TICK = (2 * Math.PI * r * GEAR_RATIO) / TPR;

    // for tracking the position of the robot
    private double x, y, heading;
    private double dx, dy, dheading;

    // for tracking the position updates
    private double lastRightTicks, lastLeftTicks, lastAuxTicks;
    private double currRightTicks, currLeftTicks, currAuxTicks;
    private double dRightTicks, dLeftTicks, dAuxTicks;

    // encoder objects
    private final DcMotor right, left, aux;

    public OdometrySubsystem(HardwareMap hardwareMap) {
        // initiating the encoder objects
        right = hardwareMap.get(DcMotor.class, "right");
        left = hardwareMap.get(DcMotor.class, "left");
        aux = hardwareMap.get(DcMotor.class, "aux");

        // directions are subject to change depending on how you assemble the robot
        right.setDirection(DcMotorSimple.Direction.FORWARD);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        aux.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void reset() {
        // reset encoder ticks for each pod
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        aux.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // reset our position
        x = y = heading = 0.0;
    }
    public void update() {
        // step 1: read tick counts from encoders
        currRightTicks = right.getCurrentPosition();
        currLeftTicks = left.getCurrentPosition();
        currAuxTicks = aux.getCurrentPosition();

        // step 2: find the change in ticks since the last update
        dRightTicks = currRightTicks - lastRightTicks;
        dLeftTicks = currLeftTicks - lastLeftTicks;
        dAuxTicks = currAuxTicks - lastAuxTicks;

        // step 3: use our ∆ticks in our derived movement equations
        dx = (CM_PER_TICK / 2) * (dRightTicks + dLeftTicks);
        dy = CM_PER_TICK * (dAuxTicks + (B / (L1 + L2)) * (dRightTicks - dLeftTicks));
        dheading = (CM_PER_TICK / (L1 + L2)) * (dRightTicks - dLeftTicks);

        // step 4: rotate relative coordinates to field coordinates, while also updating the position
        x += dx * Math.cos(heading) - dy * Math.sin(heading);
        y += dx * Math.sin(heading) + dy * Math.cos(heading);
        heading += dheading;

        // record the previous encoder positions for the next update
        lastRightTicks = currRightTicks;
        lastLeftTicks = currLeftTicks;
        lastAuxTicks = currAuxTicks;
    }

    // getter methods to return coordinates and heading
    public double getx() {
        return x;
    }
    public double gety() {
        return y;
    }
    public double getHeading() {
        return heading;
    }

    // getter methods to return the encoder tick counts for debugging
    public int getRightTicks() {
        return right.getCurrentPosition();
    }
    public int getLeftTicks() {
        return left.getCurrentPosition();
    }
    public int getAuxTicks() {
        return aux.getCurrentPosition();
    }
}
