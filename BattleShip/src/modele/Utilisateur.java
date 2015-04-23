package modele;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.ResultSet;
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
	
	public void inscription(String pseudo,String nom,
			String prenom, int jj,int mm, int aaaa,
			String email, int num, String rue,int cp,String ville)
					throws InscriptionInvalideException{
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM joueurs WHERE pseudo='"+pseudo+"'"); // cherche si le pseudo existe
		req.execute();
		try{
			ResultSet res = req.getResult();
			if (res.next()){
				throw new InscriptionInvalideException(res.getString(1) + " existe déja !");
			} else {
				System.out.println("Inscription de " + pseudo);
				SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),
						"INSERT INTO users VALUES('"+pseudo+"', '"+nom+"', '"+prenom+"', "+jj+", " +
						mm+", "+aaaa+", '"+email+"', "+num+", '"+rue+"', "+cp+", '"+ville+"')"); // insertion du nouveau pseudo et commit
				req1.execute();
				
				// valider (commit)
			}
		} catch(Exception e){
			System.err.println("Echec à la récupération du résultat");
			e.printStackTrace(System.err);
		}
		req.close();
	}
	
	public static void connexion(String pseudo){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM joueurs WHERE pseudo='"+pseudo+"'"); // cherche si le pseudo existe
		req.execute();
		try{
			ResultSet res = req.getResult();
			if (res.next()){
				System.out.println("Connexion");
			} else {
				System.out.println("Utilisateur inconnu");
			}
		} catch(Exception e){
			System.err.println("Echec à la récupération du résultat");
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
