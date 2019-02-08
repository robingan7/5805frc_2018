package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetSolenoidPosition extends Command{
private boolean toSet;
    
    public SetSolenoidPosition(boolean status){
        toSet = status;
        requires(Robot.manipulator);
    }
    
    @Override
    protected void initialize() {
        Robot.manipulator.setSolenoidPosition(toSet);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

}
