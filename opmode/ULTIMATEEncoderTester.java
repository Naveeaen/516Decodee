package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.util.Input;

@TeleOp(name = "EncoderTester")

public class ULTIMATEEncoderTester extends LinearOpMode {
    Servo servo;
    DcMotorEx motor;

    private final String[] servoNames = {
            "cs0", "cs1", "cs2", "cs3", "cs4", "cs5", "cs6",
            "es0", "es1", "es2", "es3", "es4", "es5", "es6"
    };
    private final String[] motorNames = {
            "cm0", "cm1", "cm2", "cm3",
            "em0", "em1", "em2", "em3"
    };
    String[] names;
    private int index;
    boolean reversed;
    double servoPos;
    int motorPos;
    double motorPow;

    private enum State {
        SELECTING,
        TESTING
    }
    private enum Test {
        MOTOR,
        SERVO
    }
    private static State state;
    private static Test test;
    public void SELECTING() {
        state = State.SELECTING;
    }
    public void MOTOR() {
        test = Test.MOTOR;
        names = motorNames;
        reversed = false;
        motorPow = 1;
        motor = hardwareMap.get(DcMotorEx.class, motorNames[index]);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void SERVO(){
        test = Test.SERVO;
        names = servoNames;
        servo = (ServoImplEx)hardwareMap.get(Servo.class, servoNames[index]);
    }
    public void TESTING(){
        state = State.TESTING;

    }

    public void runOpMode() {
        Input.initInputs(gamepad1);
        SELECTING();

        waitForStart();

        while(opModeIsActive()){
            Input.back.toggle(
                    this::TESTING,
                    this::SELECTING
            );
            switch (state) {
                case SELECTING:
                    selection();
                case TESTING:
                    switch(test) {
                        case MOTOR:
                            motorPowerTest();
                        case SERVO:
                            servoTest();
                    }
            }
            telemetry.update();
        }
    }
    public void selection(){
        Input.a.toggle(
                this::MOTOR,
                this::SERVO
        );
        Input.lTrigger.press(() -> index = (index!=0)? index-1 : names.length-1);
        Input.rTrigger.press(() -> index = (index!=names.length-1)? index+1:0);

        telemetry.addLine("PROGRAM SELECTION MENU");
        telemetry.addLine("press a to toggle type and L/R Trigger to change index");
        telemetry.addLine("Select " + names[index] + "?");
        telemetry.addLine("press back to proceed");
    }

    public void servoTest(){
        Input.x.press(() -> servoPos -= 0.01);
        Input.b.press(() -> servoPos += 0.01);
        servo.setPosition(servoPos);

        telemetry.addLine("SERVO POSITION TESTER");
        telemetry.addLine("Testing " + servoNames[index] + ". (press back to change selection)");
        telemetry.addLine("press b to increase and x to decrease pos");
        telemetry.addLine("Position " + servo.getPosition());
        telemetry.addLine("Target " + servoPos);
    }

    public void motorPowerTest(){
        Input.dPadUp.hold(() -> motor.setPower(motorPow), () ->
        Input.dPadDown.hold(() -> motor.setPower(-motorPow), () ->
                motor.setPower(0)));

        Input.y.press(() -> motorPow = 0.75);
        Input.b.press(() -> motorPow = 0.5);
        Input.x.press(() -> motorPow = 0.25);

        Input.lBump.press(() -> reversed = !reversed);
        motor.setDirection(reversed ? DcMotorEx.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);

        telemetry.addLine("MOTOR POWER TESTER");
        telemetry.addLine("Testing " + motorNames[index] + ". (press back to change selection)");
        telemetry.addLine("press dpad up for forward and dpad down for reverse");
        telemetry.addLine("press a for 100% speed, y for 75%, b for 50%, and x for 25%");
        telemetry.addLine("Motor is " + (reversed ? "" : "NOT") + " reversed! (press lBump to toggle)");
        telemetry.addLine("Position " + motor.getCurrentPosition());
        telemetry.addLine("Amp Draw: " + motor.getCurrent(CurrentUnit.AMPS));
        telemetry.addLine("Power: " + motor.getPower());
        telemetry.addLine("Velocity: " + motor.getVelocity());
        telemetry.update();
    }
    public void motorPositionTest(){

    }
}