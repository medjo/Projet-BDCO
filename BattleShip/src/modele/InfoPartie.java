package modele;

public class InfoPartie {
	private String idPartie;
	private String pseudo1;
	private String pseudo2;
	private String date;
	private String vainqueur;
	
	//Constructeur si partie en cours
	public InfoPartie(String idPartie, String pseudo1, String pseudo2, String date) {
		this.idPartie=idPartie;
		this.pseudo1=pseudo1;
		this.pseudo2=pseudo2;
		this.date=date;
	}
	
	//Constructeur si partie finie
	public InfoPartie(String idPartie, String pseudo1, String pseudo2, String date, String vainqueur) {
		this.idPartie=idPartie;
		this.pseudo1=pseudo1;
		this.pseudo2=pseudo2;
		this.date=date;
		this.vainqueur=vainqueur;
	}
	
	public String getId(){
		return this.idPartie;
	}
	
	public String getPseudo1(){
		return this.pseudo1;
	}
	
	public String getPseudo2(){
		return this.pseudo2;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getVainqueur(){
		return this.vainqueur;
	}
}
