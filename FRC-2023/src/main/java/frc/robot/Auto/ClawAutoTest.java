package frc.robot.Auto;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Intake.Arm;
import frc.robot.Intake.Rollers;

public class ClawAutoTest {
        
    public static double savedTime;
    public static int action = 0;

    public static void clawAuto() {
        if (action == 0) {
            savedTime = Timer.getFPGATimestamp();
            action += 1;
        } else if (action == 1) {
            Arm.clawOpen(false, false, false, true);
            if ((Timer.getFPGATimestamp() - savedTime) > 4) {
                action += 1;
                savedTime = Timer.getFPGATimestamp();
            }
        } else if (action == 2) {
            Rollers.intakeExtend(true, false);
            action += 1;
            savedTime = Timer.getFPGATimestamp();
        } else if (action == 3) {
            if ((Timer.getFPGATimestamp() - savedTime) > 2) {
                action += 1;
            }
        } else if (action == 4) {
            Rollers.intakeExtend(true, false);
            action += 1;
            savedTime = Timer.getFPGATimestamp();
        } else if (action == 5) {
            if ((Timer.getFPGATimestamp() - savedTime) > 2) {
                action += 1;
            }
        } else if (action == 6) {
            Arm.clawOpen(false, true, false, false);
            if ((Timer.getFPGATimestamp() - savedTime) > 4) {
                action += 1;
            }
        }
    }
}
