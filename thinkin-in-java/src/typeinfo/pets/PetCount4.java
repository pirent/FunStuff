package typeinfo.pets;

import util.TypeCounter;

public class PetCount4 {
	public static void main(String[] args) {
		TypeCounter counter = new TypeCounter(Pet.class);
		for (Pet pet : Pets.arrayList(20)) {
			System.out.print(pet.getClass().getSimpleName() + " ");
			counter.count(pet);
		}
		System.out.println();
		System.out.println(counter);
	}
}
