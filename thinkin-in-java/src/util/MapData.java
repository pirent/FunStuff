package util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A {@link Map} filled with data using a {@link Generator} object.
 * <br><br>
 * This is also an <b>Adapter</b> pattern which adapt Generator to collection.
 * 
 * @author vhphuc
 *
 */
public class MapData<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A single Pair generator
	 * 
	 * @param gen
	 * @param quantity
	 */
	public MapData(Generator<Pair<K, V>> gen, int quantity) {
		for (int i = 0; i < quantity; i++) {
			Pair<K,V> pair = gen.next();
			put(pair.key, pair.value);
		}
	}
	
	/**
	 * With two separate Generators
	 * 
	 * @param keyGen
	 * @param valueGen
	 * @param quantity
	 */
	public MapData(Generator<K> keyGen, Generator<V> valueGen, int quantity) {
		for (int i = 0; i < quantity; i++) {
			put(keyGen.next(), valueGen.next());
		}
	}
	
	/**
	 * A key Generator and a single value
	 * 
	 * @param keyGen
	 * @param value
	 * @param quantity
	 */
	public MapData(Generator<K> keyGen, V value, int quantity) {
		for (int i = 0; i < quantity; i++) {
			put(keyGen.next(), value);
		}
	}
	
	/**
	 * An Iterable and a value Generator
	 * 
	 * @param keyGen
	 * @param valueGen
	 */
	public MapData(Iterable<K> keyGen, Generator<V> valueGen) {
		for (K k : keyGen) {
			put(k, valueGen.next());
		}
	}
	
	/**
	 * An Iterable and a single value
	 * 
	 * @param keyGen
	 * @param value
	 */
	public MapData(Iterable<K> keyGen, V value) {
		for (K k : keyGen) {
			put(k, value);
		}
	}
	
	/*
	 * ======================================================= ||
	 * Generic convenience methods
	 * ======================================================= ||
	 */
	public static <K, V> MapData<K, V> map(Generator<Pair<K, V>> gen, int quantity) {
		return new MapData<K, V>(gen, quantity);
	}
	
	public static <K, V> MapData<K, V> map(Generator<K> keyGen, Generator<V> valueGen, int quantity) {
		return new MapData<K, V>(keyGen, valueGen, quantity);
	}
	
	public static <K, V> MapData<K, V> map(Generator<K> keyGen, V value, int quantity) {
		return new MapData<K, V>(keyGen, value, quantity);
	}
	
	public static <K, V> MapData<K, V> map(Iterable<K> keyGen, Generator<V> valueGen) {
		return new MapData<K, V>(keyGen, valueGen);
	}
	
	public static <K, V> MapData<K, V> map(Iterable<K> keyGen, V value) {
		return new MapData<K, V>(keyGen, value);
	}
}
