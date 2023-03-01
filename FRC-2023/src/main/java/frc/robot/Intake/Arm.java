package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;

public class Arm {

    public static double armLifterSpeed;
    public static double lastPos;
    public static double currPos;
    public static double setPos;

    //initialize the two motors and set them to break mode so they don't drift
    public static void initialize() {
        Map.winch.setNeutralMode(NeutralMode.Brake);

        //Cube Close
        Map.linearActuatorLeft.setBounds(1.75, 1.7, 1.355, 1.05, 1.0);
        Map.linearActuatorRight.setBounds(1.75, 1.7, 1.355, 1.05, 1.0);

        //Cone Close
        // Map.linearActuatorLeft.setBounds(2.0, 1.9, 1.5, 1.1, 1.0);
        // Map.linearActuatorRight.setBounds(2.0, 1.9, 1.5, 1.1, 1.0);

        Map.armLifter.setNeutralMode(NeutralMode.Brake);
        Map.armLifter2.setNeutralMode(NeutralMode.Brake);

        Map.armLifter2.follow(Map.armLifter);
        Map.armLifter2.setInverted(true);
    }

    //extend the arm or retract the arm - NO LIMITS CURRENTLY -
    public static void ArmExtend(double forward, double reverse) {
        if (forward > 0.1 || reverse > 0.1) {
            Map.winch.set(ControlMode.PercentOutput, (forward - 0.1) - (reverse - 0.1));
        } else {
            Map.winch.set(ControlMode.PercentOutput, 0);
        }
    }

    //move the arm either to the top or bottom
    public static void LiftArm(boolean top, boolean bottom) {

        currPos = Map.armLifter.getSelectedSensorPosition();

        //if we are trying to move to the top, the set pos should be 0, otherwise 130,000
        if (top) {
            setPos = -102000;
        } else if (bottom) {
            setPos = 0;
        } else {
            setPos = currPos;
        }

        // calculate the speed of the motor and maximize it at 40%
        double armSpeed = (setPos - currPos) / 80000;
        if (armSpeed > 0.3) {
            armSpeed = 0.3;
        }

        armSpeed = armSpeed * 0.7;

        if (Map.topLimit.get() || Map.bottomLimit.get()) {
            Map.armLifter.set(ControlMode.PercentOutput, 0);
        } else {
            Map.armLifter.set(ControlMode.PercentOutput, armSpeed);
        }  

        SmartDashboard.putNumber("Arm speed", armSpeed);
        SmartDashboard.putNumber("Arm angle", currPos);
    }


    public static void IntakeExtend(boolean move) {
        if (!move) {
            return;
        }
        Map.intakeServoLeft.set(Map.intakePos + 0.5);
        Map.intakeServoRight.set(-Map.intakePos + 0.5);
        Map.intakePos = -Map.intakePos;

        SmartDashboard.putNumber("ServoAngle Left", Map.intakeServoLeft.getPosition());
        SmartDashboard.putNumber("ServoAngle Right", Map.intakeServoRight.getPosition());
    }
    
    public static void ClawOpen() {
        Map.linearActuatorLeft.set(Map.clawPos);
        Map.linearActuatorRight.set(Map.clawPos);
    }
}
