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
	
	
	
	
}
