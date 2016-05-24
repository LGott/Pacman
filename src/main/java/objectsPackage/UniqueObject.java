package objectsPackage;

public class UniqueObject {
	private int ID;
	private String description;

	public UniqueObject(int ID, String description) {
		this.ID = ID;
		this.description = description;
	}

	public int getID() {
		return ID;
	}

	public String getDescription() {
		return description;
	}
}
