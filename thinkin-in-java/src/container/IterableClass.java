package container;

import java.util.Iterator;
import java.util.Map;

public class IterableClass implements Iterable<String> {

	protected String[] words = "And this is how we know the Earth to be banana-shaped".split(" ");
	
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < words.length;
			}

			@Override
			public String next() {
				return words[index++];
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args) {
		IterableClass iterableClass = new IterableClass();
		for (String s : iterableClass) {
			System.out.print(s + " ");
		}
		
		/*
		 * A number of classes have been made Iterable, primarily all Collection
		 * classes (except Map)
		 */
		System.out.println("\n\n=== print out environment variables ===\n\n");
		for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

}
