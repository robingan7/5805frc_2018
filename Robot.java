/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5805.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5805.robot.subsystems.DriveBase;
import org.usfirst.frc.team5805.robot.subsystems.Elevator;
import org.usfirst.frc.team5805.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5805.robot.subsystems.Manipulator;
import org.usfirst.frc.team5805.robot.autos.*;
import org.usfirst.frc.team5805.robot.commands.AutoSelector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;


import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
    public static final DriveBase driveBase = new DriveBase();
    public static final Elevator elevator = new Elevator();
    public static final Manipulator manipulator = new Manipulator();
    public static OI m_oi;
    private SendableChooser<Integer> location;
    SendableChooser<Boolean> leftSwitch, rightSwitch, leftScale, rightScale;
    private boolean leftSwitchOpen, rightSwitchOpen, leftScaleOpen, rightScaleOpen;
    private int position;
    private DigitalInput elevatorLimit = new DigitalInput(0);
    String gameData;
    public static AHRS ahrs;

    
	@Override
	public void robotInit() {
//	    CameraServer.getInstance().startAutomaticCapture();
	    
	    joy = new Joystick(0);
        joy2 = new Joystick(1);
		m_oi = new OI();
		
		climbRight = new VictorSPX(17);
		climbLeft = new VictorSPX(18);
		
        ramp = new Solenoid (6);
        climb = new DoubleSolenoid(4, 5);
        
        test = new Compressor();
        
        location = new SendableChooser<Integer>();
        location.addDefault("Position: 1", 1);
        location.addObject("Position: 2", 2);
        location.addObject("Position: 3", 3);

        leftSwitch = new SendableChooser<Boolean>();
        leftSwitch.addDefault("Left Switch Open", true);
        leftSwitch.addObject("Left Switch Closed", false);
        
        rightSwitch = new SendableChooser<Boolean>();
        rightSwitch.addDefault("Right Switch Open", true);
        rightSwitch.addObject("Right Switch Closed", false);
        
        leftScale = new SendableChooser<Boolean>();
        leftScale.addDefault("Left Scale Open", true);
        leftScale.addObject("Left Scale Closed", false);
        
        rightScale = new SendableChooser<Boolean>();
        rightScale.addDefault("Right Scale Open", true);
        rightScale.addObject("Right Scale Closed", false);
        
        SmartDashboard.putData("Position", location);
        SmartDashboard.putData("Left Switch", leftSwitch);
        SmartDashboard.putData("Right Switch", rightSwitch);
        SmartDashboard.putData("Left Scale", leftScale);
        SmartDashboard.putData("Right Scale", rightScale);
        
        ahrs = new AHRS(SPI.Port.kMXP); 
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("gyro" , ahrs.getYaw());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
	    Robot.ahrs.zeroYaw();
	    
	    gameData = DriverStation.getInstance().getGameSpecificMessage();
	    position = location.getSelected();
	    leftSwitchOpen = leftSwitch.getSelected();
	    rightSwitchOpen = rightSwitch.getSelected();
	    leftScaleOpen = leftScale.getSelected();
	    rightScaleOpen = rightScale.getSelected();
	    new AutoSelector(gameData, position, leftSwitchOpen, rightSwitchOpen, leftScaleOpen, rightScaleOpen).start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public static Joystick joy, joy2;
	public static VictorSPX climbLeft, climbRight;
	public Solenoid driveSpeed, ramp;
	public DoubleSolenoid climb;
	public Compressor test;
	@Override
	public void teleopInit() {
	    SmartDashboard.putData("Position", location);
        SmartDashboard.putData("Left Switch", leftSwitch);
        SmartDashboard.putData("Right Switch", rightSwitch);
        SmartDashboard.putData("Left Scale", leftScale);
        SmartDashboard.putData("Right Scale", rightScale);
        
        Robot.ahrs.zeroYaw();
        Robot.driveBase.zeroSensor();
	}

	
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	       test.setClosedLoopControl(true);
	       
           double forward = joy.getRawAxis(1);
           double turn = -joy.getRawAxis(2);
           
           if (Robot.elevator.getEncoderPosition() <= -15000){
               forward = forward * 0.75;
               turn = turn * 0.75;
           }
           Robot.driveBase.arcadeDrive(forward, turn);
            
           Scheduler.getInstance().run();
           
           climbLeft.set(ControlMode.PercentOutput, joy2.getRawAxis(5));
           climbRight.set(ControlMode.PercentOutput, joy2.getRawAxis(5));
           
           if(Robot.joy2.getRawButton(13) && Robot.joy.getRawButton(13)){
               climb.set(Value.kReverse);
           }
           else if (Robot.joy2.getRawButton(3)){
               climb.set(Value.kForward);
           }
           
           if(Robot.joy2.getRawButton(5) && Robot.joy.getRawButton(5)){
               ramp.set(true);
           }
           else if (Robot.joy2.getRawButton(3)){
               ramp.set(false);
           }
           
           if (!elevatorLimit.get()){
               elevator.zeroSensor();
           }
               
   	}
	
           

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	    
	}
}
