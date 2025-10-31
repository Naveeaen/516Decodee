package com.pedropathing.ftc.opmode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {

    //constructor
    private static Hardware myInstance = null;

    public static Hardware getInstance() {
        if (myInstance == null)
            myInstance = new Hardware();
        return myInstance;
    }

    //define hardware stuff (variables)
    Servo gateL, gateR, ramp;
    DcMotorEx intakeL, intakeR, flywheel, turret;


    //initialize hardware stuff (method)
    public void init(HardwareMap hwMap){
        intakeL = hwMap.get(DcMotorEx.class, "em0"); // congifure deviceName in driver station as 'cm0' (control hub, motor port 0)
        intakeL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intakeL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        intakeR = hwMap.get(DcMotorEx.class, "em1");
        intakeR.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intakeR.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        flywheel = hwMap.get(DcMotorEx.class, "em2");
        flywheel.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        flywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        turret = hwMap.get(DcMotorEx.class, "em3");
        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        gateL = hwMap.get(Servo.class, "cs0"); // (control hub, servo port 0)

        gateR = hwMap.get(Servo.class, "cs1");

        ramp = hwMap.get(Servo.class, "cs2");
    }

    //method for arm
    public void armTo(int pos, double pow){
        arm.setTargetPosition(pos);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setPower(pow);
    }
    public void armTo(int pos){
        armTo(pos, 1);
    }
    public void runArm(){

    }
    public void aim(){}
}