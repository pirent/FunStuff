package typeinfo.toys;

public class GenericToyTest {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Class<FancyToy> ftClass = FancyToy.class;
		
		// Produces exact type
		FancyToy fancyToy = ftClass.newInstance();
		Class<? super FancyToy> up = ftClass.getSuperclass();
		
		// THis won't compile
		// Class<Toy> up2 = ftClass.getSuperclass();
		
		// Only produce Object
		Object obj = up.newInstance();
	}

}
