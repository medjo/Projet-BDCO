package modele;
import ihm.Connexion;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import jdbc.*;
import Controleur.ControleurConnexion;

public class BattleShip {
	
	public static TheConnection theConnection;
	public static Utilisateur user;
	public static Partie partie;
	
	public static void main (String[] args) throws SQLException, InscriptionInvalideException, UtilisateurExistantException {
		BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
		BattleShip.theConnection.open();
		BattleShip.user = new Utilisateur();
		
		//Affichage de la table joueurs
		/*SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM joueurs");
		req.execute();
		req.afficher();
		req.close();*/
		
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
		erAdv(listeJoueurs);
		System.out.println("L'adversaire sélectionné est"+adv.getPseudo()+"");*/
		/*
		Rejouer histo = new Rejouer();
		ArrayList<InfoPartie>liste = histo.getInfoParties();
		int i=0;
		for(InfoPartie a:liste){
			System.out.println("x");
			System.out.println(a.getId()+" "+a.getPseudo1()+" "+a.getPseudo2()+" "+a.getDate());
			if (a.getVainqueur()!=null)System.out.println(a.getVainqueur());
			i++;
		}
		System.out.println("Nb parties"+i);
		BattleShip.theConnection.close();*/
	}
	
	
		
}
