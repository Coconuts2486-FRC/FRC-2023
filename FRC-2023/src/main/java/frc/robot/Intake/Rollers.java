package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Map;

public class Rollers {

    public static VictorSPX intakeMotor1 = new VictorSPX(20);
    public static VictorSPX intakeMotor2 = new VictorSPX(21);
    
    public static void roll(double in, double out) {
        intakeMotor1.set(VictorSPXControlMode.PercentOutput, (in - out));
        intakeMotor2.set(VictorSPXControlMode.PercentOutput, -(in - out));
    }

    public static void intakeExtend(boolean buttonPress) {
        if (buttonPress) {
            if (Map.intakePos == 0.4) {
                Map.intakePos = 0;
                roll(0, 0);
            } else {
                Map.intakePos = 0.4;
                roll(0.9, 0);
            }
        }

        Map.intakeServoLeft.set(Map.intakePos - 0.07);
        Map.intakeServoRight.set((1 - Map.intakePos) + 0.1);
    }
}
