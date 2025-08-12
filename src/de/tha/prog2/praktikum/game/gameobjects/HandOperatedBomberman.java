package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.core.ControllerContext;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.XY;


public class HandOperatedBomberman extends Bomberman implements BotController {

	public HandOperatedBomberman(XY pos) {
		super(pos);
//		System.out.println("HandOperatedBomberman wurde erstellt! Pos: " + pos.getX() + "," + pos.getY() + " ID: " + getID());
	}
	
	public HandOperatedBomberman(XY location, String playerName, int points, int destroyedMonsters, int destroyedDestructibleWalls) {
		super(location, playerName, points, destroyedMonsters, destroyedDestructibleWalls);
	}
	
	@Override
	public char toChar() { return 'B'; }
	
	@Override
    public void nextStep(ControllerContext view) {
        MoveCommand cmd = this.command;  // command wird von GameImpl über setMoveCommand() befüllt
        if (cmd == null) {
            return;
        }

        // Befehl in ControllerContext übersetzen:
        switch (cmd) {
            case UP:
                view.move(new XY(0, -1));
                break;
            case DOWN:
                view.move(new XY(0,  1));
                break;
            case LEFT:
                view.move(new XY(-1, 0));
                break;
            case RIGHT:
                view.move(new XY( 1, 0));
                break;
            case BOMB:
                view.plantBomb(getLocation());
                break;
        }

        this.command = null; // Reset
    }
	
}
