package modele;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;
import modele.ShipsFactory;
import modele.ActionFactory.infoActionJoueur;


import java.sql.*;



public class Partie {
	private int idPartie;
	private int numTour;
	private String vainqueur;
	Utilisateur user;
	private ArrayList<Ship> bateauxCourants;
	
	
	//Méthode testée avec BD sans la classe participant
	//Selectionne toutes les parties que l'on a déjà commencée
	public ArrayList<InfoPartie> partiesDebutees() {
		ArrayList<InfoPartie> partiesDebutees = new ArrayList<InfoPartie>(); 
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN participants NATURAL JOIN participants WHERE finie=0 AND pseudo ="+user.getPseudo());
		//SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN joueurs WHERE finie=0 AND pseudo ='"+user.getPseudo()+"'");
		
		//Le résultat devrait donner une table de colonnes: idPartie/debut/finie/pseudo/pseudo
		try{
			req.execute();
			ResultSet res = req.getResult();
			while(res.next()){
				//on crée une liste de pseudo et nombre de parties des joueurs en attente
				partiesDebutees.add(new InfoPartie(res.getInt(1),res.getString(4),res.getString(5),res.getDate(2)));
			}
		} catch (Exception e) {
			
		}
		
		req.close();
		return partiesDebutees;
	}
	
	//Méthode testée avec la BD
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
	
