package org.usfirst.frc.team4739.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;
import org.usfirst.frc.team4739.robot.CustomRobot;

/**
 * Controls 4739 Thunderbolts Robotics' robot, Zeus.
 *
 * @author Thunderbolts Robotics
 * @version 2.1 2015-06-23
 * @see CustomRobot
 * @see <a href="http://first.wpi.edu/FRC/roborio/release/docs/java/">WPILibJ Javadoc</a>
 */

public class Robot extends IterativeRobot {
	//Length of game periods
	public static final int AUTO_TIMEOUT_SECONDS = 15;
	public static final int TEST_TIMEOUT_SECONDS = AUTO_TIMEOUT_SECONDS;
	
	//Automatic stacking 'up-phase' duration and input buffer duration, respectively.
	public static final int AUTOWINCH_LIFTING_PHASE_TIME = 25;
	public static final int AUTOWINCH_INPUT_RESET_TIME = 75;

	//Keybindings for tank drive, deadzone, reverse toggles and analog-to-digital toggle.
	public static final int AXISBIND_TANKDRIVE_LEFT = 1;
	public static final int AXISBIND_TANKDRIVE_RIGHT = 5;
	public static final double AXISBIND_TANKDRIVE_DEADZONE = 1/20;
	public static final boolean AXISBIND_TANKDRIVE_LEFT_REVERSED = true;
	public static final boolean AXISBIND_TANKDRIVE_RIGHT_REVERSED = true;
	public static final boolean AXISBIND_FORCE_DIGITAL_DRIVE = false;
	
	//Keybindings for winch, deadzone, and analog-to-digital toggle.
	public static final int AXISBIND_LIFTER_DOWN = 2;
	public static final int AXISBIND_LIFTER_UP = (int)Math.floor(Math.PI); //The number 3 is cursed, see xkcd.com/1275
	public static final double AXISBIND_LIFTER_DEADZONE = 1/10;
	public static final boolean AXISBIND_FORCE_DIGITAL_LIFTER = false;
	
	//Keybindings for winch compensation increment and decrement.
	public static final int KEYBIND_LIFTER_BALANCE_MINUS = 5;
	public static final int KEYBIND_LIFTER_BALANCE_PLUS = 6;
	public static final int INPUT_BUFFER_DURATION = 25;
	
	//Construct a new CustomRobot: a class designed specifically for creating a simpler, extensible interface between the roboRIO and the 4739 software engineer.
	CustomRobot r = new CustomRobot();
	
	int autoCycle;
	int autowinchReset;
	int autowinchTimeout;
	int periodicCycle;
	int testCycle;
	
	double[] winchBalance = {0, 0.1, 0.15, 0.2};
	int winchBalanceIndex;
   
	/*Joystick left = new Joystick(0);
	Joystick right = new Joystick(1);*/
	Joystick controller = new Joystick(0);
	
	boolean autowinchActive;
	int inputBufferBalanceMinus = 0;
	int inputBufferBalancePlus = 0;
	
	
	
	//PID
	AnalogInput irSensor = new AnalogInput(0);
	
	
	
	//"It's like the Placebo Effect, but being an idiot." - Hamish
	public void autonomousInit () {
		 autoCycle = 0;
	}
	
	public void autonomousPeriodic () {
		if (autoCycle > 25) {
			if (autoCycle < 50) {
				r.winch(-0.4);
			} else if (autoCycle < 75) {
				r.winch(0.4);
			} else {
				r.winch(0.1);
			}
		} else {
			r.drive(0.5, 0.5);
		}
		autoCycle++;
	}
	
	public void robotInit () {
		winchBalanceIndex = 0;
	}
	
	public void teleopInit () {
		winchBalanceIndex = 0;
		inputBufferBalancePlus = 0;
		inputBufferBalanceMinus = 0;
	}
	
