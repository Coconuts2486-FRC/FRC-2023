package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Drive.Swerve;

public class Map {

    /*
     * JOYSTICK buttons and their controls:
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
     */
    
    public static Joystick driver = new Joystick(0);
    public static Swerve swerve = new Swerve();

    public static Pigeon2 gyro = new Pigeon2(40);
    public static double initialAngle;
    
    public static double deadband = 0.1;

    public static double elapsedTime;

    public static int[] driverMode = {0, 1, 4};
    public static int[] normalMode = {0, 1, 4};
    public static int[] kateMode = {4, 5, 0};
}
