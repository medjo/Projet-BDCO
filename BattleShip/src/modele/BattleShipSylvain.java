package modele;
import ihm.Connexion;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import jdbc.*;
import Controleur.ControleurConnexion;
import Controleur.ControleurPartie;

//CETTE CLASSE VA PERMETTRE DE SIMULER UNE BATAILLE EN ATTENDANT LA FIN DE L'IHM
public class BattleShipSylvain {

		
		public static TheConnection theConnection;
		public static Utilisateur user;
		public static Partie partie;
		
		public static void main (String[] args) throws SQLException, InscriptionInvalideException, UtilisateurExistantException {
			BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
			BattleShip.theConnection.open();
			BattleShip.user = new Utilisateur();
			
			
			//CONNEXION DE L'UTILISATEUR
			try {
				ControleurConnexion.connexion("Rubixbob");
			} catch (UtilisateurInconnuException e) {
				e.printStackTrace();
			}
			
			
			//CREATION DE LA PARTIE
			BattleShip.partie=new Partie();
			
			
			//ON VA ESSAYER DE REPRENDRE UNE PARTIE
			//REPREND PARTIE
			/*
			 * Set l'id, nTour, Adv, Pseudo, et récupère les bateux courants si au moins positionnés
			 */
			
			if(ControleurPartie.reprendrePartieEnCours(134, "jacouille")){
				System.out.println("On reprend une partie en cours");
			}
			else{
				System.out.println("On reprend une partie à l'initialisation");
				
				/*
				 * On vérifie que l'adversaire à bien fini de positionner ses bateaux
				 */
				if(ControleurPartie.reprendreAInit()){
					System.out.println("L'adversaire a bien positionné ses bateaux. " +
							"Je peux donc placer mes bateaux");
					
				}
				else{
					System.out.println("L'adversaire n'a pas encore fini de placer ses bateaux");
				}
					//System.out.println(BattleShip.partie.aMoiDeJouer());
				
			}

			System.out.println(ControleurPartie.reprendreAInit()); //Je peux reprendre
			
			ControleurPartie.placerBateau(5, 6, "e", 2);
			ControleurPartie.placerBateau(1, 1, "o", 3);
			ControleurPartie.placerBateau(2, 2, "s", 2);
			
			BattleShip.theConnection.getConnection().commit();
			
			//SUITE DU SCENARIO: A PARTIR DE LA IL Y AURA MODIF DE LA BD
		/*	System.out.println("Dernier num bateau: "+BattleShip.partie.getDernierNumeroBateau());
			System.out.println("Placement: "+ControleurPartie.placerBateau(5, 5, "e", 3));
			System.out.println("Dernier num bateau: "+BattleShip.partie.getDernierNumeroBateau());
			System.out.println("Placement: "+ControleurPartie.placerBateau(6, 7, "s", 2));
			System.out.println("Dernier num bateau: "+BattleShip.partie.getDernierNumeroBateau());
			System.out.println("Placement: "+ControleurPartie.placerBateau(2, 3, "o", 3));
			
			//On valide les positionnements
			BattleShip.theConnection.getConnection().commit();
		
			
			*/
			
			
			
			
			
		BattleShip.theConnection.close();
			
			
			
			
			
		}
		
		
			
	}


