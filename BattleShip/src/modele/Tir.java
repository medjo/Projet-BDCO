package modele;

public class Tir extends Action{
	private int x;
	private int y;
	
	public Tir(String idBateau, String idPartie) {
		this.x=x;
		this.y=y;
	}
	
	public void setCoord(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getCoordX(){
		return this.x;
	}
	
	public int getCoordY(){
		return this.y;
	}
}
