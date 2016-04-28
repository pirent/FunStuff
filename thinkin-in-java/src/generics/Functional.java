package generics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// ================================================== ||
// Different types of function objects
// ================================================== ||
/**
 * Abstracts away the specific detail of trying to add two objects,
 * and just say that they are being combined somehow.
 */
interface Combiner<T> {
	T combine(T x, T y);
}

/**
 * Takes a single argument and produces a result.
 * <br><br>
 * The argument and result need not be of the same type.
 */
interface UnaryFunction<R, T> {
	R function(T x);
}

/**
 * Used as a "collecting parameter" and you can extract
 * the result when you're finished.
 */
interface Collector<T> extends UnaryFunction<T, T> {
	
	/**
	 * Extract result of collecting parameter
	 * 
	 * @return result of collecting parameter
	 */
	T result();
}

interface UnaryPredicate<T> {
	boolean test(T x);
}

/**
 * Create a sum over a sequence of elements (of any type that can be summed), 
 * <br><br>
 * This is a case where we have common operations across classes, but the operations 
 * are not represented in any base class that we can specify - sometimes you can even
 * use a '+' operatior, and other times there may be some kind of "add" method.
 * 
 * This is the situation that you encounter when trying to write generic code, because
 * you want the code to apply across multiple classes - especially, multiple classes
 * that already exist and we have no ability to "fix".
 * <br><br>
 * Solution: <i>Strategy</i> design pattern, because it completely isolates "the thing
 * that changes inside a function object"
 */
public class Functional {
	
	/*
	 * Contains a number of generic methods that apply function objects to sequences.
	 */
	
	/**
	 * Calls the {@link Combiner} object on each element to combine it
	 * with a running result, which is finally returned.
	 */
	public static <T> T reduce(Iterable<T> seq, Combiner<T> combiner) {
		Iterator<T> iterator = seq.iterator();
		if (iterator.hasNext()) {
			T result = iterator.next();
			while (iterator.hasNext()) {
				result = combiner.combine(result, iterator.next());
			}
			return result;
		}
		
		// If seq is empty
		return null;
	}
	
	/**
	 * Take a function object and call it on each object in the list,
	 * ignoring the return value. The function object may act as a
	 * collecting parameter, so it is returned at the end.
	 */
	public static <T> Collector<T> forEach(Iterable<T> seq, Collector<T> func) {
		for (T t : seq) {
			func.function(t);
		}
		return func;
	}
	
	/**
	 * Create a list of results by calling a function object
	 * for each object in the list
	 */
	public static <R, T> List<R> transform(Iterable<T> seq, UnaryFunction<R, T> func) {
		List<R> result = new ArrayList<R>();
		for (T t : seq) {
			result.add(func.function(t));
		}
		return result;
	}
	
	/**
	 * Applies a unary predicate to each item in a sequence,
	 * and returns a list of items that produced <code>true<code>
	 */
	public static <T> List<T> filter(Iterable<T> seq, UnaryPredicate<T> predicate) {
		List<T> result = new ArrayList<T>();
		for (T t : seq) {
			if (predicate.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
	
	// ======================================================================== ||
	// To use the above generic method, these function objects need to be created
	// to adapt to particular needs
	// ======================================================================== ||
	
	static class IntegerAdder implements Combiner<Integer> {

		@Override
		public Integer combine(Integer x, Integer y) {
			return x + y;
		}
		
	}
	
	static class IntegerSubtracter implements Combiner<Integer> {

		@Override
		public Integer combine(Integer x, Integer y) {
			return x - y;
		}
		
	}
	
	static class BigDecimalAdder implements Combiner<BigDecimal> {

		@Override
		public BigDecimal combine(BigDecimal x, BigDecimal y) {
			return x.add(y);
		}
		
	}
	
	static class BigIntegerAdder implements Combiner<BigInteger> {

		@Override
		public BigInteger combine(BigInteger x, BigInteger y) {
			return x.add(y);
		}
		
	}
	
	static class AtomicLongAdder implements Combiner<AtomicLong> {

		@Override
		public AtomicLong combine(AtomicLong x, AtomicLong y) {
			return new AtomicLong(x.addAndGet(y.get()));
		}
		
	}
	
	static class BigDecimalUlp implements UnaryFunction<BigDecimal, BigDecimal> {

		@Override
		public BigDecimal function(BigDecimal x) {
			return x.ulp();
		}
		
	}
	
	static class GreaterThan<T extends Comparable<T>> implements UnaryPredicate<T> {

		private T bound;

		public GreaterThan(T bound) {
			super();
			this.bound = bound;
		}
		
		@Override
		public boolean test(T x) {
			return x.compareTo(bound) > 0;
		}
		
	}
	
	static class MultiplyingIntegerCollector implements Collector<Integer> {

		private Integer val = 1;
		
		@Override
		public Integer function(Integer x) {
			val *= x;
			return val;
		}

		@Override
		public Integer result() {
			return val;
		}
		
	}
	// ======================================================================== ||
	
	public static void main(String[] args) {
		List<Integer> li = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		Integer result = reduce(li, new IntegerAdder());
		System.out.println("Calling reduce() with IntegerAdder on " + li + ": " + result);
		
		result = reduce(li, new IntegerSubtracter());
		System.out.println("Calling reduce() with IntegerSubtracter on " + li + ": " + result);
		
		List<Integer> greaterThan4 = filter(li, new GreaterThan<Integer>(4));
		System.out.println("Calling filter() with GreaterThan(bound of 4) on " + li + ": " + greaterThan4);
		
		Integer resultOfForEachAndMultiplying = forEach(li, new MultiplyingIntegerCollector()).result();
		System.out.println("Calling forEach() with MultiplyingIntegerCollector on " + li + ": " + resultOfForEachAndMultiplying);
		
		resultOfForEachAndMultiplying = forEach(greaterThan4, new MultiplyingIntegerCollector()).result();
		System.out.println("Calling forEach() with MultiplyingIntegerCollector on " + greaterThan4 + ": " + resultOfForEachAndMultiplying);
	}
}
