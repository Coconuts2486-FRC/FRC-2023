package frc.robot.Auto;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Intake.Arm;

public class OneConeBalance {

    public static int action;
    public static double savedTime;
    
    public static void driveRoute() {
        if (action == 0) {
            Arm.armExtend(0, 0, false, false, false);
            if ((Timer.getFPGATimestamp() - savedTime) > 1) {
                action += 1;
            }
        } else if (action == 1) {
            if (Arm.liftArm(true, false, false) > -0.12) {
                action += 1;
            }
        } else if (action == 2) {
            if (Arm.armExtend(0.0, 0.0, false, false, true) < 0.007) {
                action += 1;
                savedTime = Timer.getFPGATimestamp();
            }
        } else if (action == 3) {
            Arm.clawOpen(false, false, false, true);
            if ((Timer.getFPGATimestamp() - savedTime) > 2) {
                action += 1;
                savedTime = Timer.getFPGATimestamp();
            }
        } else if (action == 4) {
            Arm.clawOpen(false, true, false, false);
            if ((Timer.getFPGATimestamp() - savedTime) > 2) {
                action += 1;
            }
        }
    }
}
