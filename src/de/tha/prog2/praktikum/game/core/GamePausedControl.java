package de.tha.prog2.praktikum.game.core;

public class GamePausedControl {
    private static final Object pausedGameLock = new Object();
    private static boolean pausedGame = false;

    public static void pause() {
        synchronized(pausedGameLock) {
            pausedGame = true;
        }
    }

    public static void resume() {
        synchronized(pausedGameLock) {
            pausedGame = false;
            pausedGameLock.notifyAll();
        }
    }

    public static void checkPaused() throws InterruptedException {
        synchronized(pausedGameLock) {
            while (pausedGame) {
                pausedGameLock.wait();
            }
        }
    }
}
