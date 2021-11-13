package edu.csc413.tankgame.model;

import java.util.*;

import edu.csc413.tankgame.Constants;

/**
 * GameWorld holds all of the model objects present in the game. GameWorld tracks all moving entities like tanks and
 * shells, and provides access to this information for any code that needs it (such as GameDriver or entity classes).
 */
public class GameWorld {
    // TODO: Implement. There's a lot of information the GameState will need to store to provide contextual information.
    //       Add whatever instance variables, constructors, and methods are needed.
	private List<Entity> entities;
	private Shell shell;
	private List<Entity> removedEntities;
	
    public GameWorld() {
        // TODO: Implement.
    	this.entities = new ArrayList<>();
    	this.shell = null;
    	removedEntities = new ArrayList<>();
    }

    /** Returns a list of all entities in the game. */
    public List<Entity> getEntities() {
        // TODO: Implement.
        return this.entities;
    }

    /** Adds a new entity to the game. */
    public void addEntity(Entity entity) {
        // TODO: Implement.
    	this.entities.add(entity);
    }
    
    public void setShell(Shell shell) {
        // TODO: Implement.
    	this.shell = shell;    	
    }
    
    public Shell getShell() {
        // TODO: Implement.
    	return this.shell;    	
    }
    
    /** Returns the Entity with the specified ID. */
    public Entity getEntity(String id) {
        // TODO: Implement.
    	for(Entity entity: entities) {
    		if(id.equals(entity.getId())) {
    			return entity;
    		}
    	}
        throw new RuntimeException("Entity with ID# " + id + " does not exist.");
    }

    /** Removes the entity with the specified ID from the game. */
    public void removeEntity(String id) {
        // TODO: Implement.
    	this.removedEntities.add(getEntity(id));
    	this.entities.remove(getEntity(id));
    }
    
    public void setRemoved(Entity entity) {
        // TODO: Implement.
    	this.removedEntities.remove(entity);
    }
    
    
    
    public List<Entity> getRemoved() {
        // TODO: Implement.
    	return this.removedEntities;
    }
    
    public boolean entitiesOverlap(Entity entity1, Entity entity2) {
    	return (entity1.getX() < entity2.getXBound() && 
    			entity1.getXBound() > entity2.getX() && 
    			entity1.getY() < entity2.getYBound() && 
    			entity1.getYBound() > entity2.getY());
    }
    
    public boolean gameLost() {
    	try {
			this.getEntity(Constants.PLAYER_TANK_ID);
			
		} catch (Exception e) {
			return true;
		}
    	return false;
    }
    public boolean gameWon() {
    	for(Entity entity : this.entities) {
    		if(entity.getId().startsWith("ai")) {
    			return false;
    		}
    	}
    	return true;
    }
}
