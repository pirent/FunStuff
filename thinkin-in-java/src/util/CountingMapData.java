package util;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * A custom {@link Map} containing pre-initialized unique Integer and String.
 * It can also be any size.
 * 
 * @author vhphuc
 *
 */
public class CountingMapData extends AbstractMap<Integer, String> {

	private int size;
	private static String[] chars = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
	
	public CountingMapData(int size) {
		if (size < 0) {
			this.size = 0;
		}
		this.size = size;
	}
	
	@Override
	public Set<java.util.Map.Entry<Integer, String>> entrySet() {
		// LinkedHashSet retains initialization order
		Set<Map.Entry<Integer,String>> entries = new LinkedHashSet<Map.Entry<Integer, String>>();
		for (int i = 0; i < size; i++) {
			entries.add(new Entry(i));
		}
		return entries;
	}

	private static class Entry implements Map.Entry<Integer, String> {

		int index;
		
		Entry (int index) {
			this.index = index;
		}
		
		@Override
		public Integer getKey() {
			return index;
		}

		@Override
		public String getValue() {
			return chars[index % chars.length] + 
					// this will show the number of iteration
					Integer.toString(index / chars.length);
		}

		@Override
		public String setValue(String value) {
			throw new UnsupportedOperationException();
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(new CountingMapData(60));
	}
}
