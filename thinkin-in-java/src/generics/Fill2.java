package generics;

import generics.coffee.Coffee;
import generics.coffee.Latte;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import util.Generator;

/**
 * Using adapters to simulate latent typing.
 */

interface Addable<T> {
	void add(T t);
}

public class Fill2 {
	// Class-token version
	public static <T> void fill(Addable<T> addable, Class<? extends T> typeToken, int size) {
		for (int i = 0; i < size; i++) {
			try {
				addable.add(typeToken.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	// Generator version
	public static <T> void fill(Addable<T> addable, Generator<T> generator, int size) {
		for (int i = 0; i < size; i++) {
			addable.add(generator.next());
		}
	}
}

/*
 * To adapt a base type, use composition.
 * 
 * Make any Collection add-able by using composition
 */
class AddableCollectionAdapter<T> implements Addable<T> {

	private Collection<T> collection;
	
	public AddableCollectionAdapter(Collection<T> collection) {
		super();
		this.collection = collection;
	}

	@Override
	public void add(T t) {
		collection.add(t);
	}
	
}

/*
 * To adapt a specific type, inheritance can be used.
 * 
 * Make a SimpleQueue add-able by using inheritance.
 */
class AddableSimpleQueue<T> extends SimpleQueue<T> implements Addable<T> {
	@Override
	public void add(T t) {
		super.add(t);
	}
}

/**
 * A Helper to capture the type automatically
 */
class Adapter {
	public static <T> Addable<T> makeCollectionAddable(Collection<T> collection) {
		return new AddableCollectionAdapter<T>(collection);
	}
}

class Fill2Test {
	public static void main(String[] args) {
		List<Coffee> carrier = new ArrayList<Coffee>();
		
		Fill2.fill(new AddableCollectionAdapter<Coffee>(carrier), Coffee.class, 3);
		
		// helper method captures the type
		Fill2.fill(Adapter.makeCollectionAddable(carrier), Latte.class, 2);
		
		for (Coffee coffee : carrier) {
			System.out.println(coffee);
		}
		System.out.println("---------------------------");
		
		// Use an adapted class
		AddableSimpleQueue<Coffee> coffeeQueue = new AddableSimpleQueue<Coffee>();
		Fill2.fill(coffeeQueue, Coffee.class, 3);
		Fill2.fill(coffeeQueue, Latte.class, 1);
		
		for (Coffee coffee : coffeeQueue) {
			System.out.println(coffee);
		}
	}
}