package org.usfirst.frc.team4739.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 * Controls 4739 Thunderbolts Robotics' robot, Zeus.
 *
 * @author Thunderbolts Robotics
 * @version 2.1 2015-06-23
 * @see Robot
 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/">WPILibJ Javadoc</a>
 */

public class CustomRobot {
	USBCamera camera = new USBCamera("cam0");
	
	Talon driveLeft = new Talon(0);
	Talon driveRight = new Talon(1);
	
	Victor winchA = new Victor(2);
	Victor winchB = new Victor(3);
	
	DigitalInput winchLimiter = new DigitalInput(0);
	
	BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();
	
	public double winchBalance = 0;
	
	public void initCamera(){
		camera.startCapture();
	}
	
	public void drive(double leftPower, double rightPower) {
		//Left is backwards
		if (Math.abs(leftPower) <= 0.1) {
			driveLeft.set(0);
		} else if (leftPower < 0) {
			driveLeft.set(-leftPower + 0.1);
		} else {
			driveLeft.set(-leftPower - 0.1);
		}
		if (Math.abs(rightPower) <= 0.1) {
			driveRight.set(0);
		} else if (rightPower < 0) {
			driveRight.set(rightPower + 0.1);
		} else {
			driveRight.set(rightPower - 0.1);
		}
		/*if (rightPower <= 0.98) {
			driveRight.set(-rightPower);
		} else {
			driveRight.set(-0.98);
		}*/
	}
	
	public void winch(double winchPower) {
		winchA.set(-winchPower);
		winchB.set(-winchPower);
	}
	
	public void autoWinch() {
		
	}
	
	public double[] getAccel() {
		double[] toReturn = {accelerometer.getX(), accelerometer.getY(), accelerometer.getZ()};
		return toReturn;
		//edu.wpi.first.wpilibj.Joystick.setRumble("left/right", "0-1");
	}
}