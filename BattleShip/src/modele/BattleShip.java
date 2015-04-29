package modele;
import ihm.Connexion;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.DebugGraphics;

import jdbc.*;
import Controleur.ControleurConnexion;
import Controleur.ControleurHistorique;
import Controleur.ControleurPartie;

public class BattleShip {
	
	public static TheConnection theConnection;
	public static Utilisateur user;
	public static Partie partie;
	public static Rejouer rejouer;
	
	public static void main (String[] args) throws SQLException, InscriptionInvalideException, UtilisateurExistantException {
		BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
		BattleShip.theConnection.open();
		BattleShip.user = new Utilisateur();
		
		
		//Lancement de l'interface graphique
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion frame = new Connexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	
	}
		
}
