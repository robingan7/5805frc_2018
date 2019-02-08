package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeDirection extends Command{
    
    public ChangeDirection(){
        requires(Robot.driveBase);
    }
    
    @Override
    protected void initialize() {
        Robot.driveBase.changeDirection();
        System.out.println("Change Direction");
    }
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

}
