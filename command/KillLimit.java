package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class KillLimit extends Command{

    public KillLimit(){
        requires(Robot.elevator);
    }
    
    @Override
    protected void initialize() {
        Robot.elevator.killLimit();
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

}
