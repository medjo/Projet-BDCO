package Controleur;
import modele.*;

import java.sql.SQLException;
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
	
	public static Ship placerBateau(int x, int y, int type) throws SQLException{
		Ship bat;
		if (type == 2) {
			bat = new Destroyer(x, y, "n", 0, BattleShip.user.getPseudo());
		} else {
			bat = new Escorteur(x, y, "n", 0, BattleShip.user.getPseudo());
		}
		BattleShip.partie.placerBateau(bat);
		return bat;
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
	
	

	//Méthode qui retourne true si c'est à mon tour de jouer
	public boolean rafraichir(){
		return BattleShip.partie.aMoiDeJouer();
		
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
	public void debutTour(){
		BattleShip.partie.setBateauxCourants(BattleShip.partie.getMyShips());
	}
	
	
	
	//Méthode qui dit si je peux encore jouer une action
	public boolean controleurNbActions(int idBateau){
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
	public void reprendrePartie(int idPartie, int num,String adv ){
		BattleShip.partie.setIdPartie(idPartie);
		BattleShip.partie.setNumTour(num);
		BattleShip.partie.setAdv(adv);
		BattleShip.partie.setBateauxCourants(BattleShip.partie.getMyShips());
		
	}
	
	
}
