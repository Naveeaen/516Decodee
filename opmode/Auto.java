package org.firstinspires.ftc.teamcode.opmode;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.FuturePose;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Timer;

import java.util.Map;

@Autonomous(name = "AutonoMoose")
public class Auto extends LinearOpMode {
    Hardware robot;
    Timer timer;
    int pathState;

    final Map<String, Pose> poses = Map.ofEntries(
            Map.entry("start", new Pose(130, 112, 0)),
            Map.entry("launch", new Pose(113, 112, 45)),
            Map.entry("pickup1Control", new Pose(94, 96)),
            Map.entry("pickup1Start", new Pose(104, 84.5, 0)),
            Map.entry("pickup1End", new Pose(126, 83.5, 0))
    );

    public void followPath(String p1, String p2, String p3){
        robot.follower.followPath(
                robot.follower.pathBuilder()
                        .addPath(new BezierCurve(poses.get(p1), poses.get(p2), poses.get(p3)))
                        .setLinearHeadingInterpolation(poses.get(p1).getHeading(), poses.get(p2).getHeading())
                        .build()
        );
    }
    public void followPath(String p1, String p2){
        robot.follower.followPath(
                robot.follower.pathBuilder()
                        .addPath(new BezierLine(poses.get(p1), poses.get(p2)))
                        .setLinearHeadingInterpolation(poses.get(p1).getHeading(), poses.get(p2).getHeading())
                        .build()
        );
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Hardware();
        robot.init(hardwareMap);

        waitForStart();

        timer.restart();
        pathState = 0;
        robot.follower.setStartingPose(poses.get("start"));
        while(opModeIsActive()){
            robot.follower.update();
            switch (pathState){
                case 0:
                    followPath("start", "launch");
                    robot.runFlywheel();
                    nextPathHold(5);
                case 1:
                    followPath("launch", "pickup1Start", "pickup1Control");
                    nextPath();
                case 2:
                    followPath("pickup1Start", "pickup1End");
                case 3:
                    followPath("pickup1End", "launch");
                case 4:
                    followPath("launch", "pickup2Start");

            }
        }
    }

    public void nextPath(){
        if(robot.follower.atParametricEnd()){
            pathState++;
            timer.restart();
        }
    }
    public void nextPath(boolean condition){
        if(condition) nextPath();
    }
    public void nextPath(long targetTime){
        nextPath(timer.elapsedSeconds() >= targetTime);
    }
    public void nextPath(boolean condition, long targetTime){
        if(condition) nextPath(targetTime);
    }
    public void nextPathHold(long targetTime){
        if(!robot.follower.atParametricEnd()) timer.restart();
        else nextPath(targetTime);
    }
    public void nextPathHold(boolean condition, long targetTime){
        if(!robot.follower.atParametricEnd()) timer.restart();
        else nextPath(condition, targetTime);
    }
}