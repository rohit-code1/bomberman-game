package de.tha.prog2.praktikum.game.exceptions;

public class IllegalMoveException extends RuntimeException{
	public IllegalMoveException() { super(); }
	public IllegalMoveException(String message) { super(message); }
}
