package de.tha.prog2.praktikum.game.container.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tha.prog2.praktikum.game.exceptions.NoSuchLevelGeneratorException;
import de.tha.prog2.praktikum.game.level.*;

class LevelGeneratorLoaderTest {
	String basicGenerator;
	String kaansGenerator;
	String testGenerator;
	String fakeGenerator;
	LevelGeneratorLoader lg_loader;
	
	@BeforeEach
	void setUp(){
		lg_loader = new LevelGeneratorLoader();
		basicGenerator = "de.tha.prog2.praktikum.game.level.BasicGenerator";
		kaansGenerator = "de.tha.prog2.praktikum.game.level.KaansGenerator";
		testGenerator = "de.tha.prog2.praktikum.game.level.TestGenerator";
		fakeGenerator = "de.tha.prog2.praktikum.game.level.FakeGenerator";
	}

	@Test
	void createGenerator_BasicGenerator_Test() {
		BasicGenerator bg = new BasicGenerator();
		assertTrue(lg_loader.createGenerator(basicGenerator) instanceof BasicGenerator, basicGenerator + "should be a Generator: Something went wrong!");
	}
	@Test
	void createGenerator_KaansGenerator_Test() {
		KaansGenerator kg = new KaansGenerator();
		assertTrue(lg_loader.createGenerator(kaansGenerator) instanceof KaansGenerator, kaansGenerator + "should be a Generator: Something went wrong!");
	}
	@Test
	void createGenerator_TestGenerator_Test() {
		TestGenerator tg = new TestGenerator();
		assertTrue(lg_loader.createGenerator(testGenerator) instanceof TestGenerator, testGenerator + "should be a Generator: Something went wrong!");
	}
	
	// FakeGenerator wirft eine Exception und soll assertThrows prüfen
	@Test
	void createGenerator_FakeGenerator_Test() {
		assertThrows(NoSuchLevelGeneratorException.class,() -> lg_loader.createGenerator(fakeGenerator), fakeGenerator + "should NOT be a Generator: Something went wrong!");
	}

}
