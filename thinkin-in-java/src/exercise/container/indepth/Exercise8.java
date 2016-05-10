package exercise.container.indepth;

import util.Null;

/*
 * Create a generic, singly linked list class called SList, which, to keep
 * things simple, does not implement the List interface. Each Link object in the list should
 * contain a reference to the next element in the list, but not the previous one (LinkedList, in
 * contrast, is a doubly linked list, which means it maintains links in both directions). 
 * 
 * Create your own SListIterator which, again for simplicity, does not implement ListIterator. The
 * only method in SList other than toString( ) should be iterator( ), which produces an
 * SListIterator. The only way to insert and remove elements from an SList is through
 * SListIterator. Write code to demonstrate SList.
 */
public class Exercise8 {

}

class SimpleList<T> {
	
	private class Node<T> {
		private T element;
		private T nextElement;
		
		public Node(T element, T nextElement) {
			this.element = element;
			this.nextElement = nextElement;
		}

		public T getNextElement() {
			return nextElement;
		}

		public void setNextElement(T nextElement) {
			this.nextElement = nextElement;
		}

		public T getElement() {
			return element;
		}

		public void setElement(T element) {
			this.element = element;
		}
	}

	private class SimpleListIterator {
		private int index;
		
		public SimpleListIterator() {}
		
		public SimpleListIterator(int index) {
			this.index = index;
		}
		
		public boolean hasNext() {
			return index < size;
		}
		
		public void add(T element) {
			if (size == 0) {
				header = new Node(element, null);
				current = header;
			}
			else {
				SimpleList<T>.Node<T> newNode = new Node<T>(element, null);
				current.nextElement = newNode;
			}
			size++;
		}
	}
	
	private int size;
	private Node current;
	private Node header;
	
	@Override
	public String toString() {
		// TODO
		return null;
	}
	
	public SimpleListIterator iterator() {
		return new SimpleListIterator();
	}
	
	public static void main(String[] args) {
		
	}
}




