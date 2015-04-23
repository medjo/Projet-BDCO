package modele;

public class Destroyer extends Ship{
	
	public static final int TAILLE_DESTROYER = 3;
	
	//Constructeur appelé pour un bateau en cours de jeu
	public Destroyer(int etat, int x, int y, String dir, int idBateau){
		super(x,y,dir,idBateau);
		this.taille=TAILLE_DESTROYER;
		this.etat=etat;
	}
	
	//Constructeur appelé pour un baeau en cours de partie
	public Destroyer(int x, int y, String dir, int idBateau){
		super(x,y,dir,idBateau);
		this.taille=TAILLE_DESTROYER;
		this.etat=TAILLE_DESTROYER;
	}
}
