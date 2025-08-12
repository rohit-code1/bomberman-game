package de.tha.prog2.praktikum.game.gameobjects;

import de.tha.prog2.praktikum.game.core.ControllerContext;

public interface BotController {
	public void nextStep(ControllerContext view) throws RuntimeException;
}
