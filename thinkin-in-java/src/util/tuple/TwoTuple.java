package util.tuple;

/**
 * A group of objects wrapped together into a single object. The recipient of
 * the object is allowed to read the elements but not put new one in.
 * 
 * @author pirent
 *
 */
public class TwoTuple<A, B> {
	
	/*
	 * Shouldn't be A and B be private and access via getter.
	 * No need to do that, clients could still read and do whatever they want
	 * with them, but they couldn't re-assign
	 */
	public final A first;
	public final B second;

	public TwoTuple(A first, B second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
}
