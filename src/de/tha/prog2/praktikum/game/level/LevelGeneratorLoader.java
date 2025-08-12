package de.tha.prog2.praktikum.game.level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.tha.prog2.praktikum.game.core.GameObjectSet;
import de.tha.prog2.praktikum.game.exceptions.NoSuchLevelGeneratorException;
import de.tha.prog2.praktikum.game.util.XY;

public class LevelGeneratorLoader {
	private static String className;
	
	public static LevelGenerator createGenerator(String name) {
		try {
//			Class<?>[] clazzPackageLevel; 
			// Dieses if statement ist nicht wirklich effizient.
			// Man bräuchte am besten eine Implementierung, wo man durch das level Paket nach den Klassen Namen iterieren könnte,
			// damit man so durch einen Loop nacheinander überprüfen kann ob die Klasse (name) im Paket verfügbar ist oder nicht.
			// Je nachdem wird eine NoSuchLevelGeneratorException geworfen oder nicht.
			
			//Keine Ahnung warum immer diese Exception geworfen wird obwohl die if Konditionen mit ! negiert werden.
//			if (!(name.equals("de.tha.prog2.praktikum.game.level.BasicGenerator")) ||  
//				!(name.equals("de.tha.prog2.praktikum.game.level.LevelGenerator"))) {
//				throw new NoSuchLevelGeneratorException(name + " was not found! Please check if the class is even available!");
//			} 
			Class<LevelGenerator> clazz = (Class<LevelGenerator>) Class.forName(name);
			String classConstructorName = name.replaceAll("de.tha.prog2.praktikum.game.level.", "");
			className = classConstructorName;
			classConstructorName = classConstructorName.concat("()");
			classConstructorName = "public de.tha.prog2.praktikum.game.level.".concat(classConstructorName);
//			System.out.println(classConstructorName);
			Constructor<?>[] constructorArray = clazz.getConstructors();
			int flag = 0;
			for(Constructor<?> cl : constructorArray) {
				if(cl.toString().equals(classConstructorName)) {flag = 1;}
//				System.out.println(cl); //DEBUG
			}
			//System.out.println(); //DEBUG
			if (flag != 1) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but your generator has not a default constructor \n ");}
			
			Method[] method = clazz.getDeclaredMethods();
			for(Method m : method) {
//				System.out.println(m); //DEBUG
			}
			//System.out.println(); //DEBUG
			
			Constructor<?> c = clazz.getConstructor();
			Object obj = c.newInstance();
//			System.out.println(obj); //DEBUG
			
			return (LevelGenerator) obj;
		} 
//		catch (NoSuchLevelGeneratorException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but there is no such LevelGenerator! Please check your input again! \n ");} 
		catch (NoSuchMethodException e) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but there is no such LevelGenerator! Please check your input again! \n ");} 
		catch (SecurityException e) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but there is no such LevelGenerator! Please check your input again! \n ");}
		catch (InstantiationException e) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but there is no such LevelGenerator! Please check your input again! \n ");}
		catch (IllegalAccessException e) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but there is no such LevelGenerator! Please check your input again! \n ");}
		catch (IllegalArgumentException e) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but there is no such LevelGenerator! Please check your input again! \n ");}
		catch (InvocationTargetException e) {throw new NoSuchLevelGeneratorException("\n \n  You typed " + name + " but there is no such LevelGenerator! Please check your input again! \n ");}
		catch (ClassCastException e) {
			throw new NoSuchLevelGeneratorException("\n \n You typed " + name + " but there is no such LevelGenerator!" + 
													"\n Maybe your class " + className + " does not implement LevelGenerator --> This leads to Casting Problems! " +
													"\n Please check your input again! \n ");
		}
		
	}

	public static void main(String[] args) {
		LevelGeneratorLoader lgl = new LevelGeneratorLoader();
		
		LevelGenerator basicGenerator = (BasicGenerator) lgl.createGenerator("de.tha.prog2.praktikum.game.level.BasicGenerator"); //OK
		GameObjectSet gos =  basicGenerator.generate(); //OK
//		System.out.println(gos.toString()); // OK
		
//		String className = "de.tha.prog2.praktikum.game.level.TestGenerator";
//		System.out.println(lgl.createGenerator(className)); // OK
//		LevelGenerator testGenerator = (TestGenerator) lgl.createGenerator(className); // OK --> Probiere selber durch auskommentieren des default Konstruktors
		
//		String className = "de.tha.prog2.praktikum.game.level.BasicGenerator";
//		System.out.println(LevelGeneratorLoader.createGenerator(className));
//		BasicGenerator bg = (BasicGenerator) LevelGeneratorLoader.createGenerator(className);
//		GameObjectSet gos =  bg.generate();
//		System.out.println(gos.toString());
	}
	
	 
}
