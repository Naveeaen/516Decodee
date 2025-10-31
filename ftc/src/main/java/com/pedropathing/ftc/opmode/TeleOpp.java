package com.pedropathing.ftc.opmode;

import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.util.Input;
import com.pedropathing.ftc.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleeleOp")
public class TeleOpp extends LinearOpMode {

    public void runOpMode(){
        Mecanum wheels = new Mecanum(hardwareMap, new MecanumConstants());
        wheels.startTeleopDrive(true);

        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        Input.initInputs(gamepad1, gamepad2);

        Timer turretTime = new Timer();

        waitForStart();

        while(opModeIsActive()){
            Input.rBump2.press(robot::runArm);
            Input.a.press(turretTime::restart);
            turretTime.atTarget(5, robot::aim);

        }


    }
}
