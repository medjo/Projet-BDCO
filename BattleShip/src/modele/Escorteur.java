package modele;

public class Escorteur extends Ship{
	
	public static final int TAILLE_ESCORTEUR = 2;
	
	public Escorteur(int taille, int etat, int x, int y, String dir, int idBateau){
		super(x,y,dir,idBateau);
		this.taille=TAILLE_ESCORTEUR;
		this.etat=TAILLE_ESCORTEUR;
	}
}
