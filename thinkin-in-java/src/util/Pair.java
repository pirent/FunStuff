package util;

public class Pair<K, V> {

	/*
	 * Key and value fields are made public and final
	 * so that Pair becomes a read-only Data Transfer Object (Messenger)
	 */
	public final K key;
	public final V value;

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
}
