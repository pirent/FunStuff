package container.indepth.performancetest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import container.indepth.testframework.Test;
import container.indepth.testframework.TestParam;
import container.indepth.testframework.Tester;

public class SetPerformance {

	static List<Test<Set<Integer>>> tests = new ArrayList<Test<Set<Integer>>>();
	static {
		tests.add(new Test<Set<Integer>>("add") {

			@Override
			protected int test(Set<Integer> container, TestParam testParam) {
				int loops = testParam.getLoops();
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < size; j++) {
						container.add(j);
					}
				}
				return loops * size;
			}
			
		});
		
		tests.add(new Test<Set<Integer>>("contains") {

			@Override
			protected int test(Set<Integer> container, TestParam testParam) {
				int loops = testParam.getLoops();
				int span = testParam.getSize() * 2;
				for (int i = 0; i < loops; i++) {
					for (int j = 0; j < span; j++) {
						container.contains(j);
					}
				}
				return loops * span;
			}
		});
		
		tests.add(new Test<Set<Integer>>("iterate") {

			@Override
			protected int test(Set<Integer> container, TestParam testParam) {
				int loops = testParam.getLoops();
				for (int i = 0; i < loops; i++) {
					Iterator<Integer> it = container.iterator();
					while (it.hasNext()) {
						it.next();
					}
				}
				return loops * container.size();
			}
		});
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			Tester.defaultPams = TestParam.array(args);
		}
		Tester.fieldWidth = 10;
		Tester.run(new HashSet<Integer>(), tests);
		Tester.run(new LinkedHashSet<Integer>(), tests);
		Tester.run(new TreeSet<Integer>(), tests);
	}
}
