// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ArmProfile;
import frc.robot.Constants.ClimberProfile;
import frc.robot.Constants.ControllerProfile;
import frc.robot.Constants.IntakeProfile;
import frc.robot.InitCommands.SetDisabledState;
import frc.robot.InitCommands.SetEnabledState;
import frc.robot.InitCommands.SetTestState;
import frc.robot.TechnitionCommands.ArmControl;
import frc.robot.TechnitionCommands.WristControl;
import frc.robot.autos.Auto1;
import frc.robot.commands.ClimbChain;
import frc.robot.commands.DropAmpNote;
import frc.robot.commands.IntakeNote;
import frc.robot.commands.RunClimbersToFirstState;
import frc.robot.commands.ShootNote;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(ControllerProfile.kDriverControllerPort);
    private final Joystick munipulator = new Joystick(ControllerProfile.kManipulatorControllerPort);
    private final Joystick technition = new Joystick(ControllerProfile.kTechnitionControllerPort);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton slowMode = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    
    /* Technition Controls */
    private final int wristAxis = XboxController.Axis.kRightY.value;
    private final int armAxis = XboxController.Axis.kLeftY.value;

    /* Sendable Choosers */
    SendableChooser<Command> m_AutoChooser = new SendableChooser<>();
    SendableChooser<Command> m_TeleOpInitChooser = new SendableChooser<>();

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final Arm s_Arm = new Arm();
    private final Intake s_Intake = new Intake();
    private final Vision s_Vision = new Vision();
    private final Climber s_Climber = new Climber();
    private final Lighting s_Lighting = new Lighting();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        //Configure the button bindings
        configureButtonBindings();

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean(),
                () -> slowMode.getAsBoolean()
            )
        );

        // s_Intake.setDefaultCommand(new WristControl(
        //     s_Intake,
        //     () -> -technition.getRawAxis(wristAxis)));
            
        // s_Arm.setDefaultCommand(new ArmControl(
        //     s_Arm, 
        //     () -> -technition.getRawAxis(armAxis)));

        /* Chooser for Auton Commands */
        m_AutoChooser.setDefaultOption("Test Auto", new Auto1(s_Swerve));
        SmartDashboard.putData(m_AutoChooser);

        // A chooser for TeleOp Initialization Commands
        m_TeleOpInitChooser.setDefaultOption("Match Mode", new SetEnabledState(s_Lighting));
        m_TeleOpInitChooser.addOption("Test mode", new SetTestState(s_Lighting));
        SmartDashboard.putData(m_TeleOpInitChooser);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading())); // Y = Zero Gryo

        /* Manipulator Buttons */
        new JoystickButton(munipulator, XboxController.Button.kA.value) // A = Intake 
        .whileTrue(new IntakeNote(s_Intake, s_Arm));
        new JoystickButton(munipulator, XboxController.Button.kY.value) // Y = Shoot
        .whileTrue(new ShootNote(s_Swerve, s_Vision, s_Intake, s_Arm));
        new JoystickButton(munipulator, XboxController.Button.kX.value) // X = Shoot In Amp
        .onTrue(new DropAmpNote(s_Intake, s_Arm));
        new JoystickButton(munipulator, XboxController.Button.kBack.value) // Back = Climbers to First State
        .whileTrue(new RunClimbersToFirstState(s_Intake, s_Arm, s_Climber)); 
        new JoystickButton(munipulator, XboxController.Button.kStart.value) // Start = Climb Chain
        .whileTrue(new ClimbChain(s_Climber));

        /* Technition Buttons */
        new JoystickButton(technition, XboxController.Button.kA.value) // X = Inner Roller
        .onTrue(new InstantCommand(() -> s_Intake.setInnerRollerOutput(IntakeProfile.kInnerDefaultOutput)))
        .onTrue(new InstantCommand(() -> s_Arm.setIndexorOuput(ArmProfile.kIndexorDefaultOutput)))
        .onFalse(new InstantCommand(() -> s_Intake.setInnerRollerOutput(0)))
        .onFalse(new InstantCommand(() -> s_Arm.setIndexorOuput(0)));
        new JoystickButton(technition, XboxController.Button.kB.value) // B = Outer Roller
        .onTrue(new InstantCommand(() -> s_Intake.setOuterRollerOutput(IntakeProfile.kOuterDefaultOutput)))
        .onFalse(new InstantCommand(() -> s_Intake.setOuterRollerOutput(0)));
        new JoystickButton(technition, XboxController.Button.kLeftBumper.value) // LB = Indexor
        .onTrue(new InstantCommand(() -> s_Arm.setIndexorOuput(ArmProfile.kIndexorDefaultOutput)))
        .onFalse(new InstantCommand(() -> s_Arm.setIndexorOuput(0)));
        new JoystickButton(technition, XboxController.Button.kRightBumper.value) // RB = Shooter
        .onTrue(new InstantCommand(() -> s_Arm.setShooterOutput(ArmProfile.kShooterDefaultOutput)))
        .onFalse(new InstantCommand(() -> s_Arm.setShooterOutput(0)));
        new JoystickButton(technition, XboxController.Button.kY.value) // Y = Climbers
        .onTrue(new InstantCommand(() -> s_Climber.setClimberOutput(ClimberProfile.outputWithZeroLoad)))
        .onFalse(new InstantCommand(() -> s_Climber.setClimberOutput(0)));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return m_AutoChooser.getSelected();
    }

    public Command getDisabledCommandInitCommand() {
        // Command to reset robot to initial lightshow/state
        Command disable = new SetDisabledState(s_Lighting);
        return disable;
    }

    public Command getTeleOpInitCommand() {
        return m_TeleOpInitChooser.getSelected();        
    }
}
