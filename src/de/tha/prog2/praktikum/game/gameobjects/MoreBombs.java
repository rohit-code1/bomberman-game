package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.XY;

public class MoreBombs extends PowerUp {

	public MoreBombs(XY pos) {
		super(pos, PowerUpType.MoreBombs);
//		System.out.println("MoreBombs wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}

}
