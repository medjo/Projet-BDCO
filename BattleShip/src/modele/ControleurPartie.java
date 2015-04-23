package modele;

public class ControleurPartie {
	private Utilisateur user;
	public void lancerNouvellePartie(){
		Partie partie = new Partie();
		int idPartie = partie.getIdDernierePartie();
		idJoueur adv = partie.selectionnerAdv(listeJoueur);
		partie.creerNouvellePartie(BattleShip.theConnection,idPartie,user.getPseudo(),adv);
	}

	public void reprendreUnePartie(){
		Partie partie= new Partie();
	}
	
}
