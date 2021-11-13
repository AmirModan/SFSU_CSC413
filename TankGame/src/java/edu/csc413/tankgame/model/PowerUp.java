package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class PowerUp extends Entity {
	
	public PowerUp(String id, double x, double y) {
		super(id, x, y, 0);
		this.health = -1;
	}

	@Override
	public void move(GameWorld gameWorld) {
		
	}

	@Override
	public void boundsCheck(GameWorld gameWorld) {
		
	}

	@Override
	public double getXBound() {
		return x + Constants.POWERUP_WIDTH;
	}
	
	@Override
	public double getYBound() {
		return y + Constants.POWERUP_HEIGHT;
	}
}
