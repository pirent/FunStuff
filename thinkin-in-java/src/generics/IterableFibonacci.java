package generics;

import java.util.Iterator;

/**
 * Just to make the {@link Fibonacci} iterable. One option is to reimplement the
 * class and add the {@link Iterable} interface, but you don't always have the
 * control of the original code, and you don't want to rewrite when you don't
 * have to. Instead, we can create an adapter to produce the desired instead.
 * 
 * @author pirent
 *
 */
public class IterableFibonacci extends Fibonacci implements Iterable<Integer> {

	private int n;
	
	public IterableFibonacci(int count) {
		this.n = count;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			
			@Override
			public Integer next() {
				n--;
				return IterableFibonacci.this.next();
			}
			
			@Override
			public boolean hasNext() {
				return n > 0;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static void main(String[] args) {
		for (Integer i : new IterableFibonacci(10)) {
			System.out.print(i + " ");
		}
	}
}
