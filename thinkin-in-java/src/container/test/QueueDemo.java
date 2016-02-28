package container.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class QueueDemo {

	
	public static void printQ(Queue queue) {
		while(queue.peek() != null) {
			System.out.print(queue.remove() + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Random rand = new Random(47);
		for (int i = 0; i < 10; i++) {
			queue.offer(rand.nextInt(i + 10));
		}
		printQ(queue);
		
		Queue<Character> qc = new LinkedList<Character>();
		for (char c : "Brontosaurus".toCharArray()) {
			qc.offer(c);
		}
		printQ(qc);
		
		System.out.println(">>> Priority queue demo <<<");
		PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
		for (int i = 0 ; i < 10; i++) {
			priorityQueue.offer(rand.nextInt(i + 10));
		}
		printQ(priorityQueue);
		
		// ================================
		List<Integer> ints = Arrays.asList(25, 22, 20, 18, 14, 9, 3, 1, 1, 2, 3, 9, 14, 18, 21, 23, 25);
		priorityQueue = new PriorityQueue<Integer>(ints);
		printQ(priorityQueue);
		
		priorityQueue = new PriorityQueue<Integer>(ints.size(), Collections.reverseOrder());
		priorityQueue.addAll(ints);
		printQ(priorityQueue);
		
		// ================================
		String fact = "EDUCATION SHOULD ESCHEW OBFUSCATION";
		List<String> strings = Arrays.asList(fact.split(""));
		PriorityQueue<String> stringPQ = new PriorityQueue<String>(strings);
		printQ(stringPQ);
		
		stringPQ = new PriorityQueue<String>(strings.size(), Collections.reverseOrder());
		stringPQ.addAll(strings);
		printQ(stringPQ);
		
		// ================================
		Set<Character> charSet = new HashSet<Character>();
		for (char c : fact.toCharArray()) {
			charSet.add(c);
		}
		PriorityQueue<Character> charPQ = new PriorityQueue<Character>(charSet);
		printQ(charPQ);
	}
}
