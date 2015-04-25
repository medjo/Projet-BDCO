package modele;

public enum Direction {
	OUEST("o"), EST("e"), NORD("n"), SUD("s");
	
	
	private String name;
	Direction(String name){
		this.name = name;
	}
	
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
	
	@Override
	public String toString(){
		return name;
	}
}