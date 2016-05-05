package util;

/**
 * The generator classes are nested within the {@link CountingGenerator},
 * so that they may use the same name as object types they are generating
 * <br><br>
 * For example, a generator that creates Integer object would be created
 * with the expression new CountingGenerator.Integer()
 * 
 * @author vhphuc
 *
 */
public class CountingGenerator {
	public static class Boolean implements Generator<java.lang.Boolean> {

		private boolean value = false;
		
		@Override
		public java.lang.Boolean next() {
			// Just flips back and forth
			value = !value;
			return value;
		}
		
	}

	public static class Byte implements Generator<java.lang.Byte> {

		private byte value = 0;
		
		@Override
		public java.lang.Byte next() {
			return value++;
		}
		
	}
	
	private static char[] chars = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
			.toCharArray();
	
	public static class Character implements Generator<java.lang.Character> {
		
		private int index = -1;
		
		@Override
		public java.lang.Character next() {
			index = (index + 1) % chars.length;
			return chars[index];
		}
		
	}
	
	public static class String implements Generator<java.lang.String> {

		private int length = 7;
		private Generator<java.lang.Character> characterGenerator = new Character();
		
		public String() {}
		
		public String(int length) {
			this.length = length;
		}
		
		@Override
		public java.lang.String next() {
			char[] buff = new char[length];
			for (int i = 0; i < length; i++) {
				buff[i] = characterGenerator.next();
			}
			return new java.lang.String(buff);
		}
		
	}
}
