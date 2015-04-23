package modele;

import java.sql.ResultSet;

import jdbc.SimpleQuery;
import jdbc.TheConnection;

public class Deplacement extends Action{
private Direction dir;
private Ship ship;
	
	public Deplacement(int nTour, int nAction,Direction dir, Ship ship) {
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
			
				SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"UPDATE bateaux SET direction="+this.dir+"WHERE idBateau="+this.ship.idBateau);
				try{	
					req.execute();
				} catch(Exception e){
			System.err.println("Echec déplacement invalide");
			e.printStackTrace(System.err);
		}
		req.close();
		}
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
}
