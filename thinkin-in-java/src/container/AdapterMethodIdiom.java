package container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

class ReversibleArrayList<T> extends ArrayList<T> {
	public ReversibleArrayList(Collection<T> c) {
		super(c);
	}
	
	public Iterable<T> reverse() {
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					int current = size() - 1;
					
					@Override
					public boolean hasNext() {
						return current > -1;
					}

					@Override
					public T next() {
						return get(current--);
					}
					
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}
/**
 * Allow you to use foreach with additional kinds of Iterables
 * 
 * @author pirent
 *
 */
public class AdapterMethodIdiom {
	public static void main(String[] args) {
		ReversibleArrayList<String> ral = new ReversibleArrayList<String>(Arrays.asList("To be or not to be".split(" ")));
		
		// Grab the ordinary iterator via iterator()
		for (String s : ral) {
			System.out.print(s + " ");
		}
		System.out.println();
		
		// Hand it the iterator of your choice
		for (String s : ral.reverse()) {
			System.out.print(s + " ");
		}
	}
}
