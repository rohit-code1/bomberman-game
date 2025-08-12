package de.tha.prog2.praktikum.game.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import de.tha.prog2.praktikum.game.core.Board;
import de.tha.prog2.praktikum.game.core.BoardView;
import de.tha.prog2.praktikum.game.core.FlattenedBoard;
import de.tha.prog2.praktikum.game.core.Game;
import de.tha.prog2.praktikum.game.core.GamePausedControl;
import de.tha.prog2.praktikum.game.core.GameImpl;
import de.tha.prog2.praktikum.game.core.GameState;
import de.tha.prog2.praktikum.game.gameobjects.*;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.Ranking;
import de.tha.prog2.praktikum.game.util.XY;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FxUI extends Application implements UI {
	private Ranking ranking;
	private BoardView view;
	private Node gameField;
	private VBox liveRanking;
	private String statusLabel;
	private HBox statusBar;
	private boolean up, down, left, right, bomb;
	private Button pauseButton, resumeButton;
	private MenuItem pauseMenuItem, resumeMenuItem;
	private boolean sprite;
	private Map<Character, Image> spriteCache = new HashMap<>();
	private ImageView[][] imageViews;
	private Canvas canvas;
	private GraphicsContext gc;
	private int cellWidth = 20; // für canvas
    private int cellHeight = 20; // für canvas
    private final Object uiLock = new Object(); // lock Object für empfindliche Thread Zugriffe  ("Schlüssel")

	
	public FxUI() {}
	
	public FxUI(boolean is_Fxui_Sprite) {
		sprite = is_Fxui_Sprite;
		loadSprites();
	}
	
	@Override
	public MoveCommand getCommand() {
		synchronized(uiLock) {
			if(up) {
				return MoveCommand.UP;
			}
			if(down) {
				return MoveCommand.DOWN;
			}
			if(left) {
				return MoveCommand.LEFT;
			}
			if(right) {
				return MoveCommand.RIGHT;
			}
			if(bomb) {
				return MoveCommand.BOMB;
			}
			return null;
		}
	}

	@Override
	public void render(BoardView view) {
		// Diese Methode ist im Game-Loop und muss das GUI Game-Field immer updaten, nicht das ConsoleUI
		
		// Blatt 8 Aufgabe :
		// Die render(Boardview view) Methode wird vom Game-Thread innerhalb der run() Methode aufgerufen.
		// Allerdings sollte der Game-Thread die FxUI nicht ändern, da es die Aufgabe des JavaFX Threads ist, 
		// Änderungen an der UI vorzunehmen.
		// Aus diesem Grund wurde die Platform.runLater() Methode als Lambda Expression aufgerufen um den 
		// Game-Thread (Hintergrund-Thread) in JavaFX Thread umzuwandeln.
		// Das heißt diese Datenstruktur wird von der sowohl von dem JavaFX-Thread als auch Game-Thread bearbeitet.
		
		synchronized(uiLock){
			this.view = view;
			ranking = new Ranking((FlattenedBoard) view); //ACHTUNG HIER KÖNNTE EINE EXCEPTION KOMMEN FALLS ES ANDERE BoardView Realisierungen gibt
		    System.out.println("\n" + ranking.toString());
		    XY size = view.getSize();
		    System.out.println("size = " + size.toString());
		    for (int y = 0; y < size.getY(); y++) {
		        for (int x = 0; x < size.getX(); x++) {
		        	
		            GameObject temp = view.getGameObject(x, y);
		            char c = (temp == null ? ' ' : temp.toChar());
		            System.out.print(c); // Konsolenausgabe
		            
		        }
		        System.out.println();
		    } 
	}
		
	    // Verwendung für die Aktualisierung der GUI-Ausgabe 
	    Platform.runLater(() -> {
		    	updateGameField();
		    	updateLiveRanking();
		    	updateStatusBar();
	    	}
	    );
	}

	

	@Override
	public void start(Stage primaryStage) {
		BorderPane borderPane = new BorderPane();
		if (!sprite) {
			this.gameField = (GridPane) createGameField();
			borderPane.setCenter(gameField); // gameField
		}
		else if(sprite) {
			canvas = (Canvas) createGameField();
			Pane centerPane = new Pane(canvas); // extra erstellt damit die lightblue Farbe über das Canvas Format hinaus erhalten bleibt
			centerPane.setStyle("-fx-background-color: lightblue;");
			borderPane.setCenter(centerPane); // canvas gameField
		}
		this.liveRanking = createRanking();
		borderPane.setRight(liveRanking); // Ranking
		borderPane.setLeft(createLeftVBox()); // Controls und Legende
		this.statusBar = createStatusBar();
		borderPane.setBottom(statusBar); // Bottom label
		borderPane.setTop(createFileBar()); // File menubar
		
		Scene scene = new Scene(borderPane,1230, 700);
		// Was soll passieren, wenn die Tasten gedrückt werden?
		scene.setOnKeyPressed(event -> {
			// WASD + Bomb Inputs:
			switch(event.getCode()) {
				case W: 
					up = true;
					break;
				case A:
					left = true;
					break;
				case S: 
					down = true;
					break;
				case D:
					right = true;
					break;
				case B:
					bomb = true;
					break;
			}
			
			// Pause/Resume/Exit Shortcuts:
			if(event.isControlDown()) {
				if(event.getCode() == KeyCode.P) {
					GamePausedControl.pause();
				} else if(event.getCode() == KeyCode.R) {
					GamePausedControl.resume();
				} else if(event.getCode() == KeyCode.E) {
					Platform.exit();
					System.exit(0);
				}
			}
		});
		
		// Was soll passieren, wenn die Tasten losgelassen werden?
		scene.setOnKeyReleased(event -> {
			// WASD + Bomb Inputs:
			switch(event.getCode()) {
			case W: 
				up = false;
				break;
			case A:
				left = false;
				break;
			case S: 
				down = false;
				break;
			case D:
				right = false;
				break;
			case B:
				bomb = false;
				break;
		}
		});
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Shape Editor");
		primaryStage.show();
	}
	
	
	private VBox createRanking() {
		VBox vbox = new VBox();
		Label rankingLabel = new Label(ranking.toString());
		vbox.getChildren().add(rankingLabel);
		vbox.setPadding(new Insets(20));
		this.liveRanking = vbox;

		return vbox;
	}
	private void updateLiveRanking() {
		synchronized(uiLock) {
		ranking.updateRanking();
		this.liveRanking.getChildren().clear();
		Label rankingLabel = new Label(ranking.toString());
		Label label = new Label("Ranking");
		label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
		this.liveRanking.getChildren().addAll(label,rankingLabel);
		this.liveRanking.setBackground(new Background(
			    new BackgroundFill(Color.POWDERBLUE, null, Insets.EMPTY)
			));
		}
	}
	
	private VBox createLeftVBox() {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(createControls(), createLegend(), createRules());
		vbox.setBackground(new Background(
			    new BackgroundFill(Color.LIGHTBLUE, null, Insets.EMPTY)
			));
		return vbox;
	}
	
	private VBox createRules() {
		VBox vbox = new VBox();
		Label label = new Label("Rules");
		label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
		Label rules = new Label(
				"w - move forward\n"
				+ "s - move backward\n"
				+ "a - move left\n"
				+ "d - move right\n"
				+ "b - place bomb\n "
				+ "  (after bomb is placed you have to move,\n "
				+ "   to activate the bomb)\n"
		);
		vbox.getChildren().addAll(label, rules);
		vbox.setPadding(new Insets(20));
		return vbox;
	}

	private VBox createControls() {
		VBox vbox = new VBox();
		pauseButton = new Button("Pause");
		resumeButton = new Button("Resume");
		resumeButton.setDisable(true);
		
		pauseButton.setOnAction((event) -> {
			this.statusLabel = "Game paused";
			updateStatusBar();
			GamePausedControl.pause();
			pauseButton.setDisable(true);
			pauseMenuItem.setDisable(true);
			
			resumeButton.setDisable(false);
			resumeMenuItem.setDisable(false);
			
//			userInput[0] = true;
			/*synchronized (view) {
				try {
					view.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			});
		
		resumeButton.setOnAction((event) -> {
			this.statusLabel = "Game resumed";
			updateStatusBar();
			GamePausedControl.resume();
			resumeButton.setDisable(true);
			resumeMenuItem.setDisable(true);
			
			pauseButton.setDisable(false);
			pauseMenuItem.setDisable(false);
//			userInput[1] = true;
			/*synchronized (view) {
				view.notify();
			}*/
		});
		
		Label label = new Label("Controls");
		label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
		vbox.getChildren().addAll(label,pauseButton,resumeButton);
		vbox.setPadding(new Insets(20));

		return vbox;
		
	}
	
	private GridPane createLegend() {
		Label monster = new Label("Monster");
		Label handOperatedBomberman = new Label("HandOperatedBomberman");
		Label bombermanBot = new Label("BombermanBot");
		Label bomb = new Label("Bomb");
		Label blast = new Label("Blast");
		Label extendBombStrength = new Label("ExtendBombStrength");
		Label extraPoints = new Label("ExtraPoints");
		Label moreBombs = new Label("MoreBombs");
		Label wall = new Label("Wall");
		Label destructibleWall = new Label("Destructible Wall");

		GridPane gridPane = new GridPane();
//		gridPane.setBackground(new Background(
//			    new BackgroundFill(Color.LIGHTGREY, null, Insets.EMPTY)
//			));
		
		gridPane.setPadding(new Insets(20));
		Label label = new Label("Legend");
		label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
		gridPane.add(label, 0, 0); //1.Element, 2.Column, 3.Row

	
		if(sprite) {
			ImageView imgBlast = createImageView("/sprites/Blast_sprite.png");
			ImageView imgBomb = createImageView("/sprites/Bomb_sprite.png");
			ImageView imgBombermanBot = createImageView("/sprites/Bomberman_1_sprite.png");
			ImageView imgHandOperatedBomberman = createImageView("/sprites/Bomberman_sprite.png");
			ImageView imgDestructibleWall = createImageView("/sprites/DestructibleWall_sprite.png");
			ImageView imgEmpty = createImageView("/sprites/Empty_sprite.png");
			ImageView imgExtendBombStrength = createImageView("/sprites/ExtendBombStrength_sprite.png");
			ImageView imgExtraPoints = createImageView("/sprites/ExtraPoints_sprite.png");
			ImageView imgMonster = createImageView("/sprites/Monster_sprite.png");
			ImageView imgMoreBombs = createImageView("/sprites/MoreBombs_sprite.png");
			ImageView imgWall = createImageView("/sprites/Wall_sprite.png");
			
			gridPane.add(imgMonster, 0, 1);
			gridPane.add(monster, 1, 1);

			gridPane.add(imgHandOperatedBomberman, 0, 2);
			gridPane.add(handOperatedBomberman, 1, 2); 
			
			gridPane.add(imgBombermanBot, 0, 3); 
			gridPane.add(bombermanBot, 1, 3); 

			gridPane.add(imgBomb, 0, 4);
			gridPane.add(bomb, 1, 4);

			gridPane.add(imgBlast, 0, 5);
			gridPane.add(blast, 1, 5);

			gridPane.add(imgExtendBombStrength, 0, 6);
			gridPane.add(extendBombStrength, 1, 6);

			gridPane.add(imgExtraPoints, 0, 7);
			gridPane.add(extraPoints, 1, 7);

			gridPane.add(imgMoreBombs, 0, 8);
			gridPane.add(moreBombs, 1, 8);

			gridPane.add(imgWall, 0, 9);
			gridPane.add(wall, 1, 9);

			gridPane.add(imgDestructibleWall, 0, 10);
			gridPane.add(destructibleWall, 1, 10);


		}
		else {
			gridPane.add(createGameObjectSymbol(Color.RED), 0, 1);
			gridPane.add(monster, 1, 1);
			
			gridPane.add(createGameObjectSymbol(Color.DARKBLUE), 0, 2);
			gridPane.add(handOperatedBomberman, 1, 2); 
			
			gridPane.add(createGameObjectSymbol(Color.DARKKHAKI), 0, 3);
			gridPane.add(bomb, 1, 3);
			
			gridPane.add(createGameObjectSymbol(Color.BLACK), 0, 4);
			gridPane.add(blast, 1, 4);
			
			gridPane.add(createGameObjectSymbol(Color.LIMEGREEN), 0, 5);
			gridPane.add(extendBombStrength, 1, 5);
			
			gridPane.add(createGameObjectSymbol(Color.DARKGREEN), 0, 6);
			gridPane.add(extraPoints, 1, 6);
			
			gridPane.add(createGameObjectSymbol(Color.LIGHTGREEN), 0, 7);
			gridPane.add(moreBombs, 1, 7);
			
			gridPane.add(createGameObjectSymbol(Color.ORANGE), 0, 8);
			gridPane.add(wall, 1, 8);
			
			gridPane.add(createGameObjectSymbol(Color.DARKGRAY), 0, 9);
			gridPane.add(destructibleWall, 1, 9);	
		}
		
		return gridPane;
		
	}
	
	private Shape createGameObjectSymbol(Color color){
		if(color.equals(Color.DARKKHAKI)) {
			Circle circ = new Circle(10, color);
			return circ;
		}
		Rectangle rect = new Rectangle(20,20,color);
		return rect;
	}
	
	
	private HBox createStatusBar() {
		HBox hbox = new HBox();
		Label statusLabel = new Label();
		statusLabel.setText(this.statusLabel); // Dies soll noch dynamisch in Abhängigkeit der Controls implementiert werden
		hbox.getChildren().add(statusLabel);
		this.statusBar = hbox;
		return hbox;
	}
	
	private void updateStatusBar() {
		synchronized(uiLock) {
		if(this.statusBar != null) {
			this.statusBar.getChildren().clear();
			Label statusLabel = new Label();
			statusLabel.setText(this.statusLabel); // Dies soll noch dynamisch in Abhängigkeit der Controls implementiert werden
			statusBar.getChildren().add(statusLabel);
		}
	}
	}
	
	private Node createGameField() {
		int xSize = view.getSize().getX();
		int ySize = view.getSize().getY();

		//Verwendung bei !sprite
		GridPane gameField = new GridPane();
		gameField.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, Insets.EMPTY)));
		//Verwendung bei sprite
		canvas = new  Canvas(xSize * cellWidth, ySize * cellHeight);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.LIGHTSKYBLUE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
	    imageViews = new ImageView[ySize][xSize];
		
		for (int y = 0; y < ySize; y++) {
	        for (int x = 0; x < xSize; x++) {
	            GameObject temp = view.getGameObject(x, y);
	            char c = (temp == null ? ' ' : temp.toChar());
	            //In Bearbeitung --> Irgenwie gibts Probleme wenn man iv in gamefield hinzufügen möchte --> Performance?
	            if(sprite) {
//	            	ImageView iv = getGameObjectSprite(temp,c);
//	            	gameField.add(iv, x, y);
	            	ImageView iv = new ImageView();
	                iv.setFitWidth(20);
	                iv.setFitHeight(20);
	                iv.setPreserveRatio(true);
	                imageViews[y][x] = iv;
	                Image imageToShow = spriteCache.getOrDefault(c, spriteCache.get(' '));
//	                gameField.add(iv, x, y);
	                gc.drawImage(imageToShow, x * cellWidth, y * cellHeight, cellWidth, cellHeight);
	                this.gameField = canvas;
	            }
	            else {
	            	Color color = getGameObjectColor(temp, c);
	            	gameField.add(createGameObjectSymbol(color), x, y);
	            	gameField.setPadding(new Insets(20));
	            	this.gameField = gameField;
	            }
	            //Funktioniert immer
//	            Color color = getGameObjectColor(temp, c);
//	            gameField.add(createGameObjectSymbol(color), x, y);
	            
//	            gridPane.add(new Label(String.valueOf(c)), x, y); // Sieht wie die Konsolenausgabe aus
	        }
	    }
//		gridPane.setVgap(-8);
//		gridPane.setHgap(-4);
		return this.gameField;
	}
	
	private void updateGameField() {
		
		synchronized(uiLock) {
		    if (!sprite) {
		        ((GridPane) this.gameField).getChildren().clear();
		    } else {
		        // Ähnliches Effekt wie  .clear bei !sprite --> zeichnet Hintergrund neu --> Pseudo canvas löschen
		        gc.setFill(Color.LIGHTSKYBLUE);
		        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		    }
	
		    int xSize = view.getSize().getX();
		    int ySize = view.getSize().getY();
	
		    for (int y = 0; y < ySize; y++) {
		        for (int x = 0; x < xSize; x++) {
		            GameObject temp = view.getGameObject(x, y);
		            char c = (temp == null ? ' ' : temp.toChar());
	
		            if (sprite) {
		                Image imageToShow;
		                if (c != '?') {
		                    imageToShow = spriteCache.getOrDefault(c, spriteCache.get(' '));
		                } else {
		                    PowerUp powerUp = (PowerUp) temp;
		                    if (powerUp.getType() == PowerUpType.ExtendBombStrength) {
		                        imageToShow = spriteCache.getOrDefault('1', spriteCache.get(' '));
		                    } else if (powerUp.getType() == PowerUpType.MoreBombs) {
		                        imageToShow = spriteCache.getOrDefault('2', spriteCache.get(' '));
		                    } else {
		                        imageToShow = spriteCache.getOrDefault('3', spriteCache.get(' '));
		                    }
		                }
		                gc.drawImage(imageToShow, x * cellWidth, y * cellHeight, cellWidth, cellHeight);
		            } else {
		                Color color = getGameObjectColor(temp, c);
		                ((GridPane)gameField).add(createGameObjectSymbol(color), x, y);
		            }
		        }
		    }
		    ranking.updateRanking();
	
		    if (sprite) {
		        this.gameField = canvas;  // nur einmal setzen
		    } else {
		        this.gameField = gameField;
		    }
		}
	}

	
	//HashMaps sind viel effizienter und performanter
	private void loadSprites() {
	    spriteCache.put('M', new Image(getClass().getResourceAsStream("/sprites/Monster_sprite.png")));
	    spriteCache.put('B', new Image(getClass().getResourceAsStream("/sprites/Bomberman_sprite.png")));
	    spriteCache.put('N', new Image(getClass().getResourceAsStream("/sprites/Bomberman_1_sprite.png")));
	    spriteCache.put('Q', new Image(getClass().getResourceAsStream("/sprites/Bomb_sprite.png")));
	    spriteCache.put('*', new Image(getClass().getResourceAsStream("/sprites/Blast_sprite.png")));
	    spriteCache.put('W', new Image(getClass().getResourceAsStream("/sprites/Wall_sprite.png")));
	    spriteCache.put('-', new Image(getClass().getResourceAsStream("/sprites/DestructibleWall_sprite.png")));
	    spriteCache.put(' ', new Image(getClass().getResourceAsStream("/sprites/Empty_sprite.png")));
	    spriteCache.put('1', new Image(getClass().getResourceAsStream("/sprites/ExtendBombStrength_sprite.png")));
	    spriteCache.put('2', new Image(getClass().getResourceAsStream("/sprites/MoreBombs_sprite.png")));
	    spriteCache.put('3', new Image(getClass().getResourceAsStream("/sprites/ExtraPoints_sprite.png")));

	}

	
	private ImageView createImageView(String path) {
	    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
	    imageView.setFitWidth(20);
	    imageView.setFitHeight(20);
	    imageView.setPreserveRatio(true);
	    return imageView;
	}
	
	private ImageView getGameObjectSprite(GameObject temp, char c) {
		ImageView ig;
		if(c != '?') {
			ig = switch(c) {
			case'M' -> createImageView("/sprites/Monster_sprite.png");
			case'B' -> createImageView("/sprites/Bomberman_sprite.png");
			case'N' -> createImageView("/sprites/Bomberman_1_sprite.png");
			case'Q' -> createImageView("/sprites/Bomb_sprite.png");
			case'*' -> createImageView("/sprites/Blast_sprite.png");
			case'W' -> createImageView("/sprites/Wall_sprite.png");
			case'-' -> createImageView("/sprites/DestructibleWall_sprite.png");
			default -> createImageView("/sprites/Empty_sprite.png");
			};
		}
		else if(c == '?') {
			PowerUp powerUp = (PowerUp) temp;
			if(powerUp.getType() == PowerUpType.ExtendBombStrength) {
				ig = createImageView("/sprites/ExtendBombStrength_sprite.png");
			} else if(powerUp.getType() == PowerUpType.MoreBombs) {
				ig = createImageView("/sprites/MoreBombs_sprite.png");
			} else { // PowerUpType.ExtraPoints
				ig = createImageView("/sprites/ExtraPoints_sprite.png");
			}
		}
		else {ig = createImageView("/sprites/Empty_sprite.png");}
		return ig;
	}


	private Color getGameObjectColor(GameObject temp, char c) {
		Color color;
		if(c != '?') {
			color = switch(c) {
			case'M' -> Color.RED;
			case'B' -> Color.DARKBLUE;
			case'N' -> Color.DARKBLUE;
			case'Q' -> Color.DARKKHAKI;
			case'*' -> Color.BLACK;
			case'W' -> Color.ORANGE;
			case'-' -> Color.DARKGRAY;
			default -> Color.WHITE;
			};
		}
		else if(c == '?') {
			PowerUp powerUp = (PowerUp) temp;
			if(powerUp.getType() == PowerUpType.ExtendBombStrength) {
				color = Color.LIMEGREEN;
			} else if(powerUp.getType() == PowerUpType.MoreBombs) {
				color = Color.DARKGREEN;
			} else { // PowerUpType.ExtraPoints
				color = Color.LIGHTGREEN;
			}
		}
		else {color = Color.WHITE;}
		return color;
	}
	
	
	private MenuBar createFileBar() {
		pauseMenuItem = new MenuItem("Pause");
		resumeMenuItem = new MenuItem("Resume");
		MenuItem exitMenuItem = new MenuItem("Exit");
		resumeMenuItem.setDisable(true);
		
		pauseMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+P"));
		pauseMenuItem.setOnAction(event -> {
			this.statusLabel = "Game paused";
			updateStatusBar();
			GamePausedControl.pause();
			pauseButton.setDisable(true);
			pauseMenuItem.setDisable(true);
			
			resumeButton.setDisable(false);
			resumeMenuItem.setDisable(false);
			
		});
		
		resumeMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+R"));
		resumeMenuItem.setOnAction(event -> {
			this.statusLabel = "Game resumed";
			updateStatusBar();
			GamePausedControl.resume();
			resumeButton.setDisable(true);
			resumeMenuItem.setDisable(true);
			
			pauseButton.setDisable(false);
			pauseMenuItem.setDisable(false);
		});
		
		exitMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+E"));
		exitMenuItem.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});
		
		Menu menu = new Menu("File");
		menu.getItems().addAll(pauseMenuItem, resumeMenuItem,  new SeparatorMenuItem(), exitMenuItem);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		
		return menuBar;
	}

	@Override
	public boolean[] getUserInput() {
		boolean[] array = {up, down, left, right, bomb};
		return array;
	}

}
