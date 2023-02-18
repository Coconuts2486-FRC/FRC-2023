package frc.robot.Auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveRoute {

    public static int index = 0;
    public static double[] target = {0, 0};

    //drive the ZigZag path using a PID to go to each point along the curve
    public static void drivePID(double cutoff) {
        if (DriveTo.goToCoordsPID(SimpleZigZag.path[index][0], SimpleZigZag.path[index][1], cutoff)) {
            index = index + 1;
            if (index == SimpleZigZag.path.length) {
                index = 0;
            }
        }
        SmartDashboard.putNumber("target x", SimpleZigZag.path[index][0]);
        SmartDashboard.putNumber("target y", SimpleZigZag.path[index][1]);
    }

}