	//Cette méthode a été testé avec BD
	//Creer une nouvelle partie dans la base de donnée
	//public void creerNouvellePartie(int idPartie, String pseudo1, String pseudo2)
	public void creerNouvellePartie(int idPartie)
	{		
		//Récupération de l'heure actuelle
		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date(); // your util date
		cal.setTime(utilDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);    
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); 
		
		
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO parties VALUES (?,?,0)");
		try{
			
			req.getStatement().setInt(1,idPartie);
			req.getStatement().setDate(2,sqlDate);
			req.execute();
			} catch (Exception e) {
				
			}
			try{//On enregistre dans la base de donnée
				req.getConnection().commit();
			}
			catch (Exception e){
				
			}
			this.idPartie=idPartie;
			req.close();
		
	}
	
	//Cette méthode a été testée avec la BD
	//Trouve un nouvel i pour une nouvelle partie
	public int getIdDernierePartie() {
		int indice=0;
		//on récupère l'indice le plus élevé de partie
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT MAX(idPartie) FROM parties");
		//Le résultat devrait donner une table de colonnes: idPartie/debut/finie/pseudo/pseudo
		
		try{
			req.execute();
			ResultSet res = req.getResult();
			res.next();
			indice=res.getInt(1);
		} catch (Exception e) {
			
		}
		req.close();
		return indice;
	}
	
	//Méthode testée avec la BD
	//Il faudra rajouter un attribut "en jeu" pour sélectionner les joueurs pas en jeu
	//Retourne la liste de tous les joueurs
	public ArrayList<idJoueur> getListeJoueurs(){
		ArrayList<idJoueur> listeJoueurs = new ArrayList<idJoueur>();
		//On suppose qu'on peut jouer avec n'importe quels joueurs
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM joueurs ");
		
		try{
			req.execute();
			ResultSet res = req.getResult();
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
	//A partir de la liste des infos de placement cela retourne la liste des bateaux initiaux
	/*public void placerBateaux(ArrayList<structInfoPlacementBateau> infoPlacementBateaux){
		ShipsFactory bateaux = new ShipsFactory();
		this.bateauxInitiaux= bateaux.prepareForBattle(infoPlacementBateaux);
	}
	
	public void executerPlacementBateaux(){
		//On enregistre dans la BD le placement des bateaux
		int i=0;
		while(i<bateauxInitiaux.size()){
			Ship bateaui = bateauxInitiaux.get(i);
			ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO bateaux VALUES (?,?,?,?,?,?,?,?,?,?,?");
			try {
				req.getStatement().setInt(1, this.idPartie);
				req.getStatement().setString(2, this.user.getPseudo());
				req.getStatement().setInt(3, bateaui.getIdBateau());
				req.getStatement().setInt(4, bateaui.getEtat());
				req.getStatement().setInt(5, bateaui.getTailleBateau());
				req.getStatement().setInt(6, bateaui.getXBateau());
				req.getStatement().setInt(7, bateaui.getYBateau());
				req.getStatement().setString(8, bateaui.getDirBateauString());
				req.getStatement().setInt(9, bateaui.getXBateau());//valeur initial
				req.getStatement().setInt(10, bateaui.getYBateau());//valeur initial
				req.getStatement().setString(11, bateaui.getDirBateauString());//valeur initial
			
			} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		req.execute();
		} catch (Exception e) {
			
		}
		req.close();
		}
	}*/
	
	
	
	public void executerPlacementBateauxInitiaux(ArrayList<structInfoPlacementBateau> infoPlacementBateaux){
		//On enregistre dans la BD le placement des bateaux à l'état initial
		int i=0;
		while(i<infoPlacementBateaux.size()){
			structInfoPlacementBateau infoBateaui = infoPlacementBateaux.get(i);
			ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO bateaux VALUES (?,?,?,?,?,?,?,?,?,?,?");
			try {
				req.getStatement().setInt(1, this.idPartie);
				req.getStatement().setString(2, this.user.getPseudo());
				req.getStatement().setInt(3, infoBateaui.idBateau);
				req.getStatement().setInt(4, infoBateaui.taille);
				req.getStatement().setInt(5, infoBateaui.taille);
				req.getStatement().setInt(6, infoBateaui.x);
				req.getStatement().setInt(7, infoBateaui.y);
				req.getStatement().setString(8, infoBateaui.dir);
				req.getStatement().setInt(9, infoBateaui.x);//valeur initial
				req.getStatement().setInt(10, infoBateaui.y);//valeur initial
				req.getStatement().setString(11, infoBateaui.dir);//valeur initial
				req.execute();
			} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Problème lors du placement initial des bateaux");
			}
			req.close();
		}
	}
	
	
	
	
	
	
	
	//Jouer son tour
	/*public void jouerSonTour(ArrayList<infoActionJoueur> actions) {
		ActionFactory fabrique = new ActionFactory();
		fabrique.actionsJoueur(actions,this.idPartie,this.numTour);
		fabrique.execute();
	}*/
	
	//Cette méthode a été testée avec la BD
	//Méthode qui teste si l'adversaire n'a pas terminé la partie 
	//Si l'adversaire a terminé la partie alors on est le vainqueur et on set l'attribut
	public boolean partieTerminee() throws SQLException{
		
		//On vérifie d'abord si l'adversaire n'a pas terminé la partie(tous ses bateaux sont morts)
		System.out.println(""+this.idPartie+"");
		SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT COUNT(*) AS nb FROM parties WHERE idPartie="+this.idPartie+" AND finie=1");
		
			req1.execute();
			ResultSet res1 = req1.getResult();
			res1.next();
			if(res1.getInt("nb")==1){ //L'adversaire a déjà mis fin à la partie
				System.out.println("ok");
				this.vainqueur=this.user.getPseudo();
				return true; }
			else {
				System.out.println("pb");
				req1.close();
				return false;
			}
		
	}
	
	public boolean aNousDeJouer(){
		//on regarde quel joueur est le premier à avoir jouer pour déterminer si c'est notre tour
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM Actions WHERE idPartie="+this.idPartie+" AND nTour=0");
		//On regarde le numéro du dernier tour
		SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM Actions WHERE idPartie="+this.idPartie+" AND nTour>=ALL(SELECT nTour FROM Actions WHERE idPartie="+this.idPartie+")");
		try{
		req.execute();
		req1.execute();
		ResultSet res = req.getResult();
		ResultSet res1 = req1.getResult();
		res.next();
		res1.next();
		if(res.getString(1)!=res1.getString(1)){
			//Dans ce cas-là c'est à nous de jouer maintenant
			return true;
		}
		else{
			return false;
		}
		}
		catch (Exception e){
			System.out.println("Problème dans l'attribution des tours");
			return false;
		}
	}
	
	
	
	//structure d'info en provenance de l'ihm
	class structInfoPlacementBateau{
		public int idBateau;
		public int taille;
		public int x;
		public int y;
		public String dir;
	}
}
