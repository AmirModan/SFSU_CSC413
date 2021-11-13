package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class NormalShell extends Shell {
	
	private double velocity;
	private double powered;

	public NormalShell(String id, double x, double y, double angle) {
		super(id, x, y, angle);
		this.health = 1;
		this.velocity = Constants.SHELL_MOVEMENT_SPEED;
		this.powered = powered;
	}

	@Override
	public void move(GameWorld gameWorld) {
		moveForward(this.velocity);
		boundsCheck(gameWorld);
	}

}
