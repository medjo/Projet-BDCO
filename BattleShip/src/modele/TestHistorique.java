package modele;

import java.sql.SQLException;
import java.util.ArrayList;

import Controleur.ControleurConnexion;
import Controleur.ControleurHistorique;
import jdbc.ConnectionInfo;
import jdbc.TheConnection;

public class TestHistorique {

	public TestHistorique() {
		// TODO Auto-generated constructor stub
	}
	
	private static void afficheBat(ArrayList<Ship> l){
		for(Ship s :l){
			System.out.println("idBateau " + s.getIdBateau() 
					+ " pseudo " + s.getPseudo()
					+ " x " + s.getXBateau()
					+ " y " + s.getYBateau()
					+ " orientation " + s.getDirBateauString()
					+ " etat " + s.getEtat() );
		}
	}

	public static void main(String[] args) throws SQLException, InscriptionInvalideException, UtilisateurExistantException {
		BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
		BattleShip.theConnection.open();
		BattleShip.user = new Utilisateur();
		

		try {
			ControleurConnexion.connexion("jacouille");
		} catch (UtilisateurInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ControleurHistorique.lancerHistorique();
		System.out.println("***** ini *******");
		afficheBat(ControleurHistorique.voirPartie(149));
		System.out.println("***** suivant *******");
		afficheBat(ControleurHistorique.suivant(149));
		System.out.println("***** suivant *******");
		afficheBat(ControleurHistorique.suivant(149));
		
		BattleShip.theConnection.getConnection().close();
		
	}

}
