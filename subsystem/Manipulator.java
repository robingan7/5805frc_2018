package org.usfirst.frc.team5805.robot.subsystems;

import org.usfirst.frc.team5805.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Manipulator extends Subsystem {
    public VictorSPX cubeRight, cubeLeft;
    //private Solenoid cubeArm; compbot
    private DoubleSolenoid cubeArm;
    private double mode = 0;
    private boolean position;
    public Manipulator() {
        cubeLeft = new VictorSPX(15);
        cubeRight = new VictorSPX(16);
        
        //cubeArm = new Solenoid (1); compbot
        cubeArm = new DoubleSolenoid (0, 1);
        
        
        cubeRight.follow(cubeLeft);
        cubeRight.setInverted(true);
    }

    protected void initDefaultCommand() {}
    
    public void setManipulatorMode(double mode){
        cubeLeft.set(ControlMode.PercentOutput, mode);
        cubeRight.set(ControlMode.PercentOutput, mode);
        
    }
    
    public void setSolenoidPosition(boolean position) {
        if (position){
            cubeArm.set(Value.kReverse);
        }
        else{
            cubeArm.set(Value.kForward);
        }
            
    }
        
    
    public void periodic() {
            if (Robot.joy2.getRawButton(8)){//2
                setManipulatorMode((Robot.joy2.getRawAxis(4)+1)*0.5 * 0.75);
            } else if(!Robot.joy2.getRawButton(8) && !Robot.joy2.getRawButton(1)){
                setManipulatorMode(0.0);
            }
        }
}
