package typeinfo.pets;

import java.util.ArrayList;

/**
 * Facade to produce a default {@link PetCreator}
 * 
 * @author pirent
 *
 */
public class Pets {
	public static final PetCreator creator = new LiteralClassCreator();
	
	public static Pet randomPet() {
		return creator.randomPet();
	}
	
	public static Pet[] createArray(int size) {
		return creator.createArray(size);
	}
	
	public static ArrayList<Pet> arrayList(int size) {
		return creator.arrayList(size);
	}
}
