package de.tha.prog2.praktikum.game.gameobjects;
import de.tha.prog2.praktikum.game.core.GameObjectContext;
import de.tha.prog2.praktikum.game.util.XY;

public class Blast extends AbstractGameObject{
	
	private Bomberman owner;
	public int timer = 2;

	public Blast(XY pos, Bomberman owner) {
		super(pos, 300);
		this.owner = owner;
//		System.out.println("Blast wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}
	
	public Blast(XY pos) {
		super(pos, 300);
//		System.out.println("Blast wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}

	@Override
	public char toChar() { return '*'; }
	
	public void nextStep(GameObjectContext context) { 
		if(timer == 1) {
			context.removeBlast(this);
		} else {
		timer--;
		}
	} 
	
	@Override
	public void collide(GameObject gameObject, GameObjectContext context) {
		String target = gameObject.getClass().getSimpleName();
		
		switch(target) {
			case "DestructibleWall": 
				context.removeGameObject(gameObject); // DestructibleWall wurde durch Blast zerstört
				context.addGameObject(this);
				context.stopExplosion();
				
				if(owner != null) {
					owner.updatePoints(gameObject.getPoints()); // Bomberman bekommt Punkte der DestructableWall
					owner.setDestroyedDestructibleWalls(owner.getDestroyedDestructibleWalls()+1);
				}
				break;
				
			case "Monster":
				context.removeGameObject(gameObject); //Monster stirbt
				context.addGameObject(this);
				
				if(owner != null) {
//					System.out.println("Monster was killed, giving ID " + owner.getID() + " " + gameObject.getPoints() + " Points!");
					owner.updatePoints(gameObject.getPoints()); // Bomberman bekommt Punkte des Monsters
					owner.setDestroyedMonsters(owner.getDestroyedMonsters()+1);
				} else {
//					System.out.println("Monster was killed, but Blast had no owner!");
				}
				break;
			
			case "ExtendBombStrength":	
			case "ExtraPoints":
			case "MoreBombs":
			case "PowerUp":
				context.removeGameObject(gameObject);
				context.addGameObject(this);
				break;
				
			case "Bomberman":
			case "HandOperatedBomberman":
				Bomberman bomberman = (Bomberman) gameObject; //Neue Referenz zum selben gameObject mit Bomberman Casting um points sicher zu aktualisieren
				bomberman.updatePoints(-this.getPoints()); //Bomberman verliert die Punkte die der Blast/die Bombe wert sind
				if(!owner.equals(gameObject)) {
					owner.updatePoints(this.getPoints()); // Bombenleger bekommt Punkte
				}
				context.stopExplosion();
				break;
				
			case "Wall":
				context.stopExplosion();
				break;
			case "Blast":
				context.removeGameObject(gameObject);
				context.addGameObject(this);
				break;
			default:
				return;
		}
	}
	
	public Bomberman getOwner() {
		return owner;
	}

	
}
