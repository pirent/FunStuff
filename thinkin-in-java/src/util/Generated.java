package util;

import java.lang.reflect.Array;

public class Generated {

	/**
	 * Take an existing array and filled it with provided {@link Generator}
	 * 
	 * @param arrayToBeFilled
	 * @param generator
	 * @return
	 */
	public static <T> T[] array(T[] arrayToBeFilled, Generator<T> generator) {
		CollectionData<T> collectionData = new CollectionData<T>(generator, arrayToBeFilled.length);
		return collectionData.toArray(arrayToBeFilled);
	}
	
	/**
	 * Take an Class object and the desired number of elements to create
	 * a new array, then filled it with {@link Generator}.
	 * 
	 * @param type
	 * @param gen
	 * @param size
	 * @return
	 */
	public static <T> T[] array(Class<?> type, Generator<T> gen, int size) {
		T[] array = (T[]) Array.newInstance(type, size);
		return new CollectionData<T>(gen, size).toArray(array);
	}
}
