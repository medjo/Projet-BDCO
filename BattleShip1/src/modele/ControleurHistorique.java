package modele;

import jdbc.TheConnection;

public class ControleurHistorique {
	public void quitter(){
		
	}
	
	public Rejouer afficherParties(TheConnection theConnection){
		Rejouer rejouer = new Rejouer(theConnection);
		rejouer.afficher();
		return rejouer;
	}
	
	public void voirPartie(TheConnection theConnection, String idPartie){
		
	}
	
	public void suivant(TheConnection theConnection){
		
	}
	
	public void recommencer(TheConnection theConnection){
		
	}
}
