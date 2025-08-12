package de.tha.prog2.praktikum.game.core;

/**
 * Speichert den Zustand des Spiels in Nutzung von einem Board und dem Highscore.
 */
public class GameState {
	
	Board board;
	int highScore;
	
	/**
	 * Ein neues Spiel wird erstellt.
	 * @param board Das Board (= Map-Zustand/Spielfeld), welches dazu genutzt werden soll.
	 */
	public GameState(Board board) {
		this.board = board;
		highScore = 0;
	}
	
	
	public void update() {
		board.update();
		
	}
	
	public FlattenedBoard flattenBoard() {
		return board.flatten();
	}

}
