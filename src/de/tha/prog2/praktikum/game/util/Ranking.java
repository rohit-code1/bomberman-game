package de.tha.prog2.praktikum.game.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import de.tha.prog2.praktikum.game.core.FlattenedBoard;
import de.tha.prog2.praktikum.game.gameobjects.Bomberman;
import de.tha.prog2.praktikum.game.gameobjects.GameObject;

public class Ranking {
    private LinkedList<Bomberman> list = new LinkedList<>();

	//Von der Aufgabenstellung vorgegeben 
    public Ranking() {}

	//In der UI soll Ranking Instanz am besten bereits so automatisiert laufen, sodass man nur System.out.println(instanz.toString()) schreiben muss.
    public Ranking(FlattenedBoard context) {
        Iterator<GameObject> it = context.getGameObjectSet().getDLL().iterator();
        while (it.hasNext()) {
            GameObject temp = it.next();
            if (temp instanceof Bomberman) {
                addBomberman((Bomberman) temp);
            }
            updateRanking();
        }
    }

    public void addBomberman(Bomberman bm) {list.add(bm);}

	// Wenn zwei oder mehrere Bomberman Objekte vom Score gleich sind sollen sie die gleiche Ranking haben.
    public int getRanking(Bomberman bm) { updateRanking(); return bm.getRanking();}

    public void updateRanking() {
        Collections.sort(list);

        // Bei leerer Liste nichts tun:
        if (list.isEmpty()) {
            return;
        }

        // Ersten Bomberman auf Rang = 1 setzen:
        int currentRank = 1;
        list.get(0).setRanking(currentRank);

        // Ab dem 2. Eintrag in der Liste:
        //  - Wenn er exakt im Gleichstand (compareTo == 0) zum Vorgänger ist, bekommt er denselben Rang
        //  - Falls nicht, erhöhen wir currentRank um 1 und setzen ihn drauf
        for (int i = 1; i < list.size(); i++) {
            Bomberman prev = list.get(i - 1);
            Bomberman curr = list.get(i);

            // Fall "Gleichstand" (selber Rang):
            if (prev.compareTo(curr) == 0) {
                curr.setRanking(currentRank);
            // Fall "kein Gleichstand" (nächster Rang):
            } else {
                currentRank++;
                curr.setRanking(currentRank);
            }
        }
    }

    @Override
    public String toString() {
        String format = "%-10s %-20s %-10s %-10s %-10s%n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(format, "Platz", "Playername", "Punkte", "Monster", "DestructibleWall"));

        updateRanking();

        for (int i = 0; i < list.size(); i++) {
            Bomberman bm = list.get(i);

            String platzStr;
            if (i == 0) {
                // Rangzahl printen:
                platzStr = String.valueOf(bm.getRanking());
            } else {
                Bomberman prev = list.get(i - 1);
                // Wenn Gleichstand -> leer
                if (prev.compareTo(bm) == 0) {
                    platzStr = "";
                // Kein Gleichstand -> Rangzahl printen:
                } else {
                    platzStr = String.valueOf(bm.getRanking());
                }
            }
            sb.append(String.format(
                format,
                platzStr,
                bm.getPlayerName(),
                bm.getPoints(),
                bm.getDestroyedMonsters(),
                bm.getDestroyedDestructibleWalls()
            ));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
    	/*Bomberman b1 = new Bomberman(new XY(5, 10), "Bob", 100, 3, 2);
		Bomberman b12 = new Bomberman(new XY(5, 10), "Bob", 100, 2, 2);
		Bomberman b13 = new Bomberman(new XY(5, 10), "Bob", 100, 2, 1);
		Bomberman b2 = new Bomberman(new XY(0, 0), "NumerDOS", 200, 5, 4);
		Bomberman b22 = new Bomberman(new XY(0, 0), "NumerDOS", 200, 5, 4);
		Bomberman b3 = new Bomberman(new XY(8, 3), "Alice", 150, 2, 6);
		Bomberman b4 = new Bomberman(new XY(12, 7), "NumeroUNO", 300, 10, 1);
		Bomberman b5 = new Bomberman(new XY(4, 9), "Random", 50, 0, 0);
		Ranking ranking = new Ranking();
		ranking.addBomberman(b1);
		ranking.addBomberman(b12);
		ranking.addBomberman(b13);
		ranking.addBomberman(b2);
		ranking.addBomberman(b22);
		ranking.addBomberman(b3);
		ranking.addBomberman(b4);
		ranking.addBomberman(b5);
		ranking.updateRanking();
		System.out.println(ranking.toString());*/
		
		//Diese Tests sollen laut den Git Server Tests einen Fehler erhalten :
		/*Test: testGetRanking() FAILED
		Failure: GameObject = HandOperatedBomberman; ID = 5; Location = 1,1; Points = 150
		Player Name: Spieler 6
		Number of Destroyed DestructibleWalls:6
		Number of Destroyed Monster:3 should be on rank 3 ==> expected: <3> but was: <-1>*/
		Bomberman spieler1 = new Bomberman(new XY(1,1), "spieler1",100,4,5);
		Bomberman spieler2 = new Bomberman(new XY(1,1), "spieler2",200,5,5);
		Bomberman spieler3 = new Bomberman(new XY(1,1), "spieler3",200,4,5);
		Bomberman spieler4 = new Bomberman(new XY(1,1), "spieler4",150,3,5);
		Bomberman spieler5 = new Bomberman(new XY(1,1), "spieler5",150,3,6);
		Bomberman spieler6 = new Bomberman(new XY(1,1), "spieler6",150,3,6);
		Ranking ranking = new Ranking();
		ranking.addBomberman(spieler1);
		ranking.addBomberman(spieler2);
		ranking.addBomberman(spieler3);
		ranking.addBomberman(spieler4);
		ranking.addBomberman(spieler5);
		ranking.addBomberman(spieler6);
		ranking.updateRanking();
		System.out.println(ranking.toString());
    }
}
