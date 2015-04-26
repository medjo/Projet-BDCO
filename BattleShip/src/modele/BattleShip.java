package modele;
import ihm.Connexion;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import jdbc.*;
import Controleur.ControleurConnexion;
import Controleur.ControleurPartie;

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
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connexion frame = new Connexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
<<<<<<< HEAD
		});
		erAdv(listeJoueurs);
=======
		});*/
		
		//Test des fonctions de connexions
		/*
		ControleurConnexion.inscription("MordokkaiserBest4","guy",
				"sylvain", 14, 02 , 1994,
				"sylvain.guy@laposte.net", 32, "chez moi",85000,"LRY");*/
		
		
		try {
			ControleurConnexion.connexion("Sylvain");
		} catch (UtilisateurInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Test de la création de partie
		BattleShip.partie=new Partie();
		BattleShip.user=user;
		try {
			ControleurPartie.lancerNouvellePartie();
		} catch (ExceptionNoAdv e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<Ship> batInit= new ArrayList<Ship>();
		batInit.add(0, new Escorteur(5,5,"n",0));
		batInit.add(1, new Destroyer(4,3,"s",1));
		partie.executerPlacementBateauxInitiaux(batInit);
		
		Tir tir;
		tir=null;
		try {
			tir = new Tir(0,BattleShip.partie.getIdPartie(),"Mordokkai",0,0,4,3);
		} catch (TirOutOfBound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tir.execute();
		} catch (TirMissed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tir.save();
		BattleShip.theConnection.getConnection().commit();
		
		
		if(BattleShip.partie.aMoiDeJouer()) {System.out.println("C'est à moi de jouer");}
		else{System.out.println("Ce n'est pas à moi de jouer");}
		
		BattleShip.user = new Utilisateur();
		try {
			ControleurConnexion.connexion("Rubixbob");
		} catch (UtilisateurInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Test de la création de partie
		BattleShip.partie=new Partie();
		BattleShip.user=user;
		
		Tir tir2;
		tir2=null;
		try {
			tir2 = new Tir(0,BattleShip.partie.getIdPartie(),"Rubixbob",1,0,4,3);
		} catch (TirOutOfBound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tir2.execute();
		} catch (TirMissed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tir2.save();
		BattleShip.theConnection.getConnection().commit();
		
		if(BattleShip.partie.aMoiDeJouer()) {System.out.println("C'est à moi de jouer");}
		else{System.out.println("Ce n'est pas à moi de jouer");}
		
		
		
		
		
		
		//Test si une partie est terminée
		/*
		
		
		
		
		
		
		
		
		
		
		//if(partie.partieTerminee()) System.out.println("Partie terminée");
		//else System.out.println("Partie en cours");
		
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
>>>>>>> 0466580e6801234168a3dfcabf2d612c9876b6c9
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
