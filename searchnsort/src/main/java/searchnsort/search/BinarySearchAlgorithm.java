package searchnsort.search;

import java.util.Arrays;

public class BinarySearchAlgorithm implements SearchAlgorithm {

	public int search(int[] data, int searchKey) {
		int lowerIndex = 0;
		int upperIndex = data.length - 1;
		
		// Array must be sorted first
		Arrays.sort(data);
		
		System.out.println("Data after sorted: " + Arrays.toString(data));
		System.out.println("  Start searching...");
		return binarySearch(data, searchKey, lowerIndex, upperIndex);
	}
	
	private int binarySearch(int[] data, int searchKey, int lowerIndex, int upperIndex) {
		// Test if array is empty
		if (upperIndex < lowerIndex) {
			// Array is empty, return value showing not found
			return -1;
		}
		
		// Calculate mid point to cut array in half
		int midPoint = getMidPoint(lowerIndex, upperIndex);
		
		// Printing stuff
		printRemainingElements(data, lowerIndex, upperIndex);
		printMidPointLocation(midPoint);
		
		// Three way comparison
		if (data[midPoint] > searchKey) {
			// Key in lower subset
			return binarySearch(data, searchKey, lowerIndex, midPoint - 1);
		}
		else if (data[midPoint] < searchKey) {
			// Key in upper subset
			return binarySearch(data, searchKey, midPoint + 1, upperIndex);
		}
		else {
			return midPoint;
		}
	}
	
	private int getMidPoint(int lowerIndex, int upperIndex) {
		return (lowerIndex + upperIndex)/2;
	}
	
	private void printRemainingElements(int[] data, int lowerIndex, int upperIndex) {
		StringBuilder sb = new StringBuilder();
		
		// Output spaces for alignment
		for (int i = 0; i < lowerIndex; i++) {
			sb.append("   ");
		}
		
		// Output elements left in array
		for (int i = lowerIndex; i <= upperIndex; i++) {
			sb.append(data[i] + " ");
		}
		System.out.println(sb.toString());
	}
	
	private void printMidPointLocation(int middlePoint) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < middlePoint; i++) {
			sb.append("   ");
		}
		sb.append(" * ");
		System.out.println(sb.toString());
	}

}
