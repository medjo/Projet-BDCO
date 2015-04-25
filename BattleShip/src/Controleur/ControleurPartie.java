package Controleur;
import modele.*;

import java.util.ArrayList;

import modele.BattleShip;
import modele.ExceptionNoAdv;
import modele.InfoPartie;
import modele.structInfoPlacementBateau;
import modele.Utilisateur;
import modele.idJoueur;

public class ControleurPartie {
	
	//Pour créer une nouvelle partie
	public static void lancerNouvellePartie() throws ExceptionNoAdv{
		BattleShip.partie = new Partie();
		int idPartie = BattleShip.partie.getIdDernierePartie();
		//Maintenant il faut construire la liste des joueurs pas entrain de jouer
		ArrayList<idJoueur>listeJoueurs=BattleShip.partie.getListeJoueurs();
		//On sélectionne l'adversaire
		idJoueur adv = BattleShip.partie.selectionnerAdv(listeJoueurs);
		//On crée la partie
		BattleShip.partie.creerNouvellePartie(idPartie);
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
	
	
	
	
	
	
	
	
	
	
}
