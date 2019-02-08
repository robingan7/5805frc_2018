package org.usfirst.frc.team5805.robot.subsystems;

import org.usfirst.frc.team5805.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase extends Subsystem{
    private TalonSRX frontLeft, frontRight;
    public VictorSPX rearLeft, rearRight;
    public Solenoid driveSpeed;
    boolean hasntHappened = true;
    public final int TURN_DEGREE = 275;
    public int direction = 1;

    public DriveBase () {
        frontLeft = new TalonSRX(10);
        frontRight = new TalonSRX(12);
        rearLeft = new VictorSPX(11);
        rearRight = new VictorSPX(13);
        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        
        frontRight.setInverted(true);
        rearRight.setInverted(true);
        
        driveSpeed = new Solenoid(2);
        
        frontLeft.config_kF(0, 0.2, 10);
        frontLeft.config_kP(0, 0.5, 10);
        frontLeft.config_kI(0, 0, 10);
        frontLeft.config_kD(0, 0.0, 10);
        
        frontRight.config_kF(0, 0.2, 10);
        frontRight.config_kP(0, 0.5, 10);
        frontRight.config_kI(0, 0, 10);
        frontRight.config_kD(0, 0.0, 10);
        
        rearLeft.follow(frontLeft);
        rearRight.follow(frontRight);
        
        frontLeft.configMotionCruiseVelocity(8000, 0); //Motion Magic
        frontLeft.configMotionAcceleration(8000, 0);
                
        frontRight.configMotionCruiseVelocity(8100, 0); //Motion Magic
        frontRight.configMotionAcceleration(8100, 0);
        
        frontLeft.setSelectedSensorPosition(0, 0, 10);
        frontRight.setSelectedSensorPosition(0, 0, 10);
                
        this.setBraking(NeutralMode.Brake);
    }
    
    public void arcadeDrive(double forward, double turn) {
        forward = forward * direction;
        double leftMotorOutput;
        double maxInputL = Math.copySign(Math.max(Math.abs(forward), Math.abs(turn)), forward);
        
        //find percent -1 to 1
        if (forward >= 0.0) {
            // First quadrant, else second quadrant
            if (turn >= 0.0) {
              leftMotorOutput = maxInputL;
            } else {
              leftMotorOutput = forward + turn;
            }
          } else {
            // Third quadrant, else fourth quadrant
            if (turn >= 0.0) {
              leftMotorOutput = forward + turn;
            } else {
              leftMotorOutput = maxInputL;
            }
          }
        
        frontLeft.set(ControlMode.PercentOutput, leftMotorOutput);
        rearLeft.set(ControlMode.PercentOutput, leftMotorOutput );
    
        
        double rightMotorOutput;
        double maxInputR = Math.copySign(Math.max(Math.abs(forward), Math.abs(turn)), forward);
        
        //find percent -1 to 1
        if (forward >= 0.0) {
            // First quadrant, else second quadrant
            if (turn >= 0.0) {
              rightMotorOutput = forward - turn;
            } else {
              rightMotorOutput = maxInputR;
            }
          } else {
            // Third quadrant, else fourth quadrant
            if (turn >= 0.0) {
              rightMotorOutput = maxInputR;
            } else {
              rightMotorOutput = forward - turn;
            }
          }
        frontRight.set(ControlMode.PercentOutput, rightMotorOutput);
        rearRight.set(ControlMode.PercentOutput, rightMotorOutput );
    }
    
    public void stop() {
        frontLeft.set(ControlMode.Velocity, 0);
        frontRight.set(ControlMode.Velocity, 0);
    }
    
    protected void initDefaultCommand() {
       
    }
    
    public void setBraking(NeutralMode brake) {
        frontLeft.setNeutralMode(brake);
        frontRight.setNeutralMode(brake);
        rearLeft.setNeutralMode(brake);
        rearRight.setNeutralMode(brake);
    }
    
    public void setGearing(boolean high){
        driveSpeed.set(high);
    }
    
    public void driveDistance(double distanceL, double distanceR) {
        
        frontLeft.setSelectedSensorPosition(0, 0, 10);
        frontRight.setSelectedSensorPosition(0, 0, 10);
        frontLeft.set(ControlMode.MotionMagic, distanceL);
        frontRight.set(ControlMode.MotionMagic, distanceR);
        }
    
    public void turn(int degrees){
        frontLeft.setSelectedSensorPosition(0, 0, 10);
        frontRight.setSelectedSensorPosition(0, 0, 10);
        frontLeft.configMotionCruiseVelocity(15000, 0); //Motion Magic
        frontLeft.configMotionAcceleration(15000, 0);
                
        frontRight.configMotionCruiseVelocity(15000, 0); //Motion Magic
        frontRight.configMotionAcceleration(15000, 0);
        if(degrees >= 0){
            frontLeft.set(ControlMode.MotionMagic, -degrees * TURN_DEGREE);
            frontRight.set(ControlMode.MotionMagic, degrees * TURN_DEGREE);
            //rearLeft.set(ControlMode.Follower, 10);
            //rearRight.set(ControlMode.Follower, 12);
        }
        else {
            degrees = Math.abs(degrees);
            frontLeft.set(ControlMode.MotionMagic, degrees * TURN_DEGREE);
            frontRight.set(ControlMode.MotionMagic, -degrees * TURN_DEGREE);
            //rearLeft.set(ControlMode.Follower, 10);
            //rearRight.set(ControlMode.Follower, 12);
        }
        
    }
    
    public void setDefaultConfig(){
        frontLeft.configMotionCruiseVelocity(8000, 0); //Motion Magic
        frontLeft.configMotionAcceleration(8000, 0);
                
        frontRight.configMotionCruiseVelocity(8100, 0); //Motion Magic
        frontRight.configMotionAcceleration(8100, 0);
    }
    
    public void changeDirection(){
        direction = direction * -1;
    }
    
    public double getEncoderPositionL() {
        return frontLeft.getSelectedSensorPosition(0);
    }
    
    public double getEncoderPositionR() {
        return frontRight.getSelectedSensorPosition(0);
    }
    
    public void zeroSensor(){
        System.out.println("ZERO THE SENSOR");
        frontLeft.setSelectedSensorPosition(0, 0, 10);
        frontRight.setSelectedSensorPosition(0, 0, 10);
    }
    
    public void periodic(){
        SmartDashboard.putNumber("Left Position: ", frontLeft.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Left Velocity: ", frontLeft.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Right Position: ", frontRight.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Right Velocity: ", frontRight.getSelectedSensorVelocity(0));
        
        SmartDashboard.putNumber("DBError: ", frontLeft.getSelectedSensorPosition(0) - frontRight.getSelectedSensorPosition(0));
    }

    
}
