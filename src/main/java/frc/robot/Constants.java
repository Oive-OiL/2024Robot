// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.util.COTSTalonFXSwerveConstants;
import frc.lib.util.PIDGains;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {

  public static class ControllerProfile {
    public static final int kDriverControllerPort = 0;
    public static final int kManipulatorControllerPort = 1;
    public static final int kTechnitionControllerPort = 2;
    public static final double stickDeadband = 0.1;
  }

  public static class ArmProfile {
    public static final int pivotMotorID_A = 15;
    public static final int pivotMotorID_B = 16;
    public static final int indexorID = 17;
    public static final int shooterID_A = 18;
    public static final int shooterID_B = 19;
    public static final int noteDetectorChannel = 0; //TODO: This must be configured

    public static final int kPivotCurrentLimit = 40;

    public static final double kPivotSoftLimitRvs = 0.0;
    public static final double kPivotSoftLiimitFwd = 23830;

    public static final double pivotInitialPos = 0;
    public static final double kPivotDegreeThreshold = 500;
    public static final double kpivotSpeakerPos = 4000; //TODO: Must be configured for speaker
    public static final double kPivotClimbPos = 20000 - kPivotDegreeThreshold; //TODO: This must be configured

    public static final double kArmGearRatio = ((3.0 * 4.0 * 5.0) * 3.0) / 1;

    public static final int neoEncoderCountsPerRev = 42;
    public static final double kPositionFactor = kArmGearRatio * 2.0 * Math.PI; //multiply SM value by this number and get arm position in radians
    public static final double kVelocityFactor = kArmGearRatio * 2.0 * Math.PI / 60.0;
    public static final double kArmFreeSpeed = 5676.0 * kVelocityFactor;

    public static final double kArmDefaultOutput = 0.5;
    public static final double kIndexorDefaultOutput = 1;
    public static final double kShooterDefaultOutput = 1;

    public static final PIDGains kArmPositionGains = new PIDGains(0.01, 0.0, 0.0);

    // Tables for arm degree vs distance
    public static final double[] ArmDegreeArray = {
        1, 2, 3
    }; 
    public static final double[] TargetDistanceArray = {
        4, 5, 6
    };
  }

  public static class IntakeProfile {
    public static final int wristID = 20;
    public static final int outerRoller = 21;
    public static final int innerRoller = 22;

    public static final int kWristCurrentLimit = 40;
    public static final int kRollerCurrentLimit = 40;

    public static final int kInitialPos = 0;
    public static final double kDeployedPos = 10053;
    public static final double kWristThreshhold = 250; //TODO: This must be configured
    public static final double kDeployedLowerLimitPos = kDeployedPos - kWristCurrentLimit;
    public static final double kInitailUpperLimitPos = kInitialPos + kWristThreshhold;
    public static final double kPlusSafeZone = 1000; // Milimeters TODO: This must be configured

    public static final int neoEncoderCountsPerRev = 42;    
    public static final double kWristGearRatio = 70 / 1;
    public static final double kPositionFactor = kWristGearRatio * 2.0 * Math.PI; //multiply SM value by this number and get arm position in radians
    public static final double kVelocityFactor = kWristGearRatio * 2.0 * Math.PI / 60.0;
    public static final double kArmFreeSpeed = 11000 * kVelocityFactor;

    public static final PIDGains kWristPositionGains = new PIDGains(0.01, 0.0, 0.0); //TODO: This must be configured

    public static final double kOuterDefaultOutput = 1;
    public static final double kInnerDefaultOutput = 0.8;
  }

  public static class LightingProfile {
    public static final int candldeID = 23;
    public static final double kBrightnessScalar = 1;
    public static final int numLEDStrip = 15; //TODO: Must be configured
  }

  public static class LimelightProfile { //TODO: All must be configured
    public static final double limelightVerticalAngle = 0;
    public static final double speakerHeightInches = 0;
    public static final double limelightHeightInches = 0;
    public static final int aprilTagPipeline = 0;
    public static final int visionProcessorEntry = 0;
    public static final int driverCameraEntry = 1;
    public static final int alignWindow = 2;
  }

  public static class ClimberProfile {
    /* Motor Id's */
    public static final int climberA_ID = 30; //TODO: Must configure CAN id's
    public static final int climberB_ID = 31;

    /* Motor Inverts */ //TODO: These must be configured
    public static final InvertedValue climberAMotorInvert = InvertedValue.Clockwise_Positive;
    public static final InvertedValue climberBMotorInvert = InvertedValue.CounterClockwise_Positive;

    /* Neutral Modes */ //TODO: These must be configured
    public static final NeutralModeValue climberNeutralMode = NeutralModeValue.Brake;

    /* Climber Current Limiting */
    public static final int climberCurrentLimit = 40;
    public static final int climberCurrentThreshold = 40;
    public static final double climberCurrentThresholdTime = 0.1;
    public static final boolean climberEnableCurrentLimit = true;

    /* Soft Limits */
    public static final boolean climberEnableRVSSoftLimit = true;
    public static final double climberRVSSoftLimitThreshold = 0;
    public static final boolean climberEnableFWDSoftLimit = true;
    public static final double climberFWDSoftLimitThreshold = 99999; //TODO: This must be configured
   
    /* Drive Motor PID Values */
    public static final double climberKP = 0.0; //TODO: This must be configured
    public static final double climberKI = 0.0;
    public static final double climberKD = 0.0;
    public static final double climberKF = 0.0;

    /* Threshold and Set Positions */ //TODO: This must be configured
    public static final double climberPosThreshold = 99;
    public static final double climberMaxHeightPos = 9999;
    public static final double climberTrueMaxPos = 9999 - climberPosThreshold;

    /* Climber Set Outputs */
    public static final double climberDefaultOutput = 0.3;
    public static final double outputWithZeroLoad = 0.4;
    public static final double outputWithRobotLoad = 0.8;
  }

  public static final class SwerveProfile {
        public static final int pigeonID = 13; //TODO: Ensure that the gyro rotation is CCW+ (Counter Clockwise Positive)

        public static final COTSTalonFXSwerveConstants chosenModule = 
        COTSTalonFXSwerveConstants.SDS.MK4.Falcon500(COTSTalonFXSwerveConstants.SDS.MK4.driveRatios.L2);

        /* Drivetrain Constants */
        public static final double trackWidth = Units.inchesToMeters(19.5); // Center to Center distance of left and right modules wheels in meters.
        public static final double wheelBase = Units.inchesToMeters(19.5); // Center to Center distance of front and rear module wheels in meters.
        public static final double wheelCircumference = chosenModule.wheelCircumference;

        /* Swerve Kinematics */
         public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

        /* Module Gear Ratios */
        public static final double driveGearRatio = chosenModule.driveGearRatio;
        public static final double angleGearRatio = chosenModule.angleGearRatio;

        /* Motor Inverts */
        public static final InvertedValue angleMotorInvert = chosenModule.angleMotorInvert;
        public static final InvertedValue driveMotorInvert = chosenModule.driveMotorInvert;

        /* Angle Encoder Invert */
        public static final SensorDirectionValue cancoderInvert = chosenModule.cancoderInvert;

        /* Swerve Current Limiting */
        public static final int angleCurrentLimit = 25;
        public static final int angleCurrentThreshold = 40;
        public static final double angleCurrentThresholdTime = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveCurrentLimit = 40;
        public static final int driveCurrentThreshold = 60;
        public static final double driveCurrentThresholdTime = 0.1;
        public static final boolean driveEnableCurrentLimit = true;

        /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
        public static final double openLoopRamp = 0.2;
        public static final double closedLoopRamp = 0.0;

        /* Angle Motor PID Values */
        public static final double angleKP = chosenModule.angleKP;
        public static final double angleKI = chosenModule.angleKI;
        public static final double angleKD = chosenModule.angleKD;

        /* Drive Motor PID Values */
        public static final double driveKP = 1.0; //TODO: This must be configured
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKF = 0.0;

        /* Drive Motor Characterization Values From SYSID */
        public static final double driveKS = 0; //TODO: This must be configured
        public static final double driveKV = 0;
        public static final double driveKA = 0;

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double maxSpeed = 4;//4; // TODO: Must be configured. 4.99m/s before weight (Currently Theoretical)
        /** Radians per Second */
        public static final double maxAngularVelocity = 8; //TODO: Must be configured. 13.99r/s before weight (Currently Theoretical)
        /** Fractional Percentage **/
        public static final double speedCap = 0.5;

        /* Neutral Modes */
        public static final NeutralModeValue angleNeutralMode = NeutralModeValue.Coast;
        public static final NeutralModeValue driveNeutralMode = NeutralModeValue.Brake;

        /* Module Specific Constants */
        /* Back Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = 7;
            public static final int angleMotorID = 8;
            public static final int canCoderID = 9;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(135.1);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Front Left Module - Module 1 */
        public static final class Mod1 {
            public static final int driveMotorID = 1;
            public static final int angleMotorID = 2;
            public static final int canCoderID = 3;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(53.6);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Front Right Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = 4;
            public static final int angleMotorID = 5;
            public static final int canCoderID = 6;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(16.5);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int driveMotorID = 10;
            public static final int angleMotorID = 11;
            public static final int canCoderID = 12;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-72.7);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Backup Moudles Specific Constants */
        /** Backup Module 1: TODO: This must be configured
            public static final int driveMotorID = 7;
            public static final int angleMotorID = 8;
            public static final int canCoderID = 4;
        ** Mod 0 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        ** Mod 1 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        ** Mod 2 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        ** Mod 3 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        
        /** Backup Module 2: TODO: This must be configured
            public static final int driveMotorID = 7;
            public static final int angleMotorID = 8;
            public static final int canCoderID = 4;
        ** Mod 0 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        ** Mod 1 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        ** Mod 2 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        ** Mod 3 Offset:
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0.0);
        */
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 1.5;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    }
}
