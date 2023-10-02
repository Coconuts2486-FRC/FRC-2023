// CTRE LIBRARY: https://maven.ctr-electronics.com/release/com/ctre/phoenixpro/PhoenixProAnd5-frc2023-latest.json

package frc.robot.Drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Map;

public class Wheel {
    
    private String id;
    private double offset;
    private TalonFX driveMotor;
    private TalonFX rotateMotor;
    private CANCoder angleSensor;
    private int rotateAngle;

    private double[] strafeVector = {0, 0};
    private double[] rotateVector = {0, 0};
    private double[] driveVector = {0, 0};
    private double[] shortcut = {0, 0};

    private PIDController anglePID;
    private PIDController speedPID;

    private double lastAngle;
    public double currentAngle;
    public double angleChange;
    public double currentSpeed;
    public double[] changeInXY = {0, 0};

    private double calculated;

    //wheel object initiallizer takes in an id, the wheel's offset and a int array of the drive, rotate motors and CANCoder ports
    public Wheel(String id, double offset, int[] ports)
    {
        this.id = id;
        this.offset = offset;

        //create the hardware objects of drive, rotate motors and the CANCoder sensor
        this.driveMotor = new TalonFX(ports[0]);
        this.rotateMotor = new TalonFX(ports[1]);
        this.angleSensor = new CANCoder(ports[2]);

        //set defaults for the hardware objects
        this.driveMotor.setNeutralMode(NeutralMode.Brake);
        this.rotateMotor.setNeutralMode(NeutralMode.Brake);

        //initialize PIDs
    
        //This is what I started with:
         this.anglePID = new PIDController(0.0039, 0.0084, 0.0);
        //this.anglePID = new PIDController(0.0065, 0.000, 0.0002);
        //this.anglePID = new PIDController(0.0065, 0.0001, 0);
        this.speedPID = new PIDController(0.0000075, 0.0001, 0.0);
        // this.speedPID = new PIDController(0.0000075, 0.0001, 0.0);

        //set the rotation angle based on which wheel it is
        switch (this.id) {
            case "FR": this.rotateAngle = 315; break;
            case "FL": this.rotateAngle = 45; break;
            case "BL": this.rotateAngle = 135; break;
            case "BR": this.rotateAngle = 225; break;
        }
    }

    public void drive(double angle, double speed, double twist, double cycleTime)
    {
        //get our strafe and rotate vectors from the inputs
        strafeVector[0] = speed;
        strafeVector[1] = angle;
        this.rotateVector[0] = twist;
        this.rotateVector[1] = this.rotateAngle;

        //get the current and angle and speed of the wheel
        this.lastAngle = this.currentAngle;
        this.currentAngle = this.angleSensor.getAbsolutePosition() - this.offset;
        this.angleChange = this.lastAngle - this.currentAngle;
        this.currentSpeed = this.driveMotor.getSelectedSensorVelocity();

        //combine the strafe and rotate vectors into a drive vector and find the shortest path
        this.driveVector = addVectors(strafeVector, this.rotateVector);
        this.shortcut = elOptimal(this.currentAngle, this.driveVector[1]);

        //set the rotate and drive motors to the calculated velocities
        if (this.id.equals("FR") || this.id.equals("FL")) {
            this.driveMotor.set(ControlMode.PercentOutput, this.shortcut[1] * -this.speedPID.calculate(Math.abs(this.currentSpeed) - (this.driveVector[0] * 24000)));
        } else {
            this.driveMotor.set(ControlMode.PercentOutput, this.shortcut[1] * this.speedPID.calculate(Math.abs(this.currentSpeed) - (this.driveVector[0] * 24000)));
        }
        this.rotateMotor.set(ControlMode.PercentOutput, this.anglePID.calculate(this.shortcut[0]));

        SmartDashboard.putNumber("current speed " + this.id, this.currentSpeed);
        calculated = 1;

        //calculate the x and y speeds of the wheel
        odometry(Map.initialAngle - Map.gyro.getYaw(), cycleTime);
    }

    //make sure the motors stop moving
    public void stop(double cycleTime)
    {
        //set both motors to 0 percent power
        this.driveMotor.set(ControlMode.PercentOutput, 0);
        this.rotateMotor.set(ControlMode.PercentOutput, 0);

        //makes the PID know it's zero so it doesnt go fast after stopping and going slow
        if (Math.abs(calculated) > 0.01) {
            calculated = this.speedPID.calculate(calculated * 24000);
        }

        //take speed so odometry doesn't act stupid
        this.lastAngle = this.currentAngle;
        this.currentAngle = this.angleSensor.getAbsolutePosition() - this.offset;
        this.angleChange = this.lastAngle - this.currentAngle;
        this.currentSpeed = this.driveMotor.getSelectedSensorVelocity();
        odometry(Map.initialAngle - Map.gyro.getYaw(), cycleTime);

    }

