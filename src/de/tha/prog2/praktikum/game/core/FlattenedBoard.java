package de.tha.prog2.praktikum.game.core;

import de.tha.prog2.praktikum.game.exceptions.IllegalGetException;

import de.tha.prog2.praktikum.game.exceptions.IllegalMoveException;
import de.tha.prog2.praktikum.game.gameobjects.Blast;
import de.tha.prog2.praktikum.game.gameobjects.Bomb;
import de.tha.prog2.praktikum.game.gameobjects.Bomberman;
import de.tha.prog2.praktikum.game.gameobjects.GameObject;
import de.tha.prog2.praktikum.game.gameobjects.Monster;
import de.tha.prog2.praktikum.game.util.XY;

public class FlattenedBoard implements BoardView, GameObjectContext {
	GameObjectSet gameObjects;
	GameObject[][] array;
	int width;
	int height;
	XY size;
	private boolean stopExplosion = false;

	/**
	 * Erstellt eine 2-dimensionale Abbildung des Boards (=Spielfelds), mithilfe dieser einfacher herausgefunden werden kann, welches Objekt
	 * auf einer bestimmten Koordinate auf dem Board steht.
	 * @param size Gibt die Größe der zu erstellenden Board-Abbildung an. Dabei ist X die Breite und Y die Höhe.
	 * @param gameObjects Das GameObjectSet, welches zur Abbildung des zugehörigen Boards genutzt werden soll.
	 */
	public FlattenedBoard(XY size, GameObjectSet gameObjects) {
		this.gameObjects = gameObjects;
		this.size = size;
		width = size.getX();
		height = size.getY();
		array = new GameObject[width][height];
		
		for(int i = 0; i < gameObjects.size(); i++) {
			GameObject temp = gameObjects.get(i);
//			gameObjects.addObject(temp);
			array[temp.getLocation().getX()][temp.getLocation().getY()] = temp;
		}
		
	}

	/**
	 * Führt move-Operation eines GameObjects durch, bei Kollision kommt vorerst ein error-print.
	 */
	@Override
	public void move(GameObject gameObject, XY direction) {
		if(!gameObjects.contains(gameObject)) { 
			return; // eigentlich unnötiger check (bewegungen von objekten, die schon tot sind, verhindern) aber tests machen manchmal probleme
		}
		XY currentLocation = gameObject.getLocation();
		XY newLocation = currentLocation.move(direction);
		int newX = newLocation.getX();
		int newY = newLocation.getY();
//		System.out.println("Es wird versucht, ID " + gameObject.getID() + " auf " + newX + "," + newY + "zu bewegen!");
		
		if(!(gameObject instanceof Bomberman) && !(gameObject instanceof Monster)){
			throw new IllegalMoveException("This GameObject is not allowed to move!"); // Nur Bomberman und Monster dürfen sich bewegen
		} else if(newX >= width || newY >= height || newY < 0 || newX < 0) {
			return; // außerhalb des Spielfelds
		} else if(Math.abs(direction.getX()) + Math.abs(direction.getY()) > 1) {
			throw new IllegalMoveException(); // nur 1 Schritt pro Runde
		} else if(getGameObject(newX, newY) != null){
			gameObject.collide(getGameObject(newX, newY), this);
			return;
		} else if(getGameObject(newX, newY) == null) {
			gameObject.setLocation(newLocation);
//			getGameObject(currentLocation.getX(),currentLocation.getY()) = null;
			array[currentLocation.getX()][currentLocation.getY()] = null;
			array[newX][newY] = gameObject;
			return;
		}
	}

	@Override
	public GameObject getGameObject(int x, int y) {
		if(x >= width || y >= height || y < 0 || x < 0) {
			throw new IllegalGetException("Cannot access (" + x + "," + y + ") on the FlattenedBoard because it is out of the bounds (" + width + "," + height + ")!");
		}
		return array[x][y];
	}

	public GameObjectSet getGameObjectSet() {return gameObjects;}
	
	@Override
	public XY getSize() {
		return size;
	}

	@Override
	public void addGameObject(GameObject gameObject) {
		// Ist schon ein anderes GameObject auf der Position?
		if(getGameObject(gameObject.getLocation().getX(), gameObject.getLocation().getY()) != null) {
			return;
		}
		gameObjects.addObject(gameObject);
		array[gameObject.getLocation().getX()][gameObject.getLocation().getY()] = gameObject;
	}

