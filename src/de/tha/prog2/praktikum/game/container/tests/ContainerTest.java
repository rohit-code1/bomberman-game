package de.tha.prog2.praktikum.game.container.tests;

import java.util.Arrays;
import de.tha.prog2.praktikum.game.container.*;

public class ContainerTest{
	
	public static void main(String[] args) {
//		testContainer(new DummyContainer()); // Alle Tests OK
		testContainer(new DoubleLinkedList()); // Tests mit 3 WARNUNGEN 
//		testContainer(new Vector()); // Alle Test OK
	}
	
	public static void testContainer(Container container) {
		
		// create test objects:
		Object o = new Object();
		Object o2 = new Object();
		
		// .add tests:
		if(container.add(o)) {
			System.out.println("01. Test erfolgreich abgeschlossen! (.add)");
		} else {
			System.out.println("WARNUNG: 1. Test fehlgeschlagen! (.add)");
		}
		
		// .remove tests:
		if(container.remove(o)) {
			System.out.println("02. Test erfolgreich abgeschlossen! (.remove)");
		} else {
			System.out.println("WARNUNG: 02. Test fehlgeschlagen! (.remove)");
		}
		
		// .size tests:
		if(container.size() == 0) {
			System.out.println("03. Test erfolgreich abgeschlossen! size = 0 -> sollte sein: 0");
		} else {
			System.out.println("WARNUNG: 03. Test fehlgeschlagen! size = " + container.size() + " , sollte sein: 0");
		}
	
		container.add(o);
		
		if(container.size() == 1) {
			System.out.println("04. Test erfolgreich abgeschlossen! size = 1 -> sollte sein: 1");
		} else {
			System.out.println("WARNUNG: 04. Test fehlgeschlagen! size = " + container.size() + " , sollte sein: 1");
		}
		
		container.remove(o);
		
		if(container.size() == 0) {
			System.out.println("05. Test erfolgreich abgeschlossen! size = 0 -> sollte sein: 0");
		} else {
			System.out.println("WARNUNG: 05. Test fehlgeschlagen! size = " + container.size() + " , sollte sein: 0");
		}
		
		// .get tests:
		
		container.add(o);
		if(container.get(0).equals(o)) {
			System.out.println("06. Test erfolgreich abgeschlossen! (.get)");
		} else {
			System.out.println("WARNUNG: 06. Test fehlgeschlagen! (.get)");
		}
		
		container.add(o2);
		if(container.get(1) == o2) {
			System.out.println("07. Test erfolgreich abgeschlossen! (.get)");
		} else {
			System.out.println("WARNUNG: 07. Test fehlgeschlagen! (.get)");
		}
		
		// .contains tests:
		
		if(container.contains(o)) {
			System.out.println("08. Test erfolgreich abgeschlossen! (.contains)");
		} else {
			System.out.println("WARNUNG: 08. Test fehlgeschlagen! (.contains)");
		}
		
		if(container.contains(o2)) {
			System.out.println("09. Test erfolgreich abgeschlossen! (.contains)");
		} else {
			System.out.println("WARNUNG: 09. Test fehlgeschlagen! (.contains)");
		}
		
		// .equals tests:
		
		if(container.equals(container)) {
			System.out.println("10. Test erfolgreich abgeschlossen! (.equals)");
		} else {
			System.out.println("WARNUNG: 10. Test fehlgeschlagen! (.equals)");
		}
		
		// .toArray tests:
		
		Object[] testArray = container.toArray();
		Object[] realArray = {o,o2};
		
		if(Arrays.equals(testArray, realArray)) {
			System.out.println("11. Test erfolgreich abgeschlossen! (.toArray)");
		} else {
			System.out.println("WARNUNG: 11. Test fehlgeschlagen! (.toArray)");
		}
		
		// .toString tests:
		
		if(container.toString() != null && !container.isEmpty()) {
			System.out.println("12. Test erfolgreich abgeschlossen! (.toString)");
		} else {
			System.out.println("WARNUNG: 12. Test fehlgeschlagen! (.toString)");
		}
		
		
		// .clear tests:
		
		container.clear();
		
		if(container.size() == 0) {
			System.out.println("13. Test erfolgreich abgeschlossen! size = 0 -> sollte sein: 0 (.clear)");
		} else {
			System.out.println("WARNUNG: 13. Test fehlgeschlagen! size = " + container.size() + " , sollte sein: 0 (.clear)");
		}
		
		// .isEmpty tests:
		
		if(container.isEmpty()) {
			System.out.println("14. Test erfolgreich abgeschlossen! isEmpty = true -> sollte sein: true (.isEmpty)");
		} else {
			System.out.println("WARNUNG: 14. Test fehlgeschlagen! isEmpty = false -> sollte sein: true (.isEmpty)");
		}
		
		
	}
}
