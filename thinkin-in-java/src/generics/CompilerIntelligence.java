package generics;

import java.util.Arrays;
import java.util.List;

public class CompilerIntelligence {

	public static void main(String[] args) {
		// -------------------------------------------------------------------------------------||
		// EXAMPLE 1
		// -------------------------------------------------------------------------------------||

		List<? extends Fruit> flist = Arrays.asList(new Apple());

		/*
		 * When you specify a list of anything extends from Fruit, the argument
		 * for add() become "? extends Fruit". That mean the compiler cannot
		 * know which specific subtype of Fruit is required here
		 * 
		 * So it won't accept any type of Fruit. The compiler refuse to call a
		 * method (like add()) if wildcard is involved in the argument list
		 */
		// flist.add(new Apple());

		/*
		 * The argument type of these methods is Object, no wildcard involved,
		 * the compiler allows the call. This mean it's up to the class designer
		 * to decide which calls are safe and to use Object type for their
		 * argument
		 */
		Apple apple = (Apple) flist.get(0); // No warning
		flist.contains(new Apple()); // Argument is "Object" type
		flist.indexOf(new Apple()); // Argument is "Object" type

		// -------------------------------------------------------------------------------------||
		// EXAMPLE 2
		// -------------------------------------------------------------------------------------||
		Holder<Apple> Apple = new Holder<Apple>(new Apple());
		Apple d = Apple.getValue();
		Apple.setValue(d);

		// Holder<Fruit> Fruit = Apple; // Cannot upcast
		Holder<? extends Fruit> Fruit = Apple; // OK

		/*
		 * If you call get(), it only returns a Fruit - This is as must as it
		 * knows given the "anything that extends Fruit" bound
		 */
		Fruit p = Fruit.getValue();
		/*
		 * If you know more about what's there, you can cast to a specific type
		 * of Fruit and there won't be any warning about it, but you risk
		 * ClassCastException
		 */
		d = (Apple) Fruit.getValue(); // Return Object

		try {
			Orange g = (Orange) Fruit.getValue(); // No warning
		}
		catch (Exception e) {
			System.out.println(e);
		}

		// Cannot call setValue()
		// Fruit.setValue(new Apple());
		// Fruit.setValue(new Fruit());

		System.out.println(Fruit.equals(d)); // OK
	}
}
