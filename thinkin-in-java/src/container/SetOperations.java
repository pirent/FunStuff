package container;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetOperations {
	public static void main(String[] args) {
		Set<String> set1 = new HashSet<String>();
		Collections.addAll(set1, "A B C D E F G H I J K L".split(" "));
		set1.add("M");
		System.out.println("Does set 1 contain H: " + set1.contains("H"));
		System.out.println("Does set 1 contain N: " + set1.contains("N"));
		
		Set<String> set2 = new HashSet<String>();
		Collections.addAll(set2, "H I J K L".split(" "));
		System.out.println("Set 2 in set 1: " + set1.containsAll(set2));
		
		set1.remove("H");
		System.out.println("Set 1: " + set1);
		System.out.println("Set 2 in set 1: " + set1.containsAll(set2));
		
		set1.removeAll(set2);
		System.out.println("After set 2 is removed, set 1 is: " + set1);
		
		Collections.addAll(set1, "X Y Z".split(" "));
		System.out.println("X Y Z are added to set 1: " + set1);
	}
}
