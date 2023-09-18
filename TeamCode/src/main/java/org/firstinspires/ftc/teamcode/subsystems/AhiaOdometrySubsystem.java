// This is not my odometry subsystem code, it's Ahia's

package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AhiaOdometrySubsystem {

    // constants that define the geometry of the robot
    public static final double WHEEL_DIAMETER = 3.5; // in centimeters
    public static final double TICKS_PER_REV = 8192;
    public static final double GEAR_RATIO = 1;
    public static final double TICKS_TO_CM = (Math.PI * WHEEL_DIAMETER * GEAR_RATIO) / TICKS_PER_REV; // circumference over ticks

    /*
    One way to accurately find these lengths is to pivot the robot (this will only work on symmetrical
    robots) in a perfect 360 circle. You can either do this by hand, or with the IMU.
    Output the raw tick counts of each encoder to telemetry and record them. This is the circumference
    of a circle in encoder ticks. Then divide the values by 2π and multiply by TICKS_TO_CM. This will give
    you the "radius" of the circle the encoder travelled in CM. The "radius" is the same as your L1/L2/L3.
     */
    private final double L1 = 19.73; // distance of right encoder from the center of rotation in CM
    private final double L2 = 20.34; // distance of left encoder from the center of rotation in CM
    private final double L3 = 19.88; // distance of horizontal encoder from the center of rotation in CM

    /*
    These tuning values are used to further calibrate the odometry pods. There will always be external
    factors such as size tolerances, friction, and assembly errors. These tuning values can found
    by rolling the robot by hand beside a set distance with a measuring tape and comparing the
    theoretical distance with the measured distance as a ratio.
    THEORETICAL DISTANCE / MEASURED DISTANCE
    These tuning values can be further improved on by running multiple tests and measurements and
    getting an average of those results. But bare in mind that this will never 100% correct all
    accuracy errors, only minimize them.
     */
    double X_TUNER = 1;
    double Y_TUNER = 1;

    // for tracking the position of the robot
    private double x, y, theta;
    private double dx, dy, dtheta;

    // for tracking the position updates
    private double lastRightTicks, lastLeftTicks, lastAuxTicks;
    private double currRightTicks, currLeftTicks, currAuxTicks;
    private double dRightTicks, dLeftTicks, dAuxTicks;

    // encoder objects
    public final DcMotor right;
    public final DcMotor left;
    public final DcMotor aux;

    public AhiaOdometrySubsystem(HardwareMap hardwareMap) {
        right = hardwareMap.get(DcMotor.class, "rightOdo");
        left = hardwareMap.get(DcMotor.class, "leftOdo");
        aux = hardwareMap.get(DcMotor.class, "horizontalOdo");

        // directions are subject to change depending on how you assemble the robot
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setDirection(DcMotorSimple.Direction.FORWARD);
        aux.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void reset() {
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        aux.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        x = y = theta = 0;
    }

    public void updatePosition() {
        // finding the total distance (S) encoders have travelled in ticks since the last update
        currRightTicks = right.getCurrentPosition();
        currLeftTicks = left.getCurrentPosition();
        currAuxTicks = aux.getCurrentPosition();

        dRightTicks = currRightTicks - lastRightTicks;
        dLeftTicks = currLeftTicks - lastLeftTicks;
        dAuxTicks = currAuxTicks - lastAuxTicks;

        // finding the changes in position since the last update using the derived movement equations
        dtheta = TICKS_TO_CM * (dRightTicks - dLeftTicks) / (L1 + L2);
        dx = TICKS_TO_CM * (dRightTicks + dLeftTicks) / 2;
        dy = TICKS_TO_CM * dAuxTicks + L3 * dtheta;

        // add the small change in position to the overall position, while also accounting for field orientation
        // NOTE: remember Java Math uses RADIANS, ensure that all theta values are in RAD
        x += dx * Math.cos(theta) - dy * Math.sin(theta);
        y += dx * Math.sin(theta) + dy * Math.cos(theta);
        theta += dtheta;

        // todo include, clip theta in the range of -180 < theta < 180 if over

        // record the previous encoder positions for the next update
        lastRightTicks = currRightTicks;
        lastLeftTicks = currLeftTicks;
        lastAuxTicks = currAuxTicks;
    }

    public double getXPos() {
        return x * X_TUNER;
    }

    public double getYPos() {
        return y * Y_TUNER;
    }

    public double getHeading() {
        return theta;
    }
}