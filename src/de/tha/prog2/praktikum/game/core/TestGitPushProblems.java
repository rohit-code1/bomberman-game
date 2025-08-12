package de.tha.prog2.praktikum.game.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tha.prog2.praktikum.game.exceptions.IllegalMoveException;
import de.tha.prog2.praktikum.game.exceptions.NoMoreBombsAllowed;
import de.tha.prog2.praktikum.game.gameobjects.*;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.PowerUpType;
import de.tha.prog2.praktikum.game.util.XY;

public class TestGitPushProblems {
	FlattenedBoard flattenedBoard;
	GameObjectSet gos;
	HandOperatedBomberman hob = new HandOperatedBomberman(new XY(7,5));
	ExtendBombStrength extend1 = new ExtendBombStrength(new XY(6,5));
	ExtendBombStrength extend2 = new ExtendBombStrength(new XY(5,5));
	
	
	@BeforeEach
	void setUp() {
		gos = new GameObjectSet();
		flattenedBoard = new FlattenedBoard(new XY(20,20), gos);
		flattenedBoard.addGameObject(hob);
		flattenedBoard.addGameObject(extend1);
		flattenedBoard.addGameObject(extend2);
	}
	
	public void printBoard() {
		int columns = flattenedBoard.getGameBoard().length;
		int rows = flattenedBoard.getGameBoard()[0].length;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if(flattenedBoard.getGameObject(x, y) == null) {
					System.out.print(" ");
				} else {
					System.out.print(flattenedBoard.getGameObject(x, y).toChar());
				}
			}
			System.out.println();
		}
	}
	
	@Test
	void testBombWithLargerStrength() {
		try {
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.LEFT);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.LEFT);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.BOMB);
			//hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.RIGHT);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
//			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			assertTrue(flattenedBoard.getGameObject(5,5) instanceof Bomb, "The object should be instance of Bomb but is " + flattenedBoard.getGameObject(5,5).getClass().getName());
			System.out.println(flattenedBoard.getGameObject(5,5) + "   " +  flattenedBoard.getGameObject(5,5).getID());
			printBoard(); //DEBUG
			hob.setMoveCommand(MoveCommand.UP);
//			hob.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			System.out.println(flattenedBoard.getGameObject(5,5) + "   " +  flattenedBoard.getGameObject(5,5).getID());
			flattenedBoard.gameObjects.nextStep(flattenedBoard);
			System.out.println(flattenedBoard.getGameObject(5,5) + "   " +  flattenedBoard.getGameObject(5,5).getID());
//			Bomb bomb = hob.getBomb(); TODO: Kaan
			
			printBoard(); //DEBUG
//			assertThrows(NoMoreBombsAllowed.class, () -> hob.nextStep(flattenedBoard), "Placing a bomb is not allowed because: bombsAllowed = 0 " );
//			hob.nextStep(flattenedBoard);
//			assertTrue(flattenedBoard.getGameObject(5,5) instanceof Blast, "The object should be instance of Blast but is " + flattenedBoard.getGameObject(5,5).getClass().getName());
			assertTrue(flattenedBoard.getGameObject(5,5) instanceof Blast, "The object should be instance of Blast but is " + flattenedBoard.getGameObject(5,5).getClass().getName());
			
		} catch (Exception e) {
			System.err.println("This operation should not throw an . " + e.getClass().getName() + "Something might be wrong with the nextStep Method");
		}
	}

}
