package generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Try to generalizing the {@link FilledList idea}
 */
public class Fill {
	
	/*
	 * Doesn't work with "anything that has an add()".
	 * There is no "Addable" interface so we are narrowed to using Collection.
	 * We cannot generalize using generics in this case
	 */
	public static <T> void fill(Collection<T> collection, Class<? extends T> typeToken, int size) {
		for (int i = 0; i < size; i++) {
			try {
				collection.add(typeToken.newInstance());
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}

class Contract {
	private static long counter = 0;
	private final long id = counter++;
	public String toString() {
		return getClass().getName() + " " + id;
	}
}

class TitleTransfer extends Contract {}

class FillTest {
	public static void main(String[] args) {
		List<Contract> contracts = new ArrayList<Contract>();
		Fill.fill(contracts, Contract.class, 3);
		Fill.fill(contracts, TitleTransfer.class, 2);
		for (Contract c : contracts) {
			System.out.println(c);
		}
		
		/*
		 * SimpleQueue does has an add() method but it won't work
		 * because fill() is constrained to work with Collection
		 */
		SimpleQueue<Contract> contractQueue = new SimpleQueue<Contract>();
		// Won't work cause fill() is not generic enough
//		Fill.fill(contractQueue, Contract.class, 2);
	}
}