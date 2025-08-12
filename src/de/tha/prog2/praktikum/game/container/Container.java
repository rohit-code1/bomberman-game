package de.tha.prog2.praktikum.game.container;

import java.util.Iterator;

public interface Container<T> extends Iterable<T> {
	/**
	 * Adds Element to the container
	 *
	 * @param o Element to add to the container
	 * @return true if this container changed as a result of the call
	 */
	boolean add(T o);
	
	/**
	 * Gets the element from index i
	 * @param i Index of the Element
	 * @return
	 */
	T get(int i);
	
	/**
	 * Gets the number of elements in the container
	 * @return Number of elements in the container
	 */
	int size();
	
	/**
	 * Removes the element from the container if it is contained in the container. 
	 * This will only remove the first instance of the object if the object is contained
	 * more than once in the container.
	 * 
	 * @param o Element to remove from the container
	 * @return true if the container changed because of the operation, false otherwise
	 */
	boolean remove(T o);
	
	/**
	 * Checks if two containers are equal. They are considered equal if all elements 
	 * contained in the container are equal. This means both containers must have the 
	 * same number of elements and the equal elements must be in the same order.
	 * @param o Other container
	 * @return
	 */
	boolean equals(Object o);
	
	/**
	 * Removes all elements from the container
	 */
	default void clear() {
		
		while(!this.isEmpty()) { //Endlos Schleife wenn alles null ist aber die größe sich nicht vermindert.
			this.remove(this.get(0));
		}
	}
	
	/**
	 * Checks if the object is contained in the container. The container 
	 * uses the equals()-Method to check if the object is in the container.
	 * 
	 * @param o Object to search for in the container
	 * @return true if the object is in the container, false otherwise
	 */
	default boolean contains(T o) {
		for (int i = 0; i < size(); i++) {
			if(get(i) == null) {continue;} // verhindert dass null mit etwas überhaupt verglichen wird. Sonst Fehler.
			if (get(i).equals(o)) {return true;} //this equal method is not from the interface
//			if (o.equals(get(i))) {return true;} //this equal method is not from the interface
//			if (equals(o)) {return true;} //equal method is from interface but it does not compare with get(i)
		}
		return false;
	}
	
	/**
	 * Converts the container content to an array an returns the array
	 * @return Array containing all elements in the container
	 */
	default Object[] toArray() {
		Object[] oa = new Object[size()];
		for (int i = 0; i < size(); i++) {
			oa[i] = get(i);
		}
		return oa;
	}
	
	/**
	 * Checks if the container contains any elements
	 * @return true if container is empty, false otherwise
	 */

    default boolean isEmpty() {
    	return this.size() == 0;
    }
    
    /**
     * Diese Methode soll einen Iterator zurück geben, 
     * der den Container in zufälliger Reihenfolge iteriert
     * @return random Iterator
     */
    public Iterator<T> iteratorRandom();
}