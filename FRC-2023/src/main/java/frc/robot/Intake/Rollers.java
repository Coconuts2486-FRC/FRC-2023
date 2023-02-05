package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Rollers {

    public static VictorSPX intakeMotor1 = new VictorSPX(20);
    
    public static void roll(double lt, double rt) {
        intakeMotor1.set(VictorSPXControlMode.PercentOutput, (rt - lt) / 1.5);
    }
}
