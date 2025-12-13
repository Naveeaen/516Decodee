package org.firstinspires.ftc.teamcode.opmode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@Disabled
@TeleOp(name = "ServoTester")

public class ServoTester extends LinearOpMode {

    public void runOpMode() {
        Hardware robot = Hardware.getInstance();
        robot.init(hardwareMap);

        waitForStart();

        //double position = robot.gateL.getPosition();
        double actualPosition = 0;
        //double position2 = robot.gateL.getPosition();
        double actualPosition2 = 0;
        boolean bumpR = false;
        boolean bumpL = false;
        boolean triggerR = false;
        boolean triggerL = false;

        while(opModeIsActive()) {
            //telemetry.addData("position", position);
            telemetry.addData("actualposition", actualPosition);
            telemetry.update();

            //left open pos = 0.22
            //left close pos = 0.099999999999
            //right open pos = 0.24
            //right close pos = 0.39
            //wrist floor = 0.169
            //wrist angled = 0.34
            //launcher open = 0.198
            //launcher max = 0.39
            //trigger init = 0.623
            //trigger pull = 1

            //robot.gateL.setPosition(position);
            //actualPosition = robot.gateL.getPosition();
            if(gamepad1.right_bumper && !bumpR) {
                //position += .01;
                bumpR = true;
            } else if (!gamepad1.right_bumper) {
                bumpR = false;
            }
            if(gamepad1.left_bumper && !bumpL) {
                //position -= .01;
                bumpL = true;
            } else if (!gamepad1.left_bumper) {
                bumpL = false;
            }

        }
    }
}