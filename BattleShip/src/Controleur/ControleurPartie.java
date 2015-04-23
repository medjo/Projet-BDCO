package Controleur;

import java.util.ArrayList;

import modele.BattleShip;
import modele.InfoPartie;
import modele.Partie;
import modele.Utilisateur;
import modele.idJoueur;

public class ControleurPartie {
	private Utilisateur user;
	public void lancerNouvellePartie(){
		Partie partie = new Partie();
		int idPartie = partie.getIdDernierePartie();
		//Maintenant il faut construire la liste des joueurs pas entrain de jouer
		ArrayList<idJoueur>listeJoueurs=partie.getListeJoueurs();
		//On sélectionne l'adversaire
		idJoueur adv = partie.selectionnerAdv(listeJoueurs);
		//On crée la partie
		partie.creerNouvellePartie(idPartie,user.getPseudo(),adv.getPseudo());
	}

	public void reprendreUnePartie(){
		Partie partie= new Partie();
	}
	
}
