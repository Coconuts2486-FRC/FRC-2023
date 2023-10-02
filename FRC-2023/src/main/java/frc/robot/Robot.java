// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.DriveRoute;
import frc.robot.Auto.AutoActions.Auto;
import frc.robot.Auto.AutoActions.Balance;
import frc.robot.Auto.AutoPaths.ConeBalanceCube;
import frc.robot.Drive.Swerve;
import frc.robot.Intake.ClawRollers;
import frc.robot.Intake.PneumaticArm;
import frc.robot.Intake.Rollers;
import frc.robot.Vision.Limelight;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */

    double x;
    double y;
    double twist;
    double joystickAngle;
    double joystickMag;
    double fieldCenOffset;

    double[] balance_drive = {0, 0};


    @Override
    public void robotInit() {
        Map.initialAngle = Map.gyro.getYaw();
        Map.swerve.straightAngle = Map.gyro.getYaw();
        Rollers.initialize();
        ClawRollers.initialize();
        CameraServer.startAutomaticCapture();
        Map.lightStrip(true);
        Auto.initChooser();
        
    }

    @Override
    public void robotPeriodic() {}

    @Override
    public void autonomousInit() {
        Map.elapsedTime = 0;
        Map.swerve.straightAngle = Map.gyro.getYaw();
        ConeBalanceCube.action = 0;
    }

    @Override
    public void autonomousPeriodic() {
        Auto.runSelectedAuto();
    }

    @Override
    public void teleopInit() {
        Map.elapsedTime = 0;
        Map.swerve.straightAngle = Map.gyro.getYaw();
        PneumaticArm.init();
    }

    @Override
    public void teleopPeriodic() {
        Limelight.track();
        // get joystick stuff

        // x = Map.driver.getRawAxis(4);
        x = Map.driver.getRawAxis(4);
        y = Map.driver.getRawAxis(5);
        // twist to limelight targets with driver B
        twist = (Map.driver.getRawAxis(0) * 0.6) + Limelight.Target(Map.driver.getRawButton(2));
        joystickAngle = 180 + (Math.atan2(y, -x) / (Math.PI) * 180);
        joystickMag = Math.sqrt(x * x + y * y);
        fieldCenOffset = Map.initialAngle - Map.gyro.getYaw();
        SmartDashboard.putNumber("Joystick Angle", joystickAngle);
        SmartDashboard.putNumber("Joystick Magnitude", joystickMag);
        // balance with left bumber otherwise drive
        // if (Map.coDriver.getRawButton(1)) {
        //     DriveRoute.driveSpeed(SimpleZigZag.path1, 2, SimpleZigZag.path1Speed);
        // } else 
        
        if (Map.driver.getRawButton(5)) {
            Balance.balanceRobot();
        }
        // drive straight foreward or back button
        else if (Map.driver.getRawButton(4)){
            if (joystickAngle < 180){
            Map.swerve.swerveDrive( 90 + fieldCenOffset, joystickMag, 0);
            } else if (joystickAngle > 180){
                Map.swerve.swerveDrive( -90 + fieldCenOffset, joystickMag, 0);
            }
        }
        
        
        
        else{
            Map.swerve.swerveDrive(joystickAngle + fieldCenOffset, joystickMag, twist);
            Map.swerve.odometry(fieldCenOffset);
        }


        // zero robot angle and position with driver Y
        if (Map.driver.getRawButton(3)) {
            Map.initialAngle = Map.gyro.getYaw();
            Map.swerve.xPos = 0;
            Map.swerve.yPos = 0;
            DriveRoute.index = 0;
        }

        // light/toggle pipelines with co driver right joystick button
        if (Map.coDriver.getRawButtonPressed(10)) {
            if (Limelight.pipelineOneOn) {
                Limelight.pipelineZero();
                Limelight.pipelineOneOn = false;
            } else {
                Limelight.pipelineOne();
                Limelight.pipelineOneOn = true;
            }
        }

        PneumaticArm.liftArm(Map.coDriver.getRawButtonPressed(6));
        PneumaticArm.armExtend(Map.coDriver.getRawButtonPressed(5));
        PneumaticArm.intakeExtend(Map.driver.getRawButtonPressed(6));

        // intake with driver right bumper
        Rollers.roll(Map.driver.getRawAxis(3) * 0.6, Map.driver.getRawAxis(2));
        // claw intake with coDriver right bumper
        ClawRollers.roll(Map.coDriver.getRawAxis(2), Map.coDriver.getRawAxis(3));
        // toggle lights with codriver A
        Map.lightStrip(Map.coDriver.getRawButtonPressed(8));

    }

    @Override
    public void disabledInit() {
        PneumaticArm.armPistonActive = false;
        PneumaticArm.intakePistonActive = false;
        PneumaticArm.extendPistonActive = false;
    }

    @Override
    public void disabledPeriodic() {}

}