	public void teleopPeriodic () {
		r.drive(controller.getRawAxis(AXISBIND_TANKDRIVE_LEFT), controller.getRawAxis(AXISBIND_TANKDRIVE_RIGHT));
		
		if (AXISBIND_FORCE_DIGITAL_LIFTER) {
			if (controller.getRawAxis(AXISBIND_LIFTER_UP) > 0.5 && controller.getRawAxis(AXISBIND_LIFTER_UP) > winchBalanceIndex) {
				r.winch(0.5);
			} else if (r.winchLimiter.get() != true && controller.getRawAxis(AXISBIND_LIFTER_DOWN) > 0.5) {
				r.winch(-0.5);
			} else {
				r.winch(winchBalanceIndex);
			}
		} else {
			if (controller.getRawAxis(AXISBIND_LIFTER_UP) > AXISBIND_LIFTER_DEADZONE) {
				if (controller.getRawAxis(AXISBIND_LIFTER_UP) > winchBalanceIndex) {
					r.winch(controller.getRawAxis(AXISBIND_LIFTER_UP));
				} else {
					r.winch(winchBalance[winchBalanceIndex]);
				}
			} else if (r.winchLimiter.get() != true && controller.getRawAxis(AXISBIND_LIFTER_DOWN) > AXISBIND_LIFTER_DEADZONE) {
				r.winch(-controller.getRawAxis(AXISBIND_LIFTER_DOWN));
			} else {
				r.winch(winchBalance[winchBalanceIndex]);
			}
		}
		
		//Winch compensation increase
		if (inputBufferBalancePlus > 0 && controller.getRawButton(KEYBIND_LIFTER_BALANCE_PLUS) == false) {
			inputBufferBalancePlus--;
		} else if (controller.getRawButton(KEYBIND_LIFTER_BALANCE_PLUS) == true) {
			if (winchBalanceIndex < winchBalance.length) {
				winchBalanceIndex ++;
			}
			inputBufferBalancePlus = INPUT_BUFFER_DURATION;
		}
		
		//Winch compensation decrease
		if (inputBufferBalanceMinus > 0 && controller.getRawButton(KEYBIND_LIFTER_BALANCE_MINUS) == false) {
			inputBufferBalanceMinus--;
		} else if (controller.getRawButton(KEYBIND_LIFTER_BALANCE_MINUS) == true) {
			if (winchBalanceIndex > 0) {
				winchBalanceIndex --;
			}
			inputBufferBalanceMinus = INPUT_BUFFER_DURATION;
		}

		SmartDashboard.putNumber("test_irsensor_value", irSensor.getValue());
		SmartDashboard.putNumber("test_irsensor_voltage", irSensor.getValue());
		
		
		
		//**********************************************************************************Commented for later use..?
		/*if(controller.getRawAxis(3) > 0) {
			r.winch(controller.getRawAxis(3));
		} else if(r.winchLimiter.get() != true) {
			r.winch(-controller.getRawAxis(2));
		} else {
			r.winch(0);
		}
		
		if(controller.getRawButton(1)) {
			r.autoWinch();
		}*/
		
		/* if (autowinchTimeout == 0 && autowinchActive) { //ON
			if (r.winchLimiter.get() != true) {
				r.winch(0.5);
				r.winch(0.5);
				autowinchTimeout--;
			} else {
				autowinchActive = false;
				r.winch(0);
				r.winch(0);
				autowinchTimeout = AUTOWINCH_INPUT_RESET_TIME; //1.5s
				autowinchReset = AUTOWINCH_LIFTING_PHASE_TIME; //0.5s
			}
		} else if (autowinchReset != 0) {
			r.winch(-0.5);
			r.winch(-0.5);
			autowinchReset--;
		}
		
		if(controller.getRawButton(1)) {
			autowinchActive = true;
		} */
	}
	
	//"I would, but I can't, 'cause I have swimming." - Jacob
	public void testInit () {
		testCycle = 0;
		//glados is potato
	}
	
	//"Noodle fighting is only allowed in the immaturity room." - James
	public void testPeriodic () { 
		if (testCycle < 50 * TEST_TIMEOUT_SECONDS) {
			//continue testing
		}
		testCycle++;
	}
}