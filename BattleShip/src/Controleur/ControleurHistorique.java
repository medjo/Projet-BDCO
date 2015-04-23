package Controleur;

import java.util.ArrayList;

import modele.BattleShip;
import modele.InfoPartie;
import modele.Rejouer;
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
