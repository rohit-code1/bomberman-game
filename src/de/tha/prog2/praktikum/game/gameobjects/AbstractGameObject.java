package de.tha.prog2.praktikum.game.gameobjects;

import java.util.Random;

import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.util.XY;

//Hier gemeinsame Verhalten des game objects einbauen
public abstract class AbstractGameObject implements GameObject {
//AbstracGameObject vererbt an Unterklasse folgende 3 Attribute.
	private static int nextFreeID = 0;
	protected final int ID;
	protected XY location;
	protected final int points;

	public AbstractGameObject(XY location, int points) {

		this.location = location;
		ID = nextFreeID++;
		this.points = points;
	}

	@Override
	public XY getLocation() {
		return location;
	}

	@Override
	public void updatePoints(int update) {
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public boolean equals(GameObject gameObject) {
		if (this.getID() == gameObject.getID()) {
			return true;
		}
		return false;
	}

///**
// * By this abtract nextStep() method one will get back in the Monster or Bomberman Class
// * if the default case in method nextStep(input) is called.
// * That is important to get back in the class an retry recursively the nextStep() method
// * with a valid movement.
// */
//@Override
//abstract public void nextStep();

	/**
	 * monster or bomberman object should be moved as wished if it is legitimate.
	 * Otherwise one has to enter aga
	 * 
	 * @param input
	 */
	public void nextStep(GameObjectContext context) {
		
		XY direction;
		
		Random random = new Random();
		char[] c = { 'w', 'a', 's', 'd' };
		char input = c[random.nextInt(c.length)];

			switch (input) {
			case 'w':
				direction = new XY(0,-1);
				break;
			case 'a':
				direction = new XY(-1,0);
				break;
			case 's':
				direction = new XY(0,1);
				break;
			case 'd':
				direction = new XY(1,0);
				break;
			default:
				direction = new XY(0,0);
				break;
			}
			
		context.move(this, direction);
		
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("GameObject = " + this.getClass().getSimpleName() + "; ID = " + ID + "; Location = " + location.getX()+ "," + location.getY() + "; Points = " + points);
		return result.toString();
	}
	

	public void setLocation(XY location) {
		this.location = location;
	}
	
	public void collide(GameObject gameObject, GameObjectContext context) {
		// Standardmäßig passiert bei einer Kollision nichts (bei Ausnahmen in den jeweiligen GameObjects überschrieben).
	}
}