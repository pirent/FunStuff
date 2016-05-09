package util;

import java.util.Arrays;

public class PrimitiveConversionDemonstration {
	public static void main(String[] args) {
		Integer[] a = Generated.array(Integer.class, new CountingGenerator.Integer(), 15);
		int[] b = ConvertTo.primitive(a);
		System.out.println("primitive integer array: " + Arrays.toString(b));
		
		Boolean[] a1 = Generated.array(Boolean.class, new CountingGenerator.Boolean(), 7);
		boolean[] b1 = ConvertTo.primitive(a1);
		System.out.println("primitive boolean array: " + Arrays.toString(b1));
	}
}
