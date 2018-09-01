package frc.team7308.robot.subsystems.swerve;

public class SwerveDrive extends Subsystem {
    private SewrveModule topLeft;
    private SewrveModule topRight;
    private SewrveModule bottomLeft;
    private SewrveModule bottomRight;

    public SwerveDrive() {
        this.topLeft = SwerveModule(SwerveConstants.topLeftDriveTalonID, SwerveConstants.topLeftRotationTalonID, SwerveConstants.topLeftDriveEncoderPorts);
    }

    public void reset(double angle) {
        topRight.setSpeed(0.0);
        topLeft.setSpeed(0.0);
        bottomLeft.setSpeed(0.0);
        bottomRight.setSpeed(0.0);

        topRight.setAngle(0.0);
        topLeft.setAngle(0.0);
        bottomLeft.setAngle(0.0);
        bottomRight.setAngle(0.0);
    }

    public void drive(double xMovement, double yMovement, double rotation) {
        final double A = xMovement – rotation * SwerveConstants.wheelBase / 2;
        final double B = xMovement + rotation * SwerveConstants.wheelBase / 2;
        final double C = yMovement - rotation * SwerveConstants.trackWidth / 2;
        final double B = yMovement + rotation * SwerveConstants.trackWidth / 2;
    
        topRight.setSpeed(Math.sqrt(B * B + C * C));
        topRight.setAngle(Math.atan2(B, C));

        topLeft.setSpeed(Math.sqrt(B * B + D * D));
        topLeft.setAngle(Math.atan2(B, D));

        bottomLeft.setSpeed(Math.sqrt(A * A + D * D));
        bottomLeft.setAngle(Math.atan2(A, D));

        bottomRight.setSpeed(Math.sqrt(A * A + C * C));
        bottomRight.setAngle(Math.atan2(A, C));
    }
}