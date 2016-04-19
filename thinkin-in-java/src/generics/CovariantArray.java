package generics;

class Fruit {
};

class Apple extends Fruit {
};

class Jonathan extends Apple {
};

class Orange extends Fruit {
};

/*
 * Demo a particular behavior of arrays: You can assign an array of derived type
 * to an array reference of base type
 */
public class CovariantArray {
	public static void main(String[] args) {
		// This assignment is that mentioned behavior
		// Apple is a kind of Fruit, so an array of Apple can also be an array of Fruit
		Fruit[] fruit = new Apple[10];
		fruit[0] = new Apple(); // OK
		fruit[1] = new Jonathan(); // OK
		
		// However, runtime type is Apple[], not Fruit[] or Orange[]
		// So only an Apple or a subtype of Apple can be placed into the array
		// Both of these operations below will throw a ArrayStoreException
		try {
			// Make sense to compiler, because it has Fruit[] reference
			fruit[2] = new Fruit();
		}
		catch (Exception e) {
			// However, runtime mechanism know that it's dealing with an Apple[]
			// and throws exception when a foreign type is placed into array
			System.out.println(e);
		}
		
		try {
			fruit[3] = new Orange();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
