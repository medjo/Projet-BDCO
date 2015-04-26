package modele;

public enum TypeDeplacement {
	AVANCER("av"), RECULER("ar"), ROTGAUCHE("rg"), ROTDROITE("rd");

	private String name;
	
	private TypeDeplacement(String name) {
		this.name = name;
	}
	
	public static TypeDeplacement createDeplacement(String str){
		TypeDeplacement type = null;
		
		if(str.equals("av"))
			type = TypeDeplacement.AVANCER;
		else if (str.equals("ar"))
			type = TypeDeplacement.RECULER;
		else if (str.equals("rg"))
			type = TypeDeplacement.ROTGAUCHE;
		else if (str.equals("rd"))
			type = TypeDeplacement.ROTDROITE;
		
		return type;
	}
	
	@Override
	public String toString(){
		return name;
	}
}