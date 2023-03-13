package frc.robot.Auto.AutoActions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Intake.ClawRollers;
import frc.robot.Intake.PneumaticArm;
import frc.robot.Intake.Rollers;

public class GeneralAutoTimedActions {

    private static boolean timeGot = false;
    private static double startTime;
    
    public static boolean outtaken(double time) {
        if (!timeGot) {
            startTime = Timer.getFPGATimestamp();
            timeGot = true;
        }
        Rollers.roll(0, 0.7);
        if ((Timer.getFPGATimestamp() - startTime) > time) {
            timeGot = false;
            Rollers.roll(0, 0);
            return true;
        }
        return false;
    }

    public static boolean armUpToggle(double time) {
        if (!timeGot) {
            startTime = Timer.getFPGATimestamp();
            PneumaticArm.liftArm(true);
            timeGot = true;
        }
        if ((Timer.getFPGATimestamp() - startTime) > time) {
            timeGot = false;
            return true;
        }
        return false;
    }

    public static boolean armOutToggle(double time) {
        if (!timeGot) {
            startTime = Timer.getFPGATimestamp();
            PneumaticArm.armExtend(true);
            timeGot = true;
        }
        if ((Timer.getFPGATimestamp() - startTime) > time) {
            timeGot = false;
            return true;
        }
        return false;
    }

    public static boolean conePlaced(double time) {
        if (!timeGot) {
            startTime = Timer.getFPGATimestamp();
            timeGot = true;
        }
        ClawRollers.roll(0, 0.7);
        if ((Timer.getFPGATimestamp() - startTime) > time) {
            timeGot = false;
            ClawRollers.roll(0, 0);
            return true;
        }
        return false;
    }

    public static boolean intakeExtendToggle(double time) {
        if (!timeGot) {
            startTime = Timer.getFPGATimestamp();
            PneumaticArm.intakeExtend(true);
            timeGot = true;
        }
        if ((Timer.getFPGATimestamp() - startTime) > time) {
            timeGot = false;
            return true;
        }
        return false;
    }

    public static boolean inttaken(double time) {
        if (!timeGot) {
            startTime = Timer.getFPGATimestamp();
            timeGot = true;
        }
        Rollers.roll(0.1, 0);
        if ((Timer.getFPGATimestamp() - startTime) > time) {
            timeGot = false;
            Rollers.roll(0, 0);
            return true;
        }
        return false;
    }
}
