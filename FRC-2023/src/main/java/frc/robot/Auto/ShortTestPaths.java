package frc.robot.Auto;

public class ShortTestPaths {

    public static boolean onBoard = false;
    
    public static void driveThenBalance() {
        if (!onBoard) {
            if (DriveTo.goToCoords(-2200, 0, 1)) {
                onBoard = true;
            }
        } else {
            Balance.balanceRobot();
        }
    }
}
