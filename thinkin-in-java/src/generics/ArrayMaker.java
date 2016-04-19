package generics;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayMaker<T> {
	private Class<T> kind;

	public ArrayMaker(Class<T> kind) {
		this.kind = kind;
	}

	@SuppressWarnings("unchecked")
	public T[] create(int size) {
		return (T[]) Array.newInstance(kind, size);
	}
	
	public static void main(String[] args) {
		ArrayMaker<String> arrayMaker = new ArrayMaker<String>(String.class);
		String[] create = arrayMaker.create(5);
		System.out.println(Arrays.toString(create));
	}
}
