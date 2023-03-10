package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// import frc.robot.Map;

public class Rollers {

    public static VictorSPX intakeMotor1 = new VictorSPX(20);
    public static VictorSPX intakeMotor2 = new VictorSPX(21);
    
    public static void roll(double in, double out) {
        intakeMotor1.set(VictorSPXControlMode.PercentOutput, (in - out));
        intakeMotor2.set(VictorSPXControlMode.PercentOutput, -(in - out));
    }

    public static void intakeExtend(boolean buttonPress, boolean outtake, boolean onlyOut) {
       //
    }
}
