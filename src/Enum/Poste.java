package Enum;
public enum Poste {
	INGENIEUR_ETUDE_ET_DEVELOPPEMENT,
	TEAM_LEADER,
	PILOTE;

@Override
public String toString() {
   
    String name = name().toLowerCase().replace('_', ' ');
    return Character.toUpperCase(name.charAt(0)) + name.substring(1);
}
}