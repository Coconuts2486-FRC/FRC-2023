package frc.robot.Auto.AutoActions;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Auto.AutoPaths.ConeBalanceCube;

public class Auto {
    
    public static SendableChooser autoChooser = new SendableChooser<>();

    public static void initChooser() {
        autoChooser.setDefaultOption("Blue plexiglass Wall/Red cable protector", 1);
        autoChooser.addOption("Blue cable protector/Red plexiglass Wall", 2);
        autoChooser.addOption("Score cone, drive straight, balance", 3);
        autoChooser.addOption("Score cone, leave community zone, pickup cube", 4);
        autoChooser.addOption("Score cone, drive straight, leave community, balance", 5);
        SmartDashboard.putData("Auto Modes", autoChooser);
    }

    public static int getSelectedAuto() {
        return (int) autoChooser.getSelected();
    }

    public static void runSelectedAuto() {
        if (getSelectedAuto() == 1) {
            ConeBalanceCube.placeConeMidPickupCubeBalance(1);
        } else if (getSelectedAuto() == 2) {
            ConeBalanceCube.placeConeMidPickupCubeBalance(-1);
        } else if (getSelectedAuto() == 3) {
            ConeBalanceCube.placeConeMidStraightBalance(1);
        } else if (getSelectedAuto() == 4) {
            ConeBalanceCube.placeConeMidPickupCube(1);
        } else if (getSelectedAuto() == 5) {
            ConeBalanceCube.placeConeMidStraightBalanceLeaveCommunity(1);
        }
    }
}
