package de.tha.prog2.praktikum.game.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.tha.prog2.praktikum.game.util.MoveCommand;

public class ConsoleUIImpl extends ConsoleUI {
	private final boolean blocking;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleUIImpl(boolean blocking) {
        this.blocking = blocking;
    }
    
    @Override
    public MoveCommand getCommand() {
    	String input;
        try {
            if (blocking) { 
                input = br.readLine(); 
            } else { 
                if (!br.ready()) {
                    return null; 
                }
                input = br.readLine();
            }

            if (input == null) {
                return null;
            }
            switch (input.toLowerCase()) {
                case "w": return MoveCommand.UP;
                case "s": return MoveCommand.DOWN;
                case "a": return MoveCommand.LEFT;
                case "d": return MoveCommand.RIGHT;
                case "b": return MoveCommand.BOMB;
                default:  return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public boolean[] getUserInput() {
		// TODO Auto-generated method stub
		return null;
	}

}
