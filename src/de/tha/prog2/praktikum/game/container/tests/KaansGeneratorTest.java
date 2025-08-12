package de.tha.prog2.praktikum.game.container.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tha.prog2.praktikum.game.core.GameObjectSet;
import de.tha.prog2.praktikum.game.util.XY;
import de.tha.prog2.praktikum.game.gameobjects.HandOperatedBomberman;
import de.tha.prog2.praktikum.game.level.KaansGenerator;

class KaansGeneratorTest {

    private KaansGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new KaansGenerator();
    }

    @Test
    void testGetSize() {
        XY size = generator.getSize();
        assertEquals(60, size.getX(), "Breite sollte 60 sein");
        assertEquals(15, size.getY(), "Höhe sollte 15 sein");
    }

    @Test
    void testGenerateNotNull() {
        GameObjectSet gos = generator.generate();
        assertNotNull(gos, "generate() darf nicht null zurückliefern");
    }

    @Test
    void testGenerateSize() {
        GameObjectSet gos = generator.generate();
        assertEquals(199, gos.size(),
            "Es sollten genau 199 Objekte generiert werden (150 Walls, 13 Monsters, "
          + "28 DestructibleWalls, 7 PowerUps, 1 Bomberman)");
    }

    @Test
    void testContainsBomberman() {
        GameObjectSet gos = generator.generate();
        boolean found = false;
        for (int i = 0; i < gos.size(); i++) {
            if (gos.get(i) instanceof HandOperatedBomberman) {
                found = true;
                break;
            }
        }
        assertTrue(found, "HandOperatedBomberman sollte im Set enthalten sein");
    }
}
