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
            armPistonActive = !armPistonActive;
            Map.armLiftSolenoid.set(armPistonActive);
        }

    }

    public static void intakeExtend(boolean intake) {
        if (intake) {
            intakePistonActive = !intakePistonActive;
            Map.intakeSolenoid.set(intakePistonActive);
        }

    }

    public static void armExtend(boolean extend) {
        if (extend) {
            extendPistonActive = !extendPistonActive;
            Map.extendSolenoid.set(extendPistonActive);
        }
    }

}
