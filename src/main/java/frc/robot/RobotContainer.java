// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ControllerProfile;
import frc.robot.autos.Auto1;
import frc.robot.commands.IntakeNote;
import frc.robot.commands.ShootNote;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;
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
    private final Joystick munipulator = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */

    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final Arm s_Arm = new Arm();
    private final Intake s_Intake = new Intake();
    private final Vision s_Vision = new Vision();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );
        // Configure the button bindings
        configureButtonBindings();
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
        new JoystickButton(munipulator, XboxController.Axis.kRightTrigger.value) // RT = Shoot
        .whileTrue(new ShootNote(s_Swerve, s_Vision, s_Intake, s_Arm));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new Auto1(s_Swerve);
    }
    public Command getTeleopLightingCommand() {
        return null; //new TeleOpLightShow(s_Lights);        
    }

    public Command getDisabledCommand() {
        // Command to reset robot to initial lightshow/state
        return null; //new DisabledLightShow(s_Lights);
    }
}
