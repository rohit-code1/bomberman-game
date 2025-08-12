package de.tha.prog2.praktikum.game.core;

import de.tha.prog2.praktikum.game.gameobjects.GameObject;
import de.tha.prog2.praktikum.game.util.XY;

public interface BoardView {

	/**
	 * Gibt das jeweilige Objekt zurück, dass auf der Koordinate (x,y) auf dem Board steht.
	 * @param x x-Koordinate des zurückzugebenden Objekts
	 * @param y y-Koordinate des zurückzugebenden Objekts
	 * @return das Objekt auf der Koordinate, wenn darauf kein Objekt ist, wird null zurückgegeben
	 */
	public GameObject getGameObject(int x, int y);
	
	/**
	 * @return die Größe des BoardView im XY-Format, wobei X die Breite und Y die Höhe darstellt.
	 */
	public XY getSize();
	
}
