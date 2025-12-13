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

        boolean gateForwards = false, gateBackwards = false, lBumpPressed = false, pressingY = false, pressingA = false, pressingLBump = false, backPressed = false, pressingBack = false, aPressed = false, dpUPressed = false, lTrigPressed = false, xPressed = false, yPressed = false;

        double speedMod = 1;

        double[] gateVoltages = {2.6, 1.5, 0.0};
        int gateIndex = 0;

        int gateRevs = 0;

        if(robot.gateLEncoder.getVoltage() > 3.0) robot.gateL.setPosition(1);
        else robot.gateL.setPosition(0.5);

        waitForStart();

        while(opModeIsActive()){
            double forward = -gamepad1.left_stick_y;
            double strafe = (gamepad1.left_trigger  > 0) ? gamepad1.left_trigger :
                            (gamepad1.right_trigger > 0) ? -gamepad1.right_trigger : 0;
            double turning = -gamepad1.right_stick_x;
            robot.follower.setTeleOpDrive(forward*speedMod, strafe*speedMod, turning*speedMod);
            robot.follower.update();

            if(gamepad1.left_bumper && !lBumpPressed) {
                pressingLBump = !pressingLBump;
                lBumpPressed = true;
                if(pressingLBump) speedMod = 0.5;
                else speedMod = 1;
            } else if(!gamepad1.left_bumper){
                lBumpPressed = false;
            }

            //driver2 controls
            if(gamepad2.back && !backPressed){
                backPressed = true;
                pressingBack = !pressingBack;
                if(pressingBack) robot.intakeON();
                else robot.intakeOFF();
            } else if(!gamepad2.back) backPressed = false;

            if(gamepad2.dpad_up && !dpUPressed){
                dpUPressed = true;
                robot.switchIntakeSpeed();
            } else if(!gamepad2.dpad_up) dpUPressed = false;
            //Input.back.toggle(robot::intakeON, robot::intakeOFF);

            if(gamepad2.a && !aPressed){
                aPressed = true;
                pressingA = !pressingA;
                double pow = pressingA?1:0;
                robot.flywheel.setPower(pow);
                robot.flywheel2.setPower(pow);
            } else if(!gamepad2.a) aPressed = false;

            /*if(gamepad2.y && !yPressed){
                yPressed = true;
            } else if(!gamepad2.y) yPressed = false;*/

            if(gamepad2.y && !yPressed) {
                gateForwards = true;
                gateIndex++;
                yPressed = true;
            } else if (!gamepad2.y) yPressed = false;

            if(gamepad2.a && !aPressed) {
                gateBackwards = true;
            }

            if(gateForwards && robot.gateLEncoder.getVoltage() > gateVoltages[gateIndex]) {
                robot.gateL.setPosition(1);
            } else if (gateForwards){
                robot.gateL.setPosition(0.5);
                gateForwards = false;
            }
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

            telemetry.addData("Flywheel", (robot.flywheel.getVelocity()*60)/robot.flywheel.getMotorType().getTicksPerRev());
            telemetry.addData("Intake Power", robot.intake.getPower());
            telemetry.update();
        }

    }
}