package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class HomingShell extends Shell {
	
	private double velocity;
	private double powered;

	public HomingShell(String id, double x, double y, double angle) {
		super(id, x, y, angle);
		this.health = 1;
		this.velocity = Constants.SHELL_MOVEMENT_SPEED;
		this.powered = powered;
	}

	@Override
	public void move(GameWorld gameWorld) {
		if(id.startsWith("Player")) {
			Tank ai = null;
			for(Entity entity : gameWorld.getEntities()) {
				if(entity.getId().startsWith("ai")) {
					ai = (Tank) entity;
					break;
				}
			}
			double dx = ai.getX() - getX();
			double dy = ai.getY() - getY();			
			
			double angleToAI = Math.atan2(dy, dx);
			double angleDifference = getAngle() - angleToAI;
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
			turnRight(Constants.HOMING_SHELL_TURN_SPEED);
			} else if (angleDifference > Math.toRadians(3.0)) {
			turnLeft(Constants.HOMING_SHELL_TURN_SPEED);
			}
		} else if(id.startsWith("ai")) {
			Tank player = (PlayerTank) gameWorld.getEntity(Constants.PLAYER_TANK_ID);;
			
			double dx = player.getX() - getX();
			double dy = player.getY() - getY();			
			
			double angleToAI = Math.atan2(dy, dx);
			double angleDifference = getAngle() - angleToAI;
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
			turnRight(Constants.HOMING_SHELL_TURN_SPEED);
			} else if (angleDifference > Math.toRadians(3.0)) {
			turnLeft(Constants.HOMING_SHELL_TURN_SPEED);
			}
		}
		moveForward(this.velocity);
		boundsCheck(gameWorld);
	}
	
	private void turnLeft(double turnSpeed) {
        angle -= turnSpeed;
    }

    private void turnRight(double turnSpeed) {
        angle += turnSpeed;
    }
}
