package generics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demo a method that will apply any method to every object in a sequence.
 * 
 * @author vhphuc
 *
 */
public class Apply {

	public static <T, S extends Iterable<? extends T>> void apply(S seq,
			Method f, Object... args) {
		try {
			for (T t : seq) {
				f.invoke(t, args);
			}
		} catch (Exception e) {
			// Failures are programmer errors
			throw new RuntimeException(e);
		}
	}
}

class Shape {
	public void rotate() {
		System.out.println(this + " rotate");
	}

	public void resize(int newSize) {
		System.out.println(this + " resize " + newSize);
	}
}

class Square extends Shape {
}

class FilledList<T> extends ArrayList<T> {
	public FilledList(Class<? extends T> type, int size) {
		try {
			for (int i = 0; i < size; i++) {
				// In order for type to be used, it must have a default constructor
				// No way to assert such thing a compile time => become runtime issue
				add(type.newInstance());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

class ApplyTest {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		List<Shape> shapes = new ArrayList<Shape>();
		for (int i = 0; i < 10; i++) {
			shapes.add(new Shape());
		}
		
		Apply.apply(shapes, Shape.class.getMethod("rotate"), args);
		Apply.apply(shapes, Shape.class.getMethod("resize", int.class), 5);
		// ============================================= ||
		
		List<Square> squares = new ArrayList<Square>();
		for (int i = 0; i < 10; i++) {
			squares.add(new Square());
		}
		Apply.apply(squares, Shape.class.getMethod("rotate"), args);
		Apply.apply(squares, Shape.class.getMethod("resize", int.class), 5);
		// ============================================= ||
		
		Apply.apply(new FilledList<Shape>(Shape.class, 10), Shape.class.getMethod("rotate"));
		Apply.apply(new FilledList<Shape>(Square.class, 10), Shape.class.getMethod("rotate"));
		// ============================================= ||
		
		SimpleQueue<Shape> shapeQueue = new SimpleQueue<Shape>();
		for (int i = 0; i < 5; i++) {
			shapeQueue.add(new Shape());
			shapeQueue.add(new Square());
		}
		Apply.apply(shapeQueue, Shape.class.getMethod("rotate"));
	}
}
