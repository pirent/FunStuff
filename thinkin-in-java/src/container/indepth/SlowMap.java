package container.indepth;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import util.Generator;
import util.RandomGenerator;

public class SlowMap<K, V> extends AbstractMap<K, V> {

	private List<K> keys = new ArrayList<K>();
	private List<V> values = new ArrayList<V>();
	
	@Override
	public V get(Object key) {
		if (keys.contains(key)) {
			int index = keys.indexOf(key);
			return values.get(index);
		}
		else {
			return null;
		}
	}

	@Override
	public V put(K key, V value) {
		if (keys.contains(key)) {
			int indexOf = keys.indexOf(key);
			values.set(indexOf, value);
		}
		else {
			keys.add(key);
			values.add(value);
		}
		return value;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<Entry<K,V>> result = new HashSet<Entry<K, V>>();
		int noKeys = keys.size();
		for (int i = 0; i < noKeys; i++) {
			result.add(new MapEntry<K, V>(keys.get(i), values.get(i)));
		}
		return result;
	}
	
	public static void main(String[] args) {
		SlowMap<Integer,String> slowMap = new SlowMap<Integer, String>();
		Generator<Integer> keyGen = new RandomGenerator.Integer(50);
		Generator<String> valueGen = new RandomGenerator.String(3);
		for (int i = 0; i < 10; i++) {
			slowMap.put(keyGen.next(), valueGen.next());
		}
		System.out.println();
		
		Integer keyToSeach = null;
		String stringToSearch = null;
		int counter = 0;
		while (stringToSearch == null) {
			keyToSeach = keyGen.next();
			stringToSearch = slowMap.get(keyToSeach);
			counter++;
		}
		
		System.out.println("Slow map: " + slowMap);
		System.out.println("Found: " + stringToSearch + " with key " + keyToSeach + " after trying " + counter + " times");
	}

}

class MapEntry<K, V> implements Entry<K, V> {

	private K key;
	private V value;
	
	public MapEntry(K k, V v) {
		this.key = k;
		this.value = v;
	}
	
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		this.value = value;
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapEntry other = (MapEntry) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		}
		else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
