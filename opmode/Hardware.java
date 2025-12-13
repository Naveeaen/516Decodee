package org.firstinspires.ftc.teamcode.opmode;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.util.VeloPIDFController;
import org.firstinspires.ftc.teamcode.util.RobotConstants;

public class Hardware {

    //constructor
    private static Hardware myInstance = null;

    public static Hardware getInstance() {
        if (myInstance == null)
            myInstance = new Hardware();
        return myInstance;
    }

    //define hardware stuff (variables)
    Servo gateR, ramp, gateL;
    DcMotorEx intake, flywheel, flywheel2, turret;
    AnalogInput gateLEncoder;
    public double robotX, robotY, robotH;
    private int intakeSpeedIndex = 0;
    private final double[] speeds = {0.25, 0.5, 0.75, 1};
    int gateAngle = 270;

    VeloPIDFController flywheelController;
    Follower follower;

    //initialize hardware stuff (method)
    public void init(HardwareMap hwMap){
        follower = Constants.createFollower(hwMap);

        intake = hwMap.get(DcMotorEx.class, "em2");
        intake.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorEx.Direction.REVERSE);

        flywheel = hwMap.get(DcMotorEx.class, "cm1");
        flywheel.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        flywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheel.setDirection(DcMotorEx.Direction.REVERSE);
        flywheel.getMotorType().setTicksPerRev(28);
        flywheel.getMotorType().setMaxRPM(6000);
        flywheel.getMotorType().setAchieveableMaxRPMFraction(0.966);

        flywheel2 = hwMap.get(DcMotorEx.class, "cm0");
        flywheel2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        flywheel2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        flywheelController = new VeloPIDFController(RobotConstants.flywheelP, RobotConstants.flywheelI, RobotConstants.flywheelD);

        turret = hwMap.get(DcMotorEx.class, "em3");
        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        turret.getMotorType().setMaxRPM(117);
        turret.getMotorType().setTicksPerRev(1425.1);

        gateL = hwMap.get(Servo.class, "es5");
        gateLEncoder = hwMap.get(AnalogInput.class, "ea0");

        gateR = hwMap.get(Servo.class, "cs1");

        //ramp = hwMap.get(Servo.class, "cs2");
    }

    public void recalculateRobotPos(){
        Pose position = follower.getPose();
        robotX = position.getX();
        robotY = position.getY();
        robotH = position.getHeading();
    }

    public void runFlywheel(){
        recalculateRobotPos();
        // position of the target in inches from the bottom left corner
        double targetX = 12;
        double targetY = 132;
        double distanceToTarget = Math.sqrt(
                Math.pow(robotX-targetX, 2) + Math.pow(robotY-targetY, 2));

        // insert kinematics here
        double targetTicksPerRev = 3750;
        double targetTicksPerSecond = targetTicksPerRev * flywheel.getMotorType().getMaxRPM() / 60;
        //flywheel.setPower(
        //        flywheelController.calcPower(flywheel.getVelocity(), targetTicksPerSecond));
        flywheel.setPower(1);
    }
    public void aimTurret(Telemetry telemetry){
        recalculateRobotPos();
        double targetY = 132;
        double targetX = 12;
        double targetHeading = Math.atan2(targetY-robotY, targetX-robotX) - Math.PI/2 - robotH;
        double gearRatio = 100.0/12;
        double targetRevolutions = targetHeading/(2*Math.PI) * gearRatio;
        double targetTicks = turret.getMotorType().getTicksPerRev() * targetRevolutions;
        turret.setTargetPosition((int)targetTicks);
        telemetry.addData("Revolutions", targetRevolutions);
        telemetry.addData("Turret Target",targetTicks);
        telemetry.addData("Turret Pos", turret.getCurrentPosition());
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turret.setPower(1);
    }
    public void aimRamp(){
        recalculateRobotPos();
    }
    public void intakeON(){
        intake.setPower(speeds[intakeSpeedIndex]);
    }
    public void intakeOFF(){
        intake.setPower(0);
    }
    public void switchIntakeSpeed(){
        intakeSpeedIndex += (intakeSpeedIndex != speeds.length-1) ? 1 : -(speeds.length-1);
        intakeON();
    }
}