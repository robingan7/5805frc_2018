package org.usfirst.frc.team5805.robot.commands;

import org.usfirst.frc.team5805.robot.autos.CrossBaseline;
import org.usfirst.frc.team5805.robot.autos.PosOneLeftScale;
import org.usfirst.frc.team5805.robot.autos.PosOneLeftSwitch;
import org.usfirst.frc.team5805.robot.autos.PosOneRightSwitch;
import org.usfirst.frc.team5805.robot.autos.PosThreeLeftSwitch;
import org.usfirst.frc.team5805.robot.autos.PosThreeRightSwitch;
import org.usfirst.frc.team5805.robot.autos.PosTwoLeftSwitch;
import org.usfirst.frc.team5805.robot.autos.PosTwoRightSwitch;

import edu.wpi.first.wpilibj.command.Command;

public class AutoSelector extends Command{
    String LED;
    int position;
    boolean leftSwitchOpen, rightSwitchOpen, leftScaleOpen, rightScaleOpen;
    public AutoSelector (String led, int location, boolean leftSwitch, boolean rightSwitch, boolean leftScale, boolean rightScale){
        LED = led;
        position = location;
        leftSwitchOpen = leftSwitch;
        leftScaleOpen = leftScale;
        rightSwitchOpen = rightSwitch;
        rightScaleOpen = rightScale;
    }
    @Override
    protected void initialize() {
        char R = 'R';
        char L = 'L';
        if (position == 1){
            if (LED.charAt(1) == L && leftScaleOpen){
                new PosOneLeftScale().start();
                System.out.println("Left Scale 1");
            }
            else if (LED.charAt(0) == R && rightSwitchOpen){
                new PosOneRightSwitch().start();
                System.out.println("Right Switch 1");
            }
            else if (LED.charAt(0) == L && leftSwitchOpen){
                new PosOneLeftSwitch().start();
                System.out.println("Left Switch 1");
            }
            else{
                new CrossBaseline().start();
                System.out.println("Cross Baseline");

            }
        }
        else if (position == 2){
            if (LED.charAt(0) == R && rightSwitchOpen){
                new PosTwoRightSwitch().start();
                System.out.println("Right Switch 2");
            }
            else if (LED.charAt(0) == L && leftSwitchOpen){
                new PosTwoLeftSwitch().start();
                System.out.println("Left Switch 2");

            }
            else{
                new CrossBaseline().start();
                System.out.println("Cross Baseline");

            }
        }
        else if(position == 3){
            if (LED.charAt(1) == R && rightScaleOpen){
                //Right Scale Auto
                System.out.println("Right Scale 3");
            }
            else if (LED.charAt(0) == L && leftSwitchOpen){
                new PosThreeLeftSwitch().start();
                System.out.println("Left Switch 3");
            }
            else if (LED.charAt(0) == R && rightSwitchOpen){
                new PosThreeRightSwitch().start();
                System.out.println("Right Switch 3");
            }
            else{
                new CrossBaseline().start();
                System.out.println("Cross Baseline");

            }
        }
        
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
}

