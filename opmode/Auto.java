package org.firstinspires.ftc.teamcode.opmode;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Timer;

@Autonomous(name = "AutonoMoose")
public class Auto extends LinearOpMode {
    Hardware robot;
    Timer pathTime;
    Timer afterPathTime;
    int pathState;

    Pose
            start = new Pose(0, 0, 0),
            launch = new Pose(0, 100, Math.toRadians(45)),
            pickup1Control = new Pose(94, 96),
            pickup1Start = new Pose(104, 84.5, 0),
            pickup1End = new Pose(126, 83.5, 0);

    public void followPath(Pose p1, Pose p2) { followPath(new BezierLine(p1, p2)); }
    public void followPath(Pose... poses) { followPath(new BezierCurve(poses)); }
    public void followPath(BezierCurve path){
        if(robot.follower.isBusy()) return;
        robot.follower.followPath(
                robot.follower.pathBuilder()
                        .addPath(path)
                        .setLinearHeadingInterpolation(path.getFirstControlPoint().getHeading(), path.getLastControlPoint().getHeading())
                        .build()
        );
    }

    @Override
    public void runOpMode() {
        robot = new Hardware();
        robot.init(hardwareMap);
        pathTime = new Timer();

        waitForStart();

        pathTime.restart();
        pathState = 0;
        robot.follower.setStartingPose(start);

        while(opModeIsActive()){
            robot.follower.update();
            switch (pathState){
                case 0:
                    followPath(start, launch);
                    robot.runFlywheel();
                    robot.aimTurret();
                    robot.aimRamp();
                    robot.shoot();
                    if(robot.artifactsLaunched) nextPath();
                case 1:
                    followPath(launch, pickup1Control, pickup1Start);
                    nextPath();
                case 2:
                    followPath(pickup1Start, pickup1End);
                case 3:
                    followPath(pickup1End, launch);
                case 4:
                    followPath(launch, pickup1Start);

            }
        }
    }

    public void nextPath() {
        if (robot.follower.atParametricEnd()) {
            pathState++;
            pathTime.restart();
        }
    }
}