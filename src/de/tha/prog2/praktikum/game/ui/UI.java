package de.tha.prog2.praktikum.game.ui;

import de.tha.prog2.praktikum.game.core.BoardView;
import de.tha.prog2.praktikum.game.util.MoveCommand;

public interface UI {

	public MoveCommand getCommand();
	
	public void render(BoardView view);
	
	public boolean[] getUserInput();
}
