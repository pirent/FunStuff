package generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsAndCovariance {
	public static void main(String[] args) {
		// Wildcards allow covariance
		// It means "some specific type which the flist reference doesn't specify
		// In order to upcast to flist, that type is a "don't actually care"
		List<? extends Fruit> flist = new ArrayList<Apple>();
		
		// If you don't what type the list is holding
		// how can you safely add an object?
		// Compile error: can't add any type of object
		// Cause the compiler don't even know which type of object the flist holding
		// it could legally point to a List of Orange
//		flist.add(new Apple());
//		flist.add(new Fruit());
//		flist.add(new Orange());
		
		flist.add(null); // legal but uninteresting
		
		// We know that it returns at least Fruit
		Fruit fruit = flist.get(0);
	}
}
