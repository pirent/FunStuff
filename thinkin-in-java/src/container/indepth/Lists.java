package container.indepth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import util.Countries;

/**
 * Covers things you can do with List
 * 
 * @author vhphuc
 *
 */
public class Lists {

	private static boolean b;
	private static String s;
	private static int i;
	private static Iterator<String> it;
	private static ListIterator<String> lit;
	
	// ========================================================== ||
	// Changing things with an Iterator
	// ========================================================== ||
	public static void iterManipulation(List<String> a) {
		ListIterator<String> it = a.listIterator();
		it.add("47");
		
		// Must move to an element after add():
		it.next();
		
		// Remove the element after the newly produced one
		it.remove();
		
		// Must move to an element after remove
		it.next();
		
		// Change the element after the deleted one
		it.set("47");
	}
	
	// ========================================================== ||
	// Seeing the effect of List manipulation
	// ========================================================== ||
	public static void testVisual(List<String> a) {
		System.out.println("a = " + a);
		List<String> b = Countries.names(3);
		System.out.println("b = " + b);
		
		a.addAll(b);
		a.addAll(b);
		System.out.println("a = " + a);
		
		// Insert, remove and replace elements using ListIterator
		ListIterator<String> x = a.listIterator(a.size()/2);
		x.add("one");
		System.out.println("a = " + a);
		System.out.println("x.next: " + x.next());
		
		x.remove();
		System.out.println("x.next: " + x.next());
		
		x.set("47");
		System.out.println("a = " + a);
		
		// Traverse the list backwards
		System.out.println("Traverse backwards");
		x = a.listIterator(a.size());
		while (x.hasPrevious()) {
			System.out.print(x.previous() + " ");
		}
		System.out.println();
		System.out.println("testVisual finished");
	}
	
	// ========================================================== ||
	// Something that only LinkedList can do
	// ========================================================== ||
	public static void testLinkedList() {
		LinkedList<String> ll = new LinkedList<String>();
		ll.addAll(Countries.names(3));
		System.out.println("Something with linked list");
		System.out.println(ll);
		
		// Treat it like a stack > pushing:
		ll.addFirst("one");
		ll.addFirst("two");
		System.out.println(ll);
		
		// Like "peeking" at the top of a stack
		System.out.println("Peeking top of the stack:" + ll.getFirst());
		
		// Like popping a stack
		System.out.println("Like popping a stack by remove first: " + ll.removeFirst());
		System.out.println("Like popping a stack by remove first: " + ll.removeFirst());
		
		// Treat it like a queue, pulling elements of the tail end
		System.out.println("Remove last: " + ll.removeLast());
		System.out.println(ll);
	}
	
	public static void main(String[] args) {
		iterManipulation(new LinkedList<String>(Countries.names(3)));
		iterManipulation(new ArrayList<String>(Countries.names(3)));
		
		testVisual(new LinkedList<String>(Countries.names(3)));
		
		testLinkedList();
	}
}
