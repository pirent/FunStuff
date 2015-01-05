package searchnsort.sort;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import util.ScannerUtil;

public class SortTheApp {
	
	private static final Random generator = new Random();
	private static int[] data;
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("------------------ Hello world! -----------------------");
		Scanner input = new Scanner(System.in);
		SortAlgorithm sortAlgorithm = null;
		
		try
		{
			// ------ Input array size and initializing ----------------- ||
			int size = ScannerUtil.promptUserInput(input, "Choosing array size (-1 for exit): ");
			if (size == -1) {
				return;
			}
			initializeData(size);
			System.out.println("Unsorted array:\n " + Arrays.toString(data));
			
			// ------ Choosing algorithm -------------------------------- ||
			System.out.println("\nSort algorithms available:");
			for (SortAlgorithmType type : SortAlgorithmType.values()) {
				StringBuilder sb = new StringBuilder();
				sb.append("  (").append(type.getValue()).append(") ");
				sb.append(type.toString());

				System.out.println(sb.toString());
			}

			while (true) {
				int sortAlgorithmType = ScannerUtil.promptUserInput(input,
						"Choosing algorithm: (-1 for exit): ");
				if (sortAlgorithmType == -1) {
					return;
				}
				try {
					sortAlgorithm = getSortAlgorithm(sortAlgorithmType);
					break;
				} catch (IllegalArgumentException e) {
					continue;
				}
			}

			// ------ Start sorting ------------------------------------- ||
			System.out.println("Start sorting...\n");
			TimeUnit.SECONDS.sleep(1);
			sortAlgorithm.sort();
			System.out.println("\nSorted array:\n" + Arrays.toString(data));
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
	
	private static SortAlgorithm getSortAlgorithm(int userInput) {
		SortAlgorithm result = null;
		SortAlgorithmType type;
		try {
			type = SortAlgorithmType.convert(userInput);
		}
		catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		
		switch (type) {
		case SELECTION_SORT:
			result = new SelectionSortAlgorithm(data);
			break;
		case INSERTION_SORT:
			result = new InsertionSortAlgorithm(data);
			break;
		case MERGE_SORT:
			result = new MergeSortAlgorithms(data);
		}
		System.out.println("--> Chosen " + result.getClass().getSimpleName());
		return result;
	}
}
