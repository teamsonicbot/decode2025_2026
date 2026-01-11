package org.firstinspires.ftc.teamcode;
//INCOMPLETE
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@Autonomous(name = "RED_2_JAVA", preselectTeleOp = "TELEOP_JAVA")
public class RED_2_JAVA extends LinearOpMode {

    private DcMotor backleft;
    private DcMotor backright;
    private DcMotor frontleft;
    private DcMotor frontright;
    private DcMotor intake;
    private DcMotorEx leftlauncher;
    private DcMotorEx rightlauncher;
    private CRServo bluefan;
    private CRServo Leftintake;
    private CRServo rightintake;
    private Servo AngleChanger;
    private VoltageSensor battery;

    // ===== SHOOTER CONSTANTS =====
    static final double TICKS_PER_REV = 28.0;
    static final double CLOSE_RPM = 3000;
    static final double MED_RPM = 3000;
    static final double FAR_RPM = 2100;
    static final double EXTRA_FAR_RPM = 2655;
    static final double CLOSE_ANG = 0.25;
    static final double MED_ANG = 0.20;
    static final double FAR_ANG = 0.15;
    static final double EXTRA_FAR_ANG = 0.145;
    static final double kP = 0.0009;
    static final double kF = 18.0;
    static final double NOMINAL_VOLTAGE = 12.0;

    @Override
    public void runOpMode() {
        int speed;
        int distance;
        int speed_left;
        int speed_right;
        int strafeleft;
        int straferight;
        int seconds;

        backleft = hardwareMap.get(DcMotor.class, "back left");
        backright = hardwareMap.get(DcMotor.class, "back right");
        frontleft = hardwareMap.get(DcMotor.class, "front left");
        frontright = hardwareMap.get(DcMotor.class, "front right");
        intake = hardwareMap.get(DcMotor.class, "intake");
        leftlauncher = hardwareMap.get(DcMotorEx.class, "left launcher");
        rightlauncher = hardwareMap.get(DcMotorEx.class, "right launcher");
        bluefan = hardwareMap.get(CRServo.class, "blue fan");
        Leftintake = hardwareMap.get(CRServo.class, "Leftintake");
        rightintake = hardwareMap.get(CRServo.class, "right intake");
        AngleChanger = hardwareMap.get(Servo.class, "Angle Changer");
        battery = hardwareMap.voltageSensor.iterator().next();

        // Put initialization blocks here.
        backleft.setTargetPosition(0);
        backright.setTargetPosition(0);
        frontleft.setTargetPosition(0);
        frontright.setTargetPosition(0);
        backleft.setPower(0);
        frontright.setPower(0);
        frontleft.setPower(0);
        backright.setPower(0);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setDirection(DcMotor.Direction.REVERSE);
        leftlauncher.setDirection(DcMotor.Direction.FORWARD);
        rightlauncher.setDirection(DcMotor.Direction.REVERSE);
        bluefan.setDirection(CRServo.Direction.FORWARD);
        Leftintake.setDirection(CRServo.Direction.FORWARD);
        rightintake.setDirection(CRServo.Direction.REVERSE);
        AngleChanger.setPosition(0.22);
        backleft.setDirection(DcMotor.Direction.REVERSE);
        frontleft.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.FORWARD);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftlauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightlauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // SHOOTER SETUP
        applyPF();

