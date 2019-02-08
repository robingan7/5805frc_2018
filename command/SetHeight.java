package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetHeight extends Command{
private double toSet;
    
    public SetHeight(double height){
        toSet = height;
        requires(Robot.elevator);
    }
    
    @Override
    protected void initialize() {
        Robot.elevator.setHeight(toSet);
    }

    @Override
    protected boolean isFinished() {
        if (Robot.elevator.getEncoderPosition() >= toSet * .98){
            return true;
        }
        // TODO Auto-generated method stub
        return false;
    }

}
