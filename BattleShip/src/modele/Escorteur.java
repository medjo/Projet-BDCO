package modele;

public class Escorteur extends Ship{
	
	public static final int TAILLE_ESCORTEUR = 2;
	
	//Constructeur appelé pour un escorteur déjà en jeu
	public Escorteur(int etat, int x, int y, String dir, int idBateau){
		super(x,y,dir,idBateau);
		this.taille=TAILLE_ESCORTEUR;
		this.etat=etat;
	}
	
	//Constucteur appelé pour un bateau crée au début 
	public Escorteur(int x, int y, String dir, int idBateau){
		super(x,y,dir,idBateau);
		this.taille=TAILLE_ESCORTEUR;
		this.etat=TAILLE_ESCORTEUR;
	}
}
