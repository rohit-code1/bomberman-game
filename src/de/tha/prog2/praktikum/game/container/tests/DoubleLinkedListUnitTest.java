package de.tha.prog2.praktikum.game.container.tests;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tha.prog2.praktikum.game.container.DoubleLinkedList;
import java.util.ConcurrentModificationException;
import de.tha.prog2.praktikum.game.exceptions.IllegalGetException;

/**
 * Unit-Test für den Container Vector. Wird aktuell nur genutzt, um Iteration zu
 * testen.
 */
public class DoubleLinkedListUnitTest {
	DoubleLinkedList list;
	Iterator iter;

	@BeforeEach
	public void setUp() {
		list = new DoubleLinkedList();
		Object object1 = new Object();
		Object object2 = new Object();
		Object object3 = new Object();
//		iter = list.iterator();
	}

	@Test
	public void checkHasNextWhenEmpty() {
		iter = list.iterator();
		assertFalse(iter.hasNext(), "hasNext() failed, because it returned 'true' for an empty container!");
	}

	@Test
	public void checkHasNextWhenNot() {
		list.add(new Object());
		iter = list.iterator();
		iter.next();
		assertFalse(iter.hasNext(),
				"hasNext() failed, because it returned 'true' for the last object of the container!");
	}

	@Test
	public void checkHasNextWhenItDoes() {
		list.add(new Object());
		iter = list.iterator();
		assertTrue(iter.hasNext(),
				"hasNext() failed, because it returned 'false', even though there is another object in the container!");
	}

	@Test
	public void checkNextObjects() {
		Object object1 = new Object();
		Object object2 = new Object();
		Object object3 = new Object();

		list.add(object1);
		list.add(object2);
		list.add(object3);

		iter = list.iterator();
		
		Object next1 = iter.next();
		assertEquals(object1, next1, "next() failed! Expected: " + object1 + ", received: " + next1);

		Object next2 = iter.next();
		assertEquals(object2, next2, "next() failed! Expected: " + object2 + ", received: " + next2);

		Object next3 = iter.next();
		assertEquals(object3, next3, "next() failed! Expected: " + object3 + ", received: " + next3);
	}

	@Test
	public void checkNextWhenEmpty() {
		iter = list.iterator();
		assertThrows(NoSuchElementException.class, () -> iter.next(),
				"next() failed! Didn't throw IllegalGetException when using next() on an empty container!");
	}

	@Test
	public void checkNextOnLastObject() {
		list.add(new Object());
		iter = list.iterator();
		iter.next();
		assertThrows(NoSuchElementException.class, () -> iter.next(),
				"next() failed! Didn't throw IllegalGetException when using next() on an empty container!");
	}
	
	@Test
	public void checkConcurrentModificationException() {
		iter = list.iterator();
		list.add(new Object()); // Dies führt zu einem inkonsistenten Zustand des Iterators da während dem Iterieren ein neues Objekt hinzugefügt wurde.
//		iter.next(); // Es kommt zu einem ConcurrentModificationException wenn man diese Zeile auskommentiert.
		assertThrows(ConcurrentModificationException.class, () -> iter.next(), "next() failed! Didn't throw ConcurrentModificationException when using next() "
				+ "and adding new Object() to Vector after initalazing the iterator. It leads to an inconsistent state of iterator!");
	}
	
	//Es ist schwierig Zufälle zu testen, deswegen bleibt dieser Testfall ungeprüft.

}
