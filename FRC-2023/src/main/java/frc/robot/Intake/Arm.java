package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

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

        Map.armLifter.configFactoryDefault();
        Map.armLifter2.configFactoryDefault();


        Map.armLifter.setNeutralMode(NeutralMode.Brake);
        Map.armLifter2.setNeutralMode(NeutralMode.Brake);


        Map.armLifter.configNeutralDeadband(0.001);
        Map.armLifter2.configNeutralDeadband(0.001);


        Map.armLifter.configNominalOutputForward(0);
        Map.armLifter.configNominalOutputReverse(0);
        Map.armLifter.configPeakOutputForward(1);
        Map.armLifter.configPeakOutputReverse(-1);


        Map.armLifter2.configNominalOutputForward(0);    
        Map.armLifter2.configNominalOutputReverse(0);      
        Map.armLifter2.configPeakOutputForward(1);
        Map.armLifter2.configPeakOutputReverse(-1);


        Map.armLifter.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        Map.armLifter2.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);


        Map.armLifter2.follow(Map.armLifter);
        Map.armLifter2.setInverted(true);
        //Map.armLifter.setSelectedSensorPosition(0);


        Map.armLifter.config_kP(0, 0.1);
    }

    public static void ShowEncoder() {
        SmartDashboard.putNumber("Arm ticks angle", Map.armLifter.getSelectedSensorPosition());

        // Map.armLifter.set(ControlMode.Position, 0);
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
