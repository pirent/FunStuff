package generics;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A different kind of container that is Iterable
 * Note: it's not in Collection hierarchy
 */
public class SimpleQueue<T> implements Iterable<T> {

	private LinkedList<T> internalStorage = new LinkedList<T>();
	
	public void add(T t) {
		internalStorage.offer(t);
	}
	
	public T get() {
		return internalStorage.poll();
	}
	
	@Override
	public Iterator<T> iterator() {
		return internalStorage.iterator();
	}
	
}
