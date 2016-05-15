package exercise.container.indepth;

import java.util.TreeSet;

import util.CollectionData;
import util.RandomGenerator;

/*
 * Use RandomGenerator.String to fill a TreeSet, but use alphabetic
 * ordering. Print the TreeSet to verify the sort order.
 */
public class Exercise9 {
	public static void main(String[] args) {
		CollectionData<String> sampleData = new CollectionData<String>(new RandomGenerator.String(), 10);
		TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		treeSet.addAll(sampleData);
		System.out.println(treeSet);
	}
}
