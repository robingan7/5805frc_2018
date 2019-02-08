package org.usfirst.frc.team5805.robot.subsystems;

import org.usfirst.frc.team5805.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {
    public TalonSRX liftA;
    private double height = 0;
    
    private static final int MAX_HEIGHT = -38500;
    private static final int SCALE_HEIGHT = -26000;
    private static final int SWITCH_HEIGHT = -10000;
    
    public Elevator () {
        liftA = new TalonSRX (14);
        liftA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
      
        liftA.config_kF(0, 0.2481, 10);
        liftA.config_kP(0, 0.03, 10);
        liftA.config_kI(0, 0.00005, 10);
        liftA.config_kD(0, 0, 10);
      
        liftA.configMotionCruiseVelocity(2500, 10);
        liftA.configMotionAcceleration(10000, 10);
        
        liftA.configReverseSoftLimitEnable(true, 10);
        liftA.configReverseSoftLimitThreshold(-35000, 10);
        
        liftA.configForwardSoftLimitEnable(true, 10);
        liftA.configForwardSoftLimitThreshold(0, 10);
                
        liftA.enableCurrentLimit(false);
//        liftA.configPeakCurrentLimit(5, 10);
//        liftA.configContinuousCurrentLimit(10, 10);
        
        
    }
    
    public void setHeight(double height) {
        if (height <= MAX_HEIGHT) {
            this.height = MAX_HEIGHT;
        } else if (height >= 0) {
            this.height = 0;
        } else {
            this.height = height;
        }
    }
    
    public void zeroSensor(){        
       liftA.setSelectedSensorPosition(0, 0, 0);
    }

    @Override
    protected void initDefaultCommand() {}
    
    public double getEncoderPosition() {
        return liftA.getSelectedSensorPosition(0);
    }
    
    public void periodic () {
        SmartDashboard.putNumber("Elevator Position: ", liftA.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Elevator Velocity: ", liftA.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Elevator Current: ", liftA.getOutputCurrent());
        SmartDashboard.putNumber("Elevator Voltage: ", liftA.getBusVoltage());
        SmartDashboard.putNumber("Elevator Setpoint: ", height);
        SmartDashboard.putNumber("Elevator out: ", liftA.getClosedLoopTarget(0));
        
        if(Math.abs(Robot.joy2.getRawAxis(1)) > 0.15 && !Robot.joy2.getRawButton(7)){
            setHeight(height + Robot.joy2.getRawAxis(1)*500);
        } else if (Robot.joy2.getRawButton(7)) {
            liftA.set(ControlMode.PercentOutput, Robot.joy2.getRawAxis(1)* 0.2);
        }
        
        if (!Robot.joy2.getRawButton(7)) {
            liftA.set(ControlMode.MotionMagic, height);
        }
        
        if(Robot.joy.getRawButton(13)){
            //liftA.setSelectedSensorPosition(0, 0, 0);
        }
        
        if (Robot.joy2.getRawButton(4)) {
            //setHeight(SCALE_HEIGHT);
        } else if (Robot.joy2.getRawButton(2)){
            //setHeight(SWITCH_HEIGHT);
        }else if (Robot.joy2.getRawButton(10)){
            //setHeight(0);
        }
    }

    public void killLimit() {
        liftA.configReverseSoftLimitEnable(false, 10);
        
        liftA.configForwardSoftLimitEnable(false, 10);
        
        System.out.println("kill");
    }

    public void restoreLimit() {
        zeroSensor();
        
        setHeight(0);
        
        liftA.configReverseSoftLimitEnable(true, 10);
        liftA.configReverseSoftLimitThreshold(-35000, 10);
        
        liftA.configForwardSoftLimitEnable(true, 10);
        liftA.configForwardSoftLimitThreshold(0, 10);
        
        System.out.println("restore");

    }
}

