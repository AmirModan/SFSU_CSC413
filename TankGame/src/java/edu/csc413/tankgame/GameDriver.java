package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.*;

import java.awt.event.ActionEvent;
import java.util.List;

public class GameDriver {
    private final MainView mainView;
    private final RunGameView runGameView;
    private GameWorld gameWorld;
    private int wallID;
    private int gameOverCount = 200;
    private KeyboardReader keyboard = KeyboardReader.instance();
    private boolean paused;
	public GameDriver() {
        mainView = new MainView(this::startMenuActionPerformed);
        runGameView = mainView.getRunGameView();
        gameWorld = new GameWorld();
        paused = false;
    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_GAME_SCREEN);
    }

    private void startMenuActionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
        	case StartMenuView.RESUME_BUTTON_ACTION_COMMAND -> paused = false;
            case StartMenuView.START_BUTTON_ACTION_COMMAND -> runGame();
            case StartMenuView.EXIT_BUTTON_ACTION_COMMAND -> mainView.closeGame();
            default -> throw new RuntimeException("Unexpected action command: " + actionEvent.getActionCommand());
        }
    }

    public void pauseGame() {
        mainView.setScreen(MainView.Screen.PAUSE_MENU_SCREEN);
        paused = true;
        while(paused) {
    		
    	}
    }
    
    private void runGame() {
        mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
        Runnable gameRunner = () -> {
            setUpGame();
            while (updateGame()) {
                runGameView.repaint();
                if(keyboard.escapePressed()) {
                	pauseGame();
                }
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            resetGame();
        };
        new Thread(gameRunner).start();
    }

    /**
     * setUpGame is called once at the beginning when the game is started. Entities that are present from the start
     * should be initialized here, with their corresponding sprites added to the RunGameView.
     */
    private void setUpGame() {
        // TODO: Implement.
    	gameWorld.addEntity(new PlayerTank(Constants.PLAYER_TANK_ID, Constants.PLAYER_TANK_INITIAL_X, Constants.PLAYER_TANK_INITIAL_Y, Constants.PLAYER_TANK_INITIAL_ANGLE, this.keyboard));
    	runGameView.addSprite(Constants.PLAYER_TANK_ID, "player-tank.png", Constants.PLAYER_TANK_INITIAL_X, Constants.PLAYER_TANK_INITIAL_Y, Constants.PLAYER_TANK_INITIAL_ANGLE);
    	
    	gameWorld.addEntity(new AITank1(Constants.AI_TANK_1_ID, Constants.AI_TANK_1_INITIAL_X, Constants.AI_TANK_1_INITIAL_Y, Constants.AI_TANK_1_INITIAL_ANGLE));
    	runGameView.addSprite(Constants.AI_TANK_1_ID, "ai-tank.png", Constants.AI_TANK_1_INITIAL_X, Constants.AI_TANK_1_INITIAL_Y, Constants.AI_TANK_1_INITIAL_ANGLE);
    	
    	gameWorld.addEntity(new AITank2(Constants.AI_TANK_2_ID, Constants.AI_TANK_2_INITIAL_X, Constants.AI_TANK_2_INITIAL_Y, Constants.AI_TANK_2_INITIAL_ANGLE));
    	runGameView.addSprite(Constants.AI_TANK_2_ID, "ai-tank.png", Constants.AI_TANK_2_INITIAL_X, Constants.AI_TANK_2_INITIAL_Y, Constants.AI_TANK_2_INITIAL_ANGLE);
    	
    	gameWorld.addEntity(new PowerUp("Power Up", 300, 300));
    	runGameView.addSprite("Power Up", "shell-explosion-1.png", 300, 300, 0);
    	
    	
    	for(WallInformation wallInfo : WallInformation.readWalls()) {
    		gameWorld.addEntity(new Wall("Wall " + wallID, wallInfo.getX(), wallInfo.getY()));
    		runGameView.addSprite("Wall " + wallID, wallInfo.getImageFile(), wallInfo.getX(), wallInfo.getY(), 0);
    		wallID++;
    	}
    }

    /**
     * updateGame is repeatedly called in the gameplay loop. The code in this method should run a single frame of the
     * game. As long as it returns true, the game will continue running. If the game should stop for whatever reason
     * (e.g. the player tank being destroyed, escape being pressed), it should return false.
     */
    private boolean updateGame() {
        // TODO: Implement.
    	while(!gameWorld.getRemoved().isEmpty()) {
			Entity entity = gameWorld.getRemoved().get(0);
			runGameView.removeSprite(entity.getId());
			gameWorld.setRemoved(entity);
		}
    	if(gameWorld.gameLost() || gameWorld.gameWon()) {
    		if(gameOverCount == 0) {
    			gameOverCount = 200;
    			return false;
    		} else if(gameOverCount == 100) {
    			if(gameWorld.gameLost()) {
    				runGameView.addSprite("Loss", "win.jpg", 0, 0, 0);
    			} else {
    				runGameView.addSprite("Win", "win.jpg", 0, 0, 0);
    			}
    			
    			gameOverCount--;
    			return true;
    		} else {
    			gameOverCount--;
    			return true;
    		}
    	} 
    	for(int i = 0; i < gameWorld.getEntities().size(); i++) {
    		Entity entity = gameWorld.getEntities().get(i);
    		entity.move(gameWorld);
    	}
    	
    	if(gameWorld.getShell() != null) {
    		Shell shell = gameWorld.getShell();
    		gameWorld.addEntity(shell);
    		runGameView.addSprite(shell.getId(), "shell.png", shell.getX(), shell.getY(), shell.getAngle());
    		
    		gameWorld.setShell(null);
    	}
    	for(int i = 0; i < gameWorld.getEntities().size(); i++) {
    		Entity entity = gameWorld.getEntities().get(i);
    		runGameView.setSpriteLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
    	}
    	List<Entity> entities = gameWorld.getEntities();
    	for(int i = 0; i < entities.size(); i++) {
    		Entity entity1 = entities.get(i);
    		for(int j = i + 1; j < entities.size(); j++) {
    			Entity entity2 = entities.get(j);
    			
    			while(gameWorld.entitiesOverlap(entity1, entity2)) {
    				handleCollision(entity1, entity2);
    				break;
    			}
    		}
    	}
    	
        return true;
    }

    /**
     * resetGame is called at the end of the game once the gameplay loop exits. This should clear any existing data from
     * the game so that if the game is restarted, there aren't any things leftover from the previous run.
     */
    private void resetGame() {
        // TODO: Implement.
        runGameView.reset();
        gameWorld = new GameWorld();
    }
    
    private void handleCollision(Entity entity1, Entity entity2) {
    	if (entity1 instanceof Shell || entity2 instanceof Shell) {
    		entity1.decrementHealth();
    		entity2.decrementHealth();
    		if(entity1.isDead()) {
        		gameWorld.removeEntity(entity1.getId());
        		if(entity1 instanceof Shell) {
        			runGameView.addAnimation(runGameView.SHELL_EXPLOSION_ANIMATION, runGameView.SHELL_EXPLOSION_FRAME_DELAY, entity1.getX(), entity1.getY());
        		} else {
        			runGameView.addAnimation(runGameView.BIG_EXPLOSION_ANIMATION, runGameView.BIG_EXPLOSION_FRAME_DELAY, entity1.getX(), entity1.getY());
        		}
    		}
    		if(entity2.isDead()) {
        		gameWorld.removeEntity(entity2.getId());
        		if(entity2 instanceof Shell) {
        			runGameView.addAnimation(runGameView.SHELL_EXPLOSION_ANIMATION, runGameView.SHELL_EXPLOSION_FRAME_DELAY, entity2.getX(), entity2.getY());
        		} else {
        			runGameView.addAnimation(runGameView.BIG_EXPLOSION_ANIMATION, runGameView.BIG_EXPLOSION_FRAME_DELAY, entity2.getX(), entity2.getY());
        		}
    		}
    	} else if (entity1 instanceof Tank && entity2 instanceof Tank) {
    		double distance = Math.abs(entity1.getXBound() - entity2.getX());
    		int caseNum = 1;
    		if (distance > Math.abs(entity1.getX() - entity2.getXBound())) {
    			distance = Math.abs(entity1.getX() - entity2.getXBound());
    			caseNum = 2;
    		}
    		if (distance > Math.abs(entity1.getYBound() - entity2.getY())) {
    			distance = Math.abs(entity1.getYBound() - entity2.getY());
    			caseNum = 3;
    		}
    		if (distance > Math.abs(entity1.getY() - entity2.getYBound())) {
    			distance = Math.abs(entity1.getY() - entity2.getYBound());
    			caseNum = 4;
    		}
    		
    		distance /= 2;
    		
    		switch(caseNum) {
    		case 1:
    			entity1.setX(entity1.getX() - distance);
    			entity2.setX(entity2.getX() + distance);
    			break;
    		case 2:
    			entity1.setX(entity1.getX() + distance);
    			entity2.setX(entity2.getX() - distance);
    			break;
    		case 3:
    			entity1.setY(entity1.getY() - distance);
    			entity2.setY(entity2.getY() + distance);
    			break;
    		case 4:
    			entity1.setY(entity1.getY() + distance);
    			entity2.setY(entity2.getY() - distance);
    			break;
    		}
    		
    	} else if (entity1 instanceof Tank && entity2 instanceof Wall || entity1 instanceof Wall && entity2 instanceof Tank) {
    		Entity tank, wall;
    		if(entity1 instanceof Tank && entity2 instanceof Wall) {
    			tank = entity1;
    			wall = entity2;
    		} else {
    			tank = entity2;
    			wall = entity1;
    		}
    		
    		double distance = Math.abs(tank.getXBound() - wall.getX());
    		int caseNum = 1;
    		if (distance > Math.abs(tank.getX() - wall.getXBound())) {
    			distance = Math.abs(tank.getX() - wall.getXBound());
    			caseNum = 2;
    		}
    		if (distance > Math.abs(tank.getYBound() - wall.getY())) {
    			distance = Math.abs(tank.getYBound() - wall.getY());
    			caseNum = 3;
    		}
    		if (distance > Math.abs(tank.getY() - wall.getYBound())) {
    			distance = Math.abs(tank.getY() - wall.getYBound());
    			caseNum = 4;
    		}
    		
    		switch(caseNum) {
    		case 1:
    			tank.setX(tank.getX() - (distance));
    			break;
    		case 2:
    			tank.setX(tank.getX() + (distance));
    			break;
    		case 3:
    			tank.setY(tank.getY() - (distance));
    			break;
    		case 4:
    			tank.setY(tank.getY() + (distance));
    			break;
    		}
        } else if(entity1 instanceof Tank && entity2 instanceof PowerUp || entity1 instanceof PowerUp && entity2 instanceof Tank) {
        	Tank tank;
        	PowerUp powerup;
    		if(entity1 instanceof Tank && entity2 instanceof PowerUp) {
    			tank = (Tank) entity1;
    			powerup = (PowerUp) entity2;
    		} else {
    			tank = (Tank) entity2;
    			powerup = (PowerUp) entity1;
    		}
    		tank.powerUp();
    		gameWorld.removeEntity(powerup.getId());	
        }
   	}


    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}
