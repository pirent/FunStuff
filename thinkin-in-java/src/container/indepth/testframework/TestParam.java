package container.indepth.testframework;

public class TestParam {

	/**
	 * Number of element in the container
	 */
	private final int size;

	/**
	 * Controls the number of iterations for that test
	 */
	private final int loop;

	public TestParam(int size, int loop) {
		super();
		this.size = size;
		this.loop = loop;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getLoops() {
		return loop;
	}

	/**
	 * Create an array of {@link TestParam} from a varargs sequence
	 * 
	 * @param values
	 *            a variable argument list containing alternating {@code size} and
	 *            {@code loop} values. Always in form of a pair of size and loop
	 * @return
	 */
	public static TestParam[] array(int... values) {
		int size = values.length/2;
		TestParam[] result = new TestParam[size];
		int n = 0;
		
		for (int i = 0; i < size; i++) {
			result[i] = new TestParam(values[n++], values[n++]);
		}
		return result;
	}
	
	/**
	 * Convert a {@link String} array to {@link TestParam} array.
	 * <br>
	 * This way it can be used to parse command-line arguments
	 * @return
	 */
	public static TestParam[] array(String[] values) {
		int[] vals = new int[values.length];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = Integer.valueOf(values[i]);
		}
		return array(vals);
	}
}
