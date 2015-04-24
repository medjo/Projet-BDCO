package modele;

public enum Direction {
	OUEST, EST, NORD, SUD;
	
	public static Direction createDirection(String str){
		Direction type = null;
		switch(str){
		case "NORD":
			type = Direction.NORD;
		case "SUD":
			type = Direction.SUD;
		case "OUEST":
			type = Direction.OUEST;
		case "EST":
			type = Direction.EST;
		}
		return type;
	}
}