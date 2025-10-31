package com.pedropathing.ftc.util;

public class VeloPIDFController {
    double p, i, d, f;
    double currentVelocity;
    double targetVelocity;
    double[] power = {0, 0};
    double[] error = {0, 0, 0};
    double[] time = {0, 0};
    public VeloPIDFController(double p, double d, double i, double f){
        calcPIDS(p, d, i, f);
    }
    public void calcPIDS(double p, double d, double i, double f){
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
    }
    public double calcPower(int measuredVelo, int targetVelo){

        error[2] = error[1];
        error[1] = error[0];
        error[0] = measuredVelo - targetVelo;

        power[1] = power[0];
        power[0] = power[1] +
                        + p*(error[1] - error[0])
                        + i*(error[1]*dt)
                        + d*(error[0] - 2*error[1] - error[2]);

        return power[0];
    }
}