package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetGearing extends Command {
    private boolean toSet;
    
    public SetGearing(boolean high){
        toSet = high;
        requires(Robot.driveBase);
    }
    
    @Override
    protected void initialize() {
        Robot.driveBase.setGearing(toSet);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }
    
}
