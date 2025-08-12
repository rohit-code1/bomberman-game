package de.tha.prog2.praktikum.game.util;

//immutable class
public class XY {
	private final int x;
	private final int y;
	
	public XY (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; } 
	
	public int getY() { return y; }
	
	public XY move(XY direction) { 
		int newX = this.x + direction.x;
		int newY = this.y + direction.y;
		return new XY(newX,newY); 
	}
	
	public String toString() {
		StringBuilder pointInfo = new StringBuilder();
		pointInfo.append("Point = (" + x + "," + y + ")");
		return pointInfo.toString();
	}
	
	/**
	 * Überprüft, ob 2 XY-Werte gleichwertig sind, also die gleichen Koordinaten haben.
	 * @param pos XY-Position der zu vergleichenden Koordinate
	 * @return true, falls es sich um die gleichen Koordinaten handelt, false, wenn nicht.
	 */
	public boolean equals(XY pos) {
		return x == pos.getX() && y == pos.getY();
	}
	
	/**
	 * Berechnet die direction (Richtung) einer Bewegung aus der aktuellen Position und der Zielposition
	 * @param currentLocation die aktuelle Position
	 * @param targetLocation die Zielposition
	 * @return
	 */
	public static XY direction(XY currentLocation, XY targetLocation) {
		int x = targetLocation.getX() - currentLocation.getX();
		int y = targetLocation.getY() - currentLocation.getY();
		return new XY(x,y);
	}
	
	public int distance(XY targetLocation) {
		return Math.abs(this.getX() - targetLocation.getX()) + Math.abs(this.getY() - targetLocation.getY());
	}
}
