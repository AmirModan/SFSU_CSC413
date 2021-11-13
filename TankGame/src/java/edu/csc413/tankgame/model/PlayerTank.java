package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class PlayerTank extends Tank {
	
	private double velocity;
	private double angularSpeed;
	private KeyboardReader keyboard;
	public PlayerTank(String id, double x, double y, double angle, KeyboardReader keyboard) {
		super(id, x, y, angle);
		this.velocity = 0;
		this.angularSpeed = Constants.TANK_TURN_SPEED;
		this.shellID = 0;
		this.keyboard = keyboard;
	}

	@Override
	public void move(GameWorld gameWorld) {
		
    	if(keyboard.upPressed()) {
    		moveForward(Constants.TANK_MOVEMENT_SPEED);
    		this.velocity = Constants.TANK_MOVEMENT_SPEED;
    	} else if(keyboard.downPressed()) {
    		moveBackward(Constants.TANK_MOVEMENT_SPEED);
    		this.velocity = -1 * Constants.TANK_MOVEMENT_SPEED;
    	} else {
    		this.velocity = 0;
    	}
    	if(keyboard.rightPressed()) {
    		turnRight(this.angularSpeed);
    	}
    	if(keyboard.leftPressed()) {
    		turnLeft(this.angularSpeed);
    	}
    	if(keyboard.spacePressed()) {
    		if(cooldown <= 0) {
    			if(poweredup > 0) {
    				gameWorld.setShell(new HomingShell("Player Shell " + Integer.toString(shellID), getShellX(), getShellY(), getShellAngle()));
    			} else {
    				gameWorld.setShell(new NormalShell("Player Shell " + Integer.toString(shellID), getShellX(), getShellY(), getShellAngle()));
    			}
        		shellID++;
        		cooldown = 100;
    		}
    	}
    	cooldown--;
		poweredup--;
		
    	boundsCheck(gameWorld);
		
	}
	
	public double getXVelocity() {
		return this.velocity * Math.cos(this.getAngle());
	}
	
	public double getYVelocity() {
		return this.velocity * Math.sin(this.getAngle());
	}

}
