package generics;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import typeinfo.pets.Cat;
import typeinfo.pets.Dog;
import typeinfo.pets.Pet;

/*
 * Demo for the function of dynamic type check
 * with problem of "putting a cat in a list of Dog"
 */
public class CheckedList {

	@SuppressWarnings("unchecked")
	static void oldStyleMethod(List probablyDogs) {
		probablyDogs.add(new Cat());
	}
	
	public static void main(String[] args) {
		List<Dog> dogs1 = new ArrayList<Dog>();
		oldStyleMethod(dogs1); // Quietly accept a cat
		
		List<Dog> dogs2 = Collections.checkedList(new ArrayList<Dog>(), Dog.class);
		try {
			oldStyleMethod(dogs2);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		// Derived type works fine
		List<Pet> pets = Collections.checkedList(new ArrayList<Pet>(), Pet.class);
		pets.add(new Dog());
		pets.add(new Cat());
	}
}
