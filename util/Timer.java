package org.firstinspires.ftc.teamcode.util;

public class Timer {
    private long startTime;
    public Timer(){
        restart();
    }
    public void restart(){
        this.startTime = System.currentTimeMillis();
    };

    public long elapsedSeconds(){
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public void atTarget(long targetTimeSec, Runnable body){
        if(elapsedSeconds() >= targetTimeSec){
            body.run();
        }
    }
}
