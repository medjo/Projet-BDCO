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
	 * crée, execute et sauve le tir. Et préviens l'ihm si l'action est valide
	 * @param idBateau id du bateau qui effectue l'action
	 * @param xTir coord de tir
	 * @param yTir coor de tir
	 * @return true si l'action est valide, false si elle ne respecte pas les contraintes
	 * @throws TirMissed exception tirMissed pour prévenir l'IHM
	 */
	public static boolean jouerTir(int idBateau, int xTir, int yTir) throws TirMissed{
		try{
			Tir tir = new Tir(idBateau, BattleShip.partie.getIdPartie(), BattleShip.user.getPseudo(), BattleShip.partie.getNTour(), BattleShip.partie.getNAction(), xTir, yTir);
			tir.execute();
			tir.save();
		}
		catch(TirOutOfBound e){
			return false;
		}
		/* si il n'y a pas d'exception on enregistre l'action */
		return true;
	}
	
	
	/**
	 * crée, execute et sauve le deplacement. Et préviens l'ihm si l'action est valide
	 * @param idBateau id du bateau qui effectue l'action
	 * @param typeDep type de deplacement (av, ar rd, rg)
	 * @return true si l'action est valide, false si elle ne respecte pas les contraintes
	 */
	public static boolean jouerDeplacement(int idBateau, TypeDeplacement typeDep){
		Deplacement dep = new Deplacement(idBateau, BattleShip.partie.getIdPartie(), BattleShip.user.getPseudo(), BattleShip.partie.getNTour(), BattleShip.partie.getNAction(), typeDep);
		try{
			dep.execute();
			dep.save();
		}
		catch(ExceptionDeplacement e){
			return false;
		}
		/* si il n'y a pas d'exception on enregistre l'action */
		return true;
	}
	
	
}
