package frc.robot.Auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Drive.Wheel;

public class Balance {
    
    public static double[] balance_drive;
    public static double[] pitch = {0, 180};
    public static double[] roll = {0, 270};

    public static void balanceRobot() {
        pitch[0] = Map.gyro.getPitch() / 45;
        roll[0] = Map.gyro.getRoll() / 45;
        // if (Math.abs(pitch[0]) < 0.1) {
        //     pitch[0] = 0;
        // }
        // if (Math.abs(roll[0]) < 0.1) {
        //     roll[0] = 0;
        // }

        balance_drive = Wheel.addVectors(pitch, roll);
        balance_drive[0] = Math.sqrt(balance_drive[0] / 3);
        if (balance_drive[0] > 0.5) {
            balance_drive[0] = 0.5;
        }

        SmartDashboard.putNumber("balance angle", balance_drive[1]);
        SmartDashboard.putNumber("pitch", pitch[0]);
        SmartDashboard.putNumber("roll", roll[0]);

        Map.swerve.swerveDrive(balance_drive[1] + Map.initialAngle - Map.gyro.getYaw(), balance_drive[0] * 0.75, 0);
    }
}
