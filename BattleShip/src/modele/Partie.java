package modele;
import java.util.Calendar;
import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;
import java.sql.*;

public class Partie {
	private int numTour;
	Utilisateur user;
	//private ArrayList<Ship> bateaux;
	
	//A REFAIRE
	public ArrayList<InfoPartie> partiesDebutees(TheConnection theConnection) {
		ArrayList<InfoPartie> partiesDebutees = new ArrayList<InfoPartie>(); 
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN participants NATURAL JOIN participants WHERE finie='false' AND pseudo ="+user.getPseudo());
		//Le résultat devrait donner une table de colonnes: idPartie/debut/finie/pseudo/pseudo
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				//on crée une liste de pseudo et nombre de parties des joueurs en attente
				partiesDebutees.add(new InfoPartie(res.getInt(1),res.getString(4),res.getString(5),res.getDate(2)));
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		return partiesDebutees;
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
