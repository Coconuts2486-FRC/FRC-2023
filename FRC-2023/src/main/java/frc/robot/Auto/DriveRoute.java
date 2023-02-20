package frc.robot.Auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveRoute {

    public static int index = 0;
    public static double speedAuto;
    public static double[] target = {0, 0};

    public static double distBetweeenPoints(double[] coords1, double[] coords2) {
        double distx = coords2[0] - coords1[0];
        double disty = coords2[1] - coords1[1];
        return Math.sqrt(distx * distx + disty * disty);
    }

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

    //drive the ZigZag path using a PID to go to each point along the curve
    public static void driveSpeed(double cutoff) {
        speedAuto = 0;
        for (int i = -3; i < 5; i++) {
            if (index + i > 0 && index + i + 1 < SimpleZigZag.path.length) {
                speedAuto += distBetweeenPoints(SimpleZigZag.path[index + i], SimpleZigZag.path[index + i + 1]);
            }
        }
         
        speedAuto = speedAuto / 2400;

        if (DriveTo.goToCoordsSpeed(SimpleZigZag.path[index][0], SimpleZigZag.path[index][1], cutoff, speedAuto)) {
            index = index + 1;
            if (index == SimpleZigZag.path.length) {
                index = 0;
            }
        }
        SmartDashboard.putNumber("target x", SimpleZigZag.path[index][0]);
        SmartDashboard.putNumber("target y", SimpleZigZag.path[index][1]);
    }

}
