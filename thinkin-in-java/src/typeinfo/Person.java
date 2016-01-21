package typeinfo;

import util.Null;

/**
 * A class with a Null Object
 * 
 * @author pirent
 *
 */
public class Person {
	public final String first;
	public final String last;
	public final String address;

	public Person(String first, String last, String address) {
		super();
		this.first = first;
		this.last = last;
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Person: " + first + " " + last + " " + address;
	}
	
	public static class NullPerson extends Person implements Null {
		private NullPerson() {
			super("None", "None", "None");
		}

		@Override
		public String toString() {
			return "NullPerson";
		}
		
	}
	
	public static final Person NULL = new NullPerson();
}
