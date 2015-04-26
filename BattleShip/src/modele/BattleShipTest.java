package modele;
import ihm.Connexion;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import jdbc.*;
import Controleur.ControleurConnexion;
import Controleur.ControleurPartie;

//CETTE CLASSE VA PERMETTRE DE SIMULER UNE BATAILLE EN ATTENDANT LA FIN DE L'IHM
public class BattleShipTest {

		
		public static TheConnection theConnection;
		public static Utilisateur user;
		public static Partie partie;
		
		public static void main (String[] args) throws SQLException, InscriptionInvalideException, UtilisateurExistantException {
			BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
			BattleShip.theConnection.open();
			BattleShip.user = new Utilisateur();
			
			
		}
		
		
			
	}


