package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.util.Input;

@TeleOp(name = "TeleeleOp")
public class TeleOpp extends LinearOpMode {

    public void runOpMode(){
        Hardware robot = new Hardware();
        Input.initInputs(gamepad1);
        robot.init(hardwareMap);
        robot.follower.startTeleOpDrive(true);

        boolean pressingX = false, gateForwards = false, gateBackwards = false, lBumpPressed = false, pressingY = false, pressingA = false, pressingLBump = false, backPressed = false, intakeIsOn = false, aPressed = false, dpUPressed = false, lTrigPressed = false, xPressed = false, yPressed = false;

        double speedMod = 1;

        double[] gateVoltages = {3.0, 1.5, 1.8};
        double prevVoltage;
        double nextVoltage = 3;
        int gateIndex = 0;

        int gateRevs = 0;

        if(robot.gateLEncoder.getVoltage() > 3.0) robot.gateL.setPosition(1);
        else robot.gateL.setPosition(0.5);

        waitForStart();

        while(opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = (gamepad1.left_trigger > 0) ? gamepad1.left_trigger :
                    (gamepad1.right_trigger > 0) ? -gamepad1.right_trigger : 0;
            double turning = -gamepad1.right_stick_x;
            robot.follower.setTeleOpDrive(forward * speedMod, strafe * speedMod, turning * speedMod);
            robot.follower.update();

            if (gamepad1.left_bumper && !lBumpPressed) {
                pressingLBump = !pressingLBump;
                lBumpPressed = true;
                if (pressingLBump) speedMod = 0.5;
                else speedMod = 1;
            } else if (!gamepad1.left_bumper) {
                lBumpPressed = false;
            }

            //driver2 controls
            if (gamepad2.back && !backPressed) {
                backPressed = true;
                intakeIsOn = !intakeIsOn;
                if (intakeIsOn) robot.intakeON();
                else robot.intakeOFF();
            } else if (!gamepad2.back) backPressed = false;

            if (gamepad2.dpad_up && !dpUPressed) {
                dpUPressed = true;
                robot.switchIntakeSpeed();
                intakeIsOn = true;
            } else if (!gamepad2.dpad_up) dpUPressed = false;
            //Input.back.toggle(robot::intakeON, robot::intakeOFF);

            if (gamepad2.x && !xPressed) {
                xPressed = true;
                pressingX = !pressingX;
                double pow = pressingX ? 0.4 : 0;
                robot.flywheel.setPower(pow);
                robot.flywheel2.setPower(pow);
            } else if (!gamepad2.x) xPressed = false;

            /*if(gamepad2.y && !yPressed){
                yPressed = true;
            } else if(!gamepad2.y) yPressed = false;*/

            if (gamepad2.y && !yPressed) {
                gateForwards = true;
                gateIndex += (gateIndex != gateVoltages.length - 1) ? 1 : -(gateVoltages.length - 1);
                yPressed = true;
            } else if (!gamepad2.y) yPressed = false;

            if (gamepad2.a && !aPressed) {
                gateBackwards = true;
                gateIndex -= (gateIndex != 0) ? 1 : -(gateVoltages.length - 1);
                aPressed = true;
            } else if (!gamepad2.a) aPressed = false;

            prevVoltage = nextVoltage;
            nextVoltage = robot.gateLEncoder.getVoltage();
            if (nextVoltage - prevVoltage > 2) {
                gateRevs++;
            } else if (nextVoltage - prevVoltage < -2) {
                gateRevs--;
            }
            telemetry.addData("Revs", gateRevs);
            telemetry.addData("TargetV", gateVoltages[gateIndex]);

            if (gateForwards && robot.gateLEncoder.getVoltage() > gateVoltages[gateIndex] - 0.2 && robot.gateLEncoder.getVoltage() < gateVoltages[gateIndex] + 0.2){
                robot.gateL.setPosition(0.5);
                gateForwards = false;
                gateBackwards = false;
            } else if (gateForwards) robot.gateL.setPosition(1);
            else if(gateBackwards) robot.gateL.setPosition(0);

            telemetry.addData("GATE", robot.gateLEncoder.getVoltage());

            /*if(gamepad2.x && !xPressed){
                xPressed = true;
                robot.aimTurret(telemetry);
            } else if (!gamepad2.x){
                xPressed = false;
            }*/

            /*robot.ramp.setPosition(rampPos);
            if(gamepad2.left_stick_y > 0){
                rampPos += 0.1;
            } else if (gamepad2.left_stick_y < 0){
                rampPos -= 0.1;
            }*/

            telemetry.addData("Flywheel", (robot.flywheel.getVelocity()*60)/28);
            telemetry.addData("Intake Power", robot.intake.getPower());
            telemetry.update();
        }

    }
}