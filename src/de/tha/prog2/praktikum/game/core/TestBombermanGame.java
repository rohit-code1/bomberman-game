package de.tha.prog2.praktikum.game.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tha.prog2.praktikum.game.exceptions.IllegalMoveException;
import de.tha.prog2.praktikum.game.exceptions.NoMoreBombsAllowed;
import de.tha.prog2.praktikum.game.gameobjects.*;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.XY;

class TestBombermanGame {
	GameObjectSet gos;
	FlattenedBoard flattenedBoard;
	XY up = new XY(0,-1);
	XY down = new XY(0,+1);
	XY left = new XY(-1,0);
	XY right = new XY(+1,0);
	Wall wall0 = new Wall(new XY(2,0)); //get(0)
	Wall wall1 = new Wall(new XY(3,0)); //get(1)
	Wall wall2 = new Wall(new XY(4,0)); //get(2)
	Wall wall3 = new Wall(new XY(5,0)); //get(3)
	Wall wall4 = new Wall(new XY(6,0)); //get(4)
	HandOperatedBomberman hob = new HandOperatedBomberman(new XY(4,2)); //get(5)
	Monster monster = new Monster(new XY(5,2)); //get(6)
	PowerUp powerUp = new PowerUp(new XY(3,2)); //get(7)
	DestructibleWall destructibleWall = new DestructibleWall(new XY(4,3)); //get(8)
//	Bomberman bomberman = new Bomberman (new XY (4,1));
	
	@BeforeEach
	void setUp() {
		gos = new GameObjectSet();
		flattenedBoard = new FlattenedBoard(new XY(10,10), gos);
		flattenedBoard.addGameObject(wall0); //get(0)
		flattenedBoard.addGameObject(wall1); //get(1)
		flattenedBoard.addGameObject(wall2); //get(2)
		flattenedBoard.addGameObject(wall3); //get(3)
		flattenedBoard.addGameObject(wall4); //get(4)
		flattenedBoard.addGameObject(hob); //get(5)
		flattenedBoard.addGameObject(monster); //get(6)
		flattenedBoard.addGameObject(powerUp); //get(7)
		flattenedBoard.addGameObject(destructibleWall); //get(8)
//		flattenedBoard.addGameObject(bomberman); //get(8)
	}

	public void printBoard() {
		for(GameObject[] row : flattenedBoard.getGameBoard()) {
			for(GameObject obj : row) { 
				if(obj == null) System.out.print(" "); 
				if(obj != null) System.out.print(obj.toChar()); 
			}
			System.out.println();
		}
	}
	
