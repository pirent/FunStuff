package util;

import java.util.Scanner;

public class ScannerUtil {

	public static int promptUserInput(Scanner scanner, String message) {
		int result = -1;

		while (true) {
			System.out.println(message);
			String line = scanner.nextLine();
			try {
				result = Integer.parseInt(line);
				return result;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input: " + line);
			}
		}
	}
}
