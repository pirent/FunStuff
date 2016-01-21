package typeinfo;

import java.util.List;

import util.Null;

public interface Robot {
	String name();
	String model();
	List<Operation> operations();
	
	class Test {
		public static void test(Robot r) {
			if (r instanceof Null) {
				System.out.println("[Null Robot]");
			}
			System.out.println("Robot name: " + r.name());
			System.out.println("Robot model: " + r.model());
			for (Operation op : r.operations()) {
				System.out.println(op.descripton());
				op.command();
			}
		}
	}
}
