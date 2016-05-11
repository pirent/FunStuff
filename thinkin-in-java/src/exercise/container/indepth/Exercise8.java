package exercise.container.indepth;



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
	
	private int size;
	private Node<T> current;
	private Node<T> header;
	
	private static class Node<T> {
		private static long counter;
		private final long id;
		private T element;
		private Node<T> nextNode;
		
		public Node(T element, Node<T> nextNode) {
			this.id = counter++;
			this.element = element;
			this.nextNode = nextNode;
		}

		public T getElement() {
			return element;
		}

		public void setElement(T element) {
			this.element = element;
		}

		public Node<T> getNextNode() {
			return nextNode;
		}

		public void setNextNode(Node<T> nextNode) {
			this.nextNode = nextNode;
		}

		@Override
		public String toString() {
			String nextNodeDesc = nextNode != null ? nextNode.id + "-" + nextNode.getElement() : null;
			return "Node(" + id + "): " + element.toString() + ", next: " + nextNodeDesc;
		}
		
	}

	private static class SimpleListIterator<T> {
		private int index;
		private SimpleList<T> list;
		
		public SimpleListIterator(SimpleList<T> list) {
			this.list = list;
		}
		
		public boolean hasNext() {
			return index < list.size;
		}
		
		public void add(T element) {
			if (list.size == 0) {
				list.header = new Node<T>(element, null);
				list.current = list.header;
			}
			else {
				Node<T> newNode = new Node<T>(element, null);
				list.current.setNextNode(newNode);
				list.current = newNode;
			}
			list.size++;
		}
		
		public T next() {
			if (list.header == null) {
				return null;
			}
			T result;
			Node<T> traverseNode = list.header;
			
			for (int i = 0; i < index; i++) {
				traverseNode = traverseNode.getNextNode();
			}
			result = traverseNode.getElement();
			index++;
			
			return result;
		}
	}

	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		SimpleListIterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			T next = iterator.next();
			result.append(next).append(" || ");
		}
		
		return result.toString();
	}
	
	public SimpleListIterator<T> iterator() {
		return new SimpleListIterator<T>(this);
	}
	
	public static void main(String[] args) {
		SimpleList<String> simpleList = new SimpleList<String>();
		SimpleListIterator<String> iterator = simpleList.iterator();
		iterator.add("A");
		iterator.add("B");
		iterator.add("C");
		
		System.out.println(simpleList);
	}
}




