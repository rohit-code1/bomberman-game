package de.tha.prog2.praktikum.game.core;

import org.junit.jupiter.api.*;

import de.tha.prog2.praktikum.game.exceptions.*;
import de.tha.prog2.praktikum.game.gameobjects.*;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.XY;

import static org.junit.jupiter.api.Assertions.*;


public class TestFixture {
	GameObjectSet gos;
	FlattenedBoard flattenedBoard;
	
	@BeforeEach
	public void setUp() {
		gos = new GameObjectSet();
		flattenedBoard = new FlattenedBoard(new XY(10,10), gos);
		//Für Kollisionsprüfungen HandOperatedBomberman auf ... mit Effekte
		flattenedBoard.addGameObject(new HandOperatedBomberman(new XY(4,3))); //get(0)
		flattenedBoard.addGameObject(new Monster(new XY(4,2))); //get(1)
		flattenedBoard.addGameObject(new DestructibleWall(new XY(3,3))); //get(2)
		flattenedBoard.addGameObject(new Blast(new XY(5,3))); //get(3)
		flattenedBoard.addGameObject(new PowerUp(new XY(4,4))); //get(4)
		
		//Für Kollisionsprüfungen HandOperatedBomberman auf ... ohne Effekte
		flattenedBoard.addGameObject(new HandOperatedBomberman(new XY(2,8))); //get(5)
		flattenedBoard.addGameObject(new Bomberman(new XY(1,8))); //get(6)
		flattenedBoard.addGameObject(new Bomb(new XY(3,8))); //get(7)
		flattenedBoard.addGameObject(new Wall(new XY(2,9))); //get(8)
		
		//Für Kollisionsprüfungen Blast auf ...
//		flattenedBoard.addGameObject(new Blast(new XY(5,3))); //get(3) --> Es wird das gleiche Blast Objekt von oben verwendet
//		flattenedBoard.addGameObject(new HandOperatedBomberman(new XY(4,3))); //get(0) --> Es wird das gleiche HOB Objekt von oben verwendet
		flattenedBoard.addGameObject(new Monster(new XY(5,2))); //get(9)
		flattenedBoard.addGameObject(new DestructibleWall(new XY(6,3))); //get(10)
		flattenedBoard.addGameObject(new PowerUp(new XY(5,4))); //get(11)
		
		//Für Kollisionsprüfungen Monster auf ...
//		flattenedBoard.addGameObject(new HandOperatedBomberman(new XY(4,3))); //get(0) --> Es wird das gleiche HOB Objekt von oben verwendet
//		flattenedBoard.addGameObject(new Monster(new XY(4,2))); //get(1) --> Es wird das gleiche Monster Objekt von oben verwendet
//		flattenedBoard.addGameObject(new Blast(new XY(5,3))); //get(3) --> Es wird das gleiche Blast Objekt von oben verwendet
//		flattenedBoard.addGameObject(new Monster(new XY(5,2))); //get(9) --> Es wird das gleiche Monster Objekt von oben verwendet
		flattenedBoard.addGameObject(new DestructibleWall(new XY(3,2))); //get(12)
		flattenedBoard.addGameObject(new PowerUp(new XY(4,1))); //get(13)
		flattenedBoard.addGameObject(new Bomberman(new XY(5,1))); //get(14)
		flattenedBoard.addGameObject(new Bomb(new XY(6,2))); //get(15)
		
//		flattenedBoard = new FlattenedBoard(new XY(10,10), gos);
		
	}
	
