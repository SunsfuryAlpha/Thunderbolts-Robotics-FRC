package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Auto_test extends OpMode {
	DcMotor leftTank;
	DcMotor rightTank;
	
	public static String driveType = "tank";
	public static boolean driveDirection = true;
	
	public void backward(speed) {
		switch(driveType) {
		case "tank":
		default:
			if (driveDirection) {
				rightTank.setSpeed(-speed);
				leftTank.setSpeed(-speed);
			} else {
				rightTank.setSpeed(speed);
				leftTank.setSpeed(speed);
			}
		}
	}
	
	public void forward(speed) {
		switch(driveType) {
		case "tank":
		default:
			if (driveDirection) {
				rightTank.setSpeed(speed);
				leftTank.setSpeed(speed);
			} else {
				rightTank.setSpeed(-speed);
				leftTank.setSpeed(-speed);
			}
		}
	}
	
	@Override
	public void init() {
		switch(driveType) {
		case "tank":
		default:
			leftTank = hardwareMap.dcMotor.get("left");
			rightTank = hardwareMap.dcMotor.get("right");
		}
		startup();
	}
	
	public void reverseDrive() {
		switch(driveType) {
		case "tank":
		default:
			if (driveDirection) {
				rightTank.setDirection(DcMotor.Direction.REVERSE);
				leftTank.setDirection(DcMotor.Direction.FORWARD);
			} else {
				rightTank.setDirection(DcMotor.Direction.FORWARD);
				leftTank.setDirection(DcMotor.Direction.REVERSE);
			}
		}
		driveDirection *= -1;
	}
	
	public void startup() {}
	
	public void turnLeft(speed) {
		switch(driveType) {
		case "tank":
		default:
			if (driveDirection) {
				rightTank.setSpeed(speed);
				leftTank.setSpeed(-speed);
			} else {
				rightTank.setSpeed(-speed);
				leftTank.setSpeed(speed);
			}
		}
	}
	
	public void forward(speed) {
		switch(driveType) {
		case "tank":
		default:
			if (driveDirection) {
				rightTank.setSpeed(-speed);
				leftTank.setSpeed(speed);
			} else {
				rightTank.setSpeed(speed);
				leftTank.setSpeed(-speed);
			}
		}
	}
}
