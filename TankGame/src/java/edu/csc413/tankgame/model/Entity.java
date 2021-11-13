package edu.csc413.tankgame.model;

/**
 * A general concept for an entity in the Tank Game. This includes everything that can move or be interacted with, such
 * as tanks, shells, walls, power ups, etc.
 */
public abstract class Entity {
	
	protected String id;
    protected double x;
    protected double y;
    protected double angle;
    protected int health;
    
    protected Entity(String id, double x, double y, double angle) {
    	this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
	
    /** All entities can move, even if the details of their move logic may vary based on the specific type of Entity. */
    public abstract void move(GameWorld gameWorld);
    
    public abstract void boundsCheck(GameWorld gameWorld);
    
    public abstract double getXBound();
    
    public abstract double getYBound();
    
    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }
    
    public void setX(double x) {
    	this.x = x;
    }

    public double getY() {
        return y;
    }
    
    public void setY(double y) {
    	this.y = y;
    }

    public double getAngle() {
        return angle;
    }
    
    public void setAngle(double angle) {
    	this.angle = angle;
    }
    public void decrementHealth() {
    	this.health--;
    }
    public boolean isDead() {
    	return this.health == 0;
    }
    
}
