package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.SPI;

public class DriveStraightGyro extends Command implements PIDOutput{
    PIDController turnController = null;
    
    private int distance;
    private double speed = 0.0;
    
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.02;
    private static final double kF = 0.0;
    private static final double kToleranceDegrees = 0.0f;
    private double rotateToAngleRate = 0;
    private double targetAngle = 0;
    
    public DriveStraightGyro(double speed, int distance) {
        Robot.driveBase.zeroSensor();
        this.speed = -speed;
        this.distance = distance;
    }
    
    @Override
    protected void initialize() {
        /*?if (driveByTime) {
            DriverStation.reportError("Driving at " + speed + " for " + timeToDrive + " milliseconds.\n", false);
            endTime = (new Date()).getTime() + timeToDrive;
        } else {
            DriverStation.reportError("Driving at " + speed + " for " + inchesToDrive + " inches.\n", false);
            setDistance(inchesToDrive);
        }*/
        Robot.ahrs.zeroYaw();
        targetAngle = Robot.ahrs.getYaw();
        turnController = new PIDController(kP, kI, kD, kF, Robot.ahrs, this);
        turnController.setInputRange(-180f, 180f);
        turnController.setOutputRange(-0.088, 0.088);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.setSetpoint(targetAngle);
        turnController.enable();
        Robot.driveBase.zeroSensor();
        System.out.println("Starting drive to " + distance + " at speed " + speed);
        System.out.println("\tCurrent encoder pos: " + Robot.driveBase.getEncoderPositionL());
    }
    
    
    @Override
    protected void execute() {
        System.out.println("\tExecute: " + rotateToAngleRate);
        System.out.println("\tAngle Error: " + (targetAngle - Robot.ahrs.getYaw()));
        System.out.println("\tDist err: " + Math.abs(distance - Robot.driveBase.getEncoderPositionL()));
        Robot.driveBase.arcadeDrive(speed, -rotateToAngleRate);
    }
    
    @Override
    protected void end(){
        Robot.driveBase.arcadeDrive(0, 0);
        System.out.println("Stopping drive to " + distance + " at speed " + speed);
        Robot.driveBase.zeroSensor();

    }

    @Override
    protected boolean isFinished() {
        // error = Math.abs(target - currentPos)
        // if (error < someNUmber) return true
        // return false
        
        double distToDrive = Math.abs(distance);
        double curDistance = Math.abs(Robot.driveBase.getEncoderPositionL());
        
        if (distToDrive - curDistance > 0) {
            return false;
        } else {
            return true;
        }
        
//        if (Math.abs(Math.abs(distance) - Math.abs(Robot.driveBase.getEncoderPositionL())) < Math.abs(distance) * .02) {
//            return true;
//        } else {
//            return false;
//        }
        
//        if (Robot.driveBase.getEncoderPositionL() <= distance *.98 && speed<0){
//            System.out.println("stop " + distance);
//            return true;
//        } else if (Robot.driveBase.getEncoderPositionL() >= distance *.98 && speed>0){
//                System.out.println("stop " + distance);
//                return true;
//        } else {
//            return false;
//        }
    }

    @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
        
    }

}
