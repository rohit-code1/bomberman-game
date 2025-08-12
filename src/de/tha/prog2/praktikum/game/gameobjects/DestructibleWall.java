package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.exceptions.IllegalMoveException;
import de.tha.prog2.praktikum.game.util.XY;

public class DestructibleWall extends AbstractGameObject{

	public DestructibleWall(XY pos) {
		super(pos, 100);
//		System.out.println("DestructibleWall wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}


	@Override
	public char toChar() {return '-'; } //Vorher D aber jetzt - wegen Übersichtlichkeit
	
	public void nextStep(GameObjectContext context) { 
		// zum überschreiben vom random herumlaufen in AbstractGameObject, kann später optimiert werden durch unterteilung in MovingObjects, etc.
//		throw new IllegalMoveException("Wall + " + this + " is not allowed to be moved!");
	} 
}
