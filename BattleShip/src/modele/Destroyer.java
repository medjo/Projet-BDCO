package modele;

public class Destroyer extends Ship{
	
	public static final int TAILLE_DESTROYER = 3;
	
	public Destroyer(int taille, int etat, int x, int y, String dir){
		super(x,y,dir);
		this.taille=TAILLE_DESTROYER;
		this.etat=TAILLE_DESTROYER;
	}
}
