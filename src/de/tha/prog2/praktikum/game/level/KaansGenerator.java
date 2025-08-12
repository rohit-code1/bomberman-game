package de.tha.prog2.praktikum.game.level;

import de.tha.prog2.praktikum.game.core.GameObjectSet;
import de.tha.prog2.praktikum.game.gameobjects.*;
import de.tha.prog2.praktikum.game.gameobjects.Wall;
import de.tha.prog2.praktikum.game.util.XY;

public class KaansGenerator implements LevelGenerator {
    private int rows = 15;
    private int cols = 60;
    GameObjectSet gos = new GameObjectSet();

	@Override
	public XY getSize() { return new XY(cols, rows); }

	@Override
	public GameObjectSet generate() {
		createWallBorder();
		createMonsters();
		createDestructibleWalls();
		createPowerUps();
		gos.addObject(new HandOperatedBomberman(new XY(40,3)));
		return gos;
	}
	
	private void createWallBorder(){
        //Wall top und bottom
        for (int x = 0; x < cols; x++) {
            Wall top = new Wall(new XY(x, 0));
            Wall bottom = new Wall(new XY(x, rows-1));
            gos.addObject(top);
            gos.addObject(bottom);
        }
        //Wall left und right
        for (int y = 0; y < rows; y++) {
            Wall left = new Wall(new XY(0, y));
            Wall right = new Wall(new XY(cols-1, y));
            gos.addObject(left);
            gos.addObject(right);
        }
        return;
    }
	
	private void createMonsters() {
		gos.addObject(new Monster(new XY(2,11)));
		gos.addObject(new Monster(new XY(3,10)));
		gos.addObject(new Monster(new XY(4,9)));
		gos.addObject(new Monster(new XY(5,8)));
		gos.addObject(new Monster(new XY(6,7)));
		gos.addObject(new Monster(new XY(7,8)));
		gos.addObject(new Monster(new XY(8,9)));
		gos.addObject(new Monster(new XY(9,8)));
		gos.addObject(new Monster(new XY(10,7)));
		gos.addObject(new Monster(new XY(11,8)));
		gos.addObject(new Monster(new XY(12,9)));
		gos.addObject(new Monster(new XY(13,10)));
		gos.addObject(new Monster(new XY(14,11)));
	}
	
	private void createDestructibleWalls() {
		gos.addObject(new DestructibleWall(new XY(25, 10)));
		gos.addObject(new DestructibleWall(new XY(35, 12)));
		gos.addObject(new DestructibleWall(new XY(24, 7)));
		gos.addObject(new DestructibleWall(new XY(30, 3)));
		gos.addObject(new DestructibleWall(new XY(55, 5)));
		gos.addObject(new DestructibleWall(new XY(23, 3)));
		gos.addObject(new DestructibleWall(new XY(56, 4)));
		gos.addObject(new DestructibleWall(new XY(56, 2)));
		gos.addObject(new DestructibleWall(new XY(1, 1)));
		gos.addObject(new DestructibleWall(new XY(58, 14)));
		gos.addObject(new DestructibleWall(new XY(15, 5)));
		gos.addObject(new DestructibleWall(new XY(20, 2)));
		gos.addObject(new DestructibleWall(new XY(40, 13)));
		gos.addObject(new DestructibleWall(new XY(50, 6)));
		gos.addObject(new DestructibleWall(new XY(10, 1)));
		gos.addObject(new DestructibleWall(new XY(45, 9)));
		gos.addObject(new DestructibleWall(new XY(58, 7)));
		gos.addObject(new DestructibleWall(new XY(8, 1)));
		gos.addObject(new DestructibleWall(new XY(2, 1)));
		gos.addObject(new DestructibleWall(new XY(3, 2)));
		gos.addObject(new DestructibleWall(new XY(4, 3)));
		gos.addObject(new DestructibleWall(new XY(5, 4)));
		gos.addObject(new DestructibleWall(new XY(6, 5)));
		gos.addObject(new DestructibleWall(new XY(7, 6)));
		gos.addObject(new DestructibleWall(new XY(9, 7)));
		gos.addObject(new DestructibleWall(new XY(16, 8)));
		gos.addObject(new DestructibleWall(new XY(17, 11)));
		gos.addObject(new DestructibleWall(new XY(58, 1)));
	}
	
	private void createPowerUps() {
		gos.addObject(new ExtendBombStrength(new XY(33, 11)));
		gos.addObject(new ExtendBombStrength(new XY(5, 1)));
		gos.addObject(new MoreBombs          (new XY(10, 3)));
		gos.addObject(new ExtraPoints        (new XY(20, 5)));
		gos.addObject(new ExtendBombStrength(new XY(30, 6)));
		gos.addObject(new MoreBombs          (new XY(40, 8)));
		gos.addObject(new ExtraPoints        (new XY(48, 12)));


	}
	

}
