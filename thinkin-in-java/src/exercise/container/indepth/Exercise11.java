package exercise.container.indepth;

import java.util.PriorityQueue;
import java.util.Random;

import util.CollectionData;
import util.Generator;

/*
 * Create a class that contains an Integer that is initialized to a value
 * between o and 100 using java.util.Random. Implement Comparable using this Integer
 * field. Fill a PriorityQueue with objects of your class, and extract the values using poll( ) to
 * show that it produces the expected order.
 */
public class Exercise11 {

	public static void main(String[] args) {
		Generator<Item> gen = new Generator<Item>() {
			
			@Override
			public Item next() {
				return new Item();
			}
		};
		
		PriorityQueue<Item> priorityQueue = new PriorityQueue<Item>();
		priorityQueue.addAll(new CollectionData<Item>(gen, 10));
		while (!priorityQueue.isEmpty()) {
			System.out.println("poll it: " + priorityQueue.poll());
		}
	}
}

class Item implements Comparable<Item> {
	private static final Random rand = new Random();
	private Integer i = rand.nextInt(100);

	public Integer getI() {
		return i;
	}

	@Override
	public int compareTo(Item other) {
		return i < other.i ? -1 : (i == other.i ? 0 : 1);
	}
	
	@Override
	public String toString() {
		return String.valueOf(i);
	}
}
