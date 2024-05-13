package net.texala.employee.enums;

public enum Gender {
   MALE("male"),FEMALE("female");
	
	private final String value;
	
	Gender(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
