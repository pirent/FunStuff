package typeinfo.pets;

import java.util.LinkedHashMap;
import java.util.Map;

public class PetCount3 {

	static class PetCounter extends LinkedHashMap<Class<? extends Pet>, Integer> {
		
		// TODO: waiting for the implementation of MapData
		public PetCounter() {
			for (Class<? extends Pet> type : LiteralClassCreator.allTypes) {
				put(type, 0);
			}
		}
		
		public void count(Pet pet) {
			for (java.util.Map.Entry<Class<? extends Pet>, Integer> pair : entrySet()) {
				if (pair.getKey().isInstance(pet)) {
					put(pair.getKey(), pair.getValue() + 1);
				}
			}
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder("{");
			for (Map.Entry<Class<? extends Pet>, Integer> pair : entrySet()) {
				result.append(pair.getKey().getSimpleName());
				result.append(" = ");
				result.append(pair.getValue());
				result.append(", ");
			}
			
			result.delete(result.length() - 2, result.length());
			result.append("}");
			
			return result.toString();
		}
	}
	
	public static void main(String[] args) {
		PetCounter counter = new PetCounter();
		for (Pet pet : Pets.createArray(20)) {
			System.out.print(pet.getClass().getSimpleName() + " ");
			counter.count(pet);
		}
		System.out.println();
		System.out.println(counter);
	}
}
