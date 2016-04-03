package util;

import java.util.HashSet;
import java.util.Set;

/**
 * For mathematical set operations
 * 
 * @author pirent
 *
 */
public class Sets {
	public static <T> Set<T> union(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>(a);
		result.addAll(b);
		return result;
	}
	
	public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>(a);
		result.retainAll(b);
		return result;
	}
	
	/**
	 * Subtract subset from the superset
	 * 
	 * @param superset
	 * @param subset
	 * @return
	 */
	public static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
		Set<T> result = new HashSet<T>(superset);
		result.removeAll(subset);
		return result;
	}
	
	/**
	 * Reflexive -- everything not in the intersection
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> complement(Set<T> a, Set<T> b) {
		return difference(union(a, b), intersection(a, b));
	}
}
