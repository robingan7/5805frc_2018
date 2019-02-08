package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;
import org.usfirst.frc.team5805.robot.subsystems.DriveBase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnGyro extends PIDCommand {    
    public TurnGyro(int degrees){
        super(0.012, 0.0, 0.0);
        requires(Robot.driveBase);
                
        setInputRange(-180f, 180f);
        setSetpoint(degrees);
        this.getPIDController().setOutputRange(-0.8, 0.8);
//        this.getPIDController().setAbsoluteTolerance(1.0);
        this.getPIDController().setPercentTolerance(1);
        this.getPIDController().setToleranceBuffer(1000);
        this.getPIDController().setContinuous(true);
        this.getPIDController().enable();
    }
    
    @Override
    protected void initialize() {
        Robot.ahrs.zeroYaw();
        System.out.println("Starting turn to " + this.getPIDController().getSetpoint() + " degrees");
    }

    @Override
    protected double returnPIDInput() {
        return Robot.ahrs.getYaw();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.driveBase.arcadeDrive(0.0, -output);
    }

    @Override
    protected boolean isFinished() {
        System.out.println("\tTurn error: " + this.getPIDController().getError());
        return this.getPIDController().onTarget();
    }

    @Override
    protected void end() {
        System.out.println("Stopping turn to " + this.getPIDController().getSetpoint() + " degrees");
    }
}
