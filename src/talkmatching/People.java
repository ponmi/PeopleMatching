package talkmatching;

public class People implements Cloneable{

	public People(String id, String name, String entryYear) {
		setName(name);
		setEntryYear(entryYear);
		setId(id);
	}

	@Override
	public People clone() throws CloneNotSupportedException{
		People newPeople = (People)super.clone();
		newPeople.setName(newPeople.getName());
		newPeople.setEntryYear(newPeople.getEntryYear());
		newPeople.setId(newPeople.getId());
		return newPeople;
	}

	private String id = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name = null;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String entryYear = null;

	public String getEntryYear() {
		return entryYear;
	}
	public void setEntryYear(String entryYear) {
		this.entryYear = entryYear;
	}
}
