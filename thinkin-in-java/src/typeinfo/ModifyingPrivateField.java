package typeinfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import container.Test;

class WithPrivateFinalField {
	private int i = 1;
	private final String s = "I'm totally safe";
	private String s2 = "Am I safe?";
	
	@Override
	public String toString() {
		return "i = " + i + ", " + s + ", " + s2;
	}
}
public class ModifyingPrivateField {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		WithPrivateFinalField pf = new WithPrivateFinalField();
		System.out.println(pf);
		
		Field f = pf.getClass().getDeclaredField("i");
		f.setAccessible(true);
		System.out.println("f.getInt(pf): " + f.getInt(pf));
		f.setInt(pf, 47);
		System.out.println(pf);
		
		f = pf.getClass().getDeclaredField("s");
		f.setAccessible(true);
		System.out.println("f.get(pf): " + f.get(pf));
		f.set(pf, "No, you don't");
		System.out.println(pf);
		
		f = pf.getClass().getDeclaredField("s2");
		f.setAccessible(true);
		System.out.println("f.get(pf): " + f.get(pf));
		f.set(pf, "No, you aren't");
		System.out.println(pf);
		
		Test testee = new Test();
		Method theFoo = testee.getClass().getDeclaredMethod("foo", new Class[]{});
		theFoo.setAccessible(true);
		Object invokeResult = theFoo.invoke(testee, new Object[]{});
		if (invokeResult instanceof List) {
			List<Date> a = (List<Date>) invokeResult;
			System.out.println("Yes");
			System.out.println("a is : " + a);
		}
	}
}
