package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Drive.Swerve;

public class Map {

    /*
     * JOYSTICK buttons and their controls:
     * (1) A - Drive temporary auto path
     * (2) B - Winch
     */
    
    public static Joystick driver = new Joystick(0);
    public static Swerve swerve = new Swerve();

    public static Pigeon2 gyro = new Pigeon2(40);
    public static double initialAngle;
    
    public static double deadband = 0.1;

    public static double elapsedTime;
}
