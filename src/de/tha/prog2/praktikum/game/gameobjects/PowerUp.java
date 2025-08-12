package de.tha.prog2.praktikum.game.gameobjects;

import java.util.Random;

import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.exceptions.IllegalMoveException;
import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.XY;

public class PowerUp extends AbstractGameObject{
	private PowerUpType type;

	public PowerUp(XY pos) {
		super(pos, 400);
		Random rand = new Random();
		int temp = rand.nextInt(3);
		if(temp == 0) {
			type = PowerUpType.ExtendBombStrength;
		}
		if(temp == 1) {
			type = PowerUpType.ExtraPoints;
		}
		if(temp == 2) {
			type = PowerUpType.MoreBombs;
		}
	}
	
	public PowerUp(XY pos, PowerUpType type) {
		super(pos, 400);
		this.type = type;
	}

	@Override
	public char toChar() { return '?'; } //? weil mystry PowerUp
	
	public void nextStep(GameObjectContext context) { 
		// zum überschreiben vom random herumlaufen in AbstractGameObject, kann später optimiert werden durch unterteilung in MovingObjects, etc.
		//throw new IllegalMoveException("Wall + " + this + " is not allowed to be moved!");
	}
	
	public PowerUpType getType() {
		return type;
	}
	
}
