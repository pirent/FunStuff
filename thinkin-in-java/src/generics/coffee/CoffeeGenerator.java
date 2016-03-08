package generics.coffee;

import java.util.Iterator;
import java.util.Random;

import util.Generator;

public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {

	private static final Class[] TYPES = { Coffee.class, Latte.class,
			Cappuccino.class, Americano.class, Breve.class };
	
	private static final Random rand = new Random(47);

	private final int size;
	
	public CoffeeGenerator() {
		this.size = 0;
	}
	
	public CoffeeGenerator(int size) {
		this.size = size;
	}
	
	@Override
	public Iterator<Coffee> iterator() {
		return new CoffeeGeneratorIterator();
	}

	@Override
	public Coffee next() {
		try {
			return (Coffee) TYPES[rand.nextInt(TYPES.length)].newInstance();
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		CoffeeGenerator coffeeGenerator = new CoffeeGenerator();
		for (int i = 0; i < 5; i++) {
			System.out.println(coffeeGenerator.next());
		}
		
		for (Coffee coffee : new CoffeeGenerator(5)) {
			System.out.println(coffee);
		}
	}
	
	class CoffeeGeneratorIterator implements Iterator<Coffee> {
		int count = size;
		
		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Coffee next() {
			count--;
			return CoffeeGenerator.this.next();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}
