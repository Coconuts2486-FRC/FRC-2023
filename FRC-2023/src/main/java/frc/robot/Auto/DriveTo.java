package frc.robot.Auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Drive.Wheel;

public class DriveTo {

    public static PIDController distAutoPID = new PIDController(0.027, 0.0001, 0.0);;
    public static double speedAuto;
    public static double angleAuto;
    public static double distAuto;
    public static double distanceY;
    public static double distanceX;
    
    public static boolean goToCoords(double x, double y)
    {
        distanceX = x - (Map.swerve.xPos + Map.swerve.coords[0]);
        distanceY = y - (Map.swerve.yPos - Map.swerve.coords[1]);
        angleAuto = Wheel.toDegrees(Math.atan2(distanceY, distanceX));
        distAuto = Math.sqrt(distanceY * distanceY + distanceX * distanceX);
        speedAuto = Math.abs(distAutoPID.calculate(distAuto / 40));
        if (speedAuto > 0.3) {
            speedAuto = 0.3;
        }

        Map.swerve.swerveDrive(angleAuto, speedAuto, 0);
        Map.swerve.odometry(Map.initialAngle - Map.gyro.getYaw());

        SmartDashboard.putNumber("angle auto", angleAuto);
        SmartDashboard.putNumber("speed auto", speedAuto);
        SmartDashboard.putBoolean("Auto", true);

        if (speedAuto < 0.05) {
            return true;
        }
        return false;
    }

    
}
