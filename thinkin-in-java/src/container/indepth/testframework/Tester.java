package container.indepth.testframework;

import java.util.List;

/**
 * To use the test framework, passing the container to be tested along with a
 * list of {@link Test} objects to a {@link #run()} method. 
 * <br>
 * This is another example of the TemplateMethod design pattern. The core code
 * doesn't change is in this {@link #test()}, parts that change is the
 * implementation of {@link Test#test(Object, TestParam)}
 * 
 * @author pirent
 *
 * @param <C> type of container
 */
public class Tester<C> {

	/**
	 * Standard width for formatting can be changed via modifying this value
	 */
	public static int fieldWidth = 8;
	
	public static TestParam[] defaultPams = TestParam.array(10, 5000, 100, 5000, 1000, 5000, 10000, 5000);
	
	protected C container;
	
	/**
	 * If you need to perform special initialization, override this. This
	 * produces an initialized container of the appropriate size - you can
	 * either modify the existing container or create a new one.
	 * 
	 * @param size
	 * @return
	 */
	protected C intialize(int size) {
		return container;
	}
	
	private String headline = "";
	
	private List<Test<C>> tests;
	
	/**
	 * Produce formatting string for outputting the results
	 * @return
	 */
	private static String stringField() {
		return "%" + fieldWidth + "s";
	}
	
	/**
	 * Produce formatting string for outputting the results
	 * @return
	 */
	private static String numberField() {
		return "%" + fieldWidth + "d";
	}
	
	private static int sizeWidth = 5;
	private static String sizeField = "%" + sizeWidth + "s";
	
	private TestParam[] paramList = defaultPams;
	
	public Tester(C container, List<Test<C>> tests) {
		this.container = container;
		this.tests = tests;
		if (container != null) {
			headline = container.getClass().getSimpleName();
		}
	}
	
	public Tester(C container, List<Test<C>> tests, TestParam[] paramList) {
		this(container, tests);
		this.paramList = paramList;
	}
	
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
	public static <C> void run(C container, List<Test<C>> tests) {
		new Tester<C>(container, tests).timedTest();;
	}
	
	public static <C> void run(C container, List<Test<C>> tests, TestParam[] paramList) {
		new Tester<C>(container, tests, paramList).timedTest();
	}
	
	public void timedTest() {
		displayHeader();
		
		for (TestParam testParam : paramList) {
			System.out.format(sizeField, testParam.getSize());
			
			for (Test<C> test : tests) {
				C kontainer = intialize(testParam.getSize());
				long startTime = System.nanoTime();
				
				int reps = test.test(kontainer, testParam);
				long duration = System.nanoTime() - startTime;
				long timePerRep = duration / reps;
				System.out.format(numberField(), timePerRep);
			}
			System.out.println();
		}
	}
	
	private void displayHeader() {
		// Calculate width and pad with '-':
		int width = fieldWidth * tests.size() + sizeWidth;
		int dashLength = width - headline.length() - 1;
		StringBuilder head = new StringBuilder(width);
		for(int i = 0; i < dashLength/2; i++)
		head.append('-');
		head.append('-');
		head.append(headline);
		head.append('-');
		for(int i = 0; i < dashLength/2; i++)
		head.append('-');
		System.out.println(head);
		// Print column headers:
		System.out.format(sizeField, "size");
		for(Test<C> test : tests)
		System.out.format(stringField(), test.name);
		System.out.println();

	}
}
