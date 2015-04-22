package modele;
import java.sql.*;
import java.util.*;

import jdbc.*;
import jdbc.TheConnection;
public class ShipsFactory {
	//private TheConnection conn;
	
	
		
	public ArrayList<Ship> allShips(TheConnection theConnection, int idPartie) {
		ArrayList<Ship> allShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN BATEAU WHERE idPartie="+idPartie);
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				if(res.getInt(5)==Destroyer.TAILLE_DESTROYER) {
					allShips.add(new Destroyer(res.getInt(5),res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}
				else if(res.getInt(5)==Escorteur.TAILLE_ESCORTEUR) {
					allShips.add(new Escorteur(res.getInt(5),res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}			
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		//On construit la liste des parties jouées à partir de l'historique
		
		return allShips;
	}
	
	public ArrayList<Ship> Ships(TheConnection theConnection, String idPartie, String pseudo) {
		ArrayList<Ship> myShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM parties WHERE idPartie="+idPartie+"AND pseudo="+pseudo);
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				if(res.getInt(5)==Destroyer.TAILLE_DESTROYER) {
					myShips.add(new Destroyer(res.getInt(5),res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}
				else if(res.getInt(5)==Escorteur.TAILLE_ESCORTEUR) {
					myShips.add(new Escorteur(res.getInt(5),res.getInt(4),res.getInt(6),res.getInt(7),res.getString(8),res.getInt(3)));
				}			
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		//On construit la liste des parties jouées à partir de l'historique
		
		return myShips;
	}
	
	
}
