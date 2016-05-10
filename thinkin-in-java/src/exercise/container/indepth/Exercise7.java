package exercise.container.indepth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import util.Countries;

/*
 * Exercise 7: (4) Create both an ArrayList and a LinkedList, and fill each using the
 * Countries.names( ) generator. Print each list using an ordinary Iterator, then insert one
 * list into the other by using a Listlterator, inserting at every other location. Now perform the
 * insertion starting at the end of the first list and moving backward.
 */
public class Exercise7 {

	public static void main(String[] args) {
		ArrayList<String> al = new ArrayList<String>(Countries.names(5));
		LinkedList<String> ll = new LinkedList<String>(Countries.names(20).subList(10, 15));
		
		// Print each list using ordinary Iterator
		Iterator<String> aliter = al.iterator();
		while (aliter.hasNext()) {
			System.out.print(aliter.next() + " ");
		}
		System.out.println("*********");
		
		for (Iterator<String> iterator = ll.iterator(); iterator.hasNext();) {
			System.out.print(iterator.next() + " ");
		}
		System.out.println("********");
		
		// Insert one list into the other by using ListIterator
		int alIndex = 0;
		Iterator<String> lllistIterator = ll.iterator();
		while (lllistIterator.hasNext()) {
			al.add(alIndex++, lllistIterator.next());
		}
		System.out.println(al);
		
		// Perform insertion at the end of the first list and moving backward
		alIndex = 0;
		ListIterator<String> listIterator = ll.listIterator(ll.size());
		while (listIterator.hasPrevious()) {
			al.add(alIndex++, listIterator.previous());
		}
		System.out.println(al);
	}
}
