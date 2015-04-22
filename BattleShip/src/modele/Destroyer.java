package modele;

public class Destroyer extends Ship{
	
	public static final int TAILLE_DESTROYER = 3;
	
	public Destroyer(int taille, int etat, int x, int y, String dir, int idBateau){
		super(x,y,dir,idBateau);
		this.taille=TAILLE_DESTROYER;
		this.etat=TAILLE_DESTROYER;
	}
}
