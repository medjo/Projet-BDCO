package Controleur;
import modele.*;

import java.util.ArrayList;

import modele.BattleShip;
import modele.ExceptionNoAdv;
import modele.InfoPartie;
import modele.TirMissed;
import modele.structInfoPlacementBateau;
import modele.Utilisateur;
import modele.idJoueur;

public class ControleurPartie {
	
	//Pour créer une nouvelle partie
	public static void lancerNouvellePartie() throws ExceptionNoAdv{
		BattleShip.partie = new Partie();
		//On crée la partie
		BattleShip.partie.creerNouvellePartie();
		//On sélectionne l'adversaire
		idJoueur adv = BattleShip.partie.selectionnerAdv(BattleShip.partie.getListeJoueurs());
		BattleShip.partie.ajouterParticipants(adv.getPseudo());
	}
	
	//Prepare for battle
	public static void initMap(ArrayList<Ship> mesBateaux) {
		//La vérification du nombre d'escorteurs devra être fait au-dessus
		//On place les bateaux envoyés par l'IHM
		BattleShip.partie.executerPlacementBateauxInitiaux(mesBateaux);
	}

	//Pour afficher les parties que l'ont a débuté
	public static ArrayList<InfoPartie> anciennesParties(){
		BattleShip.partie=new Partie();
		return BattleShip.partie.partiesDebutees();
	}
	
	

	//Méthode qui retourne true si c'est à mon tour de jouer (sauf dans cas init
	public boolean rafraichir(){
			return BattleShip.partie.aMoiDeJouer();
	}

	//Méthode qui retourne true si c'est à mon tour de jouer dans le cas du init
	public boolean rafraichirInit(){
		return ControleurPartie.reprendreAInit();
	}
	
	//A ne tester que quand il n'y a pas déjà eu d'actions
	//Méthode qui permet de tester si je dois jouer une action
	public static boolean rafraichirAction(){
		//Si l'adv a bien positionné ses bateaux et que je suis joueur 1 
		if(BattleShip.partie.advAPositionneSesBateaux() && BattleShip.partie.getJoueur1().equals(BattleShip.user.getPseudo())){
			return true;
		}
		return false;
	}

	/**
	 * execute et sauve l'action et préviens l'ihm si l'action est valide
	 * @param action
	 * @return true si l'action est valide, false si elle ne respecte pas les contraintes
	 * @throws TirMissed exception tirMissed pour prévenir l'IHM
	 */
	public static boolean jouerAction(Action action) throws TirMissed{
		try{
			action.execute();
		}
		catch(ExceptionDeplacement e){
			return false;
		}
		/* si il n'y a pas d'exception on enregistre l'action */
		action.save();
		return true;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Méthode de début de tour: elle set l'arraylist de bateauxCourants du joueur
	public static void debutTour(){
		BattleShip.partie.setBateauxCourants(BattleShip.partie.getMyShips());
	}
	
	
	
	//Méthode qui dit si je peux encore jouer une action
	public static boolean controleurNbActions(int idBateau){
		ArrayList<Ship> myShips=BattleShip.partie.getBateauxCourants();
		for(Ship s:myShips){
			if(s.getIdBateau()==idBateau){	//Si on tombe sur le bon bateau
				if(s.getCoupsBateau()==0)
					return false;
				else
					return true;
			}
		}
		return false; //Au cas où il aurait sélectionné une case qui ne lui appartient pas
	}
			
	//Méthode pour reprendre une partie déjà commencée
	//Elle doit setter les paramètres de la partie
	//Elle renvoie vraie si on reprend à un tour quelconque
	//Elle renvoie faux si on reprend à une étape d'intialisation
	public static boolean reprendrePartieEnCours(int idPartie, String adv ){
		BattleShip.partie.setIdPartie(idPartie);
		BattleShip.partie.setNumTour(BattleShip.partie.getNumeroDernierTour());
		//System.out.println("NumDernierTour"+BattleShip.partie.getNumeroDernierTour());
		BattleShip.partie.setAdv(adv);
		System.out.println(BattleShip.user.getPseudo());
		if(!BattleShip.partie.meAPositionneSesBateaux()){
			BattleShip.partie.setBateauxCourants(BattleShip.partie.getMyShips());
			return false;
		}
		return true;
	}
	
	//Méthode qui permet de tester si l'on doit reprendre une partie au démarrage ie au 
	//positionnement des bateaux (différent de reprendre partie car pas de numéro d'action
	//pour tester si c'est notre tour ou pas)
	public static boolean reprendreAInit(){
		//On lance un test sur la création des bateaux de l'adversaire
		return BattleShip.partie.advAPositionneSesBateaux();
		
		
	}
	
	
	
	
	
	
	
	
}
