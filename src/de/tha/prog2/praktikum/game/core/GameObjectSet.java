package de.tha.prog2.praktikum.game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.tha.prog2.praktikum.game.container.DoubleLinkedList;
import de.tha.prog2.praktikum.game.gameobjects.Bomberman;
import de.tha.prog2.praktikum.game.gameobjects.BombermanBot;
import de.tha.prog2.praktikum.game.gameobjects.BotController;
import de.tha.prog2.praktikum.game.gameobjects.GameObject;

public class GameObjectSet {
	private DoubleLinkedList<GameObject> dll;
	
	public GameObjectSet() {
		//Speicherung der Instanzen. Man hätte auch Vector nehmen können.
		dll = new DoubleLinkedList<GameObject>(); 
	}
	
	public boolean contains(GameObject gameObject) {
		return dll.contains(gameObject);
	}
	
	public void removeObject(GameObject gameObject) {
		dll.remove(gameObject);
	}
	
	public void nextStep(GameObjectContext context) {
	    // 1) Schnappschuss aller GameObjects
	    List<GameObject> snapshot = new ArrayList<>();
	    for (GameObject o : dll) {
	        snapshot.add(o);
	    }

	    // 2) zufällige Reihenfolge
	    Collections.shuffle(snapshot);

	    // 3) jeden einen Tick weiterbewegen
	    for (GameObject obj : snapshot) {
	        if (obj instanceof BombermanBot bot) {
	            // eigener Context für Bots
	            ControllerContext botCtx = new ControllerContextImpl((FlattenedBoard) context, (Bomberman) obj);
	            try {
	                bot.nextStep(botCtx);
	            } catch (RuntimeException e) {
	                // Bot-Fehler abfangen, nur loggen
	                System.err.println("Bot " + obj.getClass().getSimpleName() + " ist abgestürzt: " + e.getMessage());
	            }
	        } else {
	            // alle anderen Objekte ganz normal
	            obj.nextStep(context);
	        }
	    }
	}

	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < dll.size(); i++) {
			result.append(dll.get(i).toString() + "\n");
		}
		return result.toString();
	}
	
	public void addObject(GameObject gameObject) {
		dll.add(gameObject);
	}
	
	public int size() {
		return dll.size();
	}
	
	public DoubleLinkedList<GameObject> getDLL() { return dll;}
	
	public GameObject get(int i) {
		return (GameObject) dll.get(i);
	}

}
