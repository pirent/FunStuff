package typeinfo;

import java.util.Random;

public abstract class Individual {

	private Random rand = new Random();
	private String name;
	private int id = rand.nextInt(20);
	
	
	public Individual() {}
	
	public Individual(String name) {
		this.name = name;
	}
	
	public int id() {
		// TODO
		return id;
	};
	
	@Override
	public String toString() {
		// TODO
		return this.getClass().getSimpleName();
	}
}
