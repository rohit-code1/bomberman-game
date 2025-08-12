package de.tha.prog2.praktikum.game.core;

import de.tha.prog2.praktikum.game.gameobjects.HandOperatedBomberman;
import de.tha.prog2.praktikum.game.level.*;
import de.tha.prog2.praktikum.game.ui.ConsoleUI;
import de.tha.prog2.praktikum.game.ui.UI;
import de.tha.prog2.praktikum.game.util.MoveCommand;
import de.tha.prog2.praktikum.game.util.XY;
import javafx.application.Platform;
import de.tha.prog2.praktikum.game.core.GameObjectSet;
import de.tha.prog2.praktikum.game.exceptions.IllegalInputException;

public class GameImpl extends Game{
	private UI ui;
	private GameObjectSet gos;
	private String[]args;
	
	public GameImpl(GameState state, UI ui) {
		super(state);
		this.state = state;
		this.ui = ui;
		gos = state.flattenBoard().gameObjects; //Funktioniert sobald eine Objet-Variable GameObjectSet gos im FlattenBoard angelegt wird.
	}
	
	//Diese 4 Zeilen Code braucht man eigentlich nicht oder?
//	HandOperatedBomberman hob = new HandOperatedBomberman(new XY(0,0));
//	LevelGenerator lg = new BasicGenerator();
//	Board board = new Board(lg); //geht nicht weil abstrakt
	
	/**
	 * Um GameImpl so einfach und redundantarm wie möglich zu halten wird die render Methode von der {@link ConsoleUI} verwendet.
	 */
	@Override
	protected void render() { ui.render(state.flattenBoard()); }
	
	/**
	 * Verarbeitet die User-Eingabe, indem sie den eingegebenen {@link input} aus der Konsole abruft 
	 * und an die HandOperatedBomberman-Instanz weiterleitet.
	 *
	 * Die Methode sucht die einzige Instanz von {@link HandOperatedBomberman} im {@link GameObjectSet} 
	 * mithilfe einer zweiten {@link HandOperatedBomberman} Referenz die nur für Hilfszwecke in dieser Methode dienen.
	 * Falls keine Instanz gefunden wird, wird eine Fehlermeldung ausgegeben. Wenn die Instanz gefunden wird, 
	 * wird der Konsolen-Eingabewert an die Instanz übergeben.
	 *
	 * @throws IllegalStateException falls keine {@link HandOperatedBomberman}-Instanz im {@code GameObjectSet} vorhanden ist.
	 */
	@Override
	protected void processInput() {
		MoveCommand input = ui.getCommand(); //Konsolen-Eingabe in Input gespeichert
		HandOperatedBomberman hob = null; //Zweite Referenz für das selbe einzige eindeutige HandOperatedBomberman Objekt im GameObjectSet
		//Suche nach der einzigen HandOperatedBomberman Instanz
		for (int i = 0; i < gos.size(); i++) {
			if (gos.getDLL().get(i).getClass().getSimpleName().equals("HandOperatedBomberman") ) {
				hob = (HandOperatedBomberman) gos.getDLL().get(i);
			}
		}
		//Falls doch keine Instanz gefunden, dann Error und man soll was dagegen unternehmen eventuell unchecked Exception 
		if (hob == null) {
			String error = """
					!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					!!Something went wrong in processInput() method.						  !!
					!!Es gibt keine HandOperatedBomberman Instanz in Gamestate.				  !!
					!!GameState -> Board -> GameObjectSet -> ❌HandOperatedBomberman Instanz❌!! 
					!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					""";
			throw new IllegalStateException(error); //--> procssInput() FAILED
			} 
		//Instanz wurde gefunden und der Wert der Konsolen-Eingabe wird hier weitergegeben --> procssInput() SUCCESFULL
		hob.setMoveCommand(input);
		
		
		//Da processInput im Game-Loop ist und immer wieder neu ausgeführt wird, kann man geschickt die Nutzer-Eingaben von der UI 
		//regelmäßig erfragen, ob der Nutzer bestimmte Eingaben gemacht hat oder nicht.
		//So übermittelt die UI (View) zwar nicht direkt dem Game (Controller) die UserINputs aber indirekt werden diese, 
		//aufgrund des endlos-Game-Loops, doch ermittelt.
		
	}
	
	public UI getUI() { // getter für UI, wird z.B. aktuell für HandOperatedBomberman genutzt
		return ui;
	}

}
