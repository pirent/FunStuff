package generics;

/*
 * Exploring the meaning of wildcards
 */
public class Wildcards {

	// Raw argument
	static void rawArgs(Holder holder, Object arg) {
		/*
		 * Warning: unchecked call to setValue(T) as a member of the raw type
		 * Holder
		 * Since it's a raw type, so any passed object will be upcast to Object
		 * So anytime you use raw type, you give up compile-time checking
		 */
		holder.setValue(arg);
		holder.setValue(new Apple());

		// Cannot do this, cause don't have any T
		// T t = holder.getValue();

		// OK, but type information is lost
		Object value = holder.getValue();
	}

	// Similar to rawArgs, but got error instead of warning
	// It's easy to think a raw Holder and a Holder<?> the same
	// But this will show how different they are
	static void unboundArg(Holder<?> holder, Object arg) {
		/*
		 * Error: setValue(?) in Holder<?> cannot be applied to (Object)
		 * A raw Holder hold a combination of any type
		 * BUT Holder<?> hold a collection of some specific type
		 * => cannot pass in Object
		 */
		// holder.setValue(arg);

		// Cannot do this too, cause don't have any T
		// holder.getValue();
		
		// OK, but type information is lost
		Object value = holder.getValue();
	}
	
	static <T> T exact1(Holder<T> holder) {
		T t = holder.getValue();
		return t;
	}
	
	static <T> T exact2(Holder<T> holder, T arg) {
		holder.setValue(arg);
		T value = holder.getValue();
		return value;
	}
	
	static <T> T wildSubtype(Holder<? extends T> holder, T arg) {
		/*
		 * setValue() in Holder cannot be applied to T
		 * For example: T could be Fruit, holder could be Holder<Apple>
		 * To prevent putting an Orange in Holder<Apple>, the call set()
		 * is disallowed
		 * 
		 * But you can still know that everything come out of a
		 * Holder<? extends Fruit> at least is a Fruit, so get is allowed
		 */
//		holder.setValue(arg);
		T t = holder.getValue();
		return t;
	}
	
	static <T> void wildSupertype(Holder<? super T> holder, T arg) {
		/*
		 * Since anything work with the base type, will polymorphically work
		 * with the a derive type
		 */
		holder.setValue(arg);
		
		/*
		 * Mismatch conversion: cannot convert from <? super T> to T
		 */
//		T t = holder.getValue();
		
		/*
		 * However, trying to call get() is not helpful, because any type is hold
		 * by holder can be any supertype, so the only safe one is Object
		 */
		// OK, but type information is lost
		Object value = holder.getValue();
	}
	
	public static void main(String[] args) {
		Holder raw = new Holder<Long>();
		// OR
		raw = new Holder();
		
		Holder<Long> qualified = new Holder<Long>();
		Holder<?> unbounded = new Holder<Long>();
		Holder<? extends Long> bounded = new Holder<Long>();
		Long lng = 1L;
		
		rawArgs(raw, lng);
		rawArgs(qualified, lng);
		rawArgs(unbounded, lng);
		rawArgs(bounded, lng);
		
		unboundArg(raw, lng);
		unboundArg(qualified, lng);
		unboundArg(unbounded, lng);
		unboundArg(bounded, lng);
		
		/*
		 * WARNING: Unchecked conversion from Holder to Holder<T>
		 */
		Object r1 = exact1(raw);
		Long r2 = exact1(qualified);
		// Must return Object
		Object r3 = exact1(unbounded);
		Long r4 = exact1(bounded);
		
		/*
		 * WARNING: Unchecked conversion from Holder to Holder<T>
		 */
		Long r5 = exact2(raw, lng);
		Long r6 = exact2(qualified, lng);
		// ERROR: argument (Holder<T>, T) cannot be applied to (Holder<?>, Long)
//		Long r7 = exact2(unbounded, lng);
		// ERROR: argument (Holder<T>, T) cannot be applied to (Holder<? extends Long>, Long)
//		Long r8 = exact2(bounded, lng);
		
		// WARNING: Unchecked conversion from Holder to Holder<? extends Long>
		Long r9 = wildSubtype(raw, lng);
		Long r10 = wildSubtype(qualified, lng);
		// OK, but can return object only
		Object r11 = wildSubtype(unbounded, lng);
		Long r12 = wildSubtype(bounded, lng);
		
		// WARNING: Unchecked conversion from Holder to Holder<? super Long>
		wildSupertype(raw, lng);
		wildSupertype(qualified, lng);
		// ERROR: argument is not applicable (Holder<? super T>, T) to (Holder<?>, Long)
//		wildSupertype(unbounded, lng);
		// ERROR: argument is not applicable (Holder<? super T>, T) to (Holder<? extends Long>, Long)
//		wildSupertype(bounded, lng);
	}
}
