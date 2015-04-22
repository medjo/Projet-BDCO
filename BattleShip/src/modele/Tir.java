package modele;

import java.sql.ResultSet;
import java.util.ArrayList;
import jdbc.TheConnection;
import jdbc.SimpleQuery;

public class Tir extends Action{
	private String idBateau;
	private String idPartie;
	
	public Tir(String idBateau, String idPartie) {
		this.idBateau=idBateau;
		this.idPartie=idPartie;
	}
	
	public void execute(TheConnection theConnection) {
		try {
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"UPDATE bateaux SET etat=etat-1 WHERE idPartie="+idPartie+"AND idBateau="+idBateau);
		req.execute();
		ResultSet res = req.getResult();
		
			
		} catch (Exception e) {
			
		}
	}
	
	public void setCoord(String idBateau, String idPartie) {
		this.idPartie=idBateau;
		this.idBateau=idPartie;
	}
	
	public String getIdBateau(){
		return this.idBateau;
	}
	
	public String getIdPartie(){
		return this.idPartie;
	}
}
