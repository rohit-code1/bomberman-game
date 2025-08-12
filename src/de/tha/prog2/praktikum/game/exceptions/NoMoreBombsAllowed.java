package de.tha.prog2.praktikum.game.exceptions;

public class NoMoreBombsAllowed extends RuntimeException{
	public NoMoreBombsAllowed() { super(); }
	public NoMoreBombsAllowed(String message) { super(message); }

}
