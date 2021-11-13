package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class AITank1 extends Tank {
	
	private double speed;
	private double angularSpeed;

	public AITank1(String id, double x, double y, double angle) {
		super(id, x, y, angle);
		this.speed = Constants.TANK_MOVEMENT_SPEED;
		this.angularSpeed = Constants.TANK_TURN_SPEED;
		this.cooldown = 0;
	}

	@Override
	public void move(GameWorld gameWorld) {
		
		PlayerTank playerTank = (PlayerTank) gameWorld.getEntity(Constants.PLAYER_TANK_ID);
		
		double dx = playerTank.getX() - getX();
		double dy = playerTank.getY() - getY();
		
		//Allows AI Tank to lead the player tank
		dx = dx - playerTank.getXVelocity() * dy/Constants.SHELL_MOVEMENT_SPEED;
		dy = dy - playerTank.getYVelocity() * dx/Constants.SHELL_MOVEMENT_SPEED;
		
		
		double angleToPlayer = Math.atan2(dy, dx);
		double angleDifference = getAngle() - angleToPlayer;
		// We want to keep the angle difference between -180 degrees and 180
		// degrees for the next step. This ensures that anything outside of that
		// range is adjusted by 360 degrees at a time until it is, so that the
		// angle is still equivalent.
		angleDifference -= Math.floor(angleDifference / Math.toRadians(360.0) + 0.5) * Math.toRadians(360.0);
		// The angle difference being positive or negative determines if we turn
		// left or right. However, we don’t want the Tank to be constantly
		// bouncing back and forth around 0 degrees, alternating between left
		// and right turns, so we build in a small margin of error.
		if (angleDifference < -Math.toRadians(3.0)) {
		turnRight(Constants.TANK_TURN_SPEED);
		} else if (angleDifference > Math.toRadians(3.0)) {
		turnLeft(Constants.TANK_TURN_SPEED);
		}
		if(cooldown <= 0) {
			gameWorld.setShell(new NormalShell("AI1 Shell " + Integer.toString(shellID), getShellX(), getShellY(), getShellAngle()));
			shellID++;
			cooldown = 100;
		} else {
			cooldown--;
		}
		if(poweredup > 0) {
			poweredup--;
		}
		boundsCheck(gameWorld);
	}

}
