package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants FConstants = new FollowerConstants()
            .mass(11); //kg

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static MecanumConstants MConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("em0")
            .rightRearMotorName("em1")
            .leftRearMotorName("em3")
            .leftFrontMotorName("em2")
            .leftFrontMotorDirection(DcMotorEx.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorEx.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorEx.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorEx.Direction.FORWARD);
    public static PinpointConstants LConstants = new PinpointConstants()
            .forwardPodY(-(264.0/2+21.5))
            .strafePodX(264.0/2+24.733)
            .distanceUnit(DistanceUnit.MM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(FConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(MConstants)
                .pinpointLocalizer(LConstants)
                .build();
    }
}
