package de.tha.prog2.praktikum.game.core;

import de.tha.prog2.praktikum.game.level.LevelGenerator;
import de.tha.prog2.praktikum.game.util.XY;

/**
 * Speichert alle Informationen über die Map, wie z.B. alle Figuren, die auf der Map platziert sind.
 */
public class Board {
	
	GameObjectSet gos;
	XY size;

	/**
	 * Ein neues Board wird erstellt. Boards speichern den aktuellen Map-Zustand.
	 * @param generator Der LevelGenerator, mit dem das Board inital erstellt werden soll (Anfangs-Mapzustand zum Spielstart).
	 */
	public Board(LevelGenerator generator) {
		gos = generator.generate();
		size = generator.getSize();
	}

	/**
	 * @return eine 2D Version des aktuellen Boards. Erlaubt es herauszufinden, welches GameObject gerade 
	 * auf einer bestimmten Koordinate steht.
	 */
	public FlattenedBoard flatten() {
		return new FlattenedBoard(size, gos);
	}
	
	public void update() {
		FlattenedBoard context = flatten();
		gos.nextStep(context);
	}

}
