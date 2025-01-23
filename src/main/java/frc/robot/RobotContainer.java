// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  private Drivetrain drivetrainX = new Drivetrain();
  private Arm arm = new Arm();
  private Intake intake = new Intake();

  private CommandXboxController controller = new CommandXboxController(Constants.CONTROLLER_PORT);
  
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
     //drivetrain defaults to arcade drive
     drivetrainX.setDefaultCommand(drivetrainX.arcadeDriveCommand(
      () -> -controller.getLeftY(), 
      () -> -controller.getRightX()
    ));


    //when the x button is pressed, the drivetrain toggles tank drive
    controller.x()
      .toggleOnTrue(drivetrainX.tankDriveCommand(
        () -> -controller.getLeftY(), 
        () -> -controller.getRightY()
      ))
      .toggleOnTrue(arm.stopArmCommand());
    
    
    controller.leftTrigger()
      .whileTrue(arm.armManualCommand(
        () -> controller.getLeftTriggerAxis()
      ));

    controller.rightTrigger()
      .whileTrue(arm.armManualCommand(
        () -> -controller.getLeftTriggerAxis()
      ));

    //when the right trigger is held, the intake spins forward
    controller.rightBumper()
      .onTrue(intake.intakeCommand())
      .onFalse(intake.stopIntakeCommand());

    //when the left trigger is held, the intake spins backward
    controller.leftBumper()
      .onTrue(intake.outtakeCommand())
      .onFalse(intake.stopIntakeCommand());
    
  }

  public Command getAutonomousCommand() {
    return Commands.sequence(
      drivetrainX.tankDriveCommand(()->0.5, ()->0.5).withTimeout(5),
      arm.armManualCommand(()->0.5).withTimeout(1),
      intake.intakeCommand().withTimeout(3),
      intake.stopIntakeCommand()
    );
  }
}
