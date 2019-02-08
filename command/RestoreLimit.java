package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RestoreLimit extends Command{

    public RestoreLimit(){
        requires(Robot.elevator);
    }
    
    @Override
    protected void initialize() {
        Robot.elevator.restoreLimit();
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

}
