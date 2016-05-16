package container.indepth.performancetest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

import util.CountingGenerator;
import util.CountingIntegerList;
import util.Generated;
import container.indepth.testframework.Test;
import container.indepth.testframework.TestParam;
import container.indepth.testframework.Tester;

public class ListPerformance {
	static Random rand = new Random();
	static int reps = 1000;
	static List<Test<List<Integer>>> tests = new ArrayList<Test<List<Integer>>>();
	static List<Test<LinkedList<Integer>>> queueTests = new ArrayList<Test<LinkedList<Integer>>>();
	
	static {
		tests.add(new Test<List<Integer>>("add") {
			
			@Override
			public int test(List<Integer> container, TestParam testParam) {
				int loops = testParam.getLoops();
				int listSize = testParam.getSize();
				
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < listSize; j++) {
						container.add(j);
					}
				}
				return loops * listSize;
			}
		});
		
		tests.add(new Test<List<Integer>>("get") {

			@Override
			protected int test(List<Integer> container, TestParam testParam) {
				int loops = testParam.getLoops() * reps;
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.get(rand.nextInt(size));
				}
				return loops;
			}
		});
		
		tests.add(new Test<List<Integer>>("set") {

			@Override
			protected int test(List<Integer> container, TestParam testParam) {
				int loops = testParam.getLoops() * reps;
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.set(rand.nextInt(size), 47);
				}
				return loops;
			}
		});
		
		tests.add(new Test<List<Integer>>("iteradd") {

			@Override
			protected int test(List<Integer> container, TestParam testParam) {
				final int loops = 1000000;
				final int half = container.size() / 2;
				ListIterator<Integer> listIterator = container.listIterator(half);
				for (int i = 0; i < loops; i++) {
					listIterator.add(47);
				}
				return loops;
			}
		});
		
		tests.add(new Test<List<Integer>>("insert") {

			@Override
			protected int test(List<Integer> container, TestParam testParam) {
				/*
				 * This test minimizes the random-access cost by inserting one element
				 * at the same index for each iteration.
				 */
				int loops = testParam.getLoops();
				for (int i = 0; i < loops; i++) {
					container.add(5, 47);
				}
				return loops;
			}
		});
		
		tests.add(new Test<List<Integer>>("remove") {

			@Override
			protected int test(List<Integer> container, TestParam testParam) {
				int loops = testParam.getLoops();
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.clear();
					container.addAll(new CountingIntegerList(size));
					while (container.size() > 5) {
						// Minimize the random-access cost
						container.remove(5);
					}
				}
				return loops * size;
			}
		});
		
		/*
		 * ================================================================
		 * TESTS FOR QUEUE BEHAVIORS
		 * ================================================================
		 */
		queueTests.add(new Test<LinkedList<Integer>>("addFirst") {

			@Override
			protected int test(LinkedList<Integer> container,
					TestParam testParam) {
				int loops = testParam.getLoops();
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < size; j++) {
						container.addFirst(47);
					}
				}
				return loops * size;
			}
		});
		
		queueTests.add(new Test<LinkedList<Integer>>("addLast") {

			@Override
			protected int test(LinkedList<Integer> container,
					TestParam testParam) {
				int loops = testParam.getLoops();
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < size; j++) {
						container.addLast(47);
					}
				}
				return loops * size;
			}
		});
		
		queueTests.add(new Test<LinkedList<Integer>>("removeFirst") {

			@Override
			protected int test(LinkedList<Integer> container,
					TestParam testParam) {
				int loops = testParam.getLoops();
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.clear();
					container.addAll(new CountingIntegerList(size));
					while (container.size() > 0) {
						container.removeFirst();
					}
				}
				return loops * size;
			}
		});
		
		queueTests.add(new Test<LinkedList<Integer>>("removeLast") {

			@Override
			protected int test(LinkedList<Integer> container,
					TestParam testParam) {
				int loops = testParam.getLoops();
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.clear();
					container.addAll(new CountingIntegerList(size));
					while (container.size() > 0) {
						container.removeLast();
					}
				}
				return loops * size;
			}
		});
	}
	
	static class ListTester extends Tester<List<Integer>> {

		public ListTester(List<Integer> container,
				List<Test<List<Integer>>> tests) {
			super(container, tests);
		}

		@Override
		protected List<Integer> intialize(int size) {
			container.clear();
			container.addAll(new CountingIntegerList(size));
			return container;
		}
		
		public static void run(List<Integer> list, List<Test<List<Integer>>> tests) {
			new ListTester(list, tests).timedTest();
		}
		
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			Tester.defaultPams = TestParam.array(args);
		}
		
		/*
		 * Can only do these two tests on an array
		 */
		Tester<List<Integer>> arrayTest = new Tester<List<Integer>>(null,
				tests.subList(1, 3)) {

			@Override
			protected List<Integer> intialize(int size) {
				Integer[] array = Generated.array(Integer.class,
						new CountingGenerator.Integer(), size);
				return Arrays.asList(array);
			}

		};

		arrayTest.setHeadline("Array as List");
		arrayTest.timedTest();
	
		Tester.defaultPams = TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
		if (args.length > 0) {
			Tester.defaultPams = TestParam.array(args);
		}
		ListTester.run(new ArrayList<Integer>(), tests);
		ListTester.run(new LinkedList<Integer>(), tests);
		ListTester.run(new Vector<Integer>(), tests);
		
		Tester.fieldWidth = 12;
		Tester<LinkedList<Integer>> qTest = new Tester<LinkedList<Integer>>(new LinkedList<Integer>(), queueTests);
		qTest.setHeadline("Queue test");
		qTest.timedTest();
	}
}