	@Test
	void TestHandOperatedBombermanCollidesPowerUp() {
		try {
		hob.setMoveCommand(MoveCommand.LEFT);
		hob.nextStep(flattenedBoard); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
		assertEquals(powerUp.getLocation().getX(),hob.getLocation().getX(), hob + " has not taken the position of " + powerUp);
		assertEquals(powerUp.getLocation().getY(),hob.getLocation().getY(), hob + " has not taken the position of " + powerUp);
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}
	
	@Test
	void TestHandOperatedBombermanCollidesWall() {
		try {
			hob.setMoveCommand(MoveCommand.UP);
			hob.nextStep(flattenedBoard);
			hob.setMoveCommand(MoveCommand.UP); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
//			assertNotEquals(wall2.getLocation().getX(),hob.getLocation().getX(), hob + " should not take the position of " + wall2); // --> x-Koordinate ist gleich da nur Bewegung nach oben
			assertNotEquals(wall2.getLocation().getY(),hob.getLocation().getY(), hob + " should not take the position of " + wall2);
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method"); 
		}
	}
	
	@Test
	void TestHandOperatedBombermanCollidesBomberman() {
		try {
//			printBoard(); //DEBUG
			Bomberman bomberman = new Bomberman (new XY (4,1));
			flattenedBoard.addGameObject(bomberman);
//			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.UP); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision zwischen hob und Bomberman(NPC)
			hob.nextStep(flattenedBoard);
//			printBoard(); //DEBUG
//			assertNotEquals(wall2.getLocation().getX(),hob.getLocation().getX(), hob + " should not take the position of " + wall2); // --> x-Koordinate ist gleich da nur Bewegung nach oben
			assertNotEquals(bomberman.getLocation().getY(),hob.getLocation().getY(), hob + " should not take the position of " + bomberman);
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method"); 
		}
	}
	
	@Test
	void TestHandOperatedBombermanCollidesDestructibleWall() {
		try {
			hob.setMoveCommand(MoveCommand.DOWN);
			hob.nextStep(flattenedBoard); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
//			assertNotEquals(destructibleWall.getLocation().getX(),hob.getLocation().getX(), hob + " should not take the position of " + destructibleWall); // --> x-Koordinate ist gleich da nur Bewegung nach unten
			assertNotEquals(destructibleWall.getLocation().getY(),hob.getLocation().getY(), hob + " should not take the position of " + destructibleWall);
			assertEquals(-destructibleWall.getPoints(), hob.getPoints(), hob + "should have updated its points by -100. But has not!");

		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}
	
	@Test
	void TestHandOperatedBombermanCollidesMonster() {
		try {
			hob.setMoveCommand(MoveCommand.RIGHT);
			hob.nextStep(flattenedBoard); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
			assertNotEquals(monster.getLocation().getX(),hob.getLocation().getX(), hob + " should not take the position of " + monster); 
//			assertNotEquals(monster.getLocation().getY(),hob.getLocation().getY(), hob + " should not take the position of " + monster); // --> y-Koordinate ist gleich da nur Bewegung nach rechts
			assertEquals(-monster.getPoints(), hob.getPoints(), hob + "should have updated its points by -300. But has not!");
			
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}
	
	@Test
	void TestHandOperatedBombermanCollidesBomb() {
		try {
			hob.setMoveCommand(MoveCommand.BOMB);
			hob.nextStep(flattenedBoard);
			hob.setMoveCommand(MoveCommand.UP);
			hob.nextStep(flattenedBoard);
			hob.setMoveCommand(MoveCommand.DOWN);
			hob.nextStep(flattenedBoard); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
//			assertNotEquals(hob.getBomb().getLocation().getX(),hob.getLocation().getX(), hob + " should not take the position of " + hob.getBomb()); // --> x-Koordinate ist gleich da nur Bewegung nach unten zur Bomb
			// TODO: Kaan unten:
			//assertNotEquals(hob.getBomb().getLocation().getY(),hob.getLocation().getY(), hob + " should not take the position of " + hob.getBomb()); 
		
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}
	/*
	@Test
	void TestHandOperatedBombermanCollidesBlast() {
		try {
			
			hob.setMoveCommand(MoveCommand.BOMB);
//			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			hob.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			Bomb bomb = hob.getBomb();
			hob.setMoveCommand(MoveCommand.UP);
//			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			hob.nextStep(flattenedBoard);
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			hob.setMoveCommand(MoveCommand.LEFT);
//			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			hob.nextStep(flattenedBoard);
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			hob.setMoveCommand(MoveCommand.DOWN);
//			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			hob.nextStep(flattenedBoard); // Hier sollte hob den PowerUp nehmen
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			assertEquals(powerUp.getLocation().getX(),hob.getLocation().getX(), hob + " has not taken the position of " + powerUp);
			assertEquals(powerUp.getLocation().getY(),hob.getLocation().getY(), hob + " has not taken the position of " + powerUp);
			//Hier vllt noch mit switch statement die drei PowerUp Type mit Enum testen mit assertEquals und deren jeweiligen Effekte
			
			hob.setMoveCommand(MoveCommand.LEFT);
//			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			hob.nextStep(flattenedBoard);
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			hob.setMoveCommand(MoveCommand.RIGHT); // Jetzt sollte die Bombe explodieren
//			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			hob.nextStep(flattenedBoard); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
//			//NUR ZU DEBUGG ZWECKE KANN NACH SUCCES GELÖSCHT WERDEN -> STAND 11.05.25 UM 14 UHR GAB ES NOCH PROBLEME WENN MAN IM SPIEL GENAU DIESE BEWEGUNG MACHTE
//			assertDoesNotThrow(() -> hob.nextStep(flattenedBoard), "THIS SHOULD NOT THROW AN EXCEPTION!!!");
//			assertDoesNotThrow(() -> hob.setMoveCommand(MoveCommand.RIGHT), "THIS SHOULD NOT THROW AN EXCEPTION!!!");

			// Hier wird die Kollision mit Blast getestet
			assertEquals(new XY(3,2).getX(), hob.getLocation().getX(), hob + " should take the position of " + hob.getBomb()); 
//			assertEquals(new XY(3,2).getY(),hob.getLocation().getY(), hob + " should take the position of " + hob.getBomb()); // --> x-Koordinate ist gleich da nur Bewegung nach rechts zur Bomb bzw. Blast
			
			// Hier Achtung beim update der Points da eventuell PowerUp von oben das Ergebnis hier fälschen kann, deswegen oben schon die points in switch überprüfen und diesen Test abhängig vom vorherigen machen
			// Dieser Test funktioniert erst dann vernünftig, wenn die Kollision von HandOperatedBomberman und Blast korrekt geregelt ist.
			assertEquals(-200, hob.getPoints(), "HandOperatedBombmerman has not updated its point by -300 blast"); //--> -200 weil DestructibleWall zerstört +100 aber Kollision mit Blast -300 = -200
			
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + " Something might be wrong with the nextStep Method");
			e.printStackTrace();
		}
	}
	*/
	/*
	@Test
	void TestHandOperatedBombermanCollidesBlast2() {
		try {
			hob.setMoveCommand(MoveCommand.BOMB);
			hob.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			Bomb bomb = hob.getBomb();
			hob.setMoveCommand(MoveCommand.UP);
			hob.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			hob.setMoveCommand(MoveCommand.LEFT);
			hob.nextStep(flattenedBoard);
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			hob.setMoveCommand(MoveCommand.DOWN);
			hob.nextStep(flattenedBoard); // Hier sollte hob den PowerUp nehmen
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			assertEquals(powerUp.getLocation().getX(),hob.getLocation().getX(), hob + " has not taken the position of " + powerUp);
			assertEquals(powerUp.getLocation().getY(),hob.getLocation().getY(), hob + " has not taken the position of " + powerUp);
			//Hier vllt noch mit switch statement die drei PowerUp Type mit Enum testen mit assertEquals und deren jeweiligen Effekte
			
			hob.setMoveCommand(MoveCommand.RIGHT); // -> Führt dieser Schritt überhaupt zum Countdown, also die Kollision mit der Bomb
			hob.nextStep(flattenedBoard);
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			//Hier erneut die Kollision mit der Bomb testen
			assertNotEquals(hob.getBomb().getLocation().getX(),hob.getLocation().getX(), hob + " should not take the position of " + hob.getBomb()); 

			hob.setMoveCommand(MoveCommand.RIGHT);
			hob.nextStep(flattenedBoard); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			assertEquals(-hob.getBomb().getPoints(), hob.getPoints(), "HandOperatedBombmerman has not updated its point by -300 blast"); // hob hat zum ersten Mal Blast getroffen deshalb -300
			
			hob.setMoveCommand(MoveCommand.RIGHT);
			hob.nextStep(flattenedBoard); // Könnte Problem verursachen bei Fehler in Implementierung --> Hier passiert die Kollision
			bomb.nextStep(flattenedBoard);
			System.out.println(hob.getBomb().rounds);
			printBoard(); //DEBUGG
			
			// Bomb ist nun explodiert und der hob sollte in der Lage sein die Position von Bomb zu nehemen, aufgrund der Kollisions Regel zwischen HandOperatedBomberman und Blast
			assertEquals(hob.getBomb().getLocation().getY(),hob.getLocation().getY(), hob + " should not take the position of " + hob.getBomb()); 
			// Hier Achtung beim update der Points da eventuell PowerUp von oben das Ergebnis hier fälschen kann, deswegen oben schon die points in switch überprüfen und diesen Test abhängig vom vorherigen machen
			// Dieser Test funktioniert erst dann vernünftig, wenn die Kollision von HandOperatedBomberman und Blast korrekt geregelt ist.
			assertEquals((-hob.getBomb().getPoints())*2, hob.getPoints(), "HandOperatedBombmerman has not updated its point by -300 blast"); // hob hat zum zweiten Mal Blast getroffen deshalb -600
			
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}
	*/
	@Test
	void TestHandOperatedBombermanHasNoMoreBombsAllowedException() {
		try {
			hob.setMoveCommand(MoveCommand.BOMB);
			hob.nextStep(flattenedBoard);
			hob.setMoveCommand(MoveCommand.UP);
			hob.nextStep(flattenedBoard);
			hob.setMoveCommand(MoveCommand.UP);
			hob.nextStep(flattenedBoard);
			hob.setMoveCommand(MoveCommand.BOMB);
			assertThrows(NoMoreBombsAllowed.class, () -> hob.nextStep(flattenedBoard), "Placing a bomb is not allowed because: bombsAllowed = 0 " );
//			hob.nextStep(flattenedBoard);
			
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}
	
	
	@Test
	void testBombWithLargerStrength() {
		flattenedBoard.gameObjects.getDLL().clear();
		try {
			flattenedBoard.gameObjects.getDLL().clear();
			HandOperatedBomberman hob = new HandOperatedBomberman(new XY(7,5));
			PowerUp extend1 = new PowerUp(new XY(6,5), PowerUpType.ExtendBombStrength);
			PowerUp extend2 = new PowerUp(new XY(5,5), PowerUpType.ExtendBombStrength);
			flattenedBoard.addGameObject(hob);
			flattenedBoard.addGameObject(extend1);
			flattenedBoard.addGameObject(extend2);
			
			
			hob.setMoveCommand(MoveCommand.LEFT);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.LEFT);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.BOMB);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.RIGHT);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.UP);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			printBoard(); //DEBUG
//			assertThrows(NoMoreBombsAllowed.class, () -> hob.nextStep(flattenedBoard), "Placing a bomb is not allowed because: bombsAllowed = 0 " );
//			hob.nextStep(flattenedBoard);
			assertEquals("Blast", flattenedBoard.getGameObject(5,5).getClass().getName(), "The object should be instance of Blast but is " + flattenedBoard.getGameObject(5,5).getClass().getName());
			
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}

}
