package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;
import org.usfirst.frc.team5805.robot.subsystems.DriveBase;

import edu.wpi.first.wpilibj.command.Command;

public class Turn extends Command{
private int toSet;
    
    public Turn(int degrees){
        toSet = degrees;
        requires(Robot.driveBase);
    }
    
    @Override
    protected void initialize() {
        Robot.driveBase.turn(toSet);
    }

    @Override
    protected boolean isFinished() {
        if (Math.abs(Robot.driveBase.getEncoderPositionL()) >= Math.abs(toSet) * 275 * .98){
            Robot.driveBase.setDefaultConfig();
            return true;
        }
        // TODO Auto-generated method stub
        return false;
    }

}
