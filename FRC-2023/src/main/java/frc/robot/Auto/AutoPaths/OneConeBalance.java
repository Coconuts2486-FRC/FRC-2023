package frc.robot.Auto.AutoPaths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Intake.Rollers;

public class OneConeBalance {

    public static int action = 0;
    public static double savedTime = 0;
    
    public static void driveRoute() {
        if (action == 0) {
            Rollers.roll(0, 0.7);
            if ((Timer.getFPGATimestamp() - savedTime) > 1) {
                action += 1;
            }
        }
    }
}
 