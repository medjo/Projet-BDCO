package modele;

enum TypeDeplacement {
	AVANCER, RECULER, ROTGAUCHE, ROTDROITE;

	
	public static TypeDeplacement createDeplacement(String str){
		TypeDeplacement type = null;
		switch(str){
		case "AVANCER":
			type = TypeDeplacement.AVANCER;
		case "RECULER":
			type = TypeDeplacement.RECULER;
		case "ROTGAUCHE":
			type = TypeDeplacement.ROTGAUCHE;
		case "ROTDROITE":
			type = TypeDeplacement.ROTDROITE;
		}
		return type;
	}
}