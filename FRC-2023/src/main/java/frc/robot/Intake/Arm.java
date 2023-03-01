package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.Map;
import frc.robot.Vision.ColorSensor;

public class Arm {

    public static double armLifterSpeed;
    public static double lastPos;
    public static double currPos;
    public static double setPos;

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
    public static void armExtend(double in, double out) {
        if (in > 0.1 || out > 0.1) {
            Map.winch.set(ControlMode.PercentOutput, (in - 0.1) - (out - 0.1));
        } else {
            Map.winch.set(ControlMode.PercentOutput, 0);
        }
    }

    //move the arm either to the top or bottom
    public static void liftArm(boolean top, boolean bottom) {

        currPos = Map.armLifter.getSelectedSensorPosition();

        //if we are trying to move to the top, the set pos should be 0, otherwise 130,000
        if (top) {
            setPos = -113000;
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
        } else if (conePress) {
            Map.linearActuatorLeft.setBounds(2.0, 1.9, 1.5, 1.1, 1.0);
            Map.linearActuatorRight.setBounds(2.0, 1.9, 1.5, 1.1, 1.0);
        }

        Map.linearActuatorLeft.setSpeed(1);
        Map.linearActuatorRight.setSpeed(1);
        
    }
}
