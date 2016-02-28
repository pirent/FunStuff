package container;

import java.util.Iterator;

import typeinfo.pets.Pet;
import typeinfo.pets.Pets;

class PetSequence {
	protected Pet[] pets = Pets.createArray(8);
}

public class NonCollectionSequence extends PetSequence {
	
	public Iterator<Pet> iterator() {
		return new Iterator<Pet>() {

			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < pets.length;
			}

			@Override
			public Pet next() {
				return pets[index++];
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args) {
		NonCollectionSequence ncs = new NonCollectionSequence();
		InterfaceVsIterator.display(ncs.iterator());
	}
}
