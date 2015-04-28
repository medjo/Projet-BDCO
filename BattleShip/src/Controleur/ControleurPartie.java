package Controleur;
import modele.*;

import java.sql.SQLException;
import java.util.ArrayList;

import oracle.sql.TypeDescriptor;
import modele.BattleShip;
import modele.ExceptionNoAdv;
import modele.InfoPartie;
import modele.TirMissed;
import modele.TirOutOfBound;
import modele.structInfoPlacementBateau;
import modele.Utilisateur;
import modele.idJoueur;
import modele.TypeDeplacement;

public class ControleurPartie {
	
	/**
	 * CREATION D'UNE NOUVELLE PARTIE
	 * 		->lancerNouvellePartie()
	 * 		->validerNouvellePartie()
	 * 		->placerBateau()
	 * 		...
	 * 		->validerPlacement()
	 * 		->annulerPlacements() ?
	 */
	
	
	
	/**
	 * Méthode de création d'une nouvelle partie
	 */
	public static void lancerNouvellePartie() throws ExceptionNoAdv{
		BattleShip.partie = new Partie(); 			//Lie la partie au BattleShip 
		BattleShip.partie.creerNouvellePartie();	//Cree la partie dans la BD
		idJoueur adv = BattleShip.partie.selectionnerAdv(BattleShip.partie.getListeJoueurs());	//Recherche l'adversaire
		BattleShip.partie.ajouterParticipants(adv.getPseudo());		//Affecte les participants
		//BattleShip.partie.incrementerNbPartiesJouees(adv.getPseudo());
	}
	
	/**
	 * Méthode de validation de la création dans la base de la nouvelle partie et des participants
	 */
	public static void validerNouvellePartie(){
		BattleShip.partie.validerPartie();
	}
	
	/**
	 * Méthode qui place un bateau à l'étape initial sans le committer
	 */
	
	public static int placerBateau(int x, int y, String dir, int taille) throws SQLException{
			
		if(taille==3){
			//System.out.println("L'id bateau est:" +BattleShip.partie.getDernierNumeroBateau() );
			BattleShip.partie.executerPlacementBateauInitial(new Destroyer(x, y, dir, BattleShip.partie.getDernierNumeroBateau()+1));
			return BattleShip.partie.getDernierNumeroBateau();
		}
		else if(taille==2){
			//System.out.println("L'id bateau est:" +BattleShip.partie.getDernierNumeroBateau() );
			BattleShip.partie.executerPlacementBateauInitial(new Escorteur(x, y, dir, BattleShip.partie.getDernierNumeroBateau()+1));
			return BattleShip.partie.getDernierNumeroBateau();
		}
		else{
			System.out.println("Probleme, bateau de taille inconnu.");
			return -1;
			//return BattleShip.partie.getDernierNumeroBateau();
		}
	}
	
	/**
	 * Méthode qui valide le placement des bateaux
	 */
	
