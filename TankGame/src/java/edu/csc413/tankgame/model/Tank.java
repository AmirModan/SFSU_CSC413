package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

/** Entity class representing all tanks in the game. */
public abstract class Tank extends Entity {
    // TODO: Implement. A lot of what's below is relevant to all Entity types, not just Tanks. Move it accordingly to
    //       Entity class.
	protected static int shellID;
	protected int cooldown;
	protected int poweredup;

    public Tank(String id, double x, double y, double angle) {
    	super(id, x, y, angle);
    	this.health = 5;
    	this.cooldown = 0;
    	this.poweredup = 0;
    }

    

    // TODO: The methods below are provided so you don't have to do the math for movement. You should call these methods
    //       from the various subclasses of Entity in their implementations of move.

    protected void moveForward(double movementSpeed) {
        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);
    }

    protected void moveBackward(double movementSpeed) {
        x -= movementSpeed * Math.cos(angle);
        y -= movementSpeed * Math.sin(angle);
    }

    protected void turnLeft(double turnSpeed) {
        angle -= turnSpeed;
    }

    protected void turnRight(double turnSpeed) {
        angle += turnSpeed;
    }
    
    public int getCooldown() {
    	return cooldown;
    }
    
    @Override
    public void boundsCheck(GameWorld gameWorld) {
    	if(x < Constants.TANK_X_LOWER_BOUND) {
    		x = Constants.TANK_X_LOWER_BOUND;
    	} else if(x > Constants.TANK_X_UPPER_BOUND) {
    		x = Constants.TANK_X_UPPER_BOUND;
    	}
    	
    	if(y < Constants.TANK_Y_LOWER_BOUND) {
    		y = Constants.TANK_Y_LOWER_BOUND;
    	} else if(y > Constants.TANK_Y_UPPER_BOUND) {
    		y = Constants.TANK_Y_UPPER_BOUND;
    	}
    }
    
    @Override
    public double getXBound() {
		return x + Constants.TANK_WIDTH;
    }
    
    @Override
    public double getYBound() {
		return y + Constants.TANK_HEIGHT;
    }
    
    public void powerUp() {
    	this.poweredup = 500;
    }
    
    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.

    protected double getShellX() {
        return getX() + Constants.TANK_WIDTH / 2 + 45.0 * Math.cos(getAngle()) - Constants.SHELL_WIDTH / 2;
    }

    protected double getShellY() {
        return getY() + Constants.TANK_HEIGHT / 2 + 45.0 * Math.sin(getAngle()) - Constants.SHELL_HEIGHT / 2;
    }

    protected double getShellAngle() {
        return getAngle();
    }
}
