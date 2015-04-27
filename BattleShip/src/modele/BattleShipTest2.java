package modele;

import ihm.Connexion;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import jdbc.*;
import Controleur.ControleurConnexion;
import Controleur.ControleurPartie;

public class BattleShipTest2 {
				
			public static TheConnection theConnection;
			public static Utilisateur user;
			public static Partie partie;
			
			public static void main (String[] args) throws SQLException, InscriptionInvalideException, UtilisateurExistantException {
				BattleShip.theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","ikhaloo","ikhaloo"));
				BattleShip.theConnection.open();
				BattleShip.user = new Utilisateur();
				
				
				//CONNEXION DE L'UTILISATEUR
				try {
					ControleurConnexion.connexion("Sylvain");
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
				
				if(ControleurPartie.reprendrePartieEnCours(93, "Rubixbob")){
					System.out.println("On reprend une partie en cours");
				}
				else{
					System.out.println("On reprend une partie à l'initialisation");
					
					/*
					 * On vérifie que l'adversaire à bien fini de positionner ses bateaux
					 */
					if(ControleurPartie.reprendreAInit()){
						System.out.println("L'adversaire a bien positionné ses bateaux");
						/*
						 * On vérifie que l'on est le joueur 1
						 */
						if(ControleurPartie.rafraichirAction()) {
							System.out.println("C'est bon l'adversaire a déjà placé ses bateaux, je peux démarrer");
						}
						else{
							System.out.println("Je suis le joueur 2 donc je ne peux pas démarrer");
						}
					}
					else{
						System.out.println("L'adversaire n'a pas encore fini de placer ses bateaux");
					}
						//System.out.println(BattleShip.partie.aMoiDeJouer());
					
				}
				
				

				//SUITE DU SCENARIO: A PARTIR DE LA IL Y AURA MODIF DE LA BD
				
				//On commence par indiquer que l'on débute le tour (récupération de nos ships depuis la BD
				ControleurPartie.debutTour();
				
				//On exécute et save les actions une par une sans committer pour l'instant
				Tir tir1= ControleurPartie.Tir(0, 3, 3);
				try {
					ControleurPartie.jouerAction(tir1);
				} catch (TirMissed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Attention il faut faire au moins un premier commit dès maintennant
				BattleShip.theConnection.getConnection().commit();
				
				
				if(ControleurPartie.controleurNbActions(0)) System.out.println("Je peux encore faire une action");
				else System.out.println("Je ne peux plus faire d'action");
				
				Tir tir2= ControleurPartie.Tir(0, 3, 3);
				try {
					ControleurPartie.jouerAction(tir2);
				} catch (TirMissed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BattleShip.theConnection.getConnection().commit();
				
				
				if(ControleurPartie.controleurNbActions(0)) System.out.println("Je peux encore faire une action");
				else System.out.println("Je ne peux plus faire d'action");
				
				Tir tir3=ControleurPartie.Tir(0, 3, 3);
				try {
					ControleurPartie.jouerAction(tir3);
				} catch (TirMissed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BattleShip.theConnection.getConnection().commit();
				
				if(ControleurPartie.controleurNbActions(0)) System.out.println("Je peux encore faire une action");
				else System.out.println("Je ne peux plus faire d'action");
				
				
			
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
			
			
				
		}



