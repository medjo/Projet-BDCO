package Controleur;
import java.sql.*;

import modele.*;
import jdbc.*;

public class ControleurConnexion {
	
	//Méthode appelée pour l'inscription
	
	public static void inscription(String pseudo,String nom,
			String prenom, int jj,int mm, int aaaa,
			String email, int num, String rue,int cp,String ville)
					throws InscriptionInvalideException, UtilisateurExistantException{
		BattleShip.user.inscription(pseudo, nom, prenom, jj, mm, aaaa, email, num, rue, cp, ville);
	}
	
	// Méthode appelée pour la connexion
	public static void connexion(String pseudo) throws UtilisateurInconnuException {
		BattleShip.user.connexion(pseudo);
	}
	
	// Méthode appelée pour quitter l'application
	public static void quitter(){
		System.exit(0); //en attendant de trouver mieux
	}
	
}
