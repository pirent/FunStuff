package searchnsort.sort;

public class InsertionSortAlgorithm extends AbstractSortAlgorithm {

	public InsertionSortAlgorithm(int[] data) {
		super(data);
	}

	/**
	 * Insert a value into a sorted sequence at the beginning of an array.
	 * <p>
	 * It operates by beginning at the end of the sequence and shifting each
	 * element one place to the right until a suitable position is found for the
	 * new element. The function has the side effect of overwriting the value
	 * stored immediately after the sorted sequence in the array.
	 */
	public void sort() {
		final int n = data.length;

		for (int next = 1; next < n; next++) {
			// Store value of the current element
			int moveItem = next;
			int insert = data[moveItem];
			for (; moveItem > 0; moveItem--) {
				if (insert < data[moveItem-1]) {
					// Shift element right one slot
					data[moveItem] = data[moveItem-1];
				} else {
					break;
				}
			}
			data[moveItem] = insert;
			printPass(data, next, moveItem);
		}
	}
	
	private void printPass(int[] data, int pass, int index) {
		System.out.println(String.format("After pass %2d: ", pass));
		StringBuilder sb = new StringBuilder();
		
		// Output elements till selected item
		for (int i = 0; i < index; i++) {
			sb.append(data[i]).append("  ");
		}
		// Indicate swap index
		sb.append(data[index]).append("* ");
		// Finish outputting
		for (int i = index + 1; i < data.length; i++) {
			sb.append(data[i]).append("  ");
		}
		System.out.println(sb.toString());
		
		// Indicate amount of array that is sorted
		sb = new StringBuilder();
		for (int i = 0; i <= pass; i++) {
			sb.append("--  ");
		}
		System.out.println(String.format("%s", sb.toString()));
	}
}
