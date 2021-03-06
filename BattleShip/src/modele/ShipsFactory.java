package modele;
import java.sql.*;
import java.util.*;

import jdbc.*;
public class ShipsFactory {	
	
		
	public static ArrayList<Ship> allShips(int idPartie) {
		ArrayList<Ship> allShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN BATEAU WHERE idPartie="+idPartie);
		try{
			req.execute();
			ResultSet res = req.getResult();
			while(res.next()){
				if(res.getInt(5)==Destroyer.TAILLE_DESTROYER) {
					allShips.add(new Destroyer(res.getInt("etat"),res.getInt("x"),res.getInt("y"),res.getString("orientation"),res.getInt("idBateau"), res.getString("pseudo")));
				}
				else if(res.getInt(5)==Escorteur.TAILLE_ESCORTEUR) {
					allShips.add(new Escorteur(res.getInt("etat"),res.getInt("x"),res.getInt("y"),res.getString("orientation"),res.getInt("idBateau"), res.getString("pseudo")));
				}			
			}
		} catch (Exception e) {
			System.out.println("Probleme dans la fabrique de bateau");
			e.printStackTrace();
		}
		
		req.close();
		//On construit la liste des parties jouées à partir de l'historique
		
		return allShips;
	}
	

	public static ArrayList<Ship> Ships(int idPartie, String pseudo) {
		ArrayList<Ship> myShips = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM bateaux WHERE iDPartie='"+idPartie+"' AND pseudo='"+pseudo+"'");
		try{
			req.execute();
			ResultSet res = req.getResult();
			while(res.next()){
				if(res.getInt(5)==Destroyer.TAILLE_DESTROYER) {
					myShips.add(new Destroyer(res.getInt("etat"),res.getInt("x"),res.getInt("y"),res.getString("orientation"),res.getInt("idBateau"), res.getString("pseudo"), res.getInt("etat")));
				}
				else if(res.getInt(5)==Escorteur.TAILLE_ESCORTEUR) {
					myShips.add(new Escorteur(res.getInt("etat"),res.getInt("x"),res.getInt("y"),res.getString("orientation"),res.getInt("idBateau"), res.getString("pseudo"),res.getInt("etat")));
				}
			}
		} catch (Exception e) {
			System.err.println("Probleme dans la fabrique de mes bateaux");
			e.printStackTrace();
		}
		
		req.close();
		
		return myShips;
	}
	
	
	//Méthode qui retourne la liste des bateaux initiaux
	public static ArrayList<Ship> shipsInit(int idPartie){
		ArrayList<Ship> shipsInit = new ArrayList<Ship>();
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT idBateau, etat, taille, xI, yI, orientationI, pseudo FROM bateaux WHERE idPartie="+idPartie);
		try{
			req.execute();
			ResultSet res = req.getResult();
			while(res.next()){
				if(res.getInt(3)==Destroyer.TAILLE_DESTROYER) {
					shipsInit.add(new Destroyer(res.getInt("xI"),res.getInt("yI"),res.getString("orientationI"),res.getInt("idBateau"), res.getString("pseudo")));
				}
				else if(res.getInt(3)==Escorteur.TAILLE_ESCORTEUR) {
					shipsInit.add(new Escorteur(res.getInt("xI"),res.getInt("yI"),res.getString("orientationI"),res.getInt("idBateau"), res.getString("pseudo")));
				}			
			}
		} catch (Exception e) {
			System.err.println("Problème lors de la récupération de l'état initial de l'historique");
			e.printStackTrace();
			return null;
		}
		req.close();
		return shipsInit;
	}
	
	
}
