package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;


@Autonomous(name = "BLUE_1_JAVA", preselectTeleOp = "TELEOP_JAVA")
public class BLUE_1_JAVA extends LinearOpMode {


    // ===== DRIVE =====
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;


    // ===== SHOOTER =====
    private DcMotorEx leftLauncher, rightLauncher;
    private Servo angleChanger;


    // ===== INTAKE =====
    private DcMotor intakeMotor;
    private CRServo rightIntake, leftIntake;


    // ===== FAN =====
    private CRServo fan;


    // ===== VOLTAGE =====
    private VoltageSensor battery;


    // ===== PF CONSTANTS =====
    private static final double kP = 0.0009;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kF = 18.0;
    private static final double NOMINAL_VOLTAGE = 12.0;


    // ===== ENCODER =====
    private static final double TICKS_PER_REV = 28.0;


    // ===== TELEOP SHOOTER PRESETS =====
    private static final double CLOSE_RPM = 2700;
    private static final double MED_RPM   = 2000;  // NEW VERSION ONLY fOR THIS CODE
    private static final double FAR_RPM   = 2100;
    private static final double EXTRA_FAR_RPM = 2511;


    private static final double CLOSE_ANG = 0.25;
    private static final double MED_ANG   = 0.169;  // NEW VERSION ONLY fOR THIS CODE
    private static final double FAR_ANG   = 0.15;
    private static final double EXTRA_FAR_ANG = 0.16;


    @Override
    public void runOpMode() {


        // ===== DRIVE MAP =====
        frontLeftMotor  = hardwareMap.get(DcMotor.class, "front left");
        backLeftMotor   = hardwareMap.get(DcMotor.class, "back left");
        frontRightMotor = hardwareMap.get(DcMotor.class, "front right");
        backRightMotor  = hardwareMap.get(DcMotor.class, "back right");


        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // ===== SHOOTER MAP =====
        leftLauncher  = hardwareMap.get(DcMotorEx.class, "left launcher");
        rightLauncher = hardwareMap.get(DcMotorEx.class, "right launcher");


        leftLauncher.setDirection(DcMotorSimple.Direction.FORWARD);
        rightLauncher.setDirection(DcMotorSimple.Direction.REVERSE);


        leftLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // ===== INTAKE MAP =====
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        rightIntake = hardwareMap.get(CRServo.class, "right intake");
        leftIntake  = hardwareMap.get(CRServo.class, "Leftintake");


        leftIntake.setDirection(CRServo.Direction.FORWARD);
        rightIntake.setDirection(CRServo.Direction.REVERSE);


        fan = hardwareMap.get(CRServo.class, "blue fan");


        // ===== ANGLE =====
        angleChanger = hardwareMap.get(Servo.class, "Angle Changer");
        angleChanger.setPosition(MED_ANG); // TeleOp MED preset


        // ===== VOLTAGE =====
        battery = hardwareMap.voltageSensor.iterator().next();


        telemetry.addLine("Autonomous Ready");
        telemetry.update();


        waitForStart();


        if (opModeIsActive()) {

//Position shot
            movingForward(0.6, 1300);
            strafing(0.7, -200);

//Shoot
            sleep(3);
            shootMED();
            sleep(3500);

 //Moving to first set artifacts
            strafing(0.8, 200);
            rotating(0.8, 1500);
            stopLauncher();
            strafing(0.8, -950);

 //Picking up artifacts
            startIntakes();
            movingForward(0.4, 1400);
            movingForward(0.8, -1400);

// Position shot
            strafing(0.8,370);
            rotating(0.8,-1450);
            movingForward(0.6, -100);
            strafing(0.8, -200);

 //Shoot
            shootMED();
            sleep(3000);
            stopLauncher();

//Moving to next set of artifacts of TeleOp
            strafing(0.8, 200);
            rotating(0.8,1440);
            strafing(0.8,-1511);


 //The following code can be used or not bc it shoots extra set
            startIntakes();
            movingForward(0.2, 1250);
            movingForward(0.6, -1250);
            strafing(0.6, 1300);
            rotating(0.5,-1411);
            strafing(0.7,-200);
 //Shoot
            shootMED();
            sleep(3200);
            stopLauncher();
            stopIntakes();
            strafing(0.8, 900);
        }
    }


    // ===== DRIVE FUNCTIONS =====
    private void movingForward(double speed, int distance) {
        resetAndRunToPosition(distance, distance, distance, distance, speed);
    }


    private void strafing(double speed, int distance) {
        resetAndRunToPosition(distance, -distance, -distance, distance, speed);
    }


    private void rotating(double speed, int distance) {
        resetAndRunToPosition(distance, -distance, distance, -distance, speed);
    }


    private void resetAndRunToPosition(int fl, int fr, int bl, int br, double speed) {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontLeftMotor.setTargetPosition(fl);
        frontRightMotor.setTargetPosition(fr);
        backLeftMotor.setTargetPosition(bl);
        backRightMotor.setTargetPosition(br);


        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        backRightMotor.setPower(speed);


        while (opModeIsActive()
                && frontLeftMotor.isBusy()
                && frontRightMotor.isBusy()
                && backLeftMotor.isBusy()
                && backRightMotor.isBusy()) {
            idle();
        }


        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }


    // ===== SHOOTER FUNCTIONS LIKE TELEOP =====
    private void shootMED() {
        double targetTPS = rpmToTicksPerSecond(MED_RPM);
        double voltage = battery.getVoltage();
        if (voltage <= 0) voltage = NOMINAL_VOLTAGE;
        double compensatedF = kF * (NOMINAL_VOLTAGE / voltage);


        leftLauncher.setVelocityPIDFCoefficients(kP, kI, kD, compensatedF);
        rightLauncher.setVelocityPIDFCoefficients(kP, kI, kD, compensatedF);


        leftLauncher.setVelocity(targetTPS);
        rightLauncher.setVelocity(targetTPS);


        angleChanger.setPosition(MED_ANG);


        intakeMotor.setPower(1.0);
        leftIntake.setPower(1.0);
        rightIntake.setPower(1.0);
        fan.setPower(1.0);
    }


    private void stopLauncher() {
        leftLauncher.setVelocity(0);
        rightLauncher.setVelocity(0);


        intakeMotor.setPower(0);
        leftIntake.setPower(0);
        rightIntake.setPower(0);
        fan.setPower(0);
    }


    private void startIntakes() {
        intakeMotor.setPower(1.0);
        leftIntake.setPower(1.0);
        rightIntake.setPower(1.0);
    }


    private void stopIntakes() {
        intakeMotor.setPower(0.0);
        leftIntake.setPower(0.0);
        rightIntake.setPower(0.0);
    }


    private double rpmToTicksPerSecond(double rpm) {
        return rpm * TICKS_PER_REV / 60.0;
    }
}





