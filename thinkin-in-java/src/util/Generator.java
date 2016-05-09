package util;

/**
 * A specialization of the Factory Method design pattern, but when you ask
 * generator for a new object, you don't past it any arguments. The generator
 * know how to create a new object without extra information.
 * 
 * @author pirent
 *
 * @param <T>
 */
public interface Generator<T> {
	
	/**
	 * Generate a new instance of a specific type.
	 * 
	 * @return
	 */
	T next();
}
