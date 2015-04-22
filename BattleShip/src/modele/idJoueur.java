package modele;

public class idJoueur {
	private String pseudo;
	private double nbParties;
	
	public idJoueur(String pseudo, double nbParties){
		this.pseudo=pseudo;
		this.nbParties=nbParties;
	}
	
	public double getNbParties(){
		return this.nbParties;
	}
	
	public String getPseudo(){
		return this.pseudo;
	}
}
