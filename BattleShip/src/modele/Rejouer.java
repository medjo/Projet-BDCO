package modele;
import java.sql.*;
import java.util.*;

import jdbc.*;

public class Rejouer{
	private ArrayList<InfoPartie> listeParties;
	private int numTour;
	
	public Rejouer(TheConnection theConnection){
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM parties");
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				listeParties.add(new InfoPartie(theConnection, res.getString(1), res.getString(2), res.getBoolean(3)));
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		//On construit la liste des parties jouées à partir de l'historique
		
	}
	
	public void afficher() {
		// appeler la fonction de yahya qui affiche l'historique
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
	public ArrayList<Ship> init(TheConnection theConnection, int idPartie){
		ShipsFactory factory = new ShipsFactory();
		ArrayList <Ship> listeBateaux = factory.allShips(theConnection, idPartie); 
		numTour = 0;
		
		return listeBateaux;
	}
	
	
	/**
	 * avance d'un tour la partie à rejouer
	 * @param idPartie
	 * @return liste des bateaux avec leurs états au tour suivant
	 */
	public ArrayList<Ship> suivant(TheConnection theConnection, int idPartie){
		ArrayList <Action> listeActions = new ArrayList <Action>();
		numTour++;
		ParamQuery req = new ParamQuery(theConnection.getConnection(),"SELECT * FROM Actions WHERE idPartie= ? AND ntour= ?");
		try {
			req.getStatement().setInt(1, idPartie);
			req.getStatement().setInt(2, numTour);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				listeActions.add(new Action())
			}
		} catch (Exception e) {
			
		}
		return null;
	}
}
