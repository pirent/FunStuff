package container;

import java.util.AbstractCollection;
import java.util.Iterator;

import typeinfo.pets.Pet;
import typeinfo.pets.Pets;

public class CollectionSequence extends AbstractCollection<Pet> {

	private Pet[] pets = Pets.createArray(8);
	
	@Override
	public Iterator<Pet> iterator() {
		return new Iterator<Pet>() {
			
			private int index = 0;
			
			@Override
			public Pet next() {
				return pets[index++];
			}
			
			@Override
			public boolean hasNext() {
				return index < pets.length;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public int size() {
		return pets.length;
	}



	public static void main(String[] args) {
		CollectionSequence cs = new CollectionSequence();
		InterfaceVsIterator.display(cs);
		InterfaceVsIterator.display(cs.iterator());
	}
}
