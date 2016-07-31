package com.github.pirent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;

/**
 * A utility class that manages collections of listeners.
 * 
 * @author pirent
 *
 * @param <T>
 */
public class Announcer<T extends EventListener> {

	private Class<T> listenerType;
	private Collection<T> listeners = new ArrayList<T>();
	
	private Announcer(Class<T> listenerType) {
		this.listenerType = listenerType;
	}
	
	public static <T extends EventListener> Announcer<T> to(Class<T> listenerType) {
		return new Announcer<T>(listenerType);
	}

	public void addListener(T listener) {
		listeners.add(listener);
	}

	public T announce() {
		// TODO: I don't know what to do here
		// Cause it look like add Listener is called only once
		// So I just return the first element
		return listeners.iterator().next();
	}
}
