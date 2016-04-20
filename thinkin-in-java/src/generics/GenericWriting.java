package generics;

import java.util.ArrayList;
import java.util.List;

//-------------------------------------------------------------------------------------||
// GOOD TO KNOW: explanation for super and extend bound
// http://stackoverflow.com/questions/4343202/difference-between-super-t-and-extends-t-in-java
//-------------------------------------------------------------------------------------||
public class GenericWriting {
	
	static <T> void writeExact(List<T> t, T item) {
		t.add(item);
	}
	
	static List<Apple> apples = new ArrayList<Apple>();
	static List<Fruit> fruits = new ArrayList<Fruit>();
	
	static void f1() {
		writeExact(apples, new Apple());
		/*
		 * XXX: now java7 allow this to be called
		 * in java5, there will be "Incompatible type: found Fruit, required Apple
		 */
		writeExact(fruits, new Apple());
	}
	
	static <T> void writeWithWildcard(List<? super T> t, T item) {
		/*
		 * This list now hold the specific type that is derived from T
		 * thus it's safe to pass T or anything derived from T as an argument
		 * to List method
		 */
		t.add(item);
	}
	
	static void f2() {
		writeWithWildcard(apples, new Apple());
		writeWithWildcard(fruits, new Apple());
	}
	
	public static void main(String[] args) {
		f1();
		f2();
	}
}
