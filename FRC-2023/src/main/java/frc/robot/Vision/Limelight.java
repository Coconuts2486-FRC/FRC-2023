package frc.robot.Vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {



    public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    
    public static NetworkTableEntry botpose = table.getEntry("botpose");
    
    
    public static NetworkTableEntry tx = table.getEntry("tx");
    public static NetworkTableEntry ty = table.getEntry("ty");
    public static NetworkTableEntry ta = table.getEntry("ta");

    // change pid vlaues here
    public static PIDController vPid = new PIDController(0.003, 0, 0);
   
    public static double xOffset;
    public static boolean pipelineOneOn = false; 

    public static void pipelineZero() {
        NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
        pipelineEntry.setNumber(0);
    
    }

    public static void track () {
        //read values periodically
        // double[] pos = botpose.getDoubleArray(new double[6]);
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);


        //post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);

    }
   
    public static void pipelineOne() {
        NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
        pipelineEntry.setNumber(1);

    }

    public static double Target(boolean buttonPressed) {
        if (buttonPressed) {
            xOffset = tx.getDouble(0.0);
            return (xOffset/60) + vPid.calculate(xOffset);
        } else {
            return 0;
        }

    }
}


