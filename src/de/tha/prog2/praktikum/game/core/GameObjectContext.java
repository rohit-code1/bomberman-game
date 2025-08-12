package de.tha.prog2.praktikum.game.core;

import de.tha.prog2.praktikum.game.gameobjects.Blast;
import de.tha.prog2.praktikum.game.gameobjects.Bomb;
import de.tha.prog2.praktikum.game.gameobjects.GameObject;
import de.tha.prog2.praktikum.game.util.XY;

public interface GameObjectContext {

	public XY getSize();
	
	public void move(GameObject gameObject, XY direction);
	
	public void addGameObject(GameObject gameObject);
	
	public void removeGameObject(GameObject gameObject);
	
	public void explode(Bomb bomb);
	
	public void stopExplosion();

	public void removeBlast( Blast blast );
	
}
