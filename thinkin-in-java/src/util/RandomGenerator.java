package util;

import java.util.Random;

/**
 * Generator that produces random values.
 * 
 * @author pirent
 *
 */
public class RandomGenerator {

	private static Random rand = new Random(47);
	
	public static class Boolean implements Generator<java.lang.Boolean> {

		@Override
		public java.lang.Boolean next() {
			return rand.nextBoolean();
		}
		
	}
	
	public static class Byte implements Generator<java.lang.Byte> {

		@Override
		public java.lang.Byte next() {
			return (byte) rand.nextInt();
		}
		
	}
	
	public static class Character implements Generator<java.lang.Character> {

		@Override
		public java.lang.Character next() {
			int random = rand.nextInt(CountingGenerator.chars.length);
			return CountingGenerator.chars[random];
		}
		
	}
	
	public static class String extends CountingGenerator.String {
		/*
		 * Plug in the random Character generator via instance initializer
		 */
		{
			characterGenerator = new RandomGenerator.Character(); 
		}
		
		public String() {}
		
		public String(int length) {
			super(length);
		}
	}
	
	public static class Short implements Generator<java.lang.Short> {

		@Override
		public java.lang.Short next() {
			return (short) rand.nextInt();
		}
		
	}
	
	public static class Integer implements Generator<java.lang.Integer> {

		private int mod = 10000;
		
		public Integer() {}
		
		/**
		 * To generate number that ain't too large, it used a default modulus of 10000.
		 * <br>
		 * But the overloaded constructor allow you to choose a smaller value.
		 * 
		 * @param modulo
		 */
		public Integer(int modulo) {
			this.mod = modulo;
		}
		
		@Override
		public java.lang.Integer next() {
			return rand.nextInt(mod);
		}
		
	}
	
	public static class Long implements Generator<java.lang.Long> {

		private int mod = 10000;
		
		public Long() {}
		
		/**
		 * To generate number that ain't too large, it used a default modulus of 10000.
		 * <br>
		 * But the overloaded constructor allow you to choose a smaller value.
		 * 
		 * @param modulo
		 */
		public Long(int modulo) {
			this.mod = modulo;
		}
		
		@Override
		public java.lang.Long next() {
			return new java.lang.Long(rand.nextInt(mod));
		}
		
	}
	
	public static class Float implements Generator<java.lang.Float> {

		/**
		 * {@inheritDoc}
		 * <br>
		 * Note: the value after the decimal point is trimmed.
		 */
		@Override
		public java.lang.Float next() {
			// Trim all but the first two decimal places
			int trimmed = Math.round(rand.nextFloat() * 100);
			return ((float) trimmed) / 100;
		}
		
	}
	
	public static class Double implements Generator<java.lang.Double> {

		@Override
		public java.lang.Double next() {
			long trimmed = Math.round(rand.nextDouble() * 100);
			return ((double) trimmed) / 100;
		}
		
	}
}
