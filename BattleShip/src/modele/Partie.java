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
	String pseudoAdversaire;
	private ArrayList<Ship> bateauxCourants;
	
	
	
	
	
	//TESTE
	//Méthode testée avec BD sans la classe participant
	//Selectionne toutes les parties que l'on a déjà commencée
	public ArrayList<InfoPartie> partiesDebutees() {
		ArrayList<InfoPartie> partiesDebutees = new ArrayList<InfoPartie>(); 
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN participants WHERE finie=0 AND (joueur1 ='"+user.getPseudo()+"' OR joueur2='"+user.getPseudo()+"')");
		//SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN joueurs WHERE finie=0 AND pseudo ='"+user.getPseudo()+"'");
		
		//Le résultat devrait donner une table de colonnes: idPartie/debut/finie/pseudo/pseudo
		try{
			req.execute();
			ResultSet res = req.getResult();
			while(res.next()){
				//on crée une liste de pseudo et nombre de parties des joueurs en attente
				partiesDebutees.add(new InfoPartie(res.getInt("idPartie"),res.getString("joueur1"),res.getString("joueur2"),res.getDate("debut")));
			}
		} catch (Exception e) {
			System.out.println("Problème à l'affichage des parties débutées");
			e.printStackTrace(System.err);
			//TODO
		}
		req.close();
		return partiesDebutees;
	}
	
	//TESTE
	//Méthode testée avec la BD
	//Sélectionne l'adversaire
	public idJoueur selectionnerAdv(ArrayList<idJoueur> listeJoueurs) throws ExceptionNoAdv{
		if(listeJoueurs.isEmpty()) {
			throw new ExceptionNoAdv();
		}
		
		int i=0;
		idJoueur joueurMin;
		joueurMin = listeJoueurs.get(0);
		System.out.println("taille de la liste"+listeJoueurs.size());
		while(i<listeJoueurs.size()){
			System.out.println("joueur:"+listeJoueurs.get(i).getPseudo()+"nbparties:"+listeJoueurs.get(i).getNbParties());
			if(listeJoueurs.get(i).getNbParties()<joueurMin.getNbParties()){
				joueurMin=listeJoueurs.get(i);
			}
			i++;
		}
		this.pseudoAdversaire=joueurMin.getPseudo();
		return joueurMin;
	}
	
	
	//TESTE
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
				System.out.println("Problème à la création de la nouvelle partie");
				e.printStackTrace(System.err);
				BattleShip.theConnection.rollbackPerso(); //On annule la requête
			}
			try{//On enregistre dans la base de donnée
				req.getConnection().commit();
			}
			catch (Exception e){
				System.out.println("Problème lors du commit");
				e.printStackTrace(System.err);
				BattleShip.theConnection.rollbackPerso();
			}
			this.idPartie=idPartie;
			req.close();
		
	}
	
	//TESTE
	//Méthode permettant d'intialiser les participants à la partie
	public void ajouterParticipants(String pseudoAdv){
		
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO participants VALUES (?,?,?)");
		

		try{
			req.getStatement().setInt(1,this.idPartie);
			req.getStatement().setString(2,this.user.getPseudo());
			req.getStatement().setString(3,this.pseudoAdversaire);
			req.execute();
			} catch (Exception e) {
				System.out.println("Problème à l'enregistrement des participants à la partie");
				e.printStackTrace(System.err);
				BattleShip.theConnection.rollbackPerso(); //On annule la requête
			}
			try{//On enregistre dans la base de donnée
				BattleShip.theConnection.getConnection().commit();
			}
			catch (Exception e){
				System.out.println("Problème lors du commit");
				e.printStackTrace(System.err);
				BattleShip.theConnection.rollbackPerso();
			}
			
			req.close();
	}
	
	
	
	//Cette méthode a été testée avec la BD
	//Trouve un nouvel i pour une nouvelle partie
	public int getIdDernierePartie() {
		int indice=0;
		//on récupère l'indice le plus élevé de partie
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT MAX(idPartie) FROM parties");
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
				listeJoueurs.add(new idJoueur(res.getString("pseudo"),res.getInt("nbPartiesJouees")));
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
	
	
	//TESTE
	//Méthode qui enregistre les positions initiales des bateaux à partir des informations fournies par l'ihm
	public void executerPlacementBateauxInitiaux(ArrayList<Ship> batInit){
		//On enregistre dans la BD le placement des bateaux à l'état initial
		int i=0;
		while(i<batInit.size()){
			Ship bateaui = batInit.get(i);
			bateaui.idBateau=i;
			ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO bateaux VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			try {
				req.getStatement().setInt(1, this.idPartie);
				req.getStatement().setString(2, this.user.getPseudo());
				req.getStatement().setInt(3, bateaui.getIdBateau());
				req.getStatement().setInt(4, bateaui.getTailleBateau());
				req.getStatement().setInt(5, bateaui.getTailleBateau());
				req.getStatement().setInt(6, bateaui.getXBateau());
				req.getStatement().setInt(7, bateaui.getYBateau());
				req.getStatement().setString(8, bateaui.getDirBateauString());
				req.getStatement().setInt(9, bateaui.getXBateau());//valeur initial
				req.getStatement().setInt(10, bateaui.getYBateau());//valeur initial
				req.getStatement().setString(11, bateaui.getDirBateauString());//valeur initial
				req.execute();
			} catch (SQLException e1) {
			BattleShip.theConnection.rollbackPerso();
			e1.printStackTrace();
			System.out.println("Problème lors du placement initial des bateaux");
			}
			try{
				BattleShip.theConnection.getConnection().commit();
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("Problème lors d'un commit");
			}
			i++;
			req.close();
		}
	}
	
	
	//Méthode qui excéute les actions du joueur et les enregistre dans la base de donnée
	public void joueurTour(ArrayList<infoActionJoueur> infosActions){
		int i=0;
		ActionFactory fabrique = new ActionFactory();
		
		try{
			while(i<infosActions.size()){
				infoActionJoueur infoi = infosActions.get(i);
			
				//Application des actions dans la BD
				fabrique.actionsJoueur(infosActions,this.idPartie,this.numTour);
				
					fabrique.execute();
					//BattleShip.theConnection.getConnection().commit();
				
				//Enregistrement dans la BD de l'action
				ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO actions VALUES (?,?,?,?,?,?,?,?,?,?)");
				
				
					req.getStatement().setInt(1,idPartie);
					req.getStatement().setString(2,this.user.getPseudo());
					req.getStatement().setInt(3,infoi.idBateau);
					req.getStatement().setInt(4,this.numTour);
					req.getStatement().setInt(5,i);
					req.getStatement().setString(6,infoi.type);
					req.getStatement().setInt(7,infoi.x);
					req.getStatement().setInt(8,infoi.y);
					req.getStatement().setString(9,infoi.typeMouvement);
					req.getStatement().setString(10,infoi.dir);
					req.execute();
						
			req.close();
			i++;
		}
		}catch(SQLException e){
			BattleShip.theConnection.rollbackPerso();
			e.printStackTrace();
			System.out.println("Problème lors de l'enregistrement des actions du joueur");
		}
		try{
			BattleShip.theConnection.getConnection().commit(); //On ne commit qu'à la fin
		}
		catch (SQLException e){
			BattleShip.theConnection.rollbackPerso();
		}
	}
	
	
	
	//TESTE juste si finie=true mais pas avec état des bateaux
	//Méthode qui teste si l'adversaire n'a pas terminé la partie 
	//Si l'adversaire a terminé la partie alors on est le vainqueur et on set l'attribut
	public boolean partieTerminee(){
		boolean ok1,ok2=true;
		try{
		//On vérifie d'abord si l'adversaire n'a pas terminé la partie(tous ses bateaux sont morts)
		System.out.println(""+this.idPartie+"");
		SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT COUNT(*) AS nb FROM parties WHERE idPartie="+this.idPartie+" AND finie=1");
		
			req1.execute();
			ResultSet res1 = req1.getResult();
			res1.next();
			if(res1.getInt("nb")==1){ //L'adversaire a déjà mis fin à la partie
				this.vainqueur=this.user.getPseudo();
				ok1= true; }
			else {
				ok1= false;
			}
			req1.close();
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Problème lors du test de partie terminée");
			return false; //Retourne un truc bidon
		}
		
		//On test maintenant si nos bateaux ne sont pas tous morts
		ShipsFactory fabrique = new ShipsFactory();
		ArrayList<Ship> myShips=fabrique.Ships(this.idPartie, this.user.getPseudo());
		int i=0;
		while(i<myShips.size()){
			if(myShips.get(i).etat!=0) ok2=false;
			i++;
		}
		if(ok2==true) {
			this.vainqueur=this.getAdv();
		}
		return ok1 && ok2;
	}
	
	
	//Méthode qui permet de tester si c'est bien à mon tour de jouer
	public boolean aMoiDeJouer(){
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
	
	//TESTE
	//Methode de récupération de l'adversaire
	public String getAdv(){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT joueur1,joueur2 FROM participants WHERE idPartie="+this.idPartie);
		try{
		req.execute();
		ResultSet res = req.getResult();
		res.next();
		if(res.getString(1)==this.user.getPseudo()) return res.getString(2);
		else return res.getString(1);
		}
		catch (Exception e){
			System.out.println("Problème dans la récupération du pseudo de l'adversaire");
			return null;
		}
	}
	
}
