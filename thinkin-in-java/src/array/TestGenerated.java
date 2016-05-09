package array;

import java.util.Arrays;

import util.CountingGenerator;
import util.Generated;

public class TestGenerated {
	public static void main(String[] args) {
		Integer[] a = {9, 8, 7, 6};
		System.out.println("Before: " + Arrays.toString(a));
		
		a = Generated.array(a, new CountingGenerator.Integer());
		System.out.println("After using Generated.array: " + Arrays.toString(a));
		
		Integer[] b = Generated.array(Integer.class, new CountingGenerator.Integer(), 15);
		System.out.println("After using Generated.array with Class argument: " + Arrays.toString(b));
	}
}
