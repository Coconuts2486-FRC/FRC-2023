package frc.robot.Auto.AutoPaths;

import frc.robot.Map;
import frc.robot.Auto.DriveRoute;
import frc.robot.Auto.AutoActions.Balance;
import frc.robot.Auto.AutoActions.GeneralAutoTimedActions;
import frc.robot.Auto.Paths.DriveOverBalance;
import frc.robot.Auto.Paths.DriveToBalance;
import frc.robot.Auto.Paths.DriveToCube;
import frc.robot.Auto.Paths.StraightBalance;
import frc.robot.Intake.Rollers;

public class ConeBalanceCube {
    
    public static int action = 0;

    public static void placeConeMidOverChargeStationPickupCubeBalance(int positive) {
    
        if (action == 0) {
            if (GeneralAutoTimedActions.armUpToggle(1.3)) {
                action += 1;
            }
        } else if (action == 1) {
            if (GeneralAutoTimedActions.armOutToggle(0.6)) {
                action += 1;
            }
        } else if (action == 2) {
            if (GeneralAutoTimedActions.conePlaced(0.3)) {
                action += 1;
            }
        } else if (action == 3) {
            if (GeneralAutoTimedActions.armOutToggle(0.3)) {
                action += 1;
            }
        } else if (action == 4) {
            if (GeneralAutoTimedActions.armUpToggle(0.8)) {
                action += 1;
            }
        } else if (action == 5) {
            if (GeneralAutoTimedActions.intakeExtendToggle(0.3)) {
                action += 1;
            }
        }  else if (action == 6) {
            Rollers.roll(0.2, 0);
            if (DriveRoute.driveSpeed(DriveOverBalance.pathB, 3, 65, positive)) {
                action += 1;
            }
        } else if (action == 7) {
            Rollers.roll(0,0);
            Balance.balanceRobot();
        }
    
    
    }
    public static void placeConeMidPickupCube(int positive) {
        if (action == 0) {
            if (GeneralAutoTimedActions.armUpToggle(1.3)) {
                action += 1;
            }
        } else if (action == 1) {
            if (GeneralAutoTimedActions.armOutToggle(1)) {
                action += 1;
            }
        } else if (action == 2) {
            if (GeneralAutoTimedActions.conePlaced(0.5)) {
                action += 1;
            }
        } else if (action == 3) {
            if (GeneralAutoTimedActions.armOutToggle(1)) {
                action += 1;
            }
        } else if (action == 4) {
            if (GeneralAutoTimedActions.armUpToggle(1)) {
                action += 1;
            }
        } else if (action == 5) {
            if (GeneralAutoTimedActions.intakeExtendToggle(0.3)) {
                action += 1;
            }
        }  else if (action == 6) {
            Rollers.roll(0.2, 0);
            if (DriveRoute.driveSpeed(DriveToCube.path, 3, 80, positive)) {
                action += 1;
                Map.initialAngle = Map.gyro.getYaw();
                Map.swerve.xPos = 0;
                Map.swerve.yPos = 0;
                DriveRoute.index = 0;
            }
        } else if (action == 7) {
            Map.swerve.swerveDrive(0, 0, 0);
            Rollers.roll(0, 0);
        }

    }

    public static void placeConeMidPickupCubeBalance(int positive) {
        if (action == 0) {
            if (GeneralAutoTimedActions.armUpToggle(1.3)) {
                action += 1;
            }
        } else if (action == 1) {
            if (GeneralAutoTimedActions.armOutToggle(1)) {
                action += 1;
            }
        } else if (action == 2) {
            if (GeneralAutoTimedActions.conePlaced(0.5)) {
                action += 1;
            }
        } else if (action == 3) {
            if (GeneralAutoTimedActions.armOutToggle(0.7)) {
                action += 1;
            }
        } else if (action == 4) {
            if (GeneralAutoTimedActions.armUpToggle(0.7)) {
                action += 1;
            }
        } else if (action == 5) {
            if (GeneralAutoTimedActions.intakeExtendToggle(0.3)) {
                action += 1;
            }
        }  else if (action == 6) {
            Rollers.roll(0.2, 0);
            if (DriveRoute.driveSpeed(DriveToCube.path, 3, 65, positive)) {
                action += 1;
                Map.initialAngle = Map.gyro.getYaw();
                Map.swerve.xPos = 0;
                Map.swerve.yPos = 0;
                DriveRoute.index = 0;
            }
        } else if (action == 7) {
            if (GeneralAutoTimedActions.intakeExtendToggle(0.3)) {
                action += 1;
            }
        } else if (action == 8) {
            if (DriveRoute.driveSpeed(DriveToBalance.path, 3, 80, positive)) {
                action += 1;
            }
        } else if (action == 9) {
            Balance.balanceRobot();
        }

    }

    public static void placeConeMidStraightBalance(int positive) {
        if (action == 0) {
            if (GeneralAutoTimedActions.armUpToggle(1.3)) {
                action += 1;
            }
        } else if (action == 1) {
            if (GeneralAutoTimedActions.armOutToggle(0.6)) {
                action += 1;
            }
        } else if (action == 2) {
            if (GeneralAutoTimedActions.conePlaced(0.3)) {
                action += 1;
            }
        } else if (action == 3) {
            if (GeneralAutoTimedActions.armOutToggle(0.3)) {
                action += 1;
            }
        } else if (action == 4) {
            if (GeneralAutoTimedActions.armUpToggle(0.8)) {
                action += 1;
            }
        } else if (action == 5) {
            if (GeneralAutoTimedActions.intakeExtendToggle(0.3)) {
                action += 1;
            }
        }  else if (action == 6) {
            if (DriveRoute.driveSpeed(StraightBalance.path, 3, 65, positive)) {
                action += 1;
            }
        } else if (action == 7) {
            Balance.balanceRobot();
        }
        
    }

    public static void placeConeMidStraightBalanceLeaveCommunity(int positive) {
        if (action == 0) {
            if (GeneralAutoTimedActions.armUpToggle(1.3)) {
                action += 1;
            }
        } else if (action == 1) {
            if (GeneralAutoTimedActions.armOutToggle(0.6)) {
                action += 1;
            }
        } else if (action == 2) {
            if (GeneralAutoTimedActions.conePlaced(0.3)) {
                action += 1;
            }
        } else if (action == 3) {
            if (GeneralAutoTimedActions.armOutToggle(0.3)) {
                action += 1;
            }
        } else if (action == 4) {
            if (GeneralAutoTimedActions.armUpToggle(0.3)) {
                action += 1;
            }
        }  else if (action == 5) {
            if (DriveRoute.driveSpeed(StraightBalance.pathFar, 3, 85, positive)) {
                action += 1;
                Map.initialAngle = Map.gyro.getYaw();
                Map.swerve.xPos = 0;
                Map.swerve.yPos = 0;
                DriveRoute.index = 0;
            }
        } else if (action == 6) {
            Map.swerve.swerveDrive(0, 0, 0);
            if (GeneralAutoTimedActions.waitTime(1)) {
                action += 1;
            }
        } else if (action == 7) {
            if (DriveRoute.driveSpeed(StraightBalance.pathFarBack, 3, 85, positive)) {
                action += 1;
            }
        } else if (action == 8) {
            Balance.balanceRobot();
        }
    }
}
