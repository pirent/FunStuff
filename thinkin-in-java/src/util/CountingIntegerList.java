package util;

import java.util.AbstractList;
import java.util.List;

/**
 * An custom container initialized with a data set of any size.
 * <br>
 * A {@link List} that can be any size and preinitialized with {@link Integer} data.
 * To create a read-only List, only need to implement get() and size().
 * 
 * @author vhphuc
 *
 */
public class CountingIntegerList extends AbstractList<Integer>{

	private int size;
	
	public CountingIntegerList(int size) {
		this.size = size < 0 ? 0 : size;
	}
	
	/**
	 * Again, the flyweight solution is used: get() produces the value when
	 * you ask for it, so the List doesn't actually have to be populated.
	 * 
	 * @param index
	 * @return
	 */
	@Override
	public Integer get(int index) {
		return Integer.valueOf(index);
	}

	@Override
	public int size() {
		return size;
	}

}
