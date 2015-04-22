package modele;
import java.util.Calendar;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.*;

public class Partie {
	private int numTour;
	//private ArrayList<Ship> bateaux;
	
	public ArrayList<idJoueur> joueursDispos(TheConnection theConnection) {
		ArrayList<idJoueur> listeJoueurEnAttente = new ArrayList<idJoueur>(); 
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM enAttente");
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				//on crée une liste de pseudo et nombre de parties des joueurs en attente
				listeJoueurEnAttente.add(new idJoueur(res.getString(1),res.getDouble(2)));
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		return listeJoueurEnAttente;
	}
	
	
	
	public idJoueur selectionnerAdv(ArrayList<idJoueur> listeJoueursEnAttente){
		int i=0;
		idJoueur joueurMin;
		joueurMin = listeJoueursEnAttente.get(0);
		while(i<listeJoueursEnAttente.size()){
			if(listeJoueursEnAttente.get(i).getNbParties()<joueurMin.getNbParties()){
				joueurMin=listeJoueursEnAttente.get(i);
			}
			i++;
		}
		return joueurMin;
	}
	
	public void creerNouvellePartie(TheConnection theConnection, int idPartie, String pseudo1, String pseudo2)
	{		
			//Récupération de l'heure
			Calendar cal = new java.util.GregorianCalendar(1982, 0, 1);
			Date datePartie = new Date(cal.getTime().getTime());

			
			ParamQuery req = new ParamQuery(theConnection.getConnection(),"INSERT INTO parties VALUES (?,?,'false'");
			try{
			
			req.getStatement().setInt(1,idPartie);
			req.getStatement().setDate(2,datePartie);
			
			
				req.execute();
			} catch (Exception e) {
				
			}
			
			req.close();
		
	}
}
