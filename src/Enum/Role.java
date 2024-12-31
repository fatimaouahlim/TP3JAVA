package Enum;

public enum Role {

		ADMIN,
		EMPLOYEE;
	@Override
	public String toString() {
	   
	    String name = name().toLowerCase().replace('_', ' ');
	    return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
}
