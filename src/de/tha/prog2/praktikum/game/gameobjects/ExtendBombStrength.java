package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.XY;

public class ExtendBombStrength extends PowerUp {

	public ExtendBombStrength(XY pos) {
		super(pos, PowerUpType.ExtendBombStrength);
//		System.out.println("ExtendBombStr wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}

}
