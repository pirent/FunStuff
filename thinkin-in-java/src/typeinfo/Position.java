package typeinfo;

public class Position {
	private String title;
	private Person person;

	public Position(String title, Person person) {
		super();
		this.title = title;
		this.person = person != null ? person : Person.NULL;
	}

	public Position(String title) {
		super();
		this.title = title;
		this.person = Person.NULL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPerson(Person person) {
		this.person = person != null ? person : Person.NULL;
	}

	public Person getPerson() {
		return person;
	}

	@Override
	public String toString() {
		return "Position: " + title + ", " + person;
	}
}
