package generics;

import util.Generator;

/**
 * Usage: calling this class' next() method inside a loop with a predefined
 * length of how far you want to dive in fibonacci numbers
 * 
 * @author pirent
 *
 */
public class Fibonacci implements Generator<Integer> {

	private int count;

	@Override
	public Integer next() {
		return fib(count++);
	}

	private Integer fib(int i) {
		return i < 2 ? 1 : fib(i - 2) + fib(i - 1);
	}

	public static void main(String[] args) {
		Fibonacci f = new Fibonacci();
		for (int i = 0; i < 10; i++) {
			System.out.print(f.next() + ", ");
		}
	}
}
