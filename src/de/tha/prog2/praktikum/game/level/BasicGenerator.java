package de.tha.prog2.praktikum.game.level;

import java.util.Random;

import java.util.Scanner;

import de.tha.prog2.praktikum.game.core.GameObjectSet;
import de.tha.prog2.praktikum.game.gameobjects.*;
import de.tha.prog2.praktikum.game.util.XY;
//Eigentlich sollte in der BasicGenerator Klasse nur die random GameObjectSet Liste generiert werden, welches die Board im Konstruktor als Parameter einnimmt.
//Daher ist die field Variable nicht nötig. 
//Zum Anderen können alle anderen Klassen die einen Generator über die Referenz des Interfaces LevelGenerator die Methoden aufrufen, nicht auf die interne Methoden
//der BasicGenerator Klasse aufrufen (Außer eine Klasse wird gegen die Angaben illegalerweise instanziiert :) )
//Somit wäre getField(), field und printGameField(field) überflüssig.
//Aber sie richten weder direkte Schäden auf diese Klasse noch auf eine andere Klasse an. Deswegen kann man es auch stehen lassen.
//Aber während dem Debuggen und der Implementierung dieser Klasse war es eine große Hilfe.
//Jedenfalls kann man mache dieser überflüssigen Implementierung in der ConsoleUI Klasse für das Rendering verwenden.
public class BasicGenerator implements LevelGenerator {
    private Random random = new Random();
    private int rows = 30;
    private int cols = 30;
    private char[][] field = new char[cols][rows];
    
    /**
     * @return die Größe des GameObjectSet (welches im generate() returned wird) im XY-Format, wobei cols die Breite und rows die Höhe darstellt.
     */
    @Override
    public XY getSize() { return new XY(cols, rows); }

    @Override
    public GameObjectSet generate() {
        GameObjectSet gos = new GameObjectSet();
        createWallBorder(gos); //Wall Umrandung wurde nun damit in GameObjectSet Objekt sowie im gameField hinzugefügt.
//        createRandomGameObjects("Blast",                10, gos);
//        createRandomGameObjects("Bomb",                 10, gos);
        createRandomGameObjects("DestructibleWall",    100, gos);
        createRandomGameObjects("Monster",              3, gos);
        createRandomGameObjects("PowerUp",              10, gos);
        createRandomGameObjects("HandOperatedBomberman", 1, gos); 
        createRandomGameObjects("BombermanBot",1,gos);
//        createRandomGameObjects("Bomberman", 			 5, gos); 
        return gos;
    }
    
    public char[][] getField() { return field; }
    public void printGameField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
    
