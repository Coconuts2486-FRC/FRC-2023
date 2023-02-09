// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.DriveTo;
import frc.robot.Drive.Wheel;
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

  double[] balance_drive = {0, 0};

  @Override
  public void robotInit() {
    Map.initialAngle = Map.gyro.getYaw();
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
    x = Map.driver.getRawAxis(0) / 3;
    y = Map.driver.getRawAxis(1) / 3;
    twist = Map.driver.getRawAxis(4) / 4;
    joystickAngle = 180 + (Math.atan2(y, -x) / (Math.PI) * 180);
    joystickMag = Math.sqrt(x * x + y * y);

    if (Map.driver.getPOV() != -1) {
      DriveTo.goToCoords(0, 0);
    } else if (!Map.driver.getRawButton(5)) {
      Map.swerve.swerveDrive(joystickAngle, joystickMag, twist);
      Map.swerve.odometry();
    } else {
      double[] pitch = {Map.gyro.getPitch() / 45, 180};
      if (pitch[0] < 0) {
        pitch[0] = -pitch[0];
        pitch[1] = 0;
      }
      double[] roll = {Map.gyro.getRoll() / 45, 270};
      if (roll[0] < 0) {
        roll[0] = -roll[0];
        roll[1] = 90;
      }
      balance_drive = Wheel.addVectors(pitch, roll);

      SmartDashboard.putNumber("balance angle", balance_drive[0]);
      Map.swerve.swerveDrive(balance_drive[1] - Map.initialAngle - Map.gyro.getYaw(), Math.sqrt(balance_drive[0] / 8), 0);
    }
    if (Map.driver.getRawButton(6)) {
      Map.initialAngle = Map.gyro.getYaw();
    }
    if (Map.driver.getRawButton(3)) {
      Map.swerve.xPos = 0;
      Map.swerve.yPos = 0;
    }

    Rollers.roll(Map.driver.getRawAxis(2), Map.driver.getRawAxis(3));


  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
