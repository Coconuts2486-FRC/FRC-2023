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
    public static double lastPos;
    public static double currPos;
    public static double setPos;

    //initialize the two motors and set them to break mode so they don't drift
    public static void initialize() {
        Winch.setNeutralMode(NeutralMode.Brake);
        ArmLifter.setNeutralMode(NeutralMode.Brake);
    }

    //move the arm either to the top or bottom
    public static void LiftArm(boolean top, boolean running) {
        currPos = ArmLifter.getSelectedSensorPosition();
        SmartDashboard.putNumber("Arm angle", currPos);

        //if the button to move it isn't pressed, keep the arm at the position we set it at, exit the function
        if (!running) {
            armSpeed = Math.pow(lastPos - currPos / 80000, 0.5);
            if (armSpeed > 0.4) {
                armSpeed = 0.4;
            }
            ArmLifter.set(ControlMode.PercentOutput, armSpeed);
            return;
        }

        //if we are trying to move to the top, the set pos should be 0, otherwise 130,000
        if (top) {
            setPos = 0;
        } else {
            setPos = 130000;
        }

        // calculate the speed of the motor and maximize it at 40%
        armSpeed = Math.pow(setPos - currPos / 80000, 0.5);
        if (armSpeed > 0.4) {
            armSpeed = 0.4;
        }

        //set the motor to the calculated speed and display it to the smart dashboard
        ArmLifter.set(ControlMode.PercentOutput, armSpeed);
        SmartDashboard.putNumber("Arm speed", armSpeed);

    }

    //extend the arm or retract the arm - NO LIMITS CURRENTLY -
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
