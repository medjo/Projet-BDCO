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
	
	public InfoPartie(int idPartie, Date date, int fini){
		SimpleQuery req = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM participants WHERE idpartie='"+idPartie+"'");
		SimpleQuery req2 = new SimpleQuery(BattleShip.theConnection.getConnection(),"SELECT * FROM vainqueurs WHERE idpartie='"+idPartie+"'");
		
		try{
			req.execute();
			req2.execute();
			ResultSet res = req.getResult();
			ResultSet res2 = req.getResult();
			if(res.next()){
				this.idPartie=idPartie;
				this.date=date;
				this.pseudo1=res.getString("joueur1");
				this.pseudo2=res.getString("joueur2");
				//System.out.println(" "+idPartie+date+this.pseudo1+this.pseudo2);
				if(res2.next()){
					this.vainqueur=res2.getString("pseudo");
				}
			}
		} catch (SQLException e) {
			System.out.println("erreur");
			e.printStackTrace(System.err);
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
