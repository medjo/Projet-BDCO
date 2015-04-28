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
		if(dir.equals("n")) this.dir=Direction.NORD;
		else if(dir.equals("s")) this.dir=Direction.SUD;
		else if(dir.equals("e")) this.dir=Direction.EST;
		else if(dir.equals("o")) this.dir=Direction.OUEST;
		this.coups=etat; //On initialise le nombre de coups à l'état du bateau
	}
	
	public Ship(int x, int y, String dir, int idBateau, String pseudo) {
		this(x, y, dir, idBateau);
		this.pseudo = pseudo;
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
		System.out.println(this.dir.toString()+Direction.NORD.toString());
		if(this.dir.toString().equals(Direction.NORD.toString())) return "n";
		else if(this.dir.toString().equals(Direction.SUD.toString())) return "s";
		else if(this.dir.toString().equals(Direction.EST.toString())) return "e";
		else return "o";
	}
	public int getCoupsBateau(){
		return this.coups;
	}
	
	public void setCoupsBateau(int c){
		this.coups=c;
	}
	
	@Override
	public boolean equals(Object ship){
		Ship ship1= (Ship)ship;
		   return (this.idBateau==ship1.getIdBateau());
	}
	
	public String getPseudo(){
		return pseudo;
	}
	
	public void decrEtat(){
		this.etat--;
	}
	
	public void setDirection(Direction dir){
		this.dir = dir;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
}
