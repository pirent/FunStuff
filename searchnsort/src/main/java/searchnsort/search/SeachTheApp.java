package searchnsort.search;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import util.ScannerUtil;

public class SeachTheApp {
	
	private static final Random generator = new Random();
	private static int[] data;
	
	public static void main(String[] args) {
		System.out.println("------------------ Hello world! -----------------------");
		Scanner input = new Scanner(System.in);
		
		try
		{
			// ------ Input array size and initializing ----------------- ||
			int size = ScannerUtil.promptUserInput(input, "Choosing array size (-1 for exit): ");
			if (size == -1) {
				return;
			}
			initializeData(size);
			System.out.println("Array value: " + Arrays.toString(data));
			
			// ------ Choosing algorithm -------------------------------- ||
			System.out.println("\nSearch algorithms:");
			for (SearchAlgorithmType type : SearchAlgorithmType.values()) {
				StringBuilder sb = new StringBuilder();
				sb.append("  (").append(type.getValue()).append(") ");
				sb.append(type.toString());
				
				System.out.println(sb.toString());
			}

			int searchAlgorithmType = ScannerUtil.promptUserInput(input,
					"Choosing algorithm: (-1 for exit): ");
			if (searchAlgorithmType == -1) {
				return;
			}
			SearchAlgorithm searchAlgorithm = getSearchAlgorithm(searchAlgorithmType);
			
			// ------ Picking up a search key ---------------------------- ||
			int searchKey = ScannerUtil
					.promptUserInput(input,"\nPlease enter an integer value to search (-1 for exit): ");

			while (searchKey != -1)  {
				int position = searchAlgorithm.search(data, searchKey);
				if (position == -1) {
					System.out.println("Not found");
				}
				else {
					System.out.println("Found at position " + (position + 1));
				}
				
				System.out.println("\nPlease enter an integer value (-1 for exit): ");
				searchKey = input.nextInt();
			} 
		}
		finally {
			input.close();
			System.out.println("------------------ Terminated -----------------------");
		}
	}

	// Filling array with random number from 10 to 99
	private static void initializeData(int size) {
		data = new int[size];
		for (int i = 0; i < size; i++) {
			data[i] = 10 + generator.nextInt(90);
		}
	}
	
	private static SearchAlgorithm getSearchAlgorithm(int userInput) {
		SearchAlgorithm result;
		SearchAlgorithmType type;
		try {
			type = SearchAlgorithmType.convert(userInput);
		}
		catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.out.println("Switch to default algorithm (Linear search).");
			type = SearchAlgorithmType.LINEAR_SEARCH;
		}
		
		switch (type) {
		case BINARY_SEARCH:
			result = new BinarySearchAlgorithm();
			break;

		case LINEAR_SEARCH:
		default:
			result = new LinearSearchAlgorithm();
		}
		System.out.println("--> Chosen " + result.getClass().getSimpleName());
		return result;
	}

}
