package de.tha.prog2.praktikum.game.container;

public abstract class AbstractContainer<T> implements Container<T> {
	
	public String toString() {
		int size = this.size();
		StringBuilder string = new StringBuilder();
		
		string.append("Container content:");
		
		for(int i = 0; i < size; i++) {
			if(this.get(i) == null) {
				string.append("\nElement " + i + ": " + "null");
			}
			else {
				string.append("\nElement " + i + ": " + this.get(i).toString());
			}
		}
		
		return string.toString();
	}
	
	public boolean equals(Object o) {
		// downcast with safety check:
		if(!(o instanceof Container)) {
			return false;
		}
		
		Container<T> container = (Container<T>) o;
		/*
		// checks here if both container have the same generic (data) type
		String thisGenericType = this.getClass().getTypeName();
		String containerGenericType = container.getClass().getTypeName();
		if(!thisGenericType.equals(containerGenericType)) {
			return false;
		}
		*/
		int thisSize = this.size();
		int containerSize = container.size();
		
		// when sized differently, they can't be equal:
		if(thisSize != containerSize) {
			return false;
		}
		
		for(int i = 0; i < thisSize; i++) {
			// do all elements from (this) equal container?
			if(this.get(i) == null) {continue;} // verhindert dass null mit etwas überhaupt verglichen wird. Sonst Fehler.
 			if(!this.get(i).equals(container.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	
	public static void main(String[] args) {
		// Main wird erst funktionieren nachdem Vector einen gerischen Typparameter erhalten hat.
		Container<String> c1 = new Vector<>(String.class);
		c1.add("String1");
		c1.add("String2");
		c1.add("String3");
		Container<Integer> c2 = new Vector<>(Integer.class);
		c2.add(1);
		c2.add(2);
		c2.add(3);
		System.out.println("Do both container have c1 and c2 the same generic type?: " + c1.equals(c2));

		System.out.println();
		/*Type[] type = c1.getClass().getGenericInterfaces();
		for(Type t : type) {
			System.out.println(t);
		}*/

	}
}


