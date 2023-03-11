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
    public static void drivePID(double[][] path, double cutoff) {
        if (DriveTo.goToCoordsPID(path[index][0], path[index][1], cutoff)) {
            index = index + 1;
        }
        SmartDashboard.putNumber("target x", path[index][0]);
        SmartDashboard.putNumber("target y", path[index][1]);
    }

    //drive the ZigZag path using a PID to go to each point along the curve
    public static void driveSpeed(double[][] path, double cutoff, int speedVariable) {
        speedAuto = 0;
        for (int i = -3; i < 5; i++) {
            if (index + i > 0 && index + i + 1 < path.length) {
                speedAuto += distBetweeenPoints(path[index + i], path[index + i + 1]);
            }
        }

        SmartDashboard.putNumber("Speed auto before", speedAuto);

        if (DriveTo.goToCoordsSpeed(path[index][0], path[index][1], cutoff, speedAuto, path.length, speedVariable)) {
            index = index + 1;
        }
        SmartDashboard.putNumber("target x", path[index][0]);
        SmartDashboard.putNumber("target y", path[index][1]);
    }

}
