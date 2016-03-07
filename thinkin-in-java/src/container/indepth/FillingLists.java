package container.indepth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class StringAddress {
	private String s;

	public StringAddress(String s) {
		this.s = s;
	}

	/**
	 * Just to make sure that later when objects of this type are used, 
	 * the hash code will show whether it's the same object
	 */
	@Override
	public String toString() {
		return super.hashCode() + " - " + s;
	}
}

/**
 * Demo {@link Collections#fill(java.util.List, Object)} and
 * {@link Collections#nCopies(int, Object)} <br>
 * Two ways to fill a Collection with references to a single object
 * 
 * @author pirent
 *
 */
public class FillingLists {
	public static void main(String[] args) {
		List<StringAddress> list = null;

		// Create a list with duplicated elements, then passed to a constructor
		List<StringAddress> nCopies = Collections.nCopies(4, new StringAddress(
				"Hello"));
		list = new ArrayList<StringAddress>(nCopies);
		System.out.println("nCopies: " + list);

		// Less useful by the fact that it can only replace elements
		// that are already in the list and will not add new elements
		Collections.fill(list, new StringAddress("World!"));
		System.out.println("fill: " + list);
	}
}
