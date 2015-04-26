package modele;

enum TypeDeplacement {
	AVANCER("av"), RECULER("ar"), ROTGAUCHE("rg"), ROTDROITE("rd");

	private String name;
	
	private TypeDeplacement(String name) {
		this.name = name;
	}
	
	public static TypeDeplacement createDeplacement(String str){
		TypeDeplacement type = null;
		switch(str){
		case "av":
			type = TypeDeplacement.AVANCER;
		case "ar":
			type = TypeDeplacement.RECULER;
		case "rg":
			type = TypeDeplacement.ROTGAUCHE;
		case "rd":
			type = TypeDeplacement.ROTDROITE;
		}
		return type;
	}
	
	@Override
	public String toString(){
		return name;
	}
}