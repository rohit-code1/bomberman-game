package de.tha.prog2.praktikum.game.container.tests;

import de.tha.prog2.praktikum.game.container.Container;

import java.util.ConcurrentModificationException;
import de.tha.prog2.praktikum.game.exceptions.IllegalGetException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class DummyContainer implements Container {
	private java.util.List<Object> arrayList = new ArrayList<>();
	int changes = 0;
	
	@Override
	public boolean add(Object o) {
		changes++;
		return arrayList.add(o);
	}

	@Override
	public Object get(int i) {
		return arrayList.get(i);
	}

	@Override
	public int size() {
		return arrayList.size();
	}

	@Override
	public boolean remove(Object o) {
		changes++;
		return arrayList.remove(o);
	}

	@Override
	public Iterator iterator() {
		return new Iterator() {
			int cursor = 0;
			int initialChanges = changes;

			@Override
			public boolean hasNext() {
				if (initialChanges != changes) throw new ConcurrentModificationException("Double Linked List " + this + " wurde während des iterieren verändert und führt nun zum inkonistenten Zustand!");
				try {
					get(cursor);
				} catch(IllegalGetException e) {
					return false;
				}
				return true;
			}
			
			@Override
			public Object next() {
				if (initialChanges != changes) throw new ConcurrentModificationException("Double Linked List " + this + " wurde während des iterieren verändert und führt nun zum inkonistenten Zustand!");
				if (!hasNext()) throw new IllegalGetException("Index " + cursor + " is out of Boundry of Double Linked List Container: Accepted boundry = [0 - " + size() + "]");
				return get(cursor++);
			}
		};
	}

	@Override
	public Iterator iteratorRandom() {
		return new Iterator() {
			int initialChanges = changes;
			private int index = 0;
			private Random rand = new Random();
			private int[] alreadyEnteredIndexRandom = createRandomOrder();

			private int createRandomIndex() {
				return rand.nextInt(size() + 1);
			}

			private int[] createRandomOrder() {
				int[] RandomOrder = new int[size()];

				for (int i = 0; i < size(); i++) {
					int randomIndex = createRandomIndex();
					boolean containsRandom = true;

					while (containsRandom) {
						containsRandom = false;

						for (int j = 0; j < i; j++) {
							if (RandomOrder[j] == randomIndex) {
								containsRandom = true;
								randomIndex = createRandomIndex();
								break;
							}
						}
					}
					RandomOrder[i] = randomIndex;
				}
				return RandomOrder;
			}

			// Diese Methode soll einen Iterator zurück geben, der den Container in
			// zufälliger Reihenfolge iteriert.
			public void iterateRandomly() {
				if (initialChanges != changes)
					throw new ConcurrentModificationException("Double Linked List " + this
							+ " wurde während des iterieren verändert und führt nun zum inkonistenten Zustand!");

				for (int i = 0; i < alreadyEnteredIndexRandom.length; i++) {
					System.out.print(alreadyEnteredIndexRandom[i] + " ");
				}
			}

			@Override
			public boolean hasNext() {
				if (initialChanges != changes)
					throw new ConcurrentModificationException("Double Linked List " + this
							+ " wurde während des iterieren verändert und führt nun zum inkonistenten Zustand!");
				try {
					get(index);
				} catch (IllegalGetException e) {
					return false;
				}
				return true;
			}

			@Override
			public Object next() {
				if (initialChanges != changes)
					throw new ConcurrentModificationException("Double Linked List " + this
							+ " wurde während des iterieren verändert und führt nun zum inkonistenten Zustand!");
				if (!hasNext()) {
					throw new IllegalGetException("Index " + index
							+ " is out of Boundry of Double Linked List Container: Accepted boundry = [0 - " + size() + "]");
				}
				return alreadyEnteredIndexRandom[index++];
			}

		};
	}
}
