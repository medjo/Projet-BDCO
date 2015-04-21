package modele;

public class Deplacement extends Action{
private Direction dir;
	
	public Deplacement(Direction dir) {
		this.dir=dir;
	}
	
	public void setDir(Direction dir) {
		this.dir=dir;
	}
	
	public Direction getDir() {
		return this.dir;
	}
}
