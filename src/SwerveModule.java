package frc.team7308.robot.subsystems.swerve;

import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.team7308.robot.subsystems.swerve.SwerveConstants;

public class SwerveModule {
    public double goal_speed;
    public int goal_angle;

    public CANTalon m_driveTalon;
    public CANTalon m_rotationTalon;
    public Encoder m_driveEncoder;
    private boolean m_reversed;

    public SwerveModule(int driveTalonID, int rotationTalonID, int[] driveEncoderPorts) {
        this.m_driveTalon = new TalonSRX(driveTalonID);
        this.m_rotationTalon = new TalonSRX(rotationTalonID);
        this.m_driveEncoder = new Encoder(driveEncoderPorts[0], driveEncoderPorts[1]);
        this.m_rotationTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

        this.loadConfig();
    }

    public void setSpeed(double speed) {
        speed = this.reversed ? speed * -1 : speed;
        this.goal_speed = speed;
        this.m_driveTalon.set(ControlMode.PercentOutput, speed);
    }

    public void setAngle(double radians) {
        int pulses = this.radiansToEncoderPulses(radians);
        this.calculateAndSetAngle(pulses);
    }

    public void setAngle(int degrees) {
        int pulses = this.degreesToEncoderPulses(degrees);
        this.calculateAndSetAngle(pulses);
    }

    private void calculateAndSetAngle(int goal) {
        int diff = Math.abs(this.m_rotationTalon.getSelectedSensorPosition(0) - goal);
        int dist = diff > SwerveConstants.kEncoderPulsesPerRadian ? SwerveConstants.kEncoderPulsesPerRevolution - diff : diff;

        // If true, must invert angle
        if (dist > SwerveConstants.kEncoderPulsesPerRadian / 2) {
            this.reversed = true;
            this.goal_angle = dist < SwerveConstants.kEncoderPulsesPerRadian ? dist + SwerveConstants.kEncoderPulsesPerRadian : dist - SwerveConstants.kEncoderPulsesPerRadian;
        } else {
            this.reversed = false;
            this.goal_angle = dist;
        }

        this.m_rotationTalon.set(ControlMode.Position, this.goal_angle);
    }

    public void zero() {
        this.m_driveEncoder.reset();
    }

    public void loadConfig() {
        this.m_rotationTalon.config_kP(SwerveConstants.kP);
        this.m_rotationTalon.config_kI(SwerveConstants.kI);
        this.m_rotationTalon.config_kD(SwerveConstants.kD);
    }

    private int radiansToEncoderPulses(double radians) {
        return (int)(radians * SwerveConstants.kEncoderPulsesPerRadian / SwerveConstants.kPI) % SwerveConstants.kEncoderPulsesPerRevolution;
    }

    private int degreesToEncoderPulses(int degrees) {
        return (int)(degrees * SwerveConstants.kEncoderPulsesPerRadian / 180.0) % SwerveConstants.kEncoderPulsesPerRevolution;
    }
}