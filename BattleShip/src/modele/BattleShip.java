package modele;
import ihm.Connexion;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import jdbc.*;
import Controleur.ControleurConnexion;

public class BattleShip {
	
	public static TheConnection theConnection;
	
	public static void main (String[] args) throws SQLException, InscriptionInvalideException {
		BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
		BattleShip.theConnection.open();
		
		//Test des fonctions de connexions
		ControleurConnexion conex= new ControleurConnexion();
		Utilisateur user = new Utilisateur();
		
		conex.inscription(user,"MordokkaiserBest4","guy",
				"sylvain", 14, 02 , 1994,
				"sylvain.guy@laposte.net", 32, "chez moi",85000,"LRY");
		conex.connexion(user,"Mordokkaiser");
		//Test de la création de partie
		Partie partie = new Partie();
		partie.user=user;
		/*partie.creerNouvellePartie(105);
		if(partie.partieTerminee()) System.out.println("Partie terminée");
		else System.out.println("Partie en cours");*/
		
		//Test de la récupération du dernier indice de partie
		//System.out.println(partie.getIdDernierePartie());
		
		//Test de la récupération de la liste des parties en cours
		/*ArrayList<InfoPartie> partiesDebutees=partie.partiesDebutees();
		int i=0;
		while(i<partiesDebutees.size()){
			System.out.println(partiesDebutees.get(i).getId());
			i++;
		}*/
		
		//Test de la construction de l'arraylist d'idjoueurs de tous les joueurs
		/*ArrayList<idJoueur> listeJoueurs = partie.getListeJoueurs();
		int j=0;
		while(j<listeJoueurs.size()){
			System.out.println(listeJoueurs.get(j).getPseudo());
			j++;
		}*/
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion frame = new Connexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		/*
		//Corps
		//Exemples de requêtes avec les classes créees
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT table_name FROM user_tables");
		req.execute();
		req.afficher();
		req.close();
		//Requête simple
		SimpleQuery req1 = new SimpleQuery(BattleShip.theConnection.getConnection(),"select * from emp");
		req1.execute();
		req1.afficher();
		req1.close();
		//Requête de mise à jour
		SimpleQuery req2 = new SimpleQuery(BattleShip.theConnection.getConnection(),"update test SET argent=argent+1");
		//attention il n'y a pas eu de commit ici
		req2.close();
		
		
		
		ParamQuery req3 = new ParamQuery(BattleShip.theConnection.getConnection(), "select * from emp where SAL > ?");
		req3.getStatement().setInt(1,200);
		req3.execute();
		*/
	}
		
}
