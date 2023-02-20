// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.Balance;
import frc.robot.Auto.DriveRoute;
import frc.robot.Auto.DriveTo;
import frc.robot.Auto.ShortTestPaths;
import frc.robot.Auto.SimpleZigZag;
import frc.robot.Intake.Arm;
import frc.robot.Intake.Rollers;

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
    x = Map.driver.getRawAxis(Map.driverMode[0]) / 3;
    y = Map.driver.getRawAxis(Map.driverMode[1]) / 3;
    twist = Map.driver.getRawAxis(Map.driverMode[2]) / 4;
    joystickAngle = 180 + (Math.atan2(y, -x) / (Math.PI) * 180);
    joystickMag = Math.sqrt(x * x + y * y);
    fieldCenOffset = Map.initialAngle - Map.gyro.getYaw();

    if (Map.driver.getPOV() != -1) {
      DriveTo.goToCoordsPID(0, 0, 100);
    } else if (Map.driver.getRawButton(1)) {
      DriveRoute.driveSpeed(SimpleZigZag.path3, 300);
    } else if (Map.driver.getRawButton(5)) {
      Balance.balanceRobot();
    } else {
      Map.swerve.swerveDrive(joystickAngle + fieldCenOffset, joystickMag, twist);
      Map.swerve.odometry(fieldCenOffset);
    }

    if (Map.driver.getRawButton(6)) {
      Map.initialAngle = Map.gyro.getYaw();
      Map.swerve.xPos = 0;
      Map.swerve.yPos = 0;
      DriveRoute.index = 0;
      ShortTestPaths.onBoard = false;
    }
    
    
    Arm.LiftArm(!Map.driver.getRawButton(4), Map.driver.getRawButton(3));
    Arm.ArmExtend(Map.driver.getRawButton(2), Map.driver.getRawButton(4));
    Rollers.roll(Map.driver.getRawAxis(2), Map.driver.getRawAxis(3));

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
