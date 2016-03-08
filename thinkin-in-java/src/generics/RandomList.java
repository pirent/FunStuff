package generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A special type of list that randomly select one of its elements each time you
 * call select
 * 
 * @author pirent
 *
 */
public class RandomList<T> {
	private List<T> storage = new ArrayList<T>();
	private Random rand = new Random(47);

	public void add(T item) {
		storage.add(item);
	}

	public T select() {
		return storage.get(rand.nextInt(storage.size()));
	}
	
	public static void main(String[] args) {
		RandomList<String> randomList = new RandomList<String>();
		String sentence = "The quick brown fox jumped over the lazy dog";
		for (String  s : sentence.split(" ")) {
			randomList.add(s);
		}
		for (int i = 0; i < randomList.storage.size(); i++) {
			System.out.print(randomList.select() + ", ");
		}
	}
}
