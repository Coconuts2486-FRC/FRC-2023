package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {

    public static TalonSRX Winch = new TalonSRX(31);
    public static TalonFX ArmLifter = new TalonFX(30);

    public static double armSpeed;

    public static void initialize() {
        Winch.setNeutralMode(NeutralMode.Brake);
        ArmLifter.setNeutralMode(NeutralMode.Brake);
    }

    public static void ArmLiftSet(boolean top, boolean running) {
        SmartDashboard.putNumber("Arm angle", ArmLifter.getSelectedSensorPosition());

        if (!running) {
            ArmLifter.set(ControlMode.PercentOutput, 0);
            return;
        }

        if (top) {
            armSpeed = Math.pow(ArmLifter.getSelectedSensorPosition() / 80000, 0.5);
            if (armSpeed > 0.4) {
                armSpeed = 0.4;
            }

            SmartDashboard.putNumber("Arm speed", armSpeed);
            ArmLifter.set(ControlMode.PercentOutput, -armSpeed);
        } else {
            armSpeed = Math.pow((130000 - ArmLifter.getSelectedSensorPosition()) / 80000, 0.3);
            if (armSpeed > 0.4) {
                armSpeed = 0.4;
            }

            ArmLifter.set(ControlMode.PercentOutput, armSpeed);
        }

    }

    public static void ArmExtend(boolean on, boolean reverse) {
        if (!on) {
            Winch.set(ControlMode.PercentOutput, 0);
            return;
        }
        if (reverse) {
            Winch.set(ControlMode.PercentOutput, 0.7);
        } else {
            Winch.set(ControlMode.PercentOutput, -0.5);
        }
    }
    
}
