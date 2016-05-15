package container.indepth.testframework;

/**
 * Framework for performing timed tests of containers.
 * 
 * @author pirent
 *
 * @param <C> type of container
 */
public abstract class Test<C> {

	/**
	 * Name of the test
	 */
	String name;
	
	public Test(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @param container
	 * @param testParam
	 * @return the actual number of repetitions of test
	 */
	abstract int test(C container, TestParam testParam);
}