	public static void validerPlacement(){
		try {
			BattleShip.theConnection.getConnection().commit();
		} catch (SQLException e) {
			System.out.println("Probleme lors du commit de placement des bateaux");
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui permet d'annuler le placement des bateaux
	 */
	
	public static void annulerPlacements(){
		try {
			BattleShip.theConnection.getConnection().rollback();
		} catch (SQLException e) {
			System.out.println("Annulation du placement des bateaux");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * REPRENDRE UNE ANCIENNE PARTIE
	 * 		-> anciennesParties()
	 * 		-> reprendrePartieEnCours()
	 * 
	 * 
	 */
	
	
	
	
	/**
	 * Afficher les anciennes parties me concernant non terminées
	 */
	public static ArrayList<InfoPartie> anciennesParties(){
		BattleShip.partie=new Partie();
		return BattleShip.partie.partiesDebutees();
	}
	
	/**
	 * Méthode pour reprendre une partie déjà commencée
	 * Elle doit setter les paramètres de la partie
	 * Elle renvoie l'état de rendu de la partie (cf structure)
	 * @param idPartie
	 * @param adv
	 * @return EtatTour
	 */
	
	public static EtatTour reprendrePartieEnCours(int idPartie, String adv ){
		BattleShip.partie.setIdPartie(idPartie);
		BattleShip.partie.setNumTour(BattleShip.partie.getNumeroDernierTour());
		//System.out.println("NumDernierTour"+BattleShip.partie.getNumeroDernierTour());
		BattleShip.partie.setAdv(adv);
		System.out.println(BattleShip.user.getPseudo());
			
		if(BattleShip.partie.meAPositionneSesBateaux() ){
			BattleShip.partie.setBateauxCourants(BattleShip.partie.getMyShips());
		}
		return ControleurPartie.rafraichirGeneral();
	}
	
	
	
	
	
	
	/**
	 * JOUER UN TOUR AUTRE QUE L'ETAT INITIAL
	 * 		-> debutTour()
	 * 		-> jouerAction(new Tir())
	 * 		-> jouerAction(new Deplacement())
	 * 		-> validerActions
	 */
	
	/**
	 * Méthode de début de tour: elle set l'arraylist de bateauxCourants du joueur, renvoi true si la partie est terminée
	 * et elle actualise le numéro de tour
	 */
	
	public static boolean debutTour(){
		BattleShip.partie.setBateauxCourants(BattleShip.partie.getMyShips());
		BattleShip.partie.actualiserNumTour();
		return(BattleShip.partie.partieTerminee());
	}
	
	/**
	 * Cette méthode permet de créer un type Tir à partir des infos de bases envoyées par l'IHM
	 * @param idBateau
	 * @param x
	 * @param y
	 * @return Tir
	 */
	
	public static Tir Tir(int idBateau, int x, int y){
		try {
			return new Tir(idBateau,BattleShip.partie.getIdPartie(),BattleShip.user.getPseudo(),BattleShip.partie.getNumTour(),BattleShip.partie.getDernierNumeroAction()+1,x,y);
		} catch (TirOutOfBound e) {
			System.out.println("Vous avez tirer en dehors de la Map !");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Cette méthode renvoie un déplacement à partir des infos envoyées par l'IHM
	 * @param idBateau
	 * @param type
	 * @return Deplacement
	 */
	
	public static Deplacement Deplacement(int idBateau, TypeDeplacement type){
		//TODO récupération erreur si en dehors de la map
			return new Deplacement(idBateau,BattleShip.partie.getIdPartie(),BattleShip.user.getPseudo(),BattleShip.partie.getNumTour(),BattleShip.partie.getDernierNumeroAction()+1,type);
	}
	
	/**
	 * Méthode qui exécute une action sans la commiter
	 * Elle MAJ le nombre de coups restants du bateau
	 * @param action
	 * @return true si l'action est valide, false si elle ne respecte pas les contraintes
	 * @throws TirMissed exception tirMissed pour prévenir l'IHM
	 */
	public static boolean jouerAction(Action action) throws TirMissed{
		try{
			action.execute();
			ArrayList<Ship> ships= BattleShip.partie.getBateauxCourants();
			for(Ship s: ships){
				if(s.getIdBateau()==action.getIdBateau()){
					s.setCoupsBateau(s.getCoupsBateau()-1);
				}
			}

		}
		catch(ExceptionDeplacement e){
			return false;
		}
		/* si il n'y a pas d'exception on enregistre l'action */
		action.save();
		return true;
	}
	
	/**
	 * Méthode qui valide les actions
	 * @return
	 */
	
	public static void validerActions(){
		try {
			BattleShip.theConnection.getConnection().commit();
		} catch (SQLException e) {
			System.out.println("Problème lors du commit des actions");
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui annule toutes les dernières actions si non validées
	 * @return
	 */
	
	public static void annulerActions(){
		try {
			BattleShip.theConnection.getConnection().rollback();
		} catch (SQLException e) {
			System.out.println("Probleme lors de l'annulation des actions");
			e.printStackTrace();
		}
	}
	/**
	 * Méthode de test qu'il reste bien un nombre suffisants d'actions.
	 * @param idBateau
	 * @return
	 */
		
	public static boolean controleurNbActions(int idBateau){
		ArrayList<Ship> myShips=BattleShip.partie.getBateauxCourants();
		for(Ship s:myShips){
			if(s.getIdBateau()==idBateau){	//Si on tombe sur le bon bateau
				System.out.println("Il reste: "+ s.getCoupsBateau()+" coups.");
				if(s.getCoupsBateau()<=0)
					return false;
				else
					return true;
			}
		}
	return false; //Au cas où il aurait sélectionné une case qui ne lui appartient pas
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Méthode de rafraichissement général 
	 * Elle renvoie une structure permettant de savoir où le sont se situe dans la partie.
	 * cf EtatTour 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	public static EtatTour rafraichirGeneral(){
		
		EtatTour tour= new EtatTour(); //Init a false dans constructeur par défaut
		
		if(BattleShip.partie.advAPositionneSesBateaux() && !BattleShip.partie.meAPositionneSesBateaux()) tour.init=true; //L'adversaire a au moins positionné ses bateaux mais pas moi
		
		if(BattleShip.partie.advAPositionneSesBateaux() && BattleShip.partie.meAPositionneSesBateaux() && BattleShip.partie.aucuneAction() && BattleShip.partie.getJoueur1().equals(BattleShip.user.getPseudo())) {//Si l'adversaire a positionné ses bateaux, et moi aussi alors je commence si je suis le joueur1
			tour.tour=true;
		}
		if(!BattleShip.partie.advAPositionneSesBateaux() && !BattleShip.partie.meAPositionneSesBateaux() && BattleShip.partie.getJoueur1().equals(BattleShip.user.getPseudo())) {//Si l'adversaire a positionné ses bateaux, et moi aussi alors je commence si je suis le joueur1
			tour.init=true;
		}
		if(BattleShip.partie.meAPositionneSesBateaux() && BattleShip.partie.advAPositionneSesBateaux() && !BattleShip.partie.aucuneAction() && BattleShip.partie.aMoiDeJouer()) {
			//Si les 2 joueurs ont placé leurs bateaux et que c'est à moi de jouer
			tour.tour=true;
		}
		return tour;
	}	
}
