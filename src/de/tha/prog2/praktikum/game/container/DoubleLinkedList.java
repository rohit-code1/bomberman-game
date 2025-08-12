package de.tha.prog2.praktikum.game.container;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import java.util.ConcurrentModificationException;
import de.tha.prog2.praktikum.game.exceptions.DuplicateObjectException;
import de.tha.prog2.praktikum.game.exceptions.IllegalGetException;
import de.tha.prog2.praktikum.game.exceptions.ObjectNotFoundException;

public class DoubleLinkedList<T> extends AbstractContainer<T> {

	Node<T> head;
	Node<T> tail;
	int size;

	int changes = 0;

	@Override
	public boolean add(T o) {
		if (contains(o)) {
			throw new DuplicateObjectException(
					"Duplicate Objects are not allowed in Double Linked List Container: Object " + o
							+ " is already in this Container");
		}
		Node<T> node = new Node<>(o);
		if (head == null) {
			head = node;
			tail = node;
		} else {
			// Am Tail new Node anhängen
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
		changes++;
		return true;

	}

	@Override
	public T get(int i) {
		if (i < 0 || i >= size) {
			throw new IllegalGetException("Index " + i
					+ " is out of Boundry of Double Linked List Container: Accepted boundry = [0 - " + size() + "]");
		}
		Node<T> temp = head;
		for (int j = 0; j < i; j++) {
			temp = temp.next;
		}
		
		return temp.data;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean remove(T o) {
		if (!contains(o)) {
			throw new ObjectNotFoundException(
					"Object is not available in Double Linked List Container Container: Object " + o
							+ " is not in this Container");
		}
		Node<T> temp = head;
		while (temp != null) {

			if (o.equals(temp.data)) {
				if (temp.prev != null) {
					temp.prev.next = temp.next;
				} else {
					head = temp.next;
				}

				if (temp.next != null) {
					temp.next.prev = temp.prev;
				}else {
					tail = temp.prev;    // ← hier fehlte die Aktualisierung von tail
				}

				size--;
				changes++;
				return true;
			}
			temp = temp.next;
		}
		return false;
	}

	@Override
	public String toString() {
		String list = "";
		Node<T> cur = head;
		while (cur != null) {
			list += cur.data + "->";
			cur = cur.next;
		}
		list += "END";
		Node<T> last = tail;
		list += "\n";
		while (last != null) {
			list += last.data + "->";
			last = last.prev;
		}
		list += "START";
		return list;
	}

	@Override
	public Iterator<T> iteratorRandom() {
	    // 1) Kopie aller aktuellen Elemente
	    List<T> snapshot = new ArrayList<T>(size);
	    Iterator<T> it = this.iterator(); // der „normale“ Iterator, der schon mod-checks hat
	    while (it.hasNext()) {
	        snapshot.add(it.next());
	    }
	    // 2) Mischen
	    Collections.shuffle(snapshot);
	    // 3) Erwarteten Änderungsstand merken
	    final int expectedChanges = changes;
	    // 4) Wrappen und zurückgeben
	    Iterator<T> snapIt = snapshot.iterator();
	    return new Iterator<T>() {
	        @Override
	        public boolean hasNext() {
	            if (expectedChanges != changes) {
	                throw new java.util.ConcurrentModificationException();
	            }
	            return snapIt.hasNext();
	        }

	        @Override
	        public T next() {
	            if (expectedChanges != changes) {
	                throw new java.util.ConcurrentModificationException();
	            }
	            return snapIt.next();
	        }
	    };
	}


	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
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
			public T next() {
				if (initialChanges != changes) throw new ConcurrentModificationException("Double Linked List " + this + " wurde während des iterieren verändert und führt nun zum inkonistenten Zustand!");
				if (!hasNext()) throw new NoSuchElementException("Index " + cursor + " is out of Boundry of Double Linked List Container: Accepted boundry = [0 - " + (size() -1) + "]");
				return get(cursor++);
			}

		};
	}

	public static void main(String[] args) {
		DoubleLinkedList<Object> dll = new DoubleLinkedList<>();
		dll.add("first String");
		dll.add("second String");
		dll.add("third String");
//		dll.add("third String"); //OK
		dll.clear();
		System.out.println(dll.size());
		System.out.println(dll.toString());
		dll.add("first String");
		dll.add("second String");
		dll.add("third String");
		System.out.println(dll.toString());
		dll.remove("second String");
//		dll.remove("fourth String"); //OK
		System.out.println(dll.size());
		System.out.println(dll.get(0));
		System.out.println(dll.get(1));
//		System.out.println(dll.get(100)); //OK
//		System.out.println(dll.get(-1)); //OK
		System.out.println(dll.toString());

//		System.out.println(dll.get(0));
//		System.out.println(dll.get(1));
//		System.out.println(dll.get(2));
//		System.out.println(dll.get(3));
////		System.out.println(dll.get(4));
//		System.out.println();
//		dll.add(99);
//		dll.remove(50);
//		System.out.println("size: " + dll.size());
//		System.out.println(dll.toString());
	}

	
	
}

class Node<T> {
	T data;
	Node<T> next;
	Node<T> prev;

	public Node(T o) {
		this.data = o;
	}

	public Node(T o, Node<T> next, Node<T> prev) {
		this.data = o;
		this.next = next;
		this.prev = prev;
	}

}

