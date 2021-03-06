package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ParamQuery;
import jdbc.SimpleQuery;
import jdbc.TheConnection;

public class Deplacement extends Action{
	private TypeDeplacement type;
	private int ofset=0;
	private int x,y;
	private Direction orientationI, orientationF; // orientation initiale et finale
	
	public Deplacement(int idBateau, int idPartie, String pseudo, int nTour, int nAction, TypeDeplacement type) {
		super(idBateau, idPartie,pseudo, nTour, nAction);
		this.type=type;
		System.out.println("nouveau dep : idPartie="+idPartie+" idbateau"+idBateau+"pseudo: "+pseudo);
		/* On récupère l'orientation actuelle du bateau */
	}
	
	private void calcDepl(){
		/* Calcul du ofset, nouvelle orientation */
		if(type.equals(TypeDeplacement.AVANCER)){
			ofset=1;
		}
		if(type.equals(TypeDeplacement.RECULER)){
			ofset=-1;
		}
		orientationF=orientationI;
		if(orientationI.equals(Direction.NORD)){
			y -= ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.EST;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.OUEST;
			}
		}
		else if(orientationI.equals(Direction.SUD)){
			y += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.OUEST;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.EST;
			}
		}
		else if(orientationI.equals(Direction.EST)){
			x += ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.SUD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.NORD;
			}
		}
		else if(orientationI.equals(Direction.OUEST)){
			x -= ofset;
			if(type == TypeDeplacement.ROTDROITE){
				orientationF = Direction.NORD;
			}
			if(type == TypeDeplacement.ROTGAUCHE){
				orientationF = Direction.SUD;
			}
		}
	}
	
	public void execute() throws ExceptionDeplacement{
		/* Recupération de l'etat actuel du bateau */
		SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT orientation,x,y FROM Bateaux WHERE idPartie ="+getIdPartie()+" AND idBateau = "+getIdBateau() +" AND pseudo='"+ getPseudo()+"'");
		try {
			req1.execute();
			ResultSet res = req1.getResult();
			if(!res.next()){System.out.println("Pb de pointeur nul");}
			orientationI = Direction.createDirection(res.getString("orientation"));
			//System.out.println("OrientationI"+orientationI.toString());
			x = res.getInt("x");
			y = res.getInt("y");
		} catch (SQLException e) {
			System.err.println("Erreur SQL pour récuperer position du bateau");
			e.printStackTrace();
		}
		req1.close();
		
		/* calcul du deplacement */
		calcDepl();
		
		/* MAJ de la position du bateau */
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"UPDATE bateaux SET x=?, y=?, orientation=? WHERE idPartie= ? AND idBateau= ? AND pseudo= ?");
		try {
			//System.out.println("La nouvelle direction est:"+orientationF.toString());
			req.getStatement().setInt(1, x);
			req.getStatement().setInt(2, y);
			req.getStatement().setString(3, orientationF.toString());
			req.getStatement().setInt(4, getIdPartie());
			req.getStatement().setInt(5, getIdBateau());
			req.getStatement().setString(6, getPseudo());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("pb lors de creation de requete du déplacement");
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
	public void save() {
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO Actions(iDPartie,pseudo,iDBateau,nTour,nAction,type,direction) "
				+ "														VALUES ( ?, ?, ?, ?, ?, ?, ?)");
		try{
			
			req.getStatement().setInt(1, getIdPartie());
			req.getStatement().setString(2, BattleShip.user.getPseudo());
			req.getStatement().setInt(3, getIdBateau());
			req.getStatement().setInt(4, getNTour());
			req.getStatement().setInt(5, getNAction());
			req.getStatement().setString(6, "dep");
			req.getStatement().setString(7, type.toString());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		req.execute();
		} catch (Exception e) {
			System.out.println("pb lors de la sauvgarde du déplacement");
			e.printStackTrace();
		}
		req.close();
		
	}


	@Override
	public void executeReplay(ArrayList<Ship> listeBateaux) {
		/*
		 * !!! Attention !!!
		 * les attributs x,y orientationI,F initialisés par le constructeur 
		 * sont totalement erronés ici.
		 */
		for(Ship s: listeBateaux){
			// selection du bateau courant
			if(s.getPseudo().equals(getPseudo()) && s.getIdBateau()==getIdBateau()){
				x = s.getXBateau();
				y = s.getYBateau();
				orientationI = s.getDirBateau();
				calcDepl();
				s.setX(x);
				s.setY(y);
				s.setDirection(orientationF);
				break;
			}
		}
	}
	
	@Override
	public String toString(){
		return ("Deplacement : typeDepl " + type.toString() 
				+ " orientation F " + orientationF
				+ " x " + x
				+ " y " + y
				+ super.toString());
	}
	
}
