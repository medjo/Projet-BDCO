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
	
	//Constructeur appelé pour un escorteur déjà en jeu
		public Escorteur(int etat, int x, int y, String dir, int idBateau, String pseudo){
			super(x,y,dir,idBateau, pseudo);
			this.taille=TAILLE_ESCORTEUR;
			this.etat=etat;
		}
		
		public Escorteur(int etat, int x, int y, String dir, int idBateau, String pseudo, int coups){
			super(x,y,dir,idBateau, pseudo);
			this.taille=TAILLE_ESCORTEUR;
			this.etat=etat;
			this.coups=etat;
		}
		
		//Constucteur appelé pour un bateau crée au début 
		public Escorteur(int x, int y, String dir, int idBateau, String pseudo){
			super(x,y,dir,idBateau, pseudo);
			this.taille=TAILLE_ESCORTEUR;
			this.etat=TAILLE_ESCORTEUR;
		}
}
