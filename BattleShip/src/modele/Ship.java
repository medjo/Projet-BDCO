package modele;

abstract class Ship {
	protected int idBateau;
	protected int taille;
	protected int etat;
	protected int x, y;
	protected Direction dir;
	protected int coups;
	
	public Ship(int x, int y, String dir, int idBateau) {
		this.taille=taille;
		this.etat=etat;
		this.x=x;
		this.y=y;
		this.idBateau=idBateau;
		if(dir=="N") this.dir=Direction.NORD;
		else if(dir=="S") this.dir=Direction.SUD;
		else if(dir=="E") this.dir=Direction.EST;
		else if(dir=="O") this.dir=Direction.OUEST;
		this.coups=etat; //On initialise le nombre de coups à l'état du bateau
	}
	
	
	public void tirer(){
		//TODO
	}
	
	public void deplacer(){
		//TODO
	}
	
	public int getEtat(){
		return this.etat;
	}
}
