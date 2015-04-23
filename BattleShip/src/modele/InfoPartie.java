package modele;
import jdbc.*;
import java.util.Calendar;
import java.sql.*;

public class InfoPartie {
	private int idPartie;
	private String pseudo1;
	private String pseudo2;
	private Date date;
	private String vainqueur;
	
	//Constructeur si partie en cours
	public InfoPartie(int idPartie, String pseudo1, String pseudo2, Date date) {
		this.idPartie=idPartie;
		this.pseudo1=pseudo1;
		this.pseudo2=pseudo2;
		this.date=date;
	}
	
	//Constructeur si partie finie
	public InfoPartie(int idPartie, String pseudo1, String pseudo2, Date date, String vainqueur) {
		this.idPartie=idPartie;
		this.pseudo1=pseudo1;
		this.pseudo2=pseudo2;
		this.date=date;
		this.vainqueur=vainqueur;
	}
	
	public InfoPartie(TheConnection theConnection, int idPartie, Date date, Boolean fini){
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT * FROM vainqueurs NATURAL JOIN participants WHERE idpartie='"+idPartie+"'");
		req.execute();
		ResultSet res = req.getResult();
		try{
			this.idPartie=idPartie;
			this.date=date;
			res.next();
			this.pseudo1=res.getString(3);
			this.vainqueur=res.getString(2);
			res.next();
			this.pseudo2=res.getString(3);
		} catch (Exception e) {
			
		}
		
		req.close();
	}
	
	public int getId(){
		return this.idPartie;
	}
	
	public String getPseudo1(){
		return this.pseudo1;
	}
	
	public String getPseudo2(){
		return this.pseudo2;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public String getVainqueur(){
		return this.vainqueur;
	}
}
