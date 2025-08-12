package de.tha.prog2.praktikum.game.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import de.tha.prog2.praktikum.game.core.BoardView;
import de.tha.prog2.praktikum.game.core.FlattenedBoard;
import de.tha.prog2.praktikum.game.gameobjects.GameObject;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.Ranking;
import de.tha.prog2.praktikum.game.util.XY;

/**
 * Die Minmalanforderung an das User-Interface ist wie folgt: 
 * 
 * Es muss Eingabekommandos abholen (zunächst nur der Move) 
 * und das Rendern des {@link Boards}. 
 * 
 * Die UI hat für das Rendering Zugriff auf
 * den Zustand des Boards weshalb es einen {@link BoardView} bekommen hat.
 */
public abstract class ConsoleUI implements UI{
	private char[][] field;
	private Ranking ranking;
	
	/**
	 * Rendert das Board bzw. Spieldfeld, indem es den aktuellen Zustand des übergebenen {@link BoardView}-Objekts abruft.
	 * Die Methode durchläuft alle Positionen des Spielbretts, extrahiert das jeweilige {@link GameObject} 
	 * und wandelt es in eine Zeichenrepräsentation mithilfe des {@link temp.toChar()} Methode, um das Spielfeld zu befüllen.
	 * 
	 * Zusätzlich werden die Größe des Spielfeldes und die Details der gerenderten Objekte zu Debugging-Zwecken 
	 * in der Konsole ausgegeben.
	 *
	 * @param view Das {@link BoardView}-Objekt hat Zugriff auf den aktuellen Zustand des Spielbretts. Sie hat nähere Infos zu dessen Größe 
	 * und der {@link GameObject}-Objekte an jeder Position.
	 */
	@Override
	public void render(BoardView view) {
	    ranking = new Ranking((FlattenedBoard) view); //ACHTUNG HIER KÖNNTE EINE EXCEPTION KOMMEN FALLS ES ANDERE BoardView Realisierungen gibt
	    System.out.println("\n" + ranking.toString());
	    XY size = view.getSize();
	    System.out.println("size = " + size.toString());
	    for (int y = 0; y < size.getY(); y++) {
	    	
	        for (int x = 0; x < size.getX(); x++) {
	            GameObject temp = view.getGameObject(x, y);
	            // 1) Wenn temp==null -> leer
	            // 2) sonst das aktuelle Zeichen
	            char c = (temp == null ? ' ' : temp.toChar());
	            System.out.print(c);
	        }
	        System.out.println();
	    }
	}
	/**
	 * Wandelt die Benutzereingabe in einen entsprechenden {@link MoveCommand} um.
	 * Gibt die Bewegungsrichtung zurück, basierend auf dem eingegebenen Zeichen.
	 * 
	 * Falls es zum default case kommt, dann wird eine erneute User-Eingabe erzwingt.
	 *
	 * @return Ein passender {@link MoveCommand} basierend auf der Benutzereingabe.
	 *         Gibt null zurück, wenn keine passende Eingabe gefunden wird 
	 *         (dieser Fall tritt praktisch nie ein, da default Werte mit erneuter Eingabe behandelt werden).
	 */
	@Override
	public MoveCommand getCommand() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Ist keine Eingabe vorhanden, kann als null als MoveCommand zurückgegeben werden
			if(!br.ready()) {return null;} 
			
			// Ist Eingabe vorhanden, dann kann eingabe abgeholt werden
			// Input scannen:
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter next step for HandOperatedBomberman: ");
			char input;
			try{
				input = sc.nextLine().toLowerCase().charAt(0);
			} catch(StringIndexOutOfBoundsException e) {
				input = '\n';
			}
			
			// input (also char) in MoveCommand umwandeln:
			switch(input) {
			case 'w' : return MoveCommand.UP;
			case 's' : return MoveCommand.DOWN;
			case 'a' : return MoveCommand.LEFT;
			case 'd' : return MoveCommand.RIGHT;
			case 'b' : return MoveCommand.BOMB;
			default : 
				System.err.println("Unexpected value: " + input + ". Please try again.\n");
				return getCommand();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Bis hier her wird es nicht kommen da oben im switch der default abgearbeitet wird.
		return getCommand();
		
	}
	

	}

