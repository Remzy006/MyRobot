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

public class Drivetrain extends SubsystemBase {
    
    private SparkMax rightMotor = new SparkMax(Constants.Drivetrain.CANIDs.RIGHT, MotorType.kBrushless);
    private SparkMax leftMotor = new SparkMax(Constants.Drivetrain.CANIDs.LEFT, MotorType.kBrushless);

    public Drivetrain()
    {
        /*
        SparkMaxConfig driveTrainConfig = new SparkMaxConfig();
        driveTrainConfig.smartCurrentLimit(Constants.Drivetrain.CURRENT_LIMIT);
        */
        SparkMaxConfig rightConfig = new SparkMaxConfig();
        SparkMaxConfig leftConfig = new SparkMaxConfig();

        rightConfig.smartCurrentLimit(Constants.Drivetrain.CURRENT_LIMIT);
        leftConfig.smartCurrentLimit(Constants.Drivetrain.CURRENT_LIMIT);
        rightConfig.inverted(true);

        rightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        leftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    /*
    public Command arcadeDrive(DoubleSupplier leftStick, DoubleSupplier rightStick){
        return
    }
    */

    /**
     * right and left motors controlled individually with different joysticks
     * @param leftStick lambda retrieving the controller's left stick Y position
     * @param rightStick lambda retrieving the controller's right stick Y position
     * @return
     */
    public Command tankDriveCommand(DoubleSupplier leftStick, DoubleSupplier rightStick){
        return this.run(()->{ 
            rightMotor.setVoltage(MathUtil.clamp(rightStick.getAsDouble()*4.0,-4.0,4.0));
            leftMotor.setVoltage(MathUtil.clamp(leftStick.getAsDouble()*4.0,-4.0,4.0));
        });
    }

    /**
     * robot has a forward speed and a turn speed
     * @param forwardSpeed lambda retrieving the driving stick's Y position
     * @param turnSpeed lambda retrieving the driving stick's X position
     * @return
     */
    public Command arcadeDriveCommand(DoubleSupplier forwardSpeed, DoubleSupplier turnSpeed){
        return this.run(()->{
            rightMotor.setVoltage(MathUtil.clamp((forwardSpeed.getAsDouble() - turnSpeed.getAsDouble())*4.0,-4.0,4.0));
            leftMotor.setVoltage(MathUtil.clamp((forwardSpeed.getAsDouble() + turnSpeed.getAsDouble())*4.0,-4.0,4.0));
        });
    }
}