package modele;

public class Deplacement extends Action{
private Direction dir;
private Ship ship;
	
	public Deplacement(Direction dir, Ship ship) {
		this.dir=dir;
		this.ship=ship;
	}
	
	public void setDir(Direction dir) {
		this.dir=dir;
	}
	
	public Direction getDir() {
		return this.dir;
	}
	
	public void execute() throws ExceptionDeplacement{
		if(ship.dir==Direction.NORD && this.dir==Direction.SUD) {
			throw new ExceptionDeplacement();
		}
		else if(ship.dir==Direction.SUD && this.dir==Direction.NORD) {
			throw new ExceptionDeplacement();
		}
		else if(ship.dir==Direction.OUEST && this.dir==Direction.EST) {
			throw new ExceptionDeplacement();
		}
		else if(ship.dir==Direction.EST && this.dir==Direction.OUEST) {
			throw new ExceptionDeplacement();
		}
		else {
			
		}
	}
}
