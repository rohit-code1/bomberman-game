package de.tha.prog2.praktikum.game.gameobjects;

import java.util.Random;

import de.tha.prog2.praktikum.game.container.DoubleLinkedList;
import de.tha.prog2.praktikum.game.core.ControllerContext;
import de.tha.prog2.praktikum.game.core.FlattenedBoard;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.XY;

public class BombermanBot extends Bomberman implements BotController {

	public BombermanBot(XY location, String playerName, int points, int destroyedMonsters,
			int destroyedDestructibleWalls) {
		super(location, playerName, points, destroyedMonsters, destroyedDestructibleWalls);
	}

	@Override
	public void nextStep(ControllerContext view) {
		XY myLocation = getLocation();

		// 1. Sichtbare Umgebung zusammentragen in Liste:
		XY lowerLeft = view.getViewLowerLeft();
		XY upperRight = view.getViewUpperRight();

		DoubleLinkedList<XY> monsterPositions = new DoubleLinkedList<>();
//		DoubleLinkedList<XY> powerUpPositions = new DoubleLinkedList<>();

		for (int y = upperRight.getY(); y <= lowerLeft.getY(); y++) {
			for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
				Class<?> currGameObjClass = view.getGameObjectAt(x, y);
				// Wenn da kein gameObject ist, weitergehen:
				if (currGameObjClass == null) {
					continue;
				}
				// GameObject gefunden:
				GameObjectEnum type = GameObjectEnum.getEnum(currGameObjClass.getSimpleName());
				if (type == null) {
					continue;
				}
				XY pos = new XY(x, y);
				// Nur Monster und PowerUps werden in die Liste gespeichert:
				if (type == GameObjectEnum.MONSTER) {
					monsterPositions.add(pos);
				}
//				else if(type == GameObjectEnum.EXTEND_BOMB_STRENGTH || type == GameObjectEnum.EXTRA_POINTS || type == GameObjectEnum.MORE_BOMBS) {
//					powerUpPositions.add(pos);
//				}
			}
		}

		// 2. Entscheidungslogik:
		Random rand = new Random();
		// Bei Monstern immer wegrennen:
		if (!monsterPositions.isEmpty()) {
			// Monster mit der geringster Distanz finden:
			XY closestMonster = monsterPositions.get(0);
			int minDistance = myLocation.distance(closestMonster);

			for (XY monster : monsterPositions) {
				int distance = myLocation.distance(monster);
				if (distance < minDistance) {
					minDistance = distance;
					closestMonster = monster;
				}
			}
			// Wegrennen vor Monster mit geringster Distanz:
			XY direction;
			int distanceX = Integer.signum(myLocation.getX() - closestMonster.getX());
			int distanceY = Integer.signum(myLocation.getY() - closestMonster.getY());

			if (Math.abs(distanceX) > Math.abs(distanceY)) {
				direction = new XY(distanceX, 0);
			} else {
				direction = new XY(0, distanceY);
			}
			
			XY prevXY = this.getLocation();
			view.move(direction);
			// Nach jeder Bewegung BombQueue checken:
			if(!bombs.isEmpty()) {
				if(!this.getLocation().equals(prevXY)) {
					view.addGameObject((Bomb) bombs.get(0));
					bombs.remove(bombs.get(0));
				}
			}
			return;
		}

		// Ansonsten bei Möglichkeit Bombe legen:
		if (getBombsAllowed() > 0 && rand.nextInt(20) == 5) {
			Bomb bomb = new Bomb(this.getLocation(), bombStrength, this);
			bombs.add(bomb);
			bombsAllowed--;
		}

		// Ansonsten random bewegen:
		
		int r = rand.nextInt(5);
		XY randomDirection;
		switch (r) {
		case 0:
			randomDirection = new XY(1, 0);
			break;
		case 1:
			randomDirection = new XY(0, 1);
			break;
		case 2:
			randomDirection = new XY(-1, 0);
			break;
		case 3:
			randomDirection = new XY(0, -1);
			break;
		default:
			randomDirection = new XY(0, 0);
			break;
		}
		
		XY prevXY = this.getLocation();
		view.move(randomDirection);
		// Nach jeder Bewegung BombQueue checken:
		if(!bombs.isEmpty()) {
			if(!this.getLocation().equals(prevXY)) {
				view.addGameObject((Bomb) bombs.get(0));
				bombs.remove(bombs.get(0));
			}
		}
		return;
	}

	public char toChar() {
		return 'N'; // N weil ein Bot ein NPC ist (bruh)
	}

}
