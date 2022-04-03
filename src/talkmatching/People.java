package talkmatching;

public class People {
	
	public People(String name, String entryYear) {
		setName(name);
		setEntryYear(entryYear);
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
