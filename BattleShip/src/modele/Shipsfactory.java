package modele;
import java.sql.*;
import java.util.*;

import jdbc.*;
public class Shipsfactory {
	//private TheConnection conn;
	//Une strucre définissant les infos envoyées par la couche IHM concernant le placement des bateaux
			public class structInfoPlacementBateau{
				public int idBateau;
				public int numCase;
				public String dir;
				public String type;
			}
	
		
	public ArrayList<Ship> allShips(TheConnection theConnection, String idPartie) {
		ArrayList<Ship> allShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN BATEAU WHERE idPartie="+idPartie);
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				if(res.getInt(5)==Destroyer.TAILLE_DESTROYER) {
					allShips.add(new Destroyer(res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}
				else if(res.getInt(5)==Escorteur.TAILLE_ESCORTEUR) {
					allShips.add(new Escorteur(res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}			
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		//On construit la liste des parties jouées à partir de l'historique
		
		return allShips;
	}
	
	public ArrayList<Ship> Ships(String idPartie, String pseudo) {
		ArrayList<Ship> myShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties WHERE idPartie="+idPartie+"AND pseudo="+pseudo);
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				if(res.getInt(5)==Destroyer.TAILLE_DESTROYER) {
					myShips.add(new Destroyer(res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}
				else if(res.getInt(5)==Escorteur.TAILLE_ESCORTEUR) {
					myShips.add(new Escorteur(res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}			
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		//On construit la liste des parties jouées à partir de l'historique
		
		return myShips;
	}
	
	public ArrayList<Ship> prepareForBattle(ArrayList<structInfoPlacementBateau> bateaux){
		ArrayList<Ship> bateauxInit = new ArrayList<Ship> ();
		int i=0;
		while(i<bateaux.size()){
			structInfoPlacementBateau bateaui=bateaux.get(i);
			if(bateaux.get(i).type=="Escorteur"){
				bateauxInit.add(new Escorteur((int)(bateaui.numCase/10),bateaui.numCase%10,bateaui.dir,bateaui.idBateau));
			}
			else if(bateaux.get(i).type=="Escorteur"){
				bateauxInit.add(new Destroyer((int)(bateaui.numCase/10),bateaui.numCase%10,bateaui.dir,bateaui.idBateau));
			}
			else {//TODO: a passer en message d'erreur
				System.out.println("Erreur: le bateau n 'est ni escorteur ni destroyer");
			}
		}
		return bateauxInit;
	}
	
	
	
}
