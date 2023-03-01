// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Arrays;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.Balance;
import frc.robot.Auto.DriveRoute;
import frc.robot.Auto.ShortTestPaths;
import frc.robot.Intake.Arm;
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
        Arm.initialize();

        CameraServer.startAutomaticCapture();
    }

    @Override
    public void robotPeriodic() {}

    @Override
    public void autonomousInit() {
        Map.elapsedTime = 0;
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        Map.elapsedTime = 0;
    }

    @Override
    public void teleopPeriodic() {

        // get joystick stuff
        x = Map.driver.getRawAxis(4) / 3;
        y = Map.driver.getRawAxis(5) / 3;
        // twist to limelight targets with driver B
        twist = (Map.driver.getRawAxis(0) / 2) + Limelight.Target(Map.driver.getRawButton(2));
        joystickAngle = 180 + (Math.atan2(y, -x) / (Math.PI) * 180);
        joystickMag = Math.sqrt(x * x + y * y);
        fieldCenOffset = Map.initialAngle - Map.gyro.getYaw();

        // balance with left bumber otherwise drive
        if (Map.driver.getRawButton(5)) {
            Balance.balanceRobot();
        } else {
            Map.swerve.swerveDrive(joystickAngle + fieldCenOffset, joystickMag, twist);
            Map.swerve.odometry(fieldCenOffset);
        }

        // zero robot angle and position with driver Y
        if (Map.driver.getRawButton(4)) {
            Map.initialAngle = Map.gyro.getYaw();
            Map.swerve.xPos = 0;
            Map.swerve.yPos = 0;
            DriveRoute.index = 0;
            ShortTestPaths.onBoard = false;
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

        // intake extend with driver right bumper
        Rollers.intakeExtend(Map.driver.getRawButtonPressed(6));
        // arm extend with co driver triggers
        Arm.armExtend(Map.coDriver.getRawAxis(2), Map.coDriver.getRawAxis(3));
        // arm lift with co driver bumpers
        Arm.liftArm(Map.coDriver.getRawButton(6), Map.coDriver.getRawButton(5));
        // toggle lights with codriver A
        Map.lightStrip(Map.coDriver.getRawButtonPressed(8));

    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {
        if (Map.driver.getRawButtonPressed(9)) {
            Map.driver.setRumble(RumbleType.kLeftRumble, 1);
            Map.driver.setRumble(RumbleType.kRightRumble, 1);
            if (Arrays.equals(Map.driverMode, Map.normalMode)) {
                Map.driverMode = Map.kateMode;
                SmartDashboard.putString("Drive mode", "Kate");
            } else {
                Map.driverMode = Map.normalMode;
                SmartDashboard.putString("Drive mode", "Normal");
            }
        } else if (Map.driver.getRawButtonReleased(9)) {
            Map.driver.setRumble(RumbleType.kLeftRumble, 0);
            Map.driver.setRumble(RumbleType.kRightRumble, 0);
        }
    }

}
