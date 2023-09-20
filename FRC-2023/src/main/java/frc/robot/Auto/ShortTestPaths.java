package frc.robot.Auto;

import frc.robot.Auto.AutoActions.Balance;

public class ShortTestPaths {

    public static boolean onBoard = false;
    
    public static void driveThenBalance() {
        if (!onBoard) {
            if (DriveTo.goToCoordsPID(-40, 0, 100)) {
                onBoard = true;
            }
        } else {
            Balance.balanceRobot();
        }
    }
}
