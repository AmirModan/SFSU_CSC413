package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public abstract class Shell extends Entity {
	
	private double velocity;

	public Shell(String id, double x, double y, double angle) {
		super(id, x, y, angle);
		this.health = 1;
		this.velocity = Constants.SHELL_MOVEMENT_SPEED;
	}
	
	protected void moveForward(double movementSpeed) {
        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);
    }
	
	public void boundsCheck(GameWorld gameWorld) {
		if(x < Constants.SHELL_X_LOWER_BOUND || x > Constants.SHELL_X_UPPER_BOUND || y < Constants.SHELL_Y_LOWER_BOUND || y > Constants.SHELL_Y_UPPER_BOUND) {
			gameWorld.removeEntity(id);
		}
	}

	@Override
	public double getXBound() {
		return x + Constants.SHELL_WIDTH;
	}

	@Override
	public double getYBound() {
		return y + Constants.SHELL_HEIGHT;
	}
}
