package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Drive.Swerve;

public class Map {

    /*
     * Driver JOYSTICK buttons and their controls:
     * Axis ----------
     * (0) Left X           - Twist
     * (1) Left Y           -
     * (2) Left Trigger     - Roll intake in
     * (3) Right Trigger    - Roll intake out
     * (4) Right X          - Strafe X
     * (5) Right Y          - Strafe Y
     * Buttons -------
     * (1) A                - Rollers out/in
     * (2) B                - Target with limelight
     * (3) X                - Zeroes robot angle and position
     * (4) Y                - Outtake
     * (5) L Bumper         - Balances robot
     * (6) R Bumper         - Toggle intake out
     * (7) Share            - 
     * (8) Menu             -
     * (9) Left Stick       -
     * (10) Right Stick     -
     * 
     * CODRIVER JOYSTICK buttons and their controls:
     * Axis ----------
     * (0) Left X           -
     * (1) Left Y           -
     * (2) Left Trigger     - Arm intake in
     * (3) Right Trigger    - Arm intake out
     * (4) Right X          -
     * (5) Right Y          -
     * Buttons -------
     * (1) A                - Drive auto path
     * (2) B                - 
     * (3) X                - 
     * (4) Y                - 
     * (5) L Bumper         - Lift arm toggle
     * (6) R Bumper         - Arm extended toggle
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

    public static Compressor pcmCompressor = new Compressor(PneumaticsModuleType.REVPH);
    public static boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();
    public static Solenoid armLiftSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 15);
    public static Solenoid intakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 14);
    public static Solenoid extendSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 13);


    public static double elapsedTime;

    // function for changing the color of the lights
    public static void lightStrip(boolean buttonPress) {
        if (buttonPress) {
            // switch the color and change the variable
            pdp.setSwitchableChannel(Map.lightOn);
            lightOn = !lightOn;
        }
    }
}
