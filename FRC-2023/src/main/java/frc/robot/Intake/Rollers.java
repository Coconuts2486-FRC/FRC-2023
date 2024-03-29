package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Rollers {

    public static VictorSPX intakeMotor1 = new VictorSPX(20);
    public static VictorSPX intakeMotor2 = new VictorSPX(21);

    public static void initialize() {
        intakeMotor1.setInverted(true);
    }
    
    public static void roll(double out, double in) {
        intakeMotor1.set(VictorSPXControlMode.PercentOutput, (in - out));
        intakeMotor2.set(VictorSPXControlMode.PercentOutput, -(in - out));
    }
}
