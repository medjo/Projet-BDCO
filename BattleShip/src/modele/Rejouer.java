package modele;
import java.sql.*;
import java.util.*;

import modele.structInfoPlacementBateau;
import jdbc.*;

public class Rejouer{
	private ArrayList<InfoPartie> listeParties;
	private int numTour;
	
	public Rejouer(TheConnection theConnection){
		numTour=0;
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM parties");
		try{
			req.execute();
			ResultSet res = req.getResult();
			while(res.next()){
				listeParties.add(new InfoPartie(theConnection, res.getInt(1), res.getDate(2), res.getBoolean(3)));
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		//On construit la liste des parties jouées à partir de l'historique
		
	}
	
	
	public void voirPartie(String idPartie) {
		Historique h = new Historique(idPartie); //On instancie l'historique sélectionné
	}
	/*Lien avec l'interface graphique: 
	 * -> lorsque l'on entre dans la fenêtre de l'historique on a la liste des parties qui s'affiche.
	 * -> si on veut voir une partie on appel la méthode, si on veut rafaichir on appel la meme méthode
	 * et on se déplacera au dernier élément.
	 */
	
	/**
	 * initialise la partie à rejouer
	 * @param idPartie
	 * @return liste des bateaux dans leurs états initiaux
	 */
	//TODO
	public ArrayList<Ship> init(int idPartie){
		ArrayList<Ship> shipsInit = ShipsFactory.shipsInit(idPartie);
		numTour = 0;
		return shipsInit;
	}
	
	
	/**
	 * avance d'un tour la partie à rejouer
	 * @param idPartie
	 * @return liste des bateaux avec leurs états au tour suivant
	 */
	public ArrayList<Ship> suivant(TheConnection theConnection, int idPartie){
		ArrayList <Action> listeActions = new ArrayList <Action>();
		ArrayList <Ship> listeBateaux = new ArrayList<Ship>();
		ParamQuery req = new ParamQuery(theConnection.getConnection(),"SELECT * FROM Actions WHERE idPartie= ? AND ntour= ?");
		try {
			req.getStatement().setInt(1, idPartie);
			req.getStatement().setInt(2, numTour);
			req.execute();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet res = req.getResult();
		try {
			/*Creation des actions */
			while(res.next()){
				if(res.getString("type")=="Tir"){
					//int idBateau, int idPartie, String pseudo, int nTour, int nAction, int x, int y
					listeActions.add(new Tir(res.getInt("idBateau"), res.getInt("idPartie"), res.getString("pseudo"), numTour, res.getInt("nAction"), res.getInt("x"), res.getInt("y")));
				}
				if(res.getString("type")=="Deplacement"){
					//int idBateau, int idPartie, String pseudo, int nTour, int nAction, TypeDeplacement type
					listeActions.add(new Deplacement(res.getInt("idBateau"), res.getInt("idPartie"), res.getString("pseudo"), numTour, res.getInt("nAction"), TypeDeplacement.createDeplacement(res.getString("direction"))));
				}
			}
			/*execution des actions*/
			for(Action a : listeActions){
				a.execute();
			}
			/* si le tour a bien été joué on passe au suivant */
			if(res.first()){
				numTour++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (TirOutOfBound e) {
			System.err.println("Erreur tir hors de la map");
			System.err.println("Anormal car partie déjà jouée !!!!");
			e.printStackTrace();
		} catch (ExceptionDeplacement e) {
			System.err.println("Erreur deplacement invalide");
			System.err.println("Anormal car partie déjà jouée !!!!");
			e.printStackTrace();
		} catch (TirMissed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*recupère l'etat des bateaux */
		listeBateaux = ShipsFactory.allShips(idPartie);
		
		return listeBateaux;
	}
	
	public ArrayList<InfoPartie> getInfoParties(){
		return this.listeParties;
	}
}
