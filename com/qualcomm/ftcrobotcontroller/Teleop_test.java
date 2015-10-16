package com.qualcomm.ftcrobotcontroller;

import com.qualcomm.ftcrobotcontroller.CustomDrivebase;

public class Auto_test extends CustomDrivebase {
	@Override
	public static final String driveType = "tank";
	
	public static final int[] CONTROLMAP = {};
	
	@Override
	public void startup() {
		
	}
	
	@Override
	public void loop() {
		if (/* controller lstick 'KEYMAP[forward]' up */){
			if (/* controller rstick 'KEYMAP[forward]' up */) {
				forward(max(/* lstick, rstick */));
			} else if (/* controller rstick 'KEYMAP[forward]' down */) {
				turnRight(min(/* lstick, -rstick */));
			} else {
				turnRight(/* lstick */);
			}
		} else if (/* controller lstick 'KEYMAP[forward]' down */) {
			if (/* controller rstick 'KEYMAP[forward]' up */) {
				turnLeft(min(/* -lstick, rstick */));
			} else if (/* controller rstick 'KEYMAP[forward]' down */) {
				backward(max(/* lstick, rstick */));
			} else {
				turnLeft(/* -lstick */);
			}
		} else {
			if (/* rstick > 0 */) {
				turnLeft(/* rstick */);
			} else if () {
				turnRight(/* -rstick */);
			} else {
				stop();
			}
		}
	}
}
