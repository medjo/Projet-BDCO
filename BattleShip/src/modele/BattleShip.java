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
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion frame = new Connexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		//Test des fonctions de connexions
		/*
		ControleurConnexion.inscription("MordokkaiserBest4","guy",
				"sylvain", 14, 02 , 1994,
				"sylvain.guy@laposte.net", 32, "chez moi",85000,"LRY");
		ControleurConnexion.connexion("Mordokkaiser");
		Test de la création de partie
		Partie partie = new Partie();
		partie.user=user;
		partie.creerNouvellePartie(105);
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
			System.out.println(listeJoueurs.get(j).getNbParties());
			j++;
		}
		idJoueur adv=partie.selectionnerAdv(listeJoueurs);
		System.out.println("L'adversaire sélectionné est"+adv.getPseudo()+"");*/
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
		
		
		//Exemple de controleur pour la création de partie
		ControleurConnexion.connexion("Mordokkai");
		Partie partie = new Partie();
		partie.user=user;
		partie.creerNouvellePartie(1069);
		idJoueur adv = partie.selectionnerAdv(partie.getListeJoueurs());
		partie.ajouterParticipants(adv.getPseudo());
		
		//Test récupération des listes de parties débutées
		ArrayList<InfoPartie> listePartiesDebutees = partie.partiesDebutees();
		int l=0;
		while(l<listePartiesDebutees.size()){
			InfoPartie li=listePartiesDebutees.get(l);
			System.out.println("idPartie:"+li.getId()+"pseudo1:"+li.getPseudo1()+"pseudo2:"+li.getPseudo2()+"vainqueur"+li.getVainqueur());
			l++;
		}
		
		
		//Test de la méthode de récupération du dernier indice de partie
		int idernier=partie.getIdDernierePartie();
		System.out.println("Le dernier indice de partie est:"+idernier);
		
		
		//Test de la méthode de placement des bateaux initiaux
		ArrayList<Ship> batInit= new ArrayList<Ship>();
		batInit.add(0, new Escorteur(5,5,"N",56));
		batInit.add(1, new Destroyer(4,3,"S",57));
		/*batInit.add(2, new Escorteur(8,6,"E",56));
		batInit.add(3, new Escorteur(2,1,"O",56));
		batInit.add(4, new Destroyer(6,5,"N",56));
		batInit.add(5, new Destroyer(3,9,"S",56));
		batInit.add(6, new Escorteur(4,10,"E",56));
		batInit.add(7, new Destroyer(1,5,"O",56));
		batInit.add(8, new Escorteur(9,9,"N",56));*/
		
		//Test si une partie est terminée
		
		
		partie.executerPlacementBateauxInitiaux(batInit);
		if(partie.partieTerminee())
			System.out.println("La partie est terminée");
		else
			System.out.println("La partie n'est pas terminée");
		//Test de la création d'une partie + ajout des participants
	}
		
}
