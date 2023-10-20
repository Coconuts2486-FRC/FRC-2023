// package frc.robot.Auto.Paths;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.Map;
// import frc.robot.Drive.Wheel;

// public class RotationTesting{
//     public Wheel wheelFR;
//     public Wheel wheelFL;
//     public Wheel wheelBL;
//     public Wheel wheelBR;
    
//     double angle = 0;
//     double speed = 0;
//     double twist = 0.2;
//     double cycleTime = 1;

//     public void DriveRotationAuto(double[] coords){
//     this.wheelFR.drive(angle, speed, twist, cycleTime);
//     this.wheelFL.drive(angle, speed, twist, cycleTime);
//     this.wheelBL.drive(angle, speed, twist, cycleTime);
//     this.wheelBR.drive(angle, speed, twist, cycleTime);
//     }
// }