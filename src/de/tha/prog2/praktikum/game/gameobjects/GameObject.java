package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.util.XY;

public interface GameObject {
	
	public XY getLocation();
	
	public void updatePoints(int update);
	
	public int getID();
	
	public int getPoints();
	
	public void nextStep(GameObjectContext context);
	
	public boolean equals(GameObject gameObject);

	public char toChar();

	public void setLocation(XY location);
	
	public void collide(GameObject gameObject, GameObjectContext context);
	
	}
