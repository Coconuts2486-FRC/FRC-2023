package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
import frc.robot.Drive.Swerve;

public class Map {

    /*
     * Driver JOYSTICK buttons and their controls:
     * (1) A                - Drive temporary auto path
     * (2) B                - Winch extends arm
     * (3) X                - Arm goes to top position
     * (4) Y                - Reverses arm movents: extend -> retract, top -> bottom
     * (5) L Bumper         - Balances robot
     * (6) R Bumper         - Zeroes odometry and angle
     * (7) Share            - Nothing
     * (8) Menu             - Nothing
     * (9) Left Stick       - Nothing
     * (10) Right Stick     - Nothing
     * 
     * CODRIVER JOYSTICK buttons and their controls:
     * (1)
     * (2)
     * (3)
     * (4)
     * (5)
     * (6)
     * (7)
     * (8)
     * (9)
     * (10)
     */

    public static double deadband = 0.1;
    public static double ticksToInches = 56.5;
    
    public static Joystick driver = new Joystick(0);
    public static Joystick coDriver = new Joystick(1);


    public static Swerve swerve = new Swerve();

    public static PowerDistribution pdp = new PowerDistribution(0, PowerDistribution.ModuleType.kRev);
    public static boolean isRedAlliance = true;

    public static Pigeon2 gyro = new Pigeon2(40);
    public static double initialAngle;

    public static double elapsedTime;

    public static int[] driverMode = {0, 1, 4};
    public static int[] normalMode = {0, 1, 4};
    public static int[] kateMode = {4, 5, 0};

    // function for changing the color of the lights
    public static void changePDPLights(boolean switchColor) {
        // do nothing if the button isn't pressed
        if (!switchColor) {
            return;
        }
        // switch the color and change the variable
        pdp.setSwitchableChannel(isRedAlliance);
        isRedAlliance = !isRedAlliance;
    }
}
