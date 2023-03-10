package frc.robot.Intake;

import frc.robot.Map;

public class PneumaticArm {
    private static boolean armPistonActive = false;
    private static boolean intakePistonActive = false;
    private static boolean extendPistonActive = false;

    public static void init() {
        Map.armLiftSolenoid.set(false);
        Map.intakeSolenoid.set(false);
        Map.extendSolenoid.set(false);

    }

    public static void liftArm(boolean lift) {

        if (lift) {

            if (!armPistonActive) {
                Map.armLiftSolenoid.set(true);
                armPistonActive = true;
            } else {
                Map.armLiftSolenoid.set(false);
                armPistonActive = false;
            }

        }

    }

    public static void intakeExtend(boolean intake) {

        if (intake) {
            if (!intakePistonActive) {
                Map.intakeSolenoid.set(true);
                intakePistonActive = true;
            } else {
                Map.intakeSolenoid.set(false);
                intakePistonActive = false;
            }

        }

    }

    public static void armExtend(boolean extend) {
        if (extend) {
            extendPistonActive = !extendPistonActive;
            Map.extendSolenoid.set(extendPistonActive);
        }
    }

}
