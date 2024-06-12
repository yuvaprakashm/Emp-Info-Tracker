package net.texala.employee.enums;

public enum GenericStatus {
	ACTIVE("active"), DEACTIVE("deactive"), DELETED("deleted");

	private String name;
	
	private GenericStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
