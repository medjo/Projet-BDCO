package modele;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;
import modele.ShipsFactory;
import modele.ActionFactory.InfoActionJoueur;


import java.sql.*;



public class Partie {
	private int idPartie;
	private int nTour;
	private int nAction;
	private String vainqueur;
	String pseudoAdversaire;
	private ArrayList<Ship> bateauxCourants;
	
	
	
	
	
	//TESTE
	//Méthode testée avec BD sans la classe participant
	//Selectionne toutes les parties que l'on a déjà commencée
	public ArrayList<InfoPartie> partiesDebutees() {
		ArrayList<InfoPartie> partiesDebutees = new ArrayList<InfoPartie>(); 
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM parties NATURAL JOIN participants WHERE finie=0 AND (joueur1 ='"+BattleShip.user.getPseudo()+"' OR joueur2='"+BattleShip.user.getPseudo()+"')");
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
		if(listeJoueurs.isEmpty()||listeJoueurs.get(1)==null) {
			throw new ExceptionNoAdv();
		}
		
		int i=0;
		idJoueur joueurMin;
		if(listeJoueurs.get(0).getPseudo().equals(BattleShip.user.getPseudo())){
			joueurMin = listeJoueurs.get(1);
		}
		else {
			joueurMin = listeJoueurs.get(0);
		}
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
	public void creerNouvellePartie()
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
		try{this.idPartie=this.getIdDernierePartie()+1;
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
			req.close();
		
	}
	
	//TESTE
	//Méthode permettant d'intialiser les participants à la partie
	public void ajouterParticipants(String pseudoAdv){
		
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO participants VALUES (?,?,?)");
		

		try{
			req.getStatement().setInt(1,this.idPartie);
			req.getStatement().setString(2,BattleShip.user.getPseudo());
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
			if(res.next()==false) return 0;
			else return res.getInt(1);
		} catch (Exception e) {
			
		}
		req.close();
		return 0; //Cas interdit normalement
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
				req.getStatement().setString(2, BattleShip.user.getPseudo());
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
	
<<<<<<< HEAD
	/*
=======
	//TESTE
	//Meme méthode mais qui ne place qu'un seul bateau et ne commit pas
	public void executerPlacementBateauInitial(Ship batInit){
		//On enregistre dans la BD le placement des bateaux à l'état initial
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO bateaux VALUES (?,?,?,?,?,?,?,?,?,?,?)");
		try {
			req.getStatement().setInt(1, this.idPartie);
			req.getStatement().setString(2, BattleShip.user.getPseudo());
			req.getStatement().setInt(3, batInit.getIdBateau());
			req.getStatement().setInt(4, batInit.getTailleBateau());
			req.getStatement().setInt(5, batInit.getTailleBateau());
			req.getStatement().setInt(6, batInit.getXBateau());
			req.getStatement().setInt(7, batInit.getYBateau());
			req.getStatement().setString(8, batInit.getDirBateauString());
			req.getStatement().setInt(9, batInit.getXBateau());//valeur initial
			req.getStatement().setInt(10, batInit.getYBateau());//valeur initial
			req.getStatement().setString(11, batInit.getDirBateauString());//valeur initial
			System.out.println("On execute");
			req.execute();
		} catch (SQLException e1) {
			BattleShip.theConnection.rollbackPerso();
			e1.printStackTrace();
			System.out.println("Problème lors du placement initial du bateau");
			}
			req.close();
	
		}
	
>>>>>>> cf20b4496ddcf3c04b7dbff8d6689d3e7dee0ce6
	//Méthode qui excéute les actions du joueur et les enregistre dans la base de donnée
	public void joueurTour(ArrayList<Action> listeActions){
		int i=0;
		ActionFactory fabrique = new ActionFactory();
		for(Action action: listeActions){
			//Application des actions dans la BD
			action.execute();
			action.save();

		}
		try{
			BattleShip.theConnection.getConnection().commit(); //On ne commit qu'à la fin
		}
		catch (SQLException e){
			BattleShip.theConnection.rollbackPerso();
		}
	}
	*/
	
	
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
				this.vainqueur=BattleShip.user.getPseudo();
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
		ArrayList<Ship> myShips=fabrique.Ships(this.idPartie, BattleShip.user.getPseudo());
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
		//SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM Actions WHERE idPartie="+this.idPartie+" AND nTour=0");
		//On regarde le numéro du dernier tour
		SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM Actions WHERE idPartie="+this.idPartie+" AND nTour>=ALL(SELECT nTour FROM Actions WHERE idPartie="+this.idPartie+")");
		try{
		//req.execute();
		req1.execute();
		//ResultSet res = req.getResult();
		ResultSet res1 = req1.getResult();
		//res.next();
		res1.next();
		//System.out.println("Num1:"+res.getString(1)+"Num2:"+res1.getString(1));
		//if(res.getString(1))
		if(res1.getString(1).equals(this.pseudoAdversaire)){
			//System.out.println("On a un souci2");
			//Dans ce cas-là c'est à nous de jouer maintenant
			return true;
		}
		else{
			System.out.println("On a un souci");
			return false;
		}
		}
		catch (Exception e){
			System.out.println("Problème dans l'attribution des tours");
			e.printStackTrace();
			return false;
		}
	}
	
	
	//TESTE
	//Methode de récupération de l'adversaire
	public  String getAdv(){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT joueur1,joueur2 FROM participants WHERE idPartie="+this.idPartie);
		try{
		req.execute();
		ResultSet res = req.getResult();
		res.next();
		//System.err.println("ya un pb");
		if(res.getString(1).equals(BattleShip.user.getPseudo())) {System.err.println(res.getString(2)); return res.getString(2);}
		else { /*System.err.println(res.getString(1));System.err.println(res.getString(2));System.err.println(user.getPseudo());*/ return res.getString(1);}
		}
		catch (Exception e){
			System.err.println("Problème dans la récupération du pseudo de l'adversaire");
			e.printStackTrace();
			return null;
		}
	}
	
	public int getIdPartie(){
		return this.idPartie;
	}
	
	public String getPseudoAdv(){
		return this.pseudoAdversaire;
	}
<<<<<<< HEAD

	public int getNTour() {
		return nTour;
	}

	public void setNTour(int nTour) {
		this.nTour = nTour;
	}

	public int getNAction() {
		return nAction;
	}

	public void setNAction(int nAction) {
		this.nAction = nAction;
	}
=======
	
	
	//Méthode qui retourne la liste de mes bateaux
	public ArrayList<Ship> getMyShips(){
		ShipsFactory fabrique= new ShipsFactory();
		return fabrique.Ships(idPartie, BattleShip.user.getPseudo());
	}
	
	
	//Retourne la liste des bateaux courants si elle a déjà étét crée
	public ArrayList<Ship> getBateauxCourants(){
		return this.bateauxCourants;
	}
	
	public void setBateauxCourants(ArrayList<Ship> myShips){
		this.bateauxCourants=myShips;
	}
	
	public void setIdPartie(int idPartie){
		this.idPartie=idPartie;
	}
	
	public void setNumTour(int num){
		this.numTour=num;
	}
	
	public int getNumTour(){
		return this.numTour;
	}
	
	public void setAdv(String adv){
		this.pseudoAdversaire=adv;
	}
	
	public int getNumeroDernierTour(){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT MAX(nTour) FROM parties NATURAL JOIN actions WHERE idPartie="+this.idPartie);
		try{
		req.execute();
		ResultSet res = req.getResult();
		res.next();
		return res.getInt(1);
		}
		catch (Exception e){
			System.err.println("Problème dans la récupération du dernier indice de tour");
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean advAPositionneSesBateaux(){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM  bateaux WHERE idPartie="+this.idPartie+" AND pseudo='"+this.pseudoAdversaire+"'");
		try{
		req.execute();
		ResultSet res = req.getResult();
		if(res.next()) return true;
		else return false;
		}
		catch (Exception e){
			System.err.println("Problème dans le test si l'adversaire a positionné ses bateaux");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean meAPositionneSesBateaux(){
		System.out.println(BattleShip.user.getPseudo());
		System.out.println(this.idPartie);
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM  bateaux WHERE idPartie="+this.idPartie+" AND pseudo='"+BattleShip.user.getPseudo()+"'");
		try{
		req.execute();
		ResultSet res = req.getResult();
		if(res.next()) return true;
		else return false;
		}
		catch (Exception e){
			System.err.println("Problème dans le test si l'adversaire a positionné ses bateaux");
			e.printStackTrace();
			return false;
		}
	}
	
	public String getJoueur1(){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT joueur1 FROM  participants WHERE idPartie="+this.idPartie);
		try{
		req.execute();
		ResultSet res = req.getResult();
		res.next();
		return res.getString(1);
		}
		catch (Exception e){
			System.err.println("Problème lors de la récupération du joueur1");
			e.printStackTrace();
			return null;
		}
	}
	
	public int getDernierNumeroAction(){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT MAX(nAction) FROM  actions WHERE idPartie="+this.idPartie+"AND nTour="+this.numTour);
		try{
		req.execute();
		ResultSet res = req.getResult();
		if(!res.next())return -1;
		System.out.println("On devrait pas etre la");
		return res.getInt(1);
		}
		catch (Exception e){
			System.err.println("Problème lors de la récupération du dernier numero d'action de ce tour");
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getDernierNumeroBateau(){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT MAX(idBateau) FROM  bateaux WHERE idPartie="+this.idPartie+"AND pseudo='"+BattleShip.user.getPseudo()+"'");
		try{
		req.execute();
		ResultSet res = req.getResult();
		if(!res.next()) return -1;
		return res.getInt(1);
		}
		catch (Exception e){
			System.err.println("Problème lors de la récupération du dernier numero de bateau");
			e.printStackTrace();
			return 0;
		}
	}
	
>>>>>>> cf20b4496ddcf3c04b7dbff8d6689d3e7dee0ce6
}
