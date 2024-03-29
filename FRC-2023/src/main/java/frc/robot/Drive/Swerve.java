package frc.robot.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;

public class Swerve {

    private int[][] ports = {{13, 5, 9}, {2, 6, 10}, {3, 7, 11}, {4, 8, 12}};
    private double[][] sumXY = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
    private double sumX;
    private double sumY;

    private Wheel wheelFR;
    private Wheel wheelFL;
    private Wheel wheelBL;
    private Wheel wheelBR;

    private double cycleTime;
    public double xPos;
    public double yPos;
    public double[] coords = {0, 0};

    public double straightAngle;

    //offset, module numbers, id's for rotate and for drive, rotation, drive, and angle
    public Swerve()
    {
        this.wheelFR = new Wheel("FR", 143, ports[0]);
        this.wheelFL = new Wheel("FL", 86, ports[1]);
        this.wheelBL = new Wheel("BL", 197, ports[2]);
        this.wheelBR = new Wheel("BR", 255, ports[3]);
    }

    public void swerveDrive(double angle, double speed, double twist)
    {
        cycleTime = Timer.getFPGATimestamp() - Map.elapsedTime;
        Map.elapsedTime += cycleTime;

        if (speed > Map.deadband || Math.abs(twist) > Map.deadbandTwist) {
            if (speed > Map.deadband) {
                speed = speed - Map.deadband;
            } else {
                speed = 0;
            }
            if (Math.abs(twist) > Map.deadbandTwist) {
                straightAngle = Map.gyro.getYaw();
                if (twist < 0) {
                    twist = twist + Map.deadbandTwist;
                } else {
                    twist = twist - Map.deadbandTwist;
                }
            } else {
                // twist = 0;
                twist = (straightAngle - Map.gyro.getYaw()) / -360;
            }

            SmartDashboard.putNumber("Speed in swerve", speed);
            
            this.wheelFR.drive(angle, speed, twist, cycleTime);
            this.wheelFL.drive(angle, speed, twist, cycleTime);
            this.wheelBL.drive(angle, speed, twist, cycleTime);
            this.wheelBR.drive(angle, speed, twist, cycleTime);
        } else {
            this.wheelFR.stop(cycleTime);
            this.wheelFL.stop(cycleTime);
            this.wheelBL.stop(cycleTime);
            this.wheelBR.stop(cycleTime);
        }
    }

    public void odometry(double robotAngle)
    {
        sumXY[0] = this.wheelFR.changeInXY;
        sumXY[1] = this.wheelFL.changeInXY;
        sumXY[2] = this.wheelBL.changeInXY;
        sumXY[3] = this.wheelBR.changeInXY;

        sumX = (sumXY[0][0] + sumXY[1][0] + sumXY[2][0] + sumXY[3][0]) / (4 * Map.ticksToInches);
        sumY = (sumXY[0][1] + sumXY[1][1] + sumXY[2][1] + sumXY[3][1]) / (4 * Map.ticksToInches);

        this.xPos += sumX;
        this.yPos += sumY;

        this.coords[0] = (Math.sin(Wheel.toRadians(robotAngle + 90)) + Math.cos(Wheel.toRadians(robotAngle + 90)) - 1) * (500 / Map.ticksToInches);
        this.coords[1] = (Math.sin(Wheel.toRadians(robotAngle)) + Math.cos(Wheel.toRadians(robotAngle)) - 1) * (525 / Map.ticksToInches);

        SmartDashboard.putNumber("x pos", (this.xPos + coords[0]));
        SmartDashboard.putNumber("y pos", (this.yPos - coords[1]));

    }
}
