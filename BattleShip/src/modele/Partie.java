package modele;

import java.sql.ResultSet;
import jdbc.TheConnection;
import jdbc.SimpleQuery;

public class Partie {
	private int numTour;
	//private ArrayList<Ship> bateaux;
	
	public void recherchePartie(TheConnection theConnection) {
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM enAttente");
		req.execute();
		ResultSet res = req.getResult();
		try{
			while(res.next()){
				listeParties.add(new InfoPartie(theConnection, res.getString(1), res.getString(2), res.getBoolean(3)));
			}
		} catch (Exception e) {
			
		}
		
		req.close();
	}
	
}
