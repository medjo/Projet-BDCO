package modele;
import java.sql.*;
import java.util.*;

import modele.structInfoPlacementBateau;
import jdbc.*;

public class Rejouer{
	private ArrayList<InfoPartie> listeParties;
	private InfoPartie infoP;
	private ArrayList<Ship> listeBateaux;
	private int idPartieSelec=0;
	private int numTour;
	
	
	//TESTE
	public Rejouer(){
		numTour=0;
		this.listeParties=new ArrayList<InfoPartie>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties");
		try{
			req.execute();
			ResultSet res = req.getResult();
			int i=0;
			while(res.next()){
				listeParties.add(new InfoPartie(res.getInt(1), res.getDate(2), res.getInt(3)));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		req.close();
		
		
	}
	
	
	/**
	 * initialise la partie à rejouer
	 * @param idPartie
	 * @return liste des bateaux dans leurs états initiaux
	 */
	//TODO
	public ArrayList<Ship> init(int idPartie){
		for(InfoPartie i : listeParties){
			if(i.getId() == idPartie){
				this.setInfoP(i);
			}
		}
		listeBateaux = ShipsFactory.shipsInit(idPartie);
		numTour = 0;
		return listeBateaux;
	}
	
	
	/**
	 * avance d'un tour la partie à rejouer
	 * @param idPartie
	 * @return liste des bateaux avec leurs états au tour suivant
	 */
	public ArrayList<Ship> suivant(int idPartie){
		boolean tourSuiv = false;
		ArrayList <Action> listeActions = new ArrayList <Action>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM Actions WHERE idPartie="+idPartie+" AND ntour="+this.numTour);
		try {
			req.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet res = req.getResult();
		try {
			/*Creation des actions */			
			while(res.next()){
				tourSuiv = true;
				if(res.getString("type").equals("tir")){
					listeActions.add(new Tir(res.getInt("idBateau"), res.getInt("idPartie"), res.getString("pseudo"), numTour, res.getInt("nAction"), res.getInt("x"), res.getInt("y")));
				}
				if(res.getString("type").equals("dep")){
					listeActions.add(new Deplacement(res.getInt("idBateau"), res.getInt("idPartie"), res.getString("pseudo"), numTour, res.getInt("nAction"), TypeDeplacement.createDeplacement(res.getString("direction"))));
				}
			}
			/*execution des actions*/
			System.out.println("**** liste des actions au tour num " + numTour + " ****");
			for(Action a : listeActions){
				a.executeReplay(listeBateaux);
				System.out.println(a.toString());
			}
		} catch (SQLException e) {
			System.err.println("SQL excption dans suivant");
			e.printStackTrace();
		} catch (TirOutOfBound e) {
			System.err.println("Erreur tir hors de la map");
			System.err.println("Anormal car partie déjà jouée !!!!");
			e.printStackTrace();
		} 
		/* si le tour a bien été joué on passe au suivant */
		if(tourSuiv){
			numTour++;
		}
		return listeBateaux;
	}
	
	public ArrayList<InfoPartie> getInfoParties(){
		return this.listeParties;
	}
	
	public int getIdPartieSelec(){
		return this.idPartieSelec;
	}
	
	public void setIdPartieSelec(int i){
		this.idPartieSelec=i;
	}
	
	public ArrayList<InfoPartie> getListeParties(){
		return listeParties;
	}

	/**
	 * @return the infoP
	 */
	public InfoPartie getInfoP() {
		return infoP;
	}

	/**
	 * @param infoP the infoP to set
	 */
	public void setInfoP(InfoPartie infoP) {
		this.infoP = infoP;
	}
}
