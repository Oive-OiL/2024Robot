// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lighting;

public class IntakeNote extends Command {
  private Intake s_Intake;
  private Arm s_Arm;
  private Lighting s_Lighting;

  /** Creates a new IntakeControl. */
  public IntakeNote(Intake intake, Arm arm) {
    // Use addRequirements() here to declare subsystem dependencies.
    s_Intake = intake;
    s_Arm = arm;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    s_Intake.enableIntake(s_Arm, s_Lighting);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_Intake.resetIntake(s_Arm, s_Lighting);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