    //calculates x and y speeds of the wheel in ticks/100ms
    public void odometry(double robotAngle, double cycleTime)
    {
        if (angleChange > 180.0) {
            angleChange -= 360.0;
        } else if (angleChange < -180.0) {
            angleChange += 360.0;
        }

        SmartDashboard.putNumber("Angle change", angleChange);

        //split the motor speed into the x and y velocities of the wheel, add offset for rotation of the wheel turning the wheel
        this.changeInXY[0] = (splitX(-this.currentSpeed, this.currentAngle - robotAngle) * cycleTime) + splitX(-angleChange / 60, this.currentAngle - robotAngle);
        this.changeInXY[1] = (splitY(-this.currentSpeed, this.currentAngle - robotAngle) * cycleTime) + splitX(-angleChange / 60, this.currentAngle - robotAngle);
       
    }

    // Returns a double array {distance to closest target, reverse variable}
    public static double[] elOptimal(double currentAngle, double targetAngle)
    {
        double[] diffAndReverse = {0, 1};
        double diff = currentAngle - targetAngle;

        // If the target and current are within 90 degrees, just return the difference
        if (diff < 90 && diff >= -90) {
            diffAndReverse[0] = diff;
            return diffAndReverse;
        
        // If it's more than 90 but less than 270 degrees, we return the difference to the opposite angle
        } else if (diff <= 270 && diff >= -270) {
            if (diff > 0) {
                diffAndReverse[0] = diff - 180;
                diffAndReverse[1] = -1;
                return diffAndReverse;

            } else {
                diffAndReverse[0] = diff + 180;
                diffAndReverse[1] = -1;
                return diffAndReverse;
            }
        
        // If it's more than 270 degrees, 
        } else {
            if (diff > 0) {
                diffAndReverse[0] = diff - 360;
                return diffAndReverse;

            } else {
                diffAndReverse[0] = diff + 360;
                return diffAndReverse;
            }
        }
    }

    //Adds two vectors {magnitude, angle}
    public static double[] addVectors(double[] arr1, double[] arr2)
    {
        double magnitudeOne = arr1[0];
        double angleOne = arr1[1];

        double magnitudeTwo = arr2[0];
        double angleTwo = arr2[1];

        //split the vectors into x and y and add the two Xs and two Ys and add them together
        double newX = splitX(magnitudeOne, angleOne) + splitX(magnitudeTwo, angleTwo);
        double newY = splitY(magnitudeOne, angleOne) + splitY(magnitudeTwo, angleTwo);

        //turn the total Xs and Ys into a new vector
        double newAngle = toDegrees(Math.atan2(newY, newX));
        double newMag = Math.sqrt((newX*newX) + (newY * newY));

        double[] resultingVector = {newMag, newAngle};

        return resultingVector;
    }

    //converts degrees to radians
    public static double toRadians(double angle)
    {
        return (angle * Math.PI) / 180;
    }

    //converts radians to degrees
    public static double toDegrees(double angle)
    {
        return (angle * 180) / Math.PI;
    }

    //splits a given vector into it's x and y components and returns the x
    public static double splitX(double magnitude, double angle)
    {
        return magnitude * Math.cos(toRadians(angle));
    }

    //splits a given vector into it's x and y components and returns the y
    public static double splitY(double magnitude, double angle)
    {
        return magnitude * Math.sin(toRadians(angle));
    }
}

    // private double kp;
    // private double ki;
    // private double kd;
    // private double setpoint;
    
    // private double prevError = 0;
    // private double integral = 0;
    
    // // Anti-windup parameters
    // private double integralLimit = 100; // Adjust as needed
    // private double windupGuard = 20;   // Adjust as needed
    
    // public void PIDController(double kp, double ki, double kd, double setpoint) {
    //     this.kp = kp;
    //     this.ki = ki;
    //     this.kd = kd;
    //     this.setpoint = setpoint;
    // }
    
    // public double compute(double current) {
    //     double error = setpoint - current;
        
    //     // Proportional term
    //     double pTerm = kp * error;
        
    //     // Integral term with anti-windup
    //     integral += ki * error;
    //     if (integral > integralLimit) {
    //         integral = integralLimit;
    //     } else if (integral < -integralLimit) {
    //         integral = -integralLimit;
    //     }
        
    //     // Derivative term
    //     double dTerm = kd * (error - prevError);
        
    //     // PID control output
    //     double controlOutput = pTerm + integral + dTerm;
        
    //     // Store current error for the next iteration
    //     prevError = error;
        
    //     return controlOutput;
    // }