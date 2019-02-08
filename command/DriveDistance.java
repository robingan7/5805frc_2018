package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command{
    private double toSetL, toSetR;
    
    public DriveDistance(double distance){
        toSetL = distance;
        toSetR = distance;
        requires(Robot.driveBase);
    }
    
    public DriveDistance(double distance, double turnGain){
        toSetL = distance + turnGain;
        toSetR = distance - turnGain;
        requires(Robot.driveBase);
    }

    
    @Override
    protected void initialize() {
        Robot.driveBase.driveDistance(toSetL, toSetR);
        System.out.println("Drive: " + toSetL);
    }
    
    @Override
    protected void end(){
        System.out.println("Finished Drive: " + toSetL);
    }
    
    @Override
    protected boolean isFinished() {
        return (Robot.driveBase.getEncoderPositionL() >= toSetL*.98 && Robot.driveBase.getEncoderPositionR() >= toSetR*.98);
    }
}
