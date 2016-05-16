package container.indepth.performancetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.WeakHashMap;

import container.indepth.testframework.Test;
import container.indepth.testframework.TestParam;
import container.indepth.testframework.Tester;

public class MapPerformance {
	static List<Test<Map<Integer, Integer>>> tests = new ArrayList<Test<Map<Integer,Integer>>>();

	static {
		tests.add(new Test<Map<Integer,Integer>>("put") {

			@Override
			protected int test(Map<Integer, Integer> container,
					TestParam testParam) {
				int loops = testParam.getLoops();
				int size = testParam.getSize();
				for (int i = 0; i < loops; i++) {
					container.clear();
					for (int j = 0; j < size; j++) {
						container.put(j, j);
					}
				}
				return loops * size;
			}
		});
		
		tests.add(new Test<Map<Integer,Integer>>("get") {

			@Override
			protected int test(Map<Integer, Integer> container,
					TestParam testParam) {
				int loops = testParam.getLoops();
				int span = testParam.getSize() * 2;
				for (int i = 0; i < loops; i++) {
					for (int j = 0; j < span; j++) {
						container.get(j);
					}
				}
				return loops * span;
			}
		});
		
		tests.add(new Test<Map<Integer,Integer>>("iterate") {

			@Override
			protected int test(Map<Integer, Integer> container,
					TestParam testParam) {
				int loops = testParam.getLoops() * 10;
				for (int i = 0; i < loops; i++) {
					Iterator<Entry<Integer, Integer>> iterator = container.entrySet().iterator();
					while (iterator.hasNext()) {
						iterator.next();
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
		
		Tester.run(new HashMap<Integer, Integer>(), tests);
		Tester.run(new TreeMap<Integer, Integer>(), tests);
		Tester.run(new LinkedHashMap<Integer, Integer>(), tests);
		Tester.run(new IdentityHashMap<Integer, Integer>(), tests);
		Tester.run(new WeakHashMap<Integer, Integer>(), tests);
		Tester.run(new Hashtable<Integer, Integer>(), tests);
	}
}
