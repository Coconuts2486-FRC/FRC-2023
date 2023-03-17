package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class ClawRollers {
    
    public static TalonFX clawSpinner = new TalonFX(31);

    public static void initialize() {
        clawSpinner.setNeutralMode(NeutralMode.Brake);
    }

    public static void roll(double out, double in) {
        clawSpinner.set(ControlMode.PercentOutput, (in - out));
    }
}
