package de.tha.prog2.praktikum.game.core;

import de.tha.prog2.praktikum.game.exceptions.NoMoreBombsAllowed;
import de.tha.prog2.praktikum.game.gameobjects.Bomb;
import de.tha.prog2.praktikum.game.gameobjects.Bomberman;
import de.tha.prog2.praktikum.game.gameobjects.GameObject;
import de.tha.prog2.praktikum.game.util.XY;

public class ControllerContextImpl implements ControllerContext {
	private FlattenedBoard flattenedBoard;
	private Bomberman bomberman;

	public ControllerContextImpl(FlattenedBoard flattenedBoard, Bomberman bomberman) {
		this.flattenedBoard = flattenedBoard;
		this.bomberman = bomberman;
	}

	@Override
	public XY getViewLowerLeft() {
		XY currentPos = bomberman.getLocation();
		int oldX = currentPos.getX();
    	int oldY = currentPos.getY();
    	int fieldBorderY = flattenedBoard.getSize().getY() - 1;
    	int newX, newY;
    	
    	if(oldY + 15 >= fieldBorderY) {
    		newY = fieldBorderY;
    	} else {
    		newY = oldY + 15;
    	}
    	
    	if(oldX - 15 <= 0) {
    		newX = 0;
    	} else {
    		newX = oldX - 15;
    	}

    	return new XY(newX, newY);
	}

	@Override
	public XY getViewUpperRight() {
		XY currentPos = bomberman.getLocation();
		int oldX = currentPos.getX();
    	int oldY = currentPos.getY();
    	int fieldBorderX = flattenedBoard.getSize().getX() - 1;
    	int newX, newY;
    	
    	if(oldX + 15 >= fieldBorderX) {
    		newX = fieldBorderX;
    	} else {
    		newX = oldX + 15;
    	}
    	
    	if(oldY - 15 <= 0) {
    		newY = 0;
    	} else {
    		newY = oldY - 15;
    	}

    	return new XY(newX, newY);
	}

	@Override
	public Class<?> getGameObjectAt(int x, int y) {
		GameObject target = flattenedBoard.getGameObject(x, y);
		if(target == null) {
			return null;
		} else {
			return target.getClass();
		}
	}

	@Override
	public void move(XY direction) {
		flattenedBoard.move(bomberman, direction);
	}

	@Override
	public void plantBomb(XY location) {
		if(bomberman.getBombsAllowed() <= 0) {
			throw new NoMoreBombsAllowed("Trying to place a bomb while " + bomberman.getBombsAllowed() + " bombs are allowed to be placed!");
		}
		
		Bomb bomb = new Bomb(bomberman.getLocation(), bomberman.getBombStrength(), bomberman);
		
		bomberman.addBomb(bomb);
		bomberman.decrementBombsAllowed();
		
		flattenedBoard.addGameObject(bomb);
	}
	
	public void addGameObject(GameObject input) {
		flattenedBoard.addGameObject(input);
	}
}
