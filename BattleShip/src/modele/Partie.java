package modele;
import java.util.Calendar;
import java.sql.ResultSet;
import java.util.ArrayList;

import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;

import java.sql.*;

import modele.Ship.structInfoPlacementBateau;

public class Partie {
	private int idPartie;
	private int numTour;
	Utilisateur user;
	//private ArrayList<Ship> bateaux;
	
	//Selectionne toues les parties que lon a déjà commencé
	public ArrayList<InfoPartie> partiesDebutees() {
		ArrayList<InfoPartie> partiesDebutees = new ArrayList<InfoPartie>(); 
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN participants NATURAL JOIN participants WHERE finie='false' AND pseudo ="+user.getPseudo());
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
	
	
	//Sélectionne l'adversaire
	public idJoueur selectionnerAdv(ArrayList<idJoueur> listeJoueurs){
		int i=0;
		idJoueur joueurMin;
		joueurMin = listeJoueurs.get(0);
		while(i<listeJoueurs.size()){
			if(listeJoueurs.get(i).getNbParties()<joueurMin.getNbParties()){
				joueurMin=listeJoueurs.get(i);
			}
			i++;
		}
		return joueurMin;
	}
	
	//Creer une nouvelle partie dans la base de donnée
	public void creerNouvellePartie(int idPartie, String pseudo1, String pseudo2)
	{		
			//Récupération de l'heure
			Calendar cal = new java.util.GregorianCalendar(1982, 0, 1);
			Date datePartie = new Date(cal.getTime().getTime());

			
			ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO parties VALUES (?,?,'false'");
			try{
			
			req.getStatement().setInt(1,idPartie);
			req.getStatement().setDate(2,datePartie);
			req.execute();
			} catch (Exception e) {
				
			}
			
			req.close();
		
	}
	
	//Trouve un nouvel i pour une nouvelle partie
	public int getIdDernierePartie() {
		int indice=0;
		//on récupère l'indice le plus élevé de partie
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT MAX(idPartie) FROM parties");
		//Le résultat devrait donner une table de colonnes: idPartie/debut/finie/pseudo/pseudo
		req.execute();
		ResultSet res = req.getResult();
		try{
			res.next();
			indice=res.getInt(1);
		} catch (Exception e) {
			
		}
		req.close();
		return indice;
	}
	
	//Retourne la liste de tous les joueurs
	public ArrayList<idJoueur> getListeJoueurs(){
		ArrayList<idJoueur> listeJoueurs = new ArrayList<idJoueur>();
		//On suppose qu'on peut jouer avec n'importe quels joueurs
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM joueurs ");
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				//On récupère pseudo et nb parties jouées
				listeJoueurs.add(new idJoueur(res.getString(1),res.getInt(6)));
			}
		} catch (Exception e) {
			
		}
		req.close();
		return listeJoueurs;
	}
	
	
	
	//A PLACER AILLEURS SUREMENT
	//A partir de la list des ifo de placement cela retourne la liste des bateaux initiaux
	public ArrayList<Ship> placerBateaux(ArrayList<structInfoPlacementBateau> infoPlacementBateaux){
		Shipsfactory bateaux = new Shipsfactory();
		return bateaux.prepareForBattle(infoPlacementBateaux);
	}	

}
