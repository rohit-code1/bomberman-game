package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.XY;

public class ExtraPoints extends PowerUp {

	public ExtraPoints(XY pos) {
		super(pos, PowerUpType.ExtraPoints);
//		System.out.println("ExtraPoints wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}
}
