package Controleur;
import modele.*;

import java.util.ArrayList;

import modele.BattleShip;
import modele.InfoPartie;
import modele.Partie;
import modele.Shipsfactory.structInfoPlacementBateau;
import modele.Utilisateur;
import modele.idJoueur;

public class ControleurPartie {
	private Utilisateur user;
	Partie partie;
	
	public void lancerNouvellePartie(){
		this.partie = new Partie();
		int idPartie = partie.getIdDernierePartie();
		//Maintenant il faut construire la liste des joueurs pas entrain de jouer
		ArrayList<idJoueur>listeJoueurs=partie.getListeJoueurs();
		//On sélectionne l'adversaire
		idJoueur adv = partie.selectionnerAdv(listeJoueurs);
		//On crée la partie
		partie.creerNouvellePartie(idPartie,user.getPseudo(),adv.getPseudo());
	}
	
	public void initMap(ArrayList<structInfoPlacementBateau> mesBateaux) {
		//La vérification du nombre d'escorteurs devra être fait au-dessus
		//On place les bateaux envoyés par l'IHM
		partie.placerBateaux(mesBateaux);
	}

	public ArrayList<InfoPartie> anciennesParies(){
		this.partie=new Partie();
		return partie.partiesDebutees();
	}
	
}
