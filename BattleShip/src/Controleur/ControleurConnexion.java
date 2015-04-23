package Controleur;
import java.sql.*;

import modele.InscriptionInvalideException;
import jdbc.*;

public class ControleurConnexion {
	public static void inscription(TheConnection theConnection,String pseudo,String nom,
			String prenom, int jj,int mm, int aaaa,
			String email, int num, String rue,int cp,String ville)
					throws InscriptionInvalideException{
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT pseudo FROM joueurs WHERE pseudo='"+pseudo+"'"); // cherche si le pseudo existe
		req.execute();
		try{
			ResultSet res = req.getResult();
			if (res.next()){
				throw new InscriptionInvalideException(res.getString(1) + " existe déja !");
			} else {
				System.out.println("Inscription de " + pseudo);
				SimpleQuery req1 = new SimpleQuery(theConnection.getConnection(),
						"INSERT INTO users VALUES('"+pseudo+"', '"+nom+"', '"+prenom+"', "+jj+", " +
						mm+", "+aaaa+", '"+email+"', "+num+", '"+rue+"', "+cp+", '"+ville+"')"); // insertion du nouveau pseudo et commit
				req1.execute();
				
				// valider (commit)
			}
		} catch(Exception e){
			System.err.println("Echec à la récupération du résultat");
			e.printStackTrace(System.err);
		}
		req.close();
	}
	
	public static void connexion(TheConnection theConnection,String pseudo){
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT pseudo FROM joueurs WHERE pseudo='"+pseudo+"'"); // cherche si le pseudo existe
		req.execute();
		try{
			ResultSet res = req.getResult();
			if (res.next()){
				System.out.println("Connexion");
			} else {
				System.out.println("Utilisateur inconnu");
			}
		} catch(Exception e){
			System.err.println("Echec à la récupération du résultat");
			e.printStackTrace(System.err);
		}
		req.close();
	}
}