        speed_left = 0;
        speed_right = 0;
        strafeleft = 0;
        straferight = 0;
        distance = 0;
        speed = 0;
        seconds = 0;
        waitForStart();
        if (opModeIsActive()) {
            // Put loop blocks here.
            moving_backward(0.3, 100);
            rotate(0.3, 230);
            Intake();

            //FIRST SHOT
            setExtraFarShot();
            bluefan.setPower(1);
            sleep(3000);
            bluefan.setPower(0);

            rotate(0.3, -1180);
            strafing_right(0.3, -1090);
            moving_forward_(0.3, 700);
            moving_forward_(0.2, 1000);
            moving_backward(0.3, 1500);
            strafing_right(0.3, 870);
            rotate(0.3, 1200);

            //SECOND SHOT
            setExtraFarShot();
            bluefan.setPower(1);
            sleep(3000);
            bluefan.setPower(0);

            rotate(0.3, -1150);
            strafing_right(0.3, -2000);
            moving_forward_(0.3, 600);
            moving_forward_(0.2, 900);
            moving_backward(0.3, 1300);

        }
    }

    // ===== YOUR ORIGINAL FUNCTIONS (UNCHANGED) =====
    private void turn_some_random_shit(double speed, int distance) {
        backleft.setTargetPosition(distance);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setPower(speed);
        backright.setTargetPosition(distance);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setPower(0);
        frontleft.setTargetPosition(distance);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setPower(speed);
        frontright.setTargetPosition(distance);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setPower(0);
        while (backleft.isBusy() && backright.isBusy() && frontleft.isBusy() && frontright.isBusy()) {
        }
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        telemetry.update();
    }

    private void moving_forward_(double speed, int distance) {
        backleft.setTargetPosition(distance);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setPower(speed);
        backright.setTargetPosition(distance);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setPower(speed);
        frontleft.setTargetPosition(distance);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setPower(speed);
        frontright.setTargetPosition(distance);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setPower(speed);
        while (backleft.isBusy() && backright.isBusy() && frontleft.isBusy() && frontright.isBusy()) {
        }
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setTargetPosition(0);
        backright.setTargetPosition(0);
        frontright.setTargetPosition(0);
        frontleft.setTargetPosition(0);
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        telemetry.update();
    }

    private void moving_backward(double speed, int distance) {
        backleft.setTargetPosition(-distance);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setPower(speed);
        backright.setTargetPosition(-distance);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setPower(speed);
        frontleft.setTargetPosition(-distance);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setPower(speed);
        frontright.setTargetPosition(-distance);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setPower(speed);
        while (backleft.isBusy() && backright.isBusy() && frontleft.isBusy() && frontright.isBusy()) {
        }
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        telemetry.update();
    }

    private void strafing_right(double speed, int distance) {
        backleft.setTargetPosition(-distance);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setPower(speed);
        backright.setTargetPosition(distance);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setPower(speed);
        frontleft.setTargetPosition(distance);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setPower(speed);
        frontright.setTargetPosition(-distance);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setPower(speed);
        while (backleft.isBusy() && backright.isBusy() && frontleft.isBusy() && frontright.isBusy()) {
        }
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        telemetry.update();
    }

    private void Intake() {
        intake.setPower(0.8);
        Leftintake.setPower(1);
        rightintake.setPower(1);
    }

    private void Intake2() {
        intake.setPower(0);
        Leftintake.setPower(0);
        rightintake.setPower(0);
    }

    private void rotate(double speed, int distance) {
        backleft.setTargetPosition(distance);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setPower(speed);
        backright.setTargetPosition(-distance);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setPower(speed);
        frontleft.setTargetPosition(distance);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontleft.setPower(speed);
        frontright.setTargetPosition(-distance);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setPower(speed);
        while (backleft.isBusy() && backright.isBusy() && frontleft.isBusy() && frontright.isBusy()) {
        }
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setPower(0);
        backright.setPower(0);
        frontleft.setPower(0);
        frontright.setPower(0);
        telemetry.update();
    }

    // ===== SHOOTER FUNCTIONS =====
    double rpmToTicksPerSecond(double rpm) {
        return rpm * TICKS_PER_REV / 60.0;
    }

    void applyPF() {
        double voltage = battery.getVoltage();
        if (voltage <= 0) voltage = NOMINAL_VOLTAGE;
        double compensatedF = kF * (NOMINAL_VOLTAGE / voltage);
        leftlauncher.setVelocityPIDFCoefficients(kP, 0.0, 0.0, compensatedF);
        rightlauncher.setVelocityPIDFCoefficients(kP, 0.0, 0.0, compensatedF);
    }

    void setCloseShot() {
        double tps = rpmToTicksPerSecond(CLOSE_RPM);
        leftlauncher.setVelocity(tps);
        rightlauncher.setVelocity(tps);
        AngleChanger.setPosition(CLOSE_ANG);
    }

    void setMediumShot() {
        double tps = rpmToTicksPerSecond(MED_RPM);
        leftlauncher.setVelocity(tps);
        rightlauncher.setVelocity(tps);
        AngleChanger.setPosition(MED_ANG);
    }

    void setFarShot() {
        double tps = rpmToTicksPerSecond(FAR_RPM);
        leftlauncher.setVelocity(tps);
        rightlauncher.setVelocity(tps);
        AngleChanger.setPosition(FAR_ANG);
    }

    void setExtraFarShot() {
        double tps = rpmToTicksPerSecond(EXTRA_FAR_RPM);
        leftlauncher.setVelocity(tps);
        rightlauncher.setVelocity(tps);
        AngleChanger.setPosition(EXTRA_FAR_ANG);
    }
}