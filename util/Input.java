package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Input {
    private static Gamepad gamepad1;
    private static Gamepad gamepad2;
    private String name;
    private boolean input;
    private boolean pressing;
    private boolean pressed;

    public Input(String name){
        this.name = name;
        pressing = false;
    }
    public Input(boolean input){
        this.input = input;
    }
    public void press(Runnable body) {
        get();
        if(input && !pressing){
            body.run();
            pressing = true;
        }
        else if(!input) pressing = false;
    }
    public void hold(Runnable body, Runnable elif){
        get();
        if(input) body.run();
        else if(elif != null) elif.run();
    }
    public void toggle(Runnable body, Runnable body2){
        get();
        press(()-> {
            if (!pressed)
            {body.run(); pressed = true;}
            else
            {body2.run();pressed = false;}
        });
    }

    private void get(){
        switch (name) {
            case "a": input = gamepad1.a;
            case "b": input = gamepad1.b;
            case "x": input = gamepad1.x;
            case "y": input = gamepad1.y;
            case "dPadUp": input = gamepad1.dpad_up;
            case "dPadDown": input = gamepad1.dpad_down;
            case "dPadLeft": input = gamepad1.dpad_left;
            case "dPadRight": input = gamepad1.dpad_right;
            case "lBump": input = gamepad1.left_bumper;
            case "rBump": input = gamepad1.right_bumper;
            case "lTrigger": input = gamepad1.left_trigger > 0;
            case "rTrigger": input = gamepad1.right_trigger > 0;
            case "back": input = gamepad1.back;
            case "lStickButton": input = gamepad1.left_stick_button;
            case "rStickButton": input = gamepad1.right_stick_button;

            case "a2": input = gamepad2.a;
            case "b2": input = gamepad2.b;
            case "x2": input = gamepad2.x;
            case "y2": input = gamepad2.y;
            case "dPadUp2": input = gamepad2.dpad_up;
            case "dPadDown2": input = gamepad2.dpad_down;
            case "dPadLeft2": input = gamepad2.dpad_left;
            case "dPadRight2": input = gamepad2.dpad_right;
            case "lBump2": input = gamepad2.left_bumper;
            case "rBump22": input = gamepad2.right_bumper;
            case "lTrigger2": input = gamepad2.left_trigger > 0;
            case "rTrigger2": input = gamepad2.right_trigger > 0;
            case "back2": input = gamepad2.back;
            case "lStickButton2": input = gamepad2.left_stick_button;
            case "rStickButton2": input = gamepad2.right_stick_button;
        }
    }

    public static Input
            a, b, x, y,
            dPadUp, dPadDown, dPadLeft, dPadRight,
            lBump, rBump, lTrigger, rTrigger,
            back, lStickButton, rStickButton;
    public static Input
            a2, b2, x2, y2,
            dPadUp2, dPadDown2, dPadLeft2, dPadRight2,
            lBump2, rBump2, lTrigger2, rTrigger2,
            back2, lStickButton2, rStickButton2;
    public static void initInputs(Gamepad gamepad){
        Input.gamepad1 = gamepad;
        a = new Input("a");
        b = new Input("b");
        x = new Input("x");
        y = new Input("y");
        dPadUp = new Input("dPadUp");
        dPadDown = new Input("dPadDown");
        dPadLeft = new Input("dPadLeft");
        dPadRight = new Input("dPadRight");
        lBump = new Input("lBump");
        rBump = new Input("rBump");
        lTrigger = new Input("lTrigger");
        rTrigger = new Input("rTrigger");
        back = new Input("back");
        lStickButton = new Input("lStickButton");
        rStickButton = new Input("rStickButton");
    }
    public static void initInputs(Gamepad gamepad1, Gamepad gamepad2){
        initInputs(gamepad1);
        Input.gamepad2 = gamepad2;
        a2 = new Input("a2");
        b2 = new Input("b2");
        x2 = new Input("x2");
        y2 = new Input("y2");
        dPadUp2 = new Input("dPadUp2");
        dPadDown2 = new Input("dPadDown2");
        dPadLeft2 = new Input("dPadLeft2");
        dPadRight2 = new Input("dPadRight2");
        lBump2 = new Input("lBump2");
        rBump2 = new Input("rBump2");
        lTrigger2 = new Input("lTrigger2");
        rTrigger2 = new Input("rightTrigger2");
        back2 = new Input("back2") ;
        lStickButton2 = new Input("lStickButton");
        rStickButton2 = new Input("rStickButton");
    }

}