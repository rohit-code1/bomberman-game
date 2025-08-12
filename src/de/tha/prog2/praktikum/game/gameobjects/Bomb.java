package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.exceptions.IllegalMoveException;
import de.tha.prog2.praktikum.game.util.XY;

public class Bomb extends AbstractGameObject{

	private int strength;
	private Bomberman owner;
	public int rounds = 5;
	
	public Bomb(XY pos, int strength, Bomberman owner) {
		super(pos, 300);
		this.strength = strength;
		this.owner = owner;
//		System.out.println("Bomb wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}
	
	public Bomb(XY pos) {
		super(pos, 300);
		strength = 1;
//		System.out.println("Bomb wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}

	@Override
	public char toChar() { return 'Q'; } //Q sieht aus wie eine Bombe mit Zündschnur
	
	@Override
	public void nextStep(GameObjectContext context) { 
		rounds--;
		if(rounds == 0) {
			context.explode(this);
		}
	} 
	
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public Bomberman getOwner() {
		return owner;
	}

}
