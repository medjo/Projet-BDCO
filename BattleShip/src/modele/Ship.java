package modele;

public abstract class Ship {
	
	protected int idBateau;
	protected int taille;
	protected int etat;
	protected int x, y;
	protected Direction dir;
	protected int coups;
	protected String pseudo;
	
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
	
	public Ship(int x, int y, String dir, int idBateau, String pseudo) {
		this(x, y, dir, idBateau);
		this.pseudo = pseudo;
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
	
	public int getIdBateau(){
		return this.idBateau;
	}
	
	public int getTailleBateau(){
		return this.taille;
	}
	public int getEtatBateau(){
		return this.etat;
	}
	
	public int getXBateau(){
		return this.x;
	}
	
	public int getYBateau(){
		return this.y;
	}
	
	public Direction getDirBateau(){
		return this.dir;
	}
	
	//TODO a supprimer pour réutiliser la méthode dans Direction
	public String getDirBateauString() {
		if(this.dir==Direction.NORD) return "N";
		else if(this.dir==Direction.SUD) return "S";
		else if(this.dir==Direction.EST) return "E";
		else return "O";
	}
	public int getCoupsBateau(){
		return this.coups;
	}
	
	
}
