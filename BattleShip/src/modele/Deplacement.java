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
	
	public Deplacement(int idBateau, int idPartie, String pseudo, int nTour, int nAction, TypeDeplacement type) {
		super(idBateau, idPartie,pseudo, nTour, nAction);
		this.type=type;
		System.out.println("nouveau dep");
		/* On récupère l'orientation actuelle du bateau */
		//ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"SELECT orientation, x, y FROM Bateaux WHERE idPartie = ? AND idBateau = ? ");
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT orientation, x, y FROM Bateaux WHERE idPartie ="+idPartie+" AND idBateau ="+idBateau);
		try {
			//req.getStatement().setInt(1, idPartie);
			//req.getStatement().setInt(2, idBateau);
			req.execute();
			ResultSet res = req.getResult();
			res.next();
			System.out.println("av pb");
			System.out.println("Resultat"+res.getString(1));
			orientationI = Direction.createDirection(res.getString(1));
			//System.out.println("OrientationI"+orientationI.toString());
			x = res.getInt(2);
			y = res.getInt(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.close();
		
		/* Calcul du xOfset, yOfset et nouvelle orientation */
		if(type.equals(TypeDeplacement.AVANCER)){
			System.out.println("youhou");
			ofset=1;
		}
		if(type.equals(TypeDeplacement.RECULER)){
			System.out.println("youhou");
			ofset=-1;
		}
		System.out.println("OrientationI"+orientationI.toString());
		orientationF=orientationI;
		if(orientationI.equals(Direction.NORD)){
			y += ofset;
			System.out.println("on y est1");
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.EST;
				System.out.println("jzhebdjze"+orientationF.toString());
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.OUEST;
			}
		}
		else if(orientationI.equals(Direction.SUD)){
			//System.out.println("on y est3");
			//System.out.println("x et y:"+y);
			y += ofset;
			System.out.println("x et y:"+y);
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.OUEST;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.EST;
			}
		}
		else if(orientationI.equals(Direction.EST)){
			//System.out.println("on y est4");
			x += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.SUD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.NORD;
			}
		}
		else if(orientationI.equals(Direction.OUEST)){
			//System.out.println("on y est5");
			x += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.NORD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.SUD;
			}
		}
		
		
		
		/*switch(orientationI){
		
		case NORD :
			y += ofset;
			System.out.println("on y est1");
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.EST;
				System.out.println("jzhebdjze"+orientationF.toString());
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.OUEST;
			}
		case SUD:
			System.out.println("on y est3");
			y += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.OUEST;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.EST;
			}
		case EST:
			System.out.println("on y est4");
			x += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.SUD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.NORD;
			}
		case OUEST:
			System.out.println("on y est5");
			x += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.NORD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.SUD;
			}
			*/
		
		
	}
	
	
	public void execute() throws ExceptionDeplacement{
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"UPDATE bateaux SET x=?, y=?, orientation=? WHERE idPartie= ? AND idBateau= ?");
		try {
			//System.out.println("La nouvelle direction est:"+orientationF.toString());
			req.getStatement().setInt(1, x);
			req.getStatement().setInt(2, y);
			req.getStatement().setString(3, orientationF.toString());
			req.getStatement().setInt(4, getIdPartie());
			req.getStatement().setInt(5, getIdBateau());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("pb lors de l'execute du déplacement");
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
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO Actions(iDPartie,pseudo,iDBateau,nTour,nAction,type,direction) "
				+ "														VALUES ( ?, ?, ?, ?, ?, ?, ?)");
		try{
			System.out.println("idpartie:"+getIdPartie());
			req.getStatement().setInt(1, getIdPartie());
			System.out.println("pseudo"+getIdPartie());
			req.getStatement().setString(2, BattleShip.user.getPseudo());
			System.out.println("idBateau:"+getIdBateau());
			req.getStatement().setInt(3, getIdBateau());
			System.out.println("NTour:"+getNTour());
			req.getStatement().setInt(4, getNTour());
			System.out.println("NAction:"+getNAction());
			req.getStatement().setInt(5, getNAction());
			System.out.println("X:"+x);
			System.out.println("Y:"+y);
			req.getStatement().setString(6, "dep");
			System.out.println("Mouv:"+type.toString());
			req.getStatement().setString(7, type.toString());
			
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
