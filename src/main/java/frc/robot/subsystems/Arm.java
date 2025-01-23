package frc.robot.subsystems;

import frc.robot.Constants;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

    private SparkMax armMotor = new SparkMax(Constants.Arm.CANIDs.ARM, MotorType.kBrushless);

    public Arm()
    {
        SparkMaxConfig armConfig = new SparkMaxConfig();
        armConfig.smartCurrentLimit(Constants.Arm.ARM_CURRENT_LIMIT);
        armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    //use suppliers as the type for lambda parameters
    public Command armManualCommand(DoubleSupplier speed)
    {
        return this.run(()->{
            armMotor.setVoltage(MathUtil.clamp(speed.getAsDouble()*4.0,-4.0,4.0));
        });
    }

    public Command rotateToPositionCommand(DoubleSupplier position)
    {
        return this.run(()->{
            
        });
    }
    
    public Command stopArmCommand()
    {
        return this.runOnce(()->{
            armMotor.setVoltage(0);
        }) ;
    }
}