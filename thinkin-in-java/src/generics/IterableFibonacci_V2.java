package generics;

import java.util.Iterator;

/**
 * Using composition instead of inheritance to adapt Fibonacci to make it
 * iterable
 * 
 * @author pirent
 *
 */
public class IterableFibonacci_V2 implements Iterable<Integer> {

	private int n;
	private Fibonacci fibonacci;

	public IterableFibonacci_V2(int count) {
		this.n = count;
		this.fibonacci = new Fibonacci();
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {

			@Override
			public boolean hasNext() {
				return n > 0;
			}

			@Override
			public Integer next() {
				n--;
				return fibonacci.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static void main(String[] args) {
		for (Integer i : new IterableFibonacci_V2(10)) {
			System.out.print(i + " ");
		}
	}
}
