package searchnsort.sort;

import util.SortUtil;

public class SelectionSortAlgorithm extends AbstractSortAlgorithm {

	public SelectionSortAlgorithm(int[] data) {
		super(data);
	}

	public void sort() {
		final int n = data.length;
		int smallestIndex;
		
		for (int i = 0; i < n - 1; i++) {
			// Assume the smallest element is the first one
			smallestIndex = i;
			
			// Test against elements after i to find the smallest
			for (int j = i + 1; j < n; j++) {
				// If this element is less, this is the new minimum
				if (data[j] < data[smallestIndex]) {
					smallestIndex = j;
				}
			}
			if (smallestIndex != i) {
				SortUtil.swap(data, smallestIndex, i);
			}
			printPass(data, i + 1, smallestIndex);
		}
	}
	
	private void printPass(int[] data, int interation, int smallestIndex) {
		System.out.println(String.format("After pass %2d:", interation));
		StringBuilder sb = new StringBuilder();
		
		// Output elements till selected item
		for (int i = 0; i < smallestIndex; i++) {
			sb.append(data[i]).append("  ");
		}
		// Indicate swap index
		sb.append(data[smallestIndex]).append("* ");
		// Finish outputting
		for (int i = smallestIndex + 1; i < data.length; i++) {
			sb.append(data[i]).append("  ");
		}
		System.out.println(sb.toString());
		
		// Indicate amount of array that is sorted
		sb = new StringBuilder();
		for (int i = 0; i < interation; i++) {
			sb.append("--  ");
		}
		System.out.println(String.format("%s", sb.toString()));
		
	}

}
