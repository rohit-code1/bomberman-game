import de.tha.prog2.praktikum.game.core.*;

import de.tha.prog2.praktikum.game.level.*;
import de.tha.prog2.praktikum.game.ui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Launcher extends Application {
	static GameImpl game;
	
	public static void main(String[] args) {
		LevelGenerator level = LevelGeneratorLoader.createGenerator(args[1]);
		Board board = new Board(level);
		GameState gameState = new GameState(board);
		GameImpl game;
		UI ui;
		if (args[0].equals("console-blocking")) {
			ui = new ConsoleUIImpl(true);
			Launcher.game = new GameImpl(gameState, ui);
			Launcher.game.setFPS(0);
			Launcher.game.run();
			
		}
		else if(args[0].equals("javafx-basic")){
			ui = new FxUI(false); // GUI
			Launcher.game = new GameImpl(gameState, ui);
			Application.launch(args);
		}
		else if(args[0].equals("javafx-sprite")){
			ui = new FxUI(true); // GUI
			Launcher.game = new GameImpl(gameState, ui);
			Application.launch(args);
		}
		else {
			System.err.print("Modus nicht bekannt " + args[0]);
			return;
		}
		
	}
	
	public static void startGame() {
		// Hier der Code der die Game−Loop steuert
		if(Launcher.game != null) {
			Thread t = new Thread(() -> {
				Launcher.game.run();
			});
			t.start();
		}
		else {
			System.err.print("Somethng went wrong \ngame is null.");
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		// Das ist nicht mehr dynamisch aber es funktioniert so.
		// Sowowol das JavaFX-Fenster als auch das Konsolen-Game wird beides ausgegeben
		Launcher.startGame();
		System.out.println(Thread.currentThread());
        primaryStage.setTitle("Bomberman-Game");
        FxUI fxui = (FxUI) Launcher.game.getUI();
        fxui.start(primaryStage);  
	}
}