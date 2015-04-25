package modele;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;

import java.sql.*;

import jdbc.SimpleQuery;

public class Utilisateur {
	//Dans BD: pseudo {pk}, nom, prénom, dateDeNaissance, email, nbPartiesJouees
	private String pseudo;
	private String nom;
	private String prenom;
	private String dateDeNaissance;
	private String email;
	private String nbPartiesJouees;
	
	public Utilisateur() {
		
	}
	
	//Cette méthode a été testé avec BD
	public void inscription(String pseudo,String nom,
			String prenom, int jj,int mm, int aaaa,
			String email, int num, String rue,int cp,String ville)
					throws InscriptionInvalideException{
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM joueurs WHERE pseudo='"+pseudo+"'"); // cherche si le pseudo existe
		
		try{
			req.execute();
			ResultSet res = req.getResult();
			if (res.next()){
				throw new InscriptionInvalideException(res.getString(1) + " existe déja !");
			} else {
				System.out.println("Inscription de " + pseudo);
				
				//Complétion de la table Joueurs
				SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),
						"INSERT INTO Joueurs VALUES ('"+pseudo+"', '"+nom+"','"+prenom+"', to_date('"+aaaa+"-"+mm+"-"+jj+"', 'YYYY-MM-DD'), '"+email+"',0)");
				req1.execute();
				req1.close();
				
				//Complétion de la table Adresse
				SimpleQuery req2 = new SimpleQuery(BattleShip.theConnection.getConnection(),
						"INSERT INTO Adresses VALUES ('"+pseudo+"', "+num+",'"+rue+"',"+cp+",'"+ville+"')");
				req2.execute();
				req2.close();
				BattleShip.theConnection.getConnection().commit();
			}
		} catch(Exception e){
			System.err.println("Echec à l'inscription");
			BattleShip.theConnection.rollbackPerso();
			e.printStackTrace(System.err);
			
			//TODO
		}
		
		this.pseudo=pseudo;
		this.nom=nom;
		this.prenom=prenom;
		this.dateDeNaissance=dateDeNaissance;
		this.email=email;
		this.nbPartiesJouees=nbPartiesJouees;
		req.close();
		
	}
	
	//Cette méthode a été testé avec BD
	public static void connexion(String pseudo){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM joueurs WHERE pseudo='"+pseudo+"'"); // cherche si le pseudo existe
		
		try{
			req.execute();
			ResultSet res = req.getResult();
			if (res.next()){
				System.out.println("Connexion");
				BattleShip.theConnection.getConnection().commit();
			} else {
				System.out.println("Utilisateur inconnu");
			}
		} catch(Exception e){
			System.err.println("Echec à la connexion");
			BattleShip.theConnection.rollbackPerso();
			e.printStackTrace(System.err);
		}
		
		req.close();
	}
	
	public String getPseudo(){
		return this.pseudo;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public String getPrenom(){
		return this.prenom;
	}
	
	public String getDate(){
		return dateDeNaissance;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getNbParties(){
		return this.nbPartiesJouees;
	}
}
