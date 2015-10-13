package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Auto_test extends OpMode {
	DcMotor leftTank = hardwareMap.dcMotor.get("left_drive");
	DcMotor rightTank = hardwareMap.dcMotor.get("right_drive");
	
	public boolean driveDirection = 1;
	
	@Override
	public void init() {
		startup();
	}
	
	public void startup() {
		
	}
	
	public void reverseDrive() {
		if (driveDirection > 0) {
			rightTank.setDirection(DcMotor.Direction.REVERSE); //TODO: check value in DcMotor.Direction
			leftTank.setDirection(DcMotor.Direction.FORWARD); //TODO: check name
		} else {
			rightTank.setDirection(DcMotor.Direction.FORWARD);
			leftTank.setDirection(DcMotor.Direction.REVERSE);
		}
	}
}
