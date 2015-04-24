package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.ParamQuery;
import jdbc.SimpleQuery;
import jdbc.TheConnection;

public class Deplacement extends Action{
	private TypeDeplacement type;
	int ofset=0;
	int x,y;
	Direction orientationI, orientationF; // orientation initiale et finale
	
	public Deplacement(int idBateau, int idPartie, int nTour, int nAction, TypeDeplacement type) {
		super(idBateau, idPartie, nTour, nAction);
		this.type=type;
		
		/* On récupère l'orientation actuelle du bateau */
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"SELECT orientation, x, y FROM Bateaux WHERE idPartie = ?, idBateau = ? ");
		try {
			req.getStatement().setInt(1, idPartie);
			req.getStatement().setInt(2, idBateau);
			req.execute();
			ResultSet res = req.getResult();
			orientationI = Direction.createDirection(res.getString(1));
			x = res.getInt(2);
			y = res.getInt(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.close();
		
		/* Calcul du xOfset, yOfset et nouvelle orientation */
		if(type == TypeDeplacement.AVANCER){
			ofset=1;
		}
		if(type == TypeDeplacement.RECULER){
			ofset=-1;
		}
		switch(orientationI){
		case NORD :
			y += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.EST;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.OUEST;
			}
		case SUD:
			y += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.OUEST;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.EST;
			}
		case EST:
			x += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.SUD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.NORD;
			}
		case OUEST:
			x += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.NORD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.SUD;
			}
		}
		
	}
	
	
	public void execute() throws ExceptionDeplacement{
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"UPDATE bateaux SET x=?, y=?, orientation=? WHERE idPartie= ? AND idBateau= ?");
		try {
			req.getStatement().setInt(1, x);
			req.getStatement().setInt(2, y);
			req.getStatement().setString(3, orientationF.toString());
			req.getStatement().setInt(4, getIdPartie());
			req.getStatement().setInt(5, getIdBateau());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		req.execute();
		} catch (Exception e) {
			throw new ExceptionDeplacement();
		}
		req.close();
	}

	@Override
	public void save() throws ExceptionDeplacement {
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INSERT INTO Actions (iDPartie, idBateau, nTour, nAction, x, y, type, direction)"
				+ "														VALUES ( ?, ?, ?, ?, ?, ?, ?");
		try{
			req.getStatement().setInt(1, getIdPartie());
			req.getStatement().setInt(2, getIdBateau());
			req.getStatement().setInt(3, getNTour());
			req.getStatement().setInt(4, getNAction());
			req.getStatement().setInt(5, x);
			req.getStatement().setInt(6, y);
			req.getStatement().setString(7, "Deplacement");
			req.getStatement().setString(8, orientationF.toString());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		req.execute();
		} catch (Exception e) {
			throw new ExceptionDeplacement();
		}
		req.close();
		
	}
}
