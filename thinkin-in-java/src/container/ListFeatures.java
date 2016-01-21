package container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import typeinfo.pets.Cymric;
import typeinfo.pets.Hamster;
import typeinfo.pets.Mouse;
import typeinfo.pets.Pet;
import typeinfo.pets.Pets;

public class ListFeatures {

	public static void main(String[] args) {
		Random rand = new Random(47);
		List<Pet> pets = Pets.arrayList(7);
		System.out.println("1: " + pets);
		
		Hamster h = new Hamster();
		pets.add(h); // Automatically resize
		System.out.println("2: " + pets);
		System.out.println("3: " + pets.contains(h));
		pets.remove(h); // Remove by object
		
		Pet p = pets.get(2);
		System.out.println("4: " + p + " " + pets.indexOf(p));
		
		Pet cymric = new Cymric();
		System.out.println("5: " + pets.indexOf(cymric));
		System.out.println("6: " + pets.remove(cymric));
		
		// Must be the exact objects
		System.out.println("7: " + pets.remove(p));
		System.out.println("8: " + pets);
		
		pets.add(3, new Mouse());
		System.out.println("9: " + pets);
		List<Pet> sub = pets.subList(1, 4);
		System.out.println("Sublist: " + sub);
		System.out.println("10: " + pets.containsAll(sub));
		
		// Compilation-error: not allow to sort
		// argument type must be subclass of comparable
//		Collections.sort(sub); // In-place sort
		
		Collections.shuffle(sub, rand); // Mix it up
		System.out.println("Shuffled sublist: " + sub);
		System.out.println("12: " + pets.containsAll(sub));
		
		List<Pet> copy = new ArrayList<Pet>(pets);
		sub = Arrays.asList(pets.get(1), pets.get(4));
		System.out.println("sub: " + sub);
		copy.retainAll(sub);
		System.out.println("13: " + copy);
		
		copy = new ArrayList<Pet>(pets); // Get a fresh copy
		copy.remove(2);
		System.out.println("14: " + copy);
		copy.removeAll(sub); // Only removes exact objets
		System.out.println("15: " + copy);
		
		copy.set(1, new Mouse()); // Replace an element
		System.out.println("16: " + copy);
		
		copy.addAll(2, sub); // Insert a list in the middle
		System.out.println("17: " + copy);
		System.out.println("18: " + pets.isEmpty());
		pets.clear();
		
		System.out.println("19: " + pets);
		System.out.println("20: " + pets.isEmpty());
		
		pets.addAll(Pets.arrayList(4));
		System.out.println("21: " + pets);
		Object[] o = pets.toArray();
		System.out.println("22: " + o[3]);
		Pet[] pa = pets.toArray(new Pet[0]);
		System.out.println("23: " + pa[3]);
		
	}
}
