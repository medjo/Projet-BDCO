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
	
	public void lancerHistorique(){
		BattleShip.rejouer=new Rejouer();
	}
	
	public void voirPartie(TheConnection theConnection, String idPartie){
		
	}
	
	public static void suivant(TheConnection theConnection){
		
	}
	
	public void recommencer(TheConnection theConnection){
		
	}
	
	public static InfoPartie suivant(){
		BattleShip.rejouer.setIdPartieSelec(BattleShip.rejouer.getIdPartieSelec()+1);
		return BattleShip.rejouer.getListeParties().get(BattleShip.rejouer.getIdPartieSelec()-1);
	}
}
