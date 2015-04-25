package modele;
import java.sql.*;
import java.util.*;

import jdbc.*;
public class ShipsFactory {	
	
		
	public ArrayList<Ship> allShips(TheConnection theConnection, int idPartie) {
		ArrayList<Ship> allShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN BATEAU WHERE idPartie="+idPartie);
		try{
			req.execute();
			ResultSet res = req.getResult();
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
	
	public ArrayList<Ship> Ships(int idPartie, String pseudo) {
		ArrayList<Ship> myShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties WHERE idPartie="+idPartie+"AND pseudo="+pseudo);
		try{
			req.execute();
			ResultSet res = req.getResult();
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
	
	
	//Méthode qui retourne la liste des bateaux initiaux
	public ArrayList<Ship> bateauxInitiaux(int idPartie, String pseudo){
		ArrayList<Ship> shipsInit = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT idBateau, etat, taille, xI, yI, orientationI FROM bateaux WHERE idPartie="+idPartie+"AND pseudo="+pseudo);
		try{
			req.execute();
			ResultSet res = req.getResult();
			while(res.next()){
				if(res.getInt(3)==Destroyer.TAILLE_DESTROYER) {
					shipsInit.add(new Destroyer(res.getInt(2),res.getInt(4),res.getInt(5),res.getString(6),res.getInt(1)));
				}
				else if(res.getInt(3)==Escorteur.TAILLE_ESCORTEUR) {
					shipsInit.add(new Escorteur(res.getInt(2),res.getInt(4),res.getInt(5),res.getString(6),res.getInt(1)));
				}			
			}
		} catch (Exception e) {
			//TODO
			System.out.println("Problème lors de la récupération de l'état initial de l'historique");
			return null;
		}
		req.close();
		return shipsInit;
	}
	
	
}
