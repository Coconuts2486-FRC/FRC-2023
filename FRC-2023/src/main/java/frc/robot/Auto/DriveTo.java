package frc.robot.Auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Drive.Wheel;

public class DriveTo {

    //create a pid controller for controlling the driving speed going to coordinates
    public static PIDController distAutoPID = new PIDController(0.001, 0.0, 0.0);
    public static double speedAuto;
    public static double angleAuto;
    public static double distAuto;
    public static double distanceY;
    public static double distanceX;
    
    //function to drive the swerve to an x & y coordinates fieldcentric using a pid
    public static boolean goToCoordsPID(double x, double y, double cutoff)
    {
        //get the distance from the target to the current position
        distanceX = x - (Map.swerve.xPos + Map.swerve.coords[0]);
        distanceY = y - (Map.swerve.yPos - Map.swerve.coords[1]);
        //using the x & y distance, get the angle to the target, and the distance using pythagoras theorem
        angleAuto = Wheel.toDegrees(Math.atan2(distanceY, distanceX));
        distAuto = Math.sqrt(distanceY * distanceY + distanceX * distanceX);
        //then calculate the speed using the pid
        speedAuto = Math.abs(distAutoPID.calculate(distAuto));
        SmartDashboard.putNumber("speed auto", speedAuto);
        if (speedAuto > 0.4) {
            speedAuto = 0.4;
        }

        //drive the swerve at the calculated speed and angle, then calculate odometry
        Map.swerve.swerveDrive(angleAuto + Map.initialAngle - Map.gyro.getYaw(), speedAuto, 0);
        Map.swerve.odometry(Map.initialAngle - Map.gyro.getYaw());

        SmartDashboard.putNumber("angle auto", angleAuto);
        SmartDashboard.putNumber("speed auto actual", speedAuto);
        SmartDashboard.putNumber("dist auto", distAuto);

        //return true when the distance is less than 100 ticks
        if (distAuto < cutoff) {
            return true;
        }
        return false;

    }

    public static boolean goToCoordsSpeed(double x, double y, double cutoff, double speedAuto)
    {
        //get the distance from the target to the current position
        distanceX = x - (Map.swerve.xPos + Map.swerve.coords[0]);
        distanceY = y - (Map.swerve.yPos - Map.swerve.coords[1]);
        //using the x & y distance, get the angle to the target, and the distance using pythagoras theorem
        angleAuto = Wheel.toDegrees(Math.atan2(distanceY, distanceX));
        distAuto = Math.sqrt(distanceY * distanceY + distanceX * distanceX);
        SmartDashboard.putNumber("speed auto", speedAuto);
        if (speedAuto > 0.5) {
            speedAuto = 0.5;
        }

        //drive the swerve at the calculated speed and angle, then calculate odometry
        Map.swerve.swerveDrive(angleAuto + Map.initialAngle - Map.gyro.getYaw(), speedAuto, 0);
        Map.swerve.odometry(Map.initialAngle - Map.gyro.getYaw());

        SmartDashboard.putNumber("angle auto", angleAuto);
        SmartDashboard.putNumber("speed auto actual", speedAuto);
        SmartDashboard.putNumber("dist auto", distAuto);

        //return true when the distance is less than 100 ticks
        if (distAuto < cutoff) {
            return true;
        }
        return false;

    }
    
}
