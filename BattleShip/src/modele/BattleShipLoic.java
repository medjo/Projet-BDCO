package modele;

import java.sql.SQLException;

import Controleur.ControleurConnexion;
import Controleur.ControleurPartie;
import jdbc.ConnectionInfo;
import jdbc.TheConnection;

public class BattleShipLoic {

	public BattleShipLoic() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SQLException, InscriptionInvalideException, UtilisateurExistantException {
		BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
		BattleShip.theConnection.open();
		BattleShip.user = new Utilisateur();
		
		System.out.println(Direction.NORD.toString());
		/*
		ControleurConnexion.inscription("jacouille","morteCouille",
				"jacouille", 14, 02 , 1994,
				"sylvain.guy@laposte.net", 32, "chez moi",85000,"LRY");
		*/
		
		try {
			ControleurConnexion.connexion("jacouille");
		} catch (UtilisateurInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Test de la création de partie
		BattleShip.partie=new Partie();
		//try {
		//	ControleurPartie.lancerNouvellePartie();
		//} catch (ExceptionNoAdv e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
		//}
//		
//		BattleShip.theConnection.rollbackPerso();
//		
		//ControleurPartie.placerBateau(1, 2, "e", 2);
		//ControleurPartie.placerBateau(5, 5, "n", 3);
		
		//BattleShip.theConnection.getConnection().commit();
//		ControleurPartie.reprendrePartieEnCours(134, "Rubixbob");
//		System.out.println(ControleurPartie.rafraichirAction());
		ControleurPartie.debutTour();
		
		try {
			ControleurPartie.jouerAction(ControleurPartie.Tir(1, 2, 2));
			ControleurPartie.jouerAction(ControleurPartie.Deplacement(2, TypeDeplacement.AVANCER));
			//BattleShip.theConnection.getConnection().commit();
		} catch (TirMissed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ControleurPartie.validerTour();
		
		BattleShip.theConnection.close();
	}

}
