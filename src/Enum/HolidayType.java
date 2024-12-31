package Enum;

public enum HolidayType {
	CONGE_PAYE,
	CONGE_NON_PAYE, 
	CONGE_MALADIE;
	
	@Override
	public String toString() { 
	    String name = name().toLowerCase().replace('_', ' ');
	    return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
}
