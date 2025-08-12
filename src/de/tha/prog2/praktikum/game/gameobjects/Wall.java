package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.exceptions.IllegalMoveException;
import de.tha.prog2.praktikum.game.util.XY;

public class Wall extends AbstractGameObject{

	public Wall(XY pos) {
		super(pos, 0);
//		System.out.println("Wall wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}

	@Override
	public char toChar() { return 'W'; }
	
	public void nextStep(GameObjectContext context) { 
		// zum überschreiben vom random herumlaufen in AbstractGameObject, kann später optimiert werden durch unterteilung in MovingObjects, etc.
		//throw new IllegalMoveException("Wall + " + this + " is not allowed to be moved!");
	} 
}