	//Ob die Punkte des HandOperatedBomberman aktualisiert wird spielt in diesem spezifischen Test keine Rolle.
	//Hier diese Test Methode wurde refactored
	/*@Test
	public void hobCollidesOnGameObjectsTest() {
		//Bei diesen 4 Bewegungen werden die Punkte des HandOperatedBomberman aktualiesiert.
		handOperatedBombermanCollidesOnMonster();
		handOperatedBombermanCollidesOnDestructibleWall(); 
		handOperatedBombermanCollidesOnBlast();
		handOperatedBombermanCollidesOnPowerUp();
		//Ab hier kommen die Tests bei denen werder Bewegung noch andere Effekte (Punkte abziehen/hinzufügen) stattfinden
		handOperatedBombermanCollidesOnBomberman(); 
		handOperatedBombermanCollidesOnBomb(); 
		handOperatedBombermanCollidesOnWall(); 
	}*/
	
	//Eventuell gleich testen ob die Points sich bei der Kollion sich geupdated haben.
	
	@Test
	public void handOperatedBombermanCollidesOnMonster() {
		//HandOperatedBomberman collides on Monster
//		((HandOperatedBomberman) gos.get(0)).setMoveCommand(MoveCommand.UP);
//		((HandOperatedBomberman) gos.get(0)).nextStep(flattenedBoard);
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(0);
		Monster monster = (Monster) flattenedBoard.gameObjects.get(1);
		assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(hob, new XY(4,2)), 
				"HandOperatedBomberman collides on Monster: HandOperatedBomberman "+ hob +
				" is not allowed to take the Position of Monster " + monster);
		hob.updatePoints(600);
		hob.updatePoints(-monster.getPoints());
		assertEquals(300, hob.getPoints());
	}
	
	@Test
	public void handOperatedBombermanCollidesOnDestructibleWall() {
		//HandOperatedBomberman collides on DestructibleWall
//		((HandOperatedBomberman) gos.get(0)).setMoveCommand(MoveCommand.LEFT);
//		((HandOperatedBomberman) gos.get(0)).nextStep(flattenedBoard);
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(0);
		DestructibleWall destructibleWall = (DestructibleWall) flattenedBoard.gameObjects.get(2);
		assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(hob, new XY(3,3)), 
				"HandOperatedBomberman collides on DestructibleWall: HandOperatedBomberman "+ hob +
				" is not allowed to take the Position of DestructibleWall " + destructibleWall);
		
		hob.updatePoints(-destructibleWall.getPoints());
		assertEquals(-100, hob.getPoints());
	}
	
	@Test
	public void handOperatedBombermanCollidesOnBlast() {
		//HandOperatedBomberman collides on Blast
//		((HandOperatedBomberman) gos.get(0)).setMoveCommand(MoveCommand.RIGHT);
//		((HandOperatedBomberman) gos.get(0)).nextStep(flattenedBoard);
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(0);
		Blast blast = (Blast) flattenedBoard.gameObjects.get(3);
		try {
			flattenedBoard.move(hob, new XY(5,3));
		} catch (IllegalMoveException e) {
			System.err.println("This operation should not throw an IllegalMoveException. "
					+ "HandOperatedBomberman " + hob +  " should take the Position of the Blast " + blast + " , but it does not!");
		}
		
		hob.updatePoints(-blast.getPoints());
		assertEquals(-300, hob.getPoints());
	}
	
	@Test
	public void handOperatedBombermanCollidesOnPowerUp() {
		//HandOperatedBomberman collides on PowerUp
//		assertDoesNotThrow(flattenedBoard.move(gos.get(0), new XY(4,4)), "This should not throw an IllegalMoveException");
//		((HandOperatedBomberman) gos.get(0)).setMoveCommand(MoveCommand.DOWN);
//		((HandOperatedBomberman) gos.get(0)).nextStep(flattenedBoard);
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(0);
		PowerUp powerUp = (PowerUp) flattenedBoard.gameObjects.get(4);
		try {
			flattenedBoard.move(hob, new XY(5,3));
		} catch (IllegalMoveException e) {
			System.err.println("This operation should not throw an IllegalMoveException. "
					+ "HandOperatedBomberman " + hob +  " should take the Position of the PowerUp " + powerUp + " , but it does not!");
		}
		/*flattenedBoard.move(gos.get(0), new XY(4,4)); //Momementan wird bei dieser Aktion IllegalMoveException geworfen, sollte aber künftig nicht so sein
		assertEquals(new XY(4,4), gos.get(0).getLocation(), 
				"HandOperatedBomberman collides on PowerUp: HandOperatedBomberman "+ gos.get(0) +
				" should take the Position of PowerUp " + gos.get(2));*/
		
		hob.updatePoints(+powerUp.getPoints());
		assertEquals(0, hob.getPoints());
	}
	
	@Test
	public void handOperatedBombermanCollidesOnBomberman() {
		//HandOperatedBomberman collides on Bomberman
//		((HandOperatedBomberman) gos.get(5)).setMoveCommand(MoveCommand.LEFT);
//		((HandOperatedBomberman) gos.get(5)).nextStep(flattenedBoard);
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(5);
		Bomberman bomberman = (Bomberman) flattenedBoard.gameObjects.get(5);
		assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(hob, new XY(1,8)), 
				"HandOperatedBomberman collides on Bomberman: HandOperatedBomberman "+ hob +
				" is not allowed to take the Position of Bomberman " + bomberman);
	}
	
	@Test
	public void handOperatedBombermanCollidesOnBomb() {
		//HandOperatedBomberman collides on Bomb
//		((HandOperatedBomberman) gos.get(5)).setMoveCommand(MoveCommand.RIGHT);
//		((HandOperatedBomberman) gos.get(5)).nextStep(flattenedBoard);
		
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(5);
		Bomb bomb = (Bomb) flattenedBoard.gameObjects.get(7);
		assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(hob, new XY(3,8)), 
				"HandOperatedBomberman collides on Bomb: HandOperatedBomberman "+ hob +
				" is not allowed to take the Position of Bomb " + bomb);
	}
	
	@Test
	public void handOperatedBombermanCollidesOnWall() {
		//HandOperatedBomberman collides on Wall
//		((HandOperatedBomberman) gos.get(5)).setMoveCommand(MoveCommand.DOWN);
//		((HandOperatedBomberman) gos.get(5)).nextStep(flattenedBoard);
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(5);
		Wall wall = (Wall) flattenedBoard.gameObjects.get(8);
		assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(hob, new XY(2,9)), 
				"HandOperatedBomberman collides on Wall: HandOperatedBomberman "+ hob +
				" is not allowed to take the Position of Wall " + wall);
	}

	
	//Eventuell gleich testen ob die Points sich bei der Kollion sich geupdated haben.

	//Blast collides on Monster -> Spread Position
	//Blast collides on Monster -> Spread Position
	@Test
	public void blastCollidesOnMonsterTest() {
		
		try {
			flattenedBoard.move(gos.get(3), new XY(5,2));
		} catch (IllegalMoveException e) {
			System.err.println("This operation should not throw an IllegalMoveException. "
					+ "Blast " + flattenedBoard.gameObjects.get(3) +  " should take/spread to the Position of the Monster " + flattenedBoard.gameObjects.get(9) + " , but it does not!");		
		}
		assertEquals(new XY(5,2), flattenedBoard.gameObjects.get(3), "Blast has not destroyed Monster. Position of Monster" + flattenedBoard.gameObjects.get(9) + " should be spread by Blast!");
	}
	
	//Blast collides on DestructibleWall -> DO NOT Spread Position
	@Test
	public void blastCollidesOnDestructibleWallTest() {
		Blast blast = (Blast) flattenedBoard.gameObjects.get(3);
		DestructibleWall destructibleWall = (DestructibleWall) flattenedBoard.gameObjects.get(12);
		try {
			flattenedBoard.move(gos.get(3), new XY(6,3));
		} catch (IllegalMoveException e) {
			System.err.println("This operation should not throw an IllegalMoveException. "
					+ "Blast " + gos.get(3) +  " should destroy the DestructibleWall " + destructibleWall + " , but it does not!");	
			return;
		}
		blast.collide(destructibleWall, flattenedBoard);
//		DestructibleWall destructibleWallAfterCollision = (DestructibleWall) flattenedBoard.gameObjects.get(12);
		Blast destructibleWallAfterCollision = (Blast) flattenedBoard.getGameObject(destructibleWall.getLocation().getX(), destructibleWall.getLocation().getY()); //Achtung der Typ hat sich nach Kollision sich geändert
		
		assertEquals(blast.getClass(), destructibleWallAfterCollision.getClass(), "Blast has not destroyed DestructibleWall. Position " + destructibleWall + " should be null!");
		
	}
	
	//Blast collides on PowerUp -> Spread Position
	@Test
	public void blastCollidesOnPowerUpTest() {
		Blast blast = (Blast) flattenedBoard.gameObjects.get(3);
		PowerUp powerUp = (PowerUp) flattenedBoard.gameObjects.get(11);
		try {
			flattenedBoard.move(gos.get(3), new XY(5,4));
		} catch (IllegalMoveException e) {
			System.err.println("This operation should not throw an IllegalMoveException. "
					+ "Blast " + gos.get(3) +  " should destroy the PowerUp " + powerUp + " , but it does not!");		
		}
		
		blast.collide(powerUp, flattenedBoard);
		Blast powerUpAfterCollision = (Blast) flattenedBoard.getGameObject(powerUp.getLocation().getX(), powerUp.getLocation().getY()); //Achtung der Typ hat sich nach Kollision sich geändert
//		assertEquals(new XY(5,4), flattenedBoard.gameObjects.get(3), "Blast has not destroyed PowerUp. Position " + flattenedBoard.gameObjects.get(11) + " should be spread by Blast!");
		assertEquals(blast.getClass(), powerUpAfterCollision.getClass(), "Blast has not destroyed PowerUp. Position " + powerUp + " should be null!");
	}
	
	//Blast collides on HandOperatedBomberman -> DO NOT Spread Position
	@Test
	public void blastCollidesOnHOBTest() {
		
		assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(gos.get(3), new XY(4,3)), 
				"Blast collides on HandOperatedBomberman: Blast "+ flattenedBoard.gameObjects.get(3) +
				" is not allowed to take the Position of HandOperatedBomberman " + flattenedBoard.gameObjects.get(0));
	}
	
	//Test: Blast --> Bomb
	//Test: Blast --> Wall
	//Test: Blast --> Bomberman
	
	
	
	//Es gibt zwei verschiedene Monster collision Test Methoden da man jedes GameObject testen musste.
	//Hier wurden bereits die updatePoints ebenfalls bei HandOperatedBomberman und Bomberman Kollison getestet
	@Test
	public void Monster1CollidesOnGameObjectsTest() {

		Monster monster1 = (Monster) flattenedBoard.gameObjects.get(1);
		XY monster1loc = monster1.getLocation(); //Monster sollte sich nun nach oben, unten, links oder rechts bewegt haben.
		HandOperatedBomberman hob = (HandOperatedBomberman) flattenedBoard.gameObjects.get(0);
		XY handOperatedBombermanLoc = hob.getLocation();
		
		monster1.nextStep(flattenedBoard); //aktualisiert die location von Monster (gos.get(1))
		
		XY monster1LocNew = monster1.getLocation(); //Monster sollte sich nun nach oben, unten, links oder rechts bewegt haben.
		
		if(monster1LocNew.equals(handOperatedBombermanLoc)) {
			//Monster collides on HandOperatedBomberman
			assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(gos.get(1), handOperatedBombermanLoc),  
					"Monster collides on HandOperatedBomberman: " +
					"Monster " + monster1 +
					" is not allowed to take the Position of HandOperatedBomberman " + hob);
			
			//Hier testen ob die point vom hob um die Points vom monster1 sich verringert hat
			hob.updatePoints(-monster1.getPoints());
			assertEquals(-300, hob.getPoints());
		}
		else {
			String notBlastNotBomberman = flattenedBoard.getGameObject(monster1LocNew.getX(), monster1LocNew.getY()).getClass().getName();
			assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(monster1, monster1LocNew), 
					"Monster collides on " +  notBlastNotBomberman + ": " + 
					"Monster "+ monster1 +
					" is not allowed to take the Position of " + notBlastNotBomberman + flattenedBoard.getGameObject(monster1LocNew.getX(), monster1LocNew.getY()));
		}
		
		//Monster collides on Wall
		//Creation of new Monster and new Wall which will be deleted after this method
		Wall wall = new Wall(new XY(0,1));
		flattenedBoard.gameObjects.addObject(wall);
		Monster monsterNew = new Monster(new XY(1,1));
		flattenedBoard.gameObjects.addObject(monsterNew);
		
		flattenedBoard.move(monsterNew, XY.direction(monsterNew.getLocation(), wall.getLocation()));
		
		assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(monsterNew, XY.direction(monsterNew.getLocation(), wall.getLocation())), 
				"Monster collides on Wall: " + 
						"Monster "+ monsterNew +
						" is not allowed to take the Position of Wall" + wall);
	}
	
	@Test
	public void Monster9CollidesOnGameObjectsTest() {
		
		Monster monster9 = (Monster) flattenedBoard.gameObjects.get(9);
		XY monster9Loc = monster9.getLocation(); 
		Bomberman bomberman = (Bomberman) flattenedBoard.gameObjects.get(14);
		XY bombermanLoc = bomberman.getLocation();
		Blast blast = (Blast) flattenedBoard.gameObjects.get(3);
		XY blastLoc = blast.getLocation();
		
		monster9.nextStep(flattenedBoard); //aktualisiert die location von Monster (gos.get(1))
		
		XY monster9LocNew = monster9.getLocation(); //Monster sollte sich nun nach oben, unten, links oder rechts bewegt haben.
		
		if(monster9LocNew.equals(bombermanLoc)) {
			//Monster collides on HandOperatedBomberman
			assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(gos.get(1), bombermanLoc),  
					"Monster collides on Bomberman: " +
							"Monster " + monster9 +
							" is not allowed to take the Position of Bomberman " + bomberman);
			
			//Hier testen ob die point vom bomberman um die Points vom monster9 sich verringert hat
			bomberman.updatePoints(-monster9.getPoints());
			assertEquals(-300, bomberman.getPoints());
		}
		else if(monster9LocNew.equals(blastLoc)) {
			//Monster collides on Blast --> Monster gets killed and on the position of where Monster got killed is a Blast
			assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(gos.get(1), blastLoc),  
					"Monster collides on Blast: " +
							"Monster " + monster9 +
							" is not allowed to take the Position of Blast " + blast);
			//Monster collides on Blast and gets killed --> monster9 is null
			assertEquals(null, monster9);
		}
		else {
			//Monster collides on an GameObject notBlastNotBomberman which leads to an effect that monster9 cannot take the position of that GameObject
			String notBlastNotBomberman = flattenedBoard.getGameObject(monster9LocNew.getX(), monster9LocNew.getY()).getClass().getName();
			assertThrows(IllegalMoveException.class, () -> flattenedBoard.move(gos.get(1), monster9LocNew), 
					"Monster collides on " +  notBlastNotBomberman + ": " + 
							"Monster "+ monster9 +
							" is not allowed to take the Position of " + notBlastNotBomberman + flattenedBoard.getGameObject(monster9LocNew.getX(), monster9LocNew.getY()));
		}
	}
	
	
	//In diesem Test werden nur getestet ob bei bestimmten unten aufgeführten Test die Points des 
	//HandOperatedBomberman aktualisiert wird.
	@Test
	public void checkUpdatePointsOfHandOperatedBomberman() {
		
	}
	
	

}
