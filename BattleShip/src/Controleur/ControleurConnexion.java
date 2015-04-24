package Controleur;
import java.sql.*;

import modele.*;
import jdbc.*;

public class ControleurConnexion {
	
	//Méthode appelée pour l'inscription
	
	public void inscription(Utilisateur user,String pseudo,String nom,
			String prenom, int jj,int mm, int aaaa,
			String email, int num, String rue,int cp,String ville) throws InscriptionInvalideException{
		user.inscription(pseudo, nom, prenom, jj, mm, aaaa, email, num, rue, cp, ville);
	}
	
	// Méthode appelée pour la connexion
	public void connexion(Utilisateur user,String pseudo) {
		user.connexion(pseudo);
	}
	
	// Méthode appelée pour quitter l'application
	public void quitter(){
		System.exit(0); //en attendant de trouver mieux
	}
	
}
