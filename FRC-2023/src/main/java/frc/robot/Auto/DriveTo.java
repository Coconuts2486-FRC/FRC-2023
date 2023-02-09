package frc.robot.Auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Drive.Wheel;

public class DriveTo {

    public static PIDController distAutoPID = new PIDController(0.025, 0.0001, 0.0);;
    public static double speedAuto;
    public static double angleAuto;
    public static double distAuto;
    public static double distanceY;
    public static double distanceX;
    
    public static void goToCoords(double x, double y)
    {
        distanceY = Map.swerve.yPos - y;
        distanceX = Map.swerve.xPos - x;
        angleAuto = Map.initialAngle - Map.gyro.getYaw() - Wheel.toDegrees(Math.atan2(Map.swerve.yPos, -Map.swerve.xPos));
        distAuto = Math.sqrt(distanceY * distanceY + distanceX * distanceX);
        speedAuto = Math.abs(distAutoPID.calculate(distAuto / 40));
        if (speedAuto > 0.3) {
            speedAuto = 0.3;
        }

        Map.swerve.swerveDrive(angleAuto, speedAuto, 0);
        Map.swerve.odometry();

        SmartDashboard.putNumber("angle auto", angleAuto);
        SmartDashboard.putNumber("speed auto", speedAuto);
        SmartDashboard.putBoolean("Auto", true);
    }
}
