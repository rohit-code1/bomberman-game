package de.tha.prog2.praktikum.game.container.tests;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.*;

import de.tha.prog2.praktikum.game.container.Vector;
import java.util.ConcurrentModificationException;
import de.tha.prog2.praktikum.game.exceptions.IllegalGetException;
import de.tha.prog2.praktikum.game.gameobjects.GameObject;

/**
 * Unit-Test für den Container Vector. Wird aktuell nur genutzt, um Iteration zu testen.
 */
public class VectorUnitTest {
	Vector vector;
	Iterator iter;
	
	@BeforeEach
	public void setUp() {
		vector = new Vector(GameObject.class);
//		iter = vector.iterator();
	}

	@Test
	public void checkHasNextWhenEmpty() {
		iter = vector.iterator();
		assertFalse(iter.hasNext(), "hasNext() failed, because it returned 'true' for an empty container!");
	}
	
	@Test
	public void checkHasNextWhenNot() {
		vector.add(new Object());
		iter = vector.iterator();
		iter.next();
		assertFalse(iter.hasNext(), "hasNext() failed, because it returned 'true' for the last object of the container!");
	}
	
	@Test
	public void checkHasNextWhenItDoes() {
		vector.add(new Object());
		iter = vector.iterator();
		assertTrue(iter.hasNext(), "hasNext() failed, because it returned 'false', even though there is another object in the container!");
	}
	
	@Test
	public void checkNextObjects() {
		Object object1 = new Object();
		Object object2 = new Object();
		Object object3 = new Object();
		
		vector.add(object1);
		vector.add(object2);
		vector.add(object3);
		
		iter = vector.iterator();
		
		Object next1 = iter.next();
		assertEquals(object1, next1, "next() failed! Expected: " + object1 + ", received: " + next1);
		
		Object next2 = iter.next();
		assertEquals(object2, next2, "next() failed! Expected: " + object2 + ", received: " + next2);
		
		Object next3 = iter.next();
		assertEquals(object3, next3, "next() failed! Expected: " + object3 + ", received: " + next3);
	}
	
	@Test
	public void checkNextWhenEmpty() {
		iter = vector.iterator();
		assertThrows(NoSuchElementException.class, () -> iter.next(), "next() failed! Didn't throw IllegalGetException when using next() on an empty container!");
	}
	
	@Test
	public void checkNextOnLastObject() {
		vector.add(new Object());
		iter = vector.iterator();
		iter.next();
		assertThrows(NoSuchElementException.class, () -> iter.next(), "next() failed! Didn't throw IllegalGetException when using next() on an empty container!");
	}
	
	@Test
	public void checkConcurrentModificationException() {
		iter = vector.iterator();
		vector.add(new Object()); // Dies führt zu einem inkonsistenten Zustand des Iterators da während dem Iterieren ein neues Objekt hinzugefügt wurde.
//		iter.next(); // Es kommt zu einem ConcurrentModificationException wenn man diese Zeile auskommentiert.
		assertThrows(ConcurrentModificationException.class, () -> iter.next(), "next() failed! Didn't throw ConcurrentModificationException when using next() "
				+ "and adding new Object() to Vector after initalazing the iterator. It leads to an inconsistent state of iterator!");
	}
	
	//Es ist schwierig Zufälle zu testen, deswegen bleibt dieser Testfall ungeprüft.
}
