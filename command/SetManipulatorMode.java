package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class SetManipulatorMode extends TimedCommand{
private double toSet;

    public SetManipulatorMode(double speed){
        super(2);
        toSet = speed;
        requires(Robot.elevator);
    }
    
    public SetManipulatorMode(double speed, double time){
        super(time);
        toSet = speed;
        requires(Robot.elevator);
    }
    
    @Override
    protected void execute() {
        Robot.manipulator.setManipulatorMode(toSet);
    }
}
