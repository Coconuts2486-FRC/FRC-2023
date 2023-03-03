package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;
import frc.robot.Vision.ColorSensor;

public class Arm {

    public static double armLifterSpeed;
    public static double lastPos;
    public static double currPos;
    public static double setPos;

    public static double extendedLow = 0;
    public static double extendedMid = -7700;
    public static double extendedHigh = -34900;
    public static double extendedLast;
    public static double currExtension;

    public static PIDController extensionPID = new PIDController(0.00014, 0, 0);

    //initialize the two motors and set them to break mode so they don't drift
    public static void initialize() {

        Map.winch.setNeutralMode(NeutralMode.Brake);
        Map.armLifter.setNeutralMode(NeutralMode.Brake);
        Map.armLifter2.setNeutralMode(NeutralMode.Brake);

        Map.armLifter2.follow(Map.armLifter);
        Map.armLifter2.setInverted(true);

        Map.linearActuatorLeft.setBounds(1.75, 1.7, 1.355, 1.05, 1.0);
        Map.linearActuatorRight.setBounds(1.75, 1.7, 1.355, 1.05, 1.0);
    }

    //extend the arm or retract the arm - NO LIMITS CURRENTLY -
    public static double armExtend(double in, double out, boolean low, boolean mid, boolean high) {

        currExtension = Map.winch.getSelectedSensorPosition();

        if ((in > 0.1 || out > 0.1)) {
            Map.winch.set(ControlMode.PercentOutput, (in - 0.1) - (out - 0.1));
            extendedLast = currExtension;
        } else if (low) {
            Map.winch.set(ControlMode.PercentOutput, extensionPID.calculate(currExtension - extendedLow));
            extendedLast = extendedLow;
        } else if (mid) {
            Map.winch.set(ControlMode.PercentOutput, extensionPID.calculate(currExtension - extendedMid));
            extendedLast = extendedMid;
        } else if (high) {
            Map.winch.set(ControlMode.PercentOutput, extensionPID.calculate(currExtension - extendedHigh));
            extendedLast = extendedHigh;
        } else {
            Map.winch.set(ControlMode.PercentOutput, extensionPID.calculate(currExtension - extendedLast));
        }

        SmartDashboard.putNumber("Extension value", currExtension);
        SmartDashboard.putNumber("PID value", extensionPID.calculate(extendedHigh - currExtension));

        return extensionPID.calculate(extendedHigh - currExtension);
    }

    //move the arm either to the top or bottom
    public static double liftArm(boolean top, boolean bottom, boolean override) {

        currPos = Map.armLifter.getSelectedSensorPosition();
        SmartDashboard.putNumber("Arm ticks angle", currPos);

        //if we are trying to move to the top, the set pos should be 0, otherwise 130,000
        if (top) {
            setPos = -116000;
        } else if (bottom) {
            setPos = 3500;
        } else {
            setPos = currPos;
        }

        // calculate the speed of the motor and maximize it at 40%
        double armSpeed = (setPos - currPos) / 80000;
        if (armSpeed > 0.3) {
            armSpeed = 0.3;
        }

        armSpeed = armSpeed * 0.7;

        if (Map.topLimit.get() && armSpeed < 0) {
            Map.armLifter.set(ControlMode.PercentOutput, 0);
        } else if (Map.bottomLimit.get() && armSpeed > 0) {
            Map.armLifter.set(ControlMode.PercentOutput, 0);
        } else {
            Map.armLifter.set(ControlMode.PercentOutput, armSpeed);
        }  

        if (Map.bottomLimit.get()) {
            Map.armLifter.setSelectedSensorPosition(0);
        }

        if (override && !Map.bottomLimit.get()) {
            Map.armLifter.set(ControlMode.PercentOutput, 0.1);
        } else if (override && Map.bottomLimit.get()) {
            Map.armLifter.set(ControlMode.PercentOutput, 0);
        }

        return armSpeed;

    }
    
    public static void clawOpen(boolean cubePress, boolean conePress, boolean closeDetected, boolean open) {
        if (open) {
            Map.linearActuatorLeft.setSpeed(0);
            Map.linearActuatorRight.setSpeed(0);
            return;
        }

        if (closeDetected) {
            if (ColorSensor.objectInClaw() == "Cube") {
                cubePress = true;
                conePress = false;
            } else if (ColorSensor.objectInClaw() == "Cone") {
                cubePress = false;
                conePress = true;
            }
        }

        if (cubePress) {
            Map.linearActuatorLeft.setBounds(1.75, 1.7, 1.355, 1.05, 1.0);
            Map.linearActuatorRight.setBounds(1.75, 1.7, 1.355, 1.05, 1.0);
            Map.linearActuatorLeft.setSpeed(1);
            Map.linearActuatorRight.setSpeed(1);
        } else if (conePress) {
            Map.linearActuatorLeft.setBounds(2.0, 1.9, 1.5, 1.1, 1.0);
            Map.linearActuatorRight.setBounds(2.0, 1.9, 1.5, 1.1, 1.0);
            Map.linearActuatorLeft.setSpeed(1);
            Map.linearActuatorRight.setSpeed(1);
        }
        
    }

    public static void toggleBrake(boolean buttonPress) {
        if (buttonPress) {
            if (Map.armIsBroke) {
                Map.armLifter.setNeutralMode(NeutralMode.Coast);
                Map.armLifter2.setNeutralMode(NeutralMode.Coast);
                Map.armIsBroke = false;
            } else {
                Map.armLifter.setNeutralMode(NeutralMode.Brake);
                Map.armLifter2.setNeutralMode(NeutralMode.Brake);
                Map.armIsBroke = true;
            }
        }

        SmartDashboard.putBoolean("Brake/Coast", Map.armIsBroke);
    }
}
