package de.tha.prog2.praktikum.game.gameobjects;

import java.util.Random;
import java.util.Scanner;

import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.util.XY;

public class Monster extends AbstractGameObject{

	public Monster(XY pos) {
		super(pos, 300);
//		System.out.println("Monster wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}


	@Override
	public char toChar() { return 'M'; }
	
	@Override
	public void collide(GameObject gameObject, GameObjectContext context) {
		String target = gameObject.getClass().getSimpleName();
		
		switch(target) {
			case "Blast": 
				context.removeGameObject(this);
				
				Blast blast = (Blast) gameObject;
				if(blast.getOwner() != null) {
					blast.getOwner().updatePoints(getPoints()); // Bomberman bekommt Punkte des Monsters
				}
				break;
				
			case "HandOperatedBomberman":
			case "Bomberman":
				gameObject.collide(this, context); // Monster läuft auf Bomberman == Bomberman läuft auf Monster
				break;
		}
	}

}
