package array;

import util.CountingGenerator;
import util.Generator;
import util.RandomGenerator;

public class GeneratorsTest {

	public static int size = 10;
	
	public static void test(Class<?> surroundingClass) {
		/*
		 * Assume that the class under test contains a set of nested Generator object,
		 * each has a default constructor,
		 * 
		 * The reflection method getClasses() produces all the nested classes
		 */
		for (Class<?> type : surroundingClass.getClasses()) {
			System.out.print(type.getSimpleName() + ": ");
			try {
				Generator<?> generator = (Generator<?>) type.newInstance();
				for (int i = 0; i < size; i++) {
					System.out.print(generator.next() + " ");
				}
				System.out.println();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("--- Testing counting generator ---");
		test(CountingGenerator.class);
		
		System.out.println("--- Testing random generator ---");
		test(RandomGenerator.class);
	}
}
