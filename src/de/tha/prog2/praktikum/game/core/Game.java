package de.tha.prog2.praktikum.game.core;

/**
 * Beinhaltet GameEngine mit GameLoop
 */
public abstract class Game {
	GameState state;
	private static int FPS = 100; // 10 * 100  = 1000
	
	public static int getFPS() {
		return FPS;
	}

	public static void setFPS(int fPS) {
		FPS = fPS;
	}

	public Game(GameState state) {
		this.state = state;
	}
	
	/**
	 * Game-Loop; führt GameEngine aus.
	 */
	 public void run() {
		 while (true) {
            try {
                GamePausedControl.checkPaused();  //hier wird gewartet, wen das game pausiert wird
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			 
			 render();
			 processInput();
			 update();
			 try {
				 Thread.sleep(FPS);
			 } catch (InterruptedException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
		 }
	 }

	/**
	 * Stellt den Spielzustand auf dem Ausgabemedium dar.
	 */
	protected abstract void render();

	
	/**
	 * Verarbeitet Benutzereingaben.
	 */
	protected abstract void processInput();

	
	/**
	 * Verändert (ggf. unter Berücksichtigung der Eingabe) den aktuellen Spielzustand, d.h. sie bereit auf den nächsten Render-Vorgang vor
	 */
	private void update() {
		state.update();
	}

}
