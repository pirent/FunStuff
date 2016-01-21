package container;

import java.util.ArrayList;
import java.util.ListIterator;

import typeinfo.pets.Pet;
import typeinfo.pets.Pets;

public class ListIteration {
	public static void main(String[] args) {
		ArrayList<Pet> pets = Pets.arrayList(8);
		ListIterator<Pet> it = pets.listIterator();
		while (it.hasNext()) {
			System.out.print(it.next() + ", next index: " + it.nextIndex() + ", previous index: " + it.previousIndex() + "; ");
		}
		System.out.println();
		
		// Backwards
		System.out.println("========= Test previous ========");
		while (it.hasPrevious()) {
			System.out.print(it.previous() + " ");
		}
		System.out.println();
		System.out.println(pets);
		
		System.out.println("=== iterator at specific index and try replacing ===");
		it = pets.listIterator(3);
		while (it.hasNext()) {
			it.next();
			it.set(Pets.randomPet());
		}
		System.out.println(pets);
	}
}
