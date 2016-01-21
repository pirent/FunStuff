package container;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

	protected List<Date> foo() {
		System.out.println("Hey, I was invoked");
		return new ArrayList<Date>();
	}
}
