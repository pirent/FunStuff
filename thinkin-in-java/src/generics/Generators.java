package generics;

import generics.coffee.Coffee;
import generics.coffee.CoffeeGenerator;

import java.util.ArrayList;
import java.util.Collection;

import util.Generator;

/**
 * Use {@link Generator} to fill a collection and it makes sense to generify
 * this method.
 */
public class Generators {

	public static <T> Collection<T> fill(Collection<T> col, Generator<T> gen, int n) {
		for (int i = 0; i < n; i++) {
			col.add(gen.next());
		}
		return col;
	}
	
	public static void main(String[] args) {
		Collection<Coffee> coffee = fill(new ArrayList<Coffee>(), new CoffeeGenerator(), 4);
		for (Coffee c : coffee) {
			System.out.println(c);
		}
		
		Collection<Integer> fibonacci = fill(new ArrayList<Integer>(), new Fibonacci(), 12);
		for (Integer i : fibonacci) {
			System.out.print(i + ", ");
		}
	}
}
