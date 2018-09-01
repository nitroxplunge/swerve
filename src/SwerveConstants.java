package frc.team7308.robot.subsystems.swerve;

public class SwerveConstants {
    // PID
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;

    // Module config
    public static double kDriveDeadzone = 0.0;
    public static double kRotateDeadzone = 0.0;
    public static final int kEncoderPulsesPerRevolution = 4096;
    public static final int kEncoderPulsesPerRadian = 2048;

    // Math Constants
    public static final double kPI = 3.141592653589793;

    // Drivetrain information
    public static final double wheelBase = 30.0;
    public static final double trackWidth = 30.0
    public static final double driveRadius = Math.sqrt(wheelBase * wheelBase + trackWidth * trackWidth);
}