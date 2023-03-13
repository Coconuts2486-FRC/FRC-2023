package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class ClawRollers {
    
    public static TalonFX clawSpinner = new TalonFX(31);

    public static void roll(double in, double out) {
        clawSpinner.set(ControlMode.PercentOutput, (in - out));
    }
}
