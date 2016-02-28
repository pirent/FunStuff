package container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MultiIterableClass extends IterableClass {

	public Iterable<String> reverse() {
		return new Iterable<String>() {
			
			@Override
			public Iterator<String> iterator() {
				return new Iterator<String>() {

					int index = words.length - 1;
					
					@Override
					public boolean hasNext() {
						return index > -1;
					}

					@Override
					public String next() {
						return words[index--];
					}
					
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	public Iterable<String> randomized() {
		return new Iterable<String>() {
			
			@Override
			public Iterator<String> iterator() {
				List<String> shuffledList = new ArrayList<String>(Arrays.asList(words));
				
				Collections.shuffle(shuffledList, new Random(47));
				
				return shuffledList.iterator();
			}
		};
	}
	
	public static void main(String[] args) {
		MultiIterableClass mi = new MultiIterableClass();
		for (String s : mi.reverse()) {
			System.out.print(s + " ");
		}
		System.out.println();
		
		for (String s : mi.randomized()) {
			System.out.print(s + " ");
		}
		System.out.println();
		
		for (String s : mi) {
			System.out.print(s + " ");
		}
	}
}
