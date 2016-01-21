package container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GrannySmith extends Apple {
}

class Gala extends Apple {
}

class Fuji extends Apple {
}

class Braeburn extends Apple {
}

public class GenericAndUpcasting {
	public static void main(String[] args) {
		ArrayList<Apple> apples = new ArrayList<Apple>();
		apples.add(new GrannySmith());
		apples.add(new Gala());
		apples.add(new Fuji());
		apples.add(new Braeburn());
		
		for (Apple a : apples) {
			System.out.println(a);
		}
		
		List<Integer> intNums = Arrays.asList(1, 2 , 3);
		intNums.add(4);
	}
}
