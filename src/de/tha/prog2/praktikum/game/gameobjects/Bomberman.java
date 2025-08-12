package de.tha.prog2.praktikum.game.gameobjects;
import de.tha.prog2.praktikum.game.container.DoubleLinkedList;
import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.exceptions.NoMoreBombsAllowed;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.XY;

public class Bomberman extends AbstractGameObject implements Comparable<Bomberman>{
	private int xp = this.points;
	protected int bombsAllowed = 1;
	protected DoubleLinkedList<Bomb> bombs = new DoubleLinkedList<>();
	protected int bombStrength = 1;
	protected MoveCommand command;
	private String playerName;
	private int destroyedMonsters;
	private int destroyedDestructibleWalls;
	private int ranking;

	public int getRanking() {return ranking;}
	public int setRanking(int ranking) {return this.ranking = ranking;}

	public Bomberman(XY pos) {
		super(pos, 0);
//		System.out.println("Bomberman wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}
	
	 public Bomberman(XY location, String playerName, int points, int destroyedMonsters, int destroyedDestructibleWalls) {
//		 this.location = location;
//		 this.xp = points;
		 super(location, points);
		 this.playerName = playerName;
		 this.destroyedMonsters = destroyedMonsters;
		 this.destroyedDestructibleWalls = destroyedDestructibleWalls;
	 }

	public String getPlayerName() {return playerName;}

	public int getDestroyedMonsters() { return destroyedMonsters; }

	public int getDestroyedDestructibleWalls() {return destroyedDestructibleWalls;}

	@Override
	public void updatePoints(int update) { 
//		System.out.println("I have + " + getPoints() + " Points and I'm receiving " + update + " Points! (my ID is: "+ getID() + ")");
		xp += update; 
	} 
	
	public void setDestroyedMonsters(int destroyedMonsters) {this.destroyedMonsters = destroyedMonsters;}

	public void setDestroyedDestructibleWalls(int destroyedDestructibleWalls) { this.destroyedDestructibleWalls = destroyedDestructibleWalls; }

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("GameObject = " + this.getClass().getSimpleName() + "; ID = " + ID + "; Location = " + location.getX() + "," + location.getY() + "; Points = " + xp);
		result.append("\nPlayer Name: " + getPlayerName());
		result.append("\nNumber of Destroyed DestructibleWalls:" + getDestroyedDestructibleWalls() + "\nNumber of Destroyed Monster:" + getDestroyedMonsters());
		return result.toString();
	}
	
	@Override
	public char toChar() { return 'B'; }
	
	public void setMoveCommand(MoveCommand command) {
		this.command = command;
	}

	public void nextStep(GameObjectContext context) {
		
		if(command == null) {
			return;
		}
		
		XY direction = new XY(0,0);
		//auf 7,5 setzen extendbombstregth 6,5 und dann 5,5 dann bomberman 2x rechts, dann bombe, dann rechts dann oben, dann 4 nextsteps fehler: auf 5,5 ist noch powerup, sollte aber nicht

		switch (command) {
		case UP:
			direction = new XY(0,-1);
			break;
		case LEFT:
			direction = new XY(-1,0);
			break;
		case DOWN:
			direction = new XY(0,1);
			break;
		case RIGHT:
			direction = new XY(1,0);
			break;
		case BOMB:
			if(bombsAllowed <= 0) {
				throw new NoMoreBombsAllowed("Trying to place a bomb while " + bombsAllowed + " bombs are allowed to be placed!");
			}
			
			Bomb bomb = new Bomb(this.getLocation(), bombStrength, this);
			bombs.add(bomb);
			bombsAllowed--;
			
			break;
		}
		
		XY prevXY = this.getLocation();
		context.move(this, direction);
		command = null; // MoveCommand zurücksetzen nach Bewegung
		if(!bombs.isEmpty()) {
			if(!this.getLocation().equals(prevXY)) {
				context.addGameObject((Bomb) bombs.get(0));
				bombs.remove(bombs.get(0));
			}
		}
		
//		System.out.println(toString()); // Sollte in der UI stattfinden. Hier könnte es zu verzögerten Aktualisieren kommen z.B Punktzahl kommt in der nächsten Runde
		
	}
	
	@Override
	public void collide(GameObject gameObject, GameObjectContext context) {
		String target = gameObject.getClass().getSimpleName();
		
		switch(target) {
			case "DestructibleWall": 
				updatePoints(-gameObject.getPoints());
				break;
				
			case "Blast":
				updatePoints(-gameObject.getPoints());
				Blast blast = (Blast) gameObject;
				Bomberman owner = blast.getOwner();
				if(!owner.equals(this)) {
					owner.updatePoints(gameObject.getPoints()); // Bombenleger bekommt Punkte
				}
				context.removeGameObject(gameObject);
				context.move(this, XY.direction(this.getLocation(), gameObject.getLocation()));
				break;
				
			case "Monster":
				updatePoints(-gameObject.getPoints());
				break;
				
			case "ExtendBombStrength":
			case "MoreBombs":
			case "ExtraPoints":
			case "PowerUp":
				PowerUp powerup = (PowerUp) gameObject;
				PowerUpType type = powerup.getType();
				System.out.println(powerup.getType().toString());
				
				if(type == PowerUpType.ExtendBombStrength) {
					bombStrength++;
				} else if(type == PowerUpType.MoreBombs) {
					bombsAllowed++;
				} else if(type == PowerUpType.ExtraPoints) {
					updatePoints(gameObject.getPoints());
				}
				
				context.removeGameObject(gameObject);
				context.move(this, XY.direction(this.getLocation(), gameObject.getLocation()));
				
				break;
		}
	}
	
	@Override
	public int getPoints() { return xp;}
	
	public void bombExploded() {
		bombsAllowed++;
	}

	@Override
	public int compareTo(Bomberman o) {
		Bomberman temp = (Bomberman) o;
		if (!(this.getPoints() == temp.getPoints())) return temp.getPoints()-this.getPoints();
		else if (!(this.getDestroyedMonsters() == temp.getDestroyedMonsters())) return temp.getDestroyedMonsters()-this.getDestroyedMonsters();
		else if (!(this.getDestroyedDestructibleWalls() == temp.getDestroyedDestructibleWalls())) return temp.getDestroyedDestructibleWalls()-this.getDestroyedDestructibleWalls();
		return 0;
	}
	
	public int getBombsAllowed() {
		return bombsAllowed;
	}
	
	public int getBombStrength() {
		return bombStrength;
	}
	
	public void addBomb(Bomb bomb) {
		bombs.add(bomb);
	}
	
	public void decrementBombsAllowed() {
		bombsAllowed--;
	}
	
}
