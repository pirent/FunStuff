package exercise.generics;

import typeinfo.pets.Cat;
import typeinfo.pets.Pet;
import typeinfo.pets.Pets;

class Holder<T> {
	private T a;

	public Holder(T a) {
		this.a = a;
	}

	public void set(T a) {
		this.a = a;
	}

	public T get() {
		return a;
	}
}

public class Exercise1 {
	public static void main(String[] args) {
		Holder<Pet> petHolder = new Holder<Pet>(Pets.randomPet());
		petHolder.set(new Cat("Tom"));
		System.out.println("yo");
	}
	
}
