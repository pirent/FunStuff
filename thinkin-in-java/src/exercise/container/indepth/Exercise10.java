package exercise.container.indepth;

import java.util.Comparator;

/*
 * Using a LinkedList as your underlying implementation, define your
 * own SortedSet
 */
public class Exercise10 {
	
}

class SimpleSortedSet<T> {
	
	private SimpleList<T> list;
	private Comparator<T> comparator;
	
	public SimpleSortedSet() {
		this.list = new SimpleList<T>();
	}
	
	
	
}
