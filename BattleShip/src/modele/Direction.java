package modele;

public enum Direction {
	OUEST("o"), EST("e"), NORD("n"), SUD("s");
	
	
	private String name;
	Direction(String name){
		this.name = name;
	}
	
	public static Direction createDirection(String str){
		Direction type = null;
		if(str.equals("n")) type = Direction.NORD;
		else if(str.equals("s"))type = Direction.SUD;
		else if(str.equals("e"))type = Direction.EST;
		else if(str.equals("o"))type = Direction.OUEST;
		return type;
	}
	
	@Override
	public final String toString(){
		return name;
	}
	
}