package org.firstinspires.ftc.teamcode.util;

public class VeloPIDFController {
    double p, i, d, f;
    double[] power = {0, 0};
    double[] error = {0, 0, 0};
    double[] time = {0, 0};
    public VeloPIDFController(double p, double i, double d, double f){
        calcPIDS(p, i, d, f);
    }
    public VeloPIDFController(double p, double i, double d){
        calcPIDS(p, i, d, 0);
    }
    public void calcPIDS(double p, double i, double d, double f){
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
    }
    public double calcPower(double measuredVelo, double targetVelo){
        time[1] = time[0];
        time[0] = System.currentTimeMillis();
        double dt = time[0]-time[1];

        error[2] = error[1];
        error[1] = error[0];
        error[0] = measuredVelo - targetVelo;
        double de = error[1]-error[0];

        power[1] = power[0];
        power[0] = power[1] +
                        + p*(de)
                        + i*(error[0]*dt)
                        + d*(error[0] - 2*error[1] + error[2])/dt;

        return power[0];
    }
}