package frc.robot.subsystems;

import frc.robot.Constants.Arm;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private SparkMax intakeMotor = new SparkMax(Arm.CANIDs.INTAKE, MotorType.kBrushless);

    public Intake()
    {
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.smartCurrentLimit(Arm.INTAKE_CURRENT_LIMIT);
        intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command intakeCommand(){
        return this.runOnce(()->intakeMotor.setVoltage(3.5));
    }

    public Command outtakeCommand(){
        return this.runOnce(()->intakeMotor.setVoltage(-3.5));
    }

    public Command stopIntakeCommand(){
        //return this.runOnce(()->intakeMotor.stopMotor());
        return this.runOnce(intakeMotor::stopMotor);
    }

}