    private void createRandomGameObjects(String type, int quantity, GameObjectSet gos) {
        if (type.equals("Blast")) {
            for (int i = 0; i < quantity; i++) {
                // x in [1..cols-1], y in [1..rows-1]
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                Blast obj = new Blast(randomXY);
                gos.addObject(obj);
                field[randomXY.getX()][randomXY.getY()] = obj.toChar();
            }
        } 
        else if (type.equals("Bomb")) {
            for (int i = 0; i < quantity; i++) {
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                Bomb obj = new Bomb(randomXY);
                gos.addObject(obj);
                field[randomXY.getX()][randomXY.getY()] = obj.toChar();
            }
        }
        else if (type.equals("HandOperatedBomberman")) {
            for (int i = 0; i < quantity; i++) {
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                HandOperatedBomberman obj = new HandOperatedBomberman(randomXY);
                gos.addObject(obj);
                field[randomXY.getX()][randomXY.getY()] = obj.toChar();
            }
        }
        else if (type.equals("Bomberman")) {
        	for (int i = 0; i < quantity; i++) {
        		int x = random.nextInt(1, cols);
        		int y = random.nextInt(1, rows);
        		XY randomXY = new XY(x, y);
        		randomXY = checkIllegalMove(gos, randomXY);
        		Bomberman obj = new Bomberman(randomXY);
        		gos.addObject(obj);
        		field[randomXY.getX()][randomXY.getY()] = obj.toChar();
        	}
        }
        else if (type.equals("DestructibleWall")) {
            for (int i = 0; i < quantity; i++) {
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                DestructibleWall obj = new DestructibleWall(randomXY);
                gos.addObject(obj);
                field[randomXY.getX()][randomXY.getY()] = obj.toChar();
            }
        }
        else if (type.equals("Monster")) {
            for (int i = 0; i < quantity; i++) {
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                Monster obj = new Monster(randomXY);
                gos.addObject(obj);
                field[randomXY.getX()][randomXY.getY()] = obj.toChar();
            }
        }
        else if (type.equals("PowerUp")) {
            for (int i = 0; i < quantity; i++) {
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                PowerUp obj = new PowerUp(randomXY);
                gos.addObject(obj);
                field[randomXY.getX()][randomXY.getY()] = obj.toChar();
            }
        }
        else if (type.equals("Wall")) {
            for (int i = 0; i < quantity; i++) {
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                Wall obj = new Wall(randomXY);
                gos.addObject(obj);
                field[randomXY.getX()][randomXY.getY()] = obj.toChar();
            }
        } else if (type.equals("BombermanBot")) {
        	System.out.println("Creating Bots!!!!!!!!!!!!!!!!!!");
            for (int i = 0; i < quantity; i++) {
            	System.out.println("Creating "+ i + "st Bot!!!!!!!!!!!!!!!");
                int x = random.nextInt(1, cols);
                int y = random.nextInt(1, rows);
                XY randomXY = new XY(x, y);
                randomXY = checkIllegalMove(gos, randomXY);
                // Neu: einen echten BombermanBot erzeugen
                BombermanBot bot = new BombermanBot(randomXY,"Bot" + i, 0, 0, 0);
                gos.addObject(bot);
                field[randomXY.getX()][randomXY.getY()] = bot.toChar();
            }
        }

        else {
            System.err.println("WRONG CLASS TYPE OF GAMEOBJECT");
        }
    }
    private XY checkIllegalMove(GameObjectSet gos, XY randomXY) {
        boolean isUnique = false;
        while (!isUnique) {
            isUnique = true; // Setze den Wert auf true, bevor wir die Überprüfung starten
            for (int j = 0; j < gos.getDLL().size(); j++) {
                GameObject temp = (GameObject) gos.getDLL().get(j);
                int tempX = temp.getLocation().getX();
                int tempY = temp.getLocation().getY();
                // Wenn die Koordinaten gleich sind, dann werden neue random Werte generiert
                if (tempX == randomXY.getX() && tempY == randomXY.getY()) {
                    // neue Position wählen
                    int x = random.nextInt(1, cols);
                    int y = random.nextInt(1, rows);
                    randomXY = new XY(x, y);
                    isUnique = false; // Wiederhole die Überprüfung
                    break;
                }
            }
        }
        return randomXY;
    }
    private void createWallBorder(GameObjectSet gos){
        int len = field.length;
        int width = field[0].length;
        //Wall top und bottom
        for (int x = 0; x < cols; x++) {
            Wall top = new Wall(new XY(x, 0));
            Wall bottom = new Wall(new XY(x, rows-1));
            field[x][0] = top.toChar();
            field[x][rows-1] = bottom.toChar();
            gos.addObject(top);
            gos.addObject(bottom);
        }
        //Wall left und right
        for (int y = 0; y < rows; y++) {
            Wall left = new Wall(new XY(0, y));
            Wall right = new Wall(new XY(cols-1, y));
            field[0][y] = left.toChar();
            field[cols-1][y] = right.toChar();
            gos.addObject(left);
            gos.addObject(right);
        }
        return ;
    }

    public static void main(String[] args) {
        BasicGenerator bg = new BasicGenerator();
        GameObjectSet gos = bg.generate();
//      gos.addObject(new HandOperatedBomberman(new XY(2,3)));
        System.out.println("\nAlle GameObjects mit Koordinaten:");
        System.out.println(gos.toString());
        bg.printGameField(bg.getField());
        
    }
}

