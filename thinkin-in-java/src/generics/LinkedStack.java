package generics;

public class LinkedStack<T> {

	private class Node {
		T item;
		Node next;

		Node() {
		}

		Node(T item, Node next) {
			this.item = item;
			this.next = next;
		}

		boolean end() {
			return item == null && next == null;
		}
	}
	
	/**
	 * Used to determine when the stack is empty
	 */
	private Node top = new Node();
	
	public void push(T item) {
		top = new Node(item, top);
	}
	
	public T pop() {
		T result = top.item;
		if (!top.end()) {
			top = top.next;
		}
		return result;
	}
	
	public static void main(String[] args) {
		LinkedStack<String> stack = new LinkedStack<String>();
		for (String temp : "How you doing?".split(" ")) {
			stack.push(temp);
		}
		String s;
		while ((s = stack.pop()) != null) {
			System.out.print(s);
		}
	}
}
