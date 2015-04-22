package modele;

import java.util.ArrayList;

import jdbc.TheConnection;

public class ControleurHistorique {
	
	public void quitter(){
		ControleurConnexion conn = new ControleurConnexion(); 
	}
	
	public ArrayList<InfoPartie> lancerHistorique(){
		Rejouer rejouer=new Rejouer(BattleShip.theConnection);
		return rejouer.getInfoParties();
	}
	
	public void voirPartie(TheConnection theConnection, String idPartie){
		
	}
	
	public void suivant(TheConnection theConnection){
		
	}
	
	public void recommencer(TheConnection theConnection){
		
	}
}