	@Override
	public void removeGameObject(GameObject gameObject) {
		if(getGameObject(gameObject.getLocation().getX(), gameObject.getLocation().getY()) == null) {
			return;
		}
		//Wenn gameObject tatsächlich im GameObjectSet drinnen ist, dann soll es removed werden.
		XY temp = gameObject.getLocation();
		if(getGameObject(temp.getX(), temp.getY()).equals(gameObject)) {
			gameObjects.removeObject(gameObject);
			array[gameObject.getLocation().getX()][gameObject.getLocation().getY()] = null;
		}
		//Allerdings wenn das gameObject im GameObjectSet noch nicht drinnen ist und man es trotzdem durch diese Methode removen möchte,
		//dann macht man einfach garnichts bzw. man returned diese void removeGameObject Methode.
		//Da gameObject nicht eingefügt ist und es nicht im GameObjectSet erwünscht ist (und deshalb man es removen möchte) returned man einfach.
		return;
	}
	
	public GameObject[][] getGameBoard(){ return array; }

	@Override
	public void explode(Bomb bomb) {
			XY bombLoc = bomb.getLocation();
			int bombX = bombLoc.getX();
			int bombY = bombLoc.getY();
			Bomberman owner = bomb.getOwner();
			//Eine Explosion heißt dass die Bombe vom Spielfeld entfernt und durch Blast-Spielfiguren ersetzt
			removeGameObject(bomb);
			owner.bombExploded();
			
			Blast middle = new Blast(bombLoc,owner);
			addGameObject(middle);
			
			//Rechte Blasts:
			for(int i = 1; i <= bomb.getStrength(); i++) {
				int rightX = bombX + i;
				int rightY = bombY;
				XY rightLocation = new XY(rightX, rightY);
				Blast right = new Blast(rightLocation, owner);
				// Überprüfen, ob innerhalb des Spielfelds:
				if((rightX >= 0 && rightY >= 0) && (rightX < width && rightY < height)) {
					// Überprüfen auf Kollision:
					if(getGameObject(rightX, rightY) != null) {
						right.collide(getGameObject(rightX, rightY), this);
						if(stopExplosion) {
							stopExplosion = false;
							break;
						}
					} else {
						addGameObject(right);
					}
				}
			}
			
			// Linke Blasts:
			for(int i = 1; i <= bomb.getStrength(); i++) {
				int leftX = bombX - i;
				int leftY = bombY;
				XY leftLocation = new XY(leftX, leftY);
				Blast left = new Blast(leftLocation, owner);
				// Überprüfen, ob innerhalb des Spielfelds:
				if((leftX >= 0 && leftY >= 0) && (leftX < width && leftY < height)) {
					// Überprüfen auf Kollision:
					if(getGameObject(leftX, leftY) != null) {
						left.collide(getGameObject(leftX, leftY), this);
						if(stopExplosion) {
							stopExplosion = false;
							break;
						}
					} else {
						addGameObject(left);
					}
				}
			}
			
			// Untere Blasts:
			for (int i = 1; i <= bomb.getStrength(); i++) {
				int lowerX = bombX;
				int lowerY = bombY + i;
				XY lowerLocation = new XY(lowerX, lowerY);
				Blast lower = new Blast(lowerLocation, owner);
				// Überprüfen, ob innerhalb des Spielfelds:
				if((lowerX >= 0 && lowerY >= 0) && (lowerX < width && lowerY < height)) {
					// Überprüfen auf Kollision:
					if(getGameObject(lowerX, lowerY) != null) {
						lower.collide(getGameObject(lowerX, lowerY), this);
						if(stopExplosion) {
							stopExplosion = false;
							break;
						}
					} else {
						addGameObject(lower);
					}
				}
			}
			
			//Obere Blasts:
			for (int i = 1; i <= bomb.getStrength(); i++) {
				int upperX = bombX;
				int upperY = bombY - i;
				XY upperLocation = new XY(upperX, upperY);
				Blast upper = new Blast(upperLocation, owner);
				// Überprüfen, ob innerhalb des Spielfelds:
				if((upperX >= 0 && upperY >= 0) && (upperX < width && upperY < height)) {
					if(getGameObject(upperX, upperY) != null) {
						upper.collide(getGameObject(upperX, upperY), this);
						if(stopExplosion) {
							stopExplosion = false;
							break;
						}
					} else {
						addGameObject(upper);
					}
				}
			}

		}
	
	public void stopExplosion(){
		stopExplosion = true;
	}
	
	@Override
	public void removeBlast(Blast blast) {
		removeGameObject(blast);
	}
	
}
