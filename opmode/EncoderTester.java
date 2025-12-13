package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


//@Disabled
@TeleOp(name = "EncoderTester")

public class EncoderTester extends LinearOpMode {

    public void runOpMode() {
        Hardware robot = new Hardware();
        robot.init(hardwareMap);
        waitForStart();

        int position = 0;
        int actualPosition = 0;
        double Sposition = 0;
        double actualSPosition = 0;
        boolean pressinga = false;
        boolean pressingy = false;
        boolean pressingx = false;
        boolean pressingb = false;
        boolean bumpR = false;
        boolean bumpL = false;
        double speed = 0.5;

        while(opModeIsActive()) {

            /*if(gamepad1.x && !pressingx){

            } else if(!gamepad1.x){
                pressingx = false;
                robot.setMotorPowers(0, 0, 0, 0);
            }
            if(gamepad1.b && !pressingb){
                robot.setMotorPowers(0, 1, 0, 0);
            } else if(!gamepad1.b){
                pressingb = false;
                robot.setMotorPowers(0, 0, 0, 0);
            }
            if(gamepad1.a && !pressinga) {
                robot.setMotorPowers(0, 0, 1, 0);
            } else if (!gamepad1.a) {
                pressinga = false;
                robot.setMotorPowers(0, 0, 0, 0);
            }

            if(gamepad1.y && !pressingy) {
                robot.setMotorPowers(0, 0, 0, 1);
            } else if (!gamepad1.y) {
                pressingy = false;
                robot.setMotorPowers(0, 0, 0, 0);
            }*/

            //x = leftfront reverse
            //y = rightfront
            //b = left back
            //a = rightrear reverse


        }
    }
}