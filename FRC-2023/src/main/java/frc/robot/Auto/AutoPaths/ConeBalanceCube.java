package frc.robot.Auto.AutoPaths;

import frc.robot.Auto.AutoActions.GeneralAutoTimedActions;

public class ConeBalanceCube {
    
    private static int action = 0;

    public static void placeConeMid() {
        if (action == 0) {
            if (GeneralAutoTimedActions.armUpToggle(1)) {
                action += 1;
            }
        } else if (action == 1) {
            if (GeneralAutoTimedActions.armOutToggle(1)) {
                action += 1;
            }
        } else if (action == 2) {
            if (GeneralAutoTimedActions.conePlaced(1)) {
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
            //drive to cube
            action += 1;
        } else if (action == 6) {
            if (GeneralAutoTimedActions.intakeExtendToggle(1)) {
                action += 1;
            }
        } else if (action == 7) {
            if (GeneralAutoTimedActions.inttaken(1)) {
                action += 1;
            }
        } else if (action == 8) {
            if (GeneralAutoTimedActions.intakeExtendToggle(1)) {
                action += 1;
            }
        } else if (action == 9) {
            if (GeneralAutoTimedActions.outtaken(1)) {
                action += 1;
            }
        }

    }
}
