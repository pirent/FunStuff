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
	private Node<T> header;

	private static class Node<T> {
		private static long counter;
		private final long id;
		private T data;
		private Node<T> next;

		public Node(T data, Node<T> next) {
			this.id = counter++;
			this.data = data;
			this.next = next;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		@Override
		public String toString() {
			String nextNodeDesc = next != null ? next.id + "-" + next.getData()
					: null;
			return "Node(" + id + "): " + data.toString() + ", next: "
					+ nextNodeDesc;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (id ^ (id >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (id != other.id)
				return false;
			return true;
		}

	}

	private static class SimpleListIterator<T> {
		private SimpleList<T> list;
		private Node<T> current;
		private Node<T> previous;

		public SimpleListIterator(SimpleList<T> list) {
			this.list = list;
			this.current = list.header;
		}

		public boolean hasNext() {
			return current != null;
		}

		public T next() {
			if (current == null) {
				return null;
			}
			T result = current.getData();
			previous = current;
			current = current.getNext();

			return result;
		}

		public void add(T element) {
			if (list.size == 0) {
				list.header = new Node<T>(element, null);
				current = list.header;
			} else {
				Node<T> currentNextNode = current.getNext();
				Node<T> newNode = new Node<T>(element, currentNextNode);
				current.setNext(newNode);
			}
			list.size++;
		}

		public void remove() {
			if (list.size == 0) {
				return; // do nothing
			}
			previous.setNext(current.getNext());
			current.setNext(null);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		SimpleListIterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			T next = iterator.next();
			result.append(next).append(" > ");
		}

		return result.toString();
	}

	public SimpleListIterator<T> iterator() {
		return new SimpleListIterator<T>(this);
	}

	public static void main(String[] args) {
		SimpleList<String> simpleList = new SimpleList<String>();
		SimpleListIterator<String> iterator = simpleList.iterator();
		iterator.add("A");	// new header
		iterator.add("B");
		iterator.next(); // the current node now switch to B
		iterator.add("C");
		iterator.next(); // the current node now switch to C
		iterator.add("D");
		iterator.next(); // the current node now switch to D
		iterator.add("E");
		iterator.next(); // the current node now switch to E
		iterator.add("F");
		
		System.out.println(simpleList);
		
		iterator = simpleList.iterator();
		for (int i = 0; i < 3; i++) {
			iterator.next();
		}
		iterator.remove();

		System.out.println(simpleList);
	}
}
