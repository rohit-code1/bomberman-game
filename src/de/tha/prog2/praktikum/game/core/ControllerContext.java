package de.tha.prog2.praktikum.game.core;

import de.tha.prog2.praktikum.game.gameobjects.GameObject;
import de.tha.prog2.praktikum.game.util.XY;

public interface ControllerContext {
	
    public XY getViewLowerLeft();
    
    public XY getViewUpperRight();

    public Class<?> getGameObjectAt(int x, int y);

    public void move(XY direction) throws RuntimeException;

    public void plantBomb(XY location) throws RuntimeException;
    
    public void addGameObject(GameObject input);
}
