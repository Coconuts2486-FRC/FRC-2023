package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Drive.Swerve;

public class Map {

    /*
     * Driver JOYSTICK buttons and their controls:
     * Axis ----------
     * (0) Left X           - Twist
     * (1) Left Y           -
     * (2) Left Trigger     -
     * (3) Right Trigger    -
     * (4) Right X          - Strafe X
     * (5) Right Y          - Strafe Y
     * Buttons -------
     * (1) A                - Zeroes robot angle and position
     * (2) B                - Target with limelight
     * (3) X                - 
     * (4) Y                - 
     * (5) L Bumper         - Balances robot
     * (6) R Bumper         - Toggle intake out and spin rollers
     * (7) Share            -
     * (8) Menu             -
     * (9) Left Stick       -
     * (10) Right Stick     -
     * 
     * CODRIVER JOYSTICK buttons and their controls:
     * Axis ----------
     * (0) Left X           -
     * (1) Left Y           -
     * (2) Left Trigger     - Winch in
     * (3) Right Trigger    - Winch out
     * (4) Right X          -
     * (5) Right Y          -
     * Buttons -------
     * (1) A                - Detected close
     * (2) B                - Open claw
     * (3) X                - Cube close and open
     * (4) Y                - Cone close and open
     * (5) L Bumper         - Arm bottom
     * (6) R Bumper         - Arm top
     * (7) Back             -
     * (8) Start            - Red & Blue lights toggle
     * (9) Left Stick       -
     * (10) Right Stick     - Toggle pipeline/limelight light
     */

    public static double deadband = 0.1;
    public static double deadbandTwist = 0.1;
    public static double ticksToInches = 56.5;
    
    public static Joystick driver = new Joystick(0);
    public static Joystick coDriver = new Joystick(1);

    public static Swerve swerve = new Swerve();

    public static PowerDistribution pdp = new PowerDistribution(1, PowerDistribution.ModuleType.kRev);
    public static boolean lightOn = true;

    public static Pigeon2 gyro = new Pigeon2(40);
    public static double initialAngle;

    public static Servo linearActuatorLeft = new Servo(0);
    public static Servo linearActuatorRight = new Servo(1);
    public static double intakePos = 0.4;
    public static Servo intakeServoLeft = new Servo(2);
    public static Servo intakeServoRight = new Servo(3);
    public static double clawPos = 1;

    public static TalonSRX winch = new TalonSRX(32);
    public static TalonFX armLifter = new TalonFX(30);
    public static TalonFX armLifter2 = new TalonFX(31);

    public static DigitalInput topLimit = new DigitalInput(0);
    public static DigitalInput bottomLimit = new DigitalInput(1);

    public static double elapsedTime;

    // function for changing the color of the lights
    public static void lightStrip(boolean buttonPress) {
        if (buttonPress) {
            // switch the color and change the variable
            pdp.setSwitchableChannel(Map.lightOn);
            SmartDashboard.putBoolean("Switchable Channel", pdp.getSwitchableChannel());
            lightOn = !lightOn;
        }
    }
}
