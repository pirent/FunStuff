package util;

import java.util.LinkedList;

/**
 * Making a stack based on {@link LinkedList}
 * 
 * @author pirent
 *
 * @param <T>
 */
public class Stack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	
	public void push(T t) {
		storage.addFirst(t);
	}
	
	public T peek() {
		return storage.getFirst();
	}
	
	public T pop() {
		return storage.removeFirst();
	}
	
	public boolean isEmpty() {
		return storage.isEmpty();
	}
	
	@Override
	public String toString() {
		return storage.toString();
	}
}
