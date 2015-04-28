package Controleur;

import java.util.ArrayList;

import modele.BattleShip;
import modele.InfoPartie;
import modele.Rejouer;
import jdbc.TheConnection;
import modele.*;

public class ControleurHistorique {
	
	public void quitter(){
		ControleurConnexion conn = new ControleurConnexion(); 
	}
	
	public static void lancerHistorique(){
		BattleShip.rejouer=new Rejouer();
	}
	
	public static ArrayList<Ship> voirPartie(int idPartie){
		return BattleShip.rejouer.init(idPartie);
	}
	
	public static ArrayList<Ship> suivant(int idPartie){
		return BattleShip.rejouer.suivant(idPartie);
	}
	
	public static ArrayList<Ship> recommencer(int idPartie){
		return BattleShip.rejouer.init(idPartie);
	}
	
	public static InfoPartie suivant(){
		BattleShip.rejouer.setIdPartieSelec(BattleShip.rejouer.getIdPartieSelec()+1);
		return BattleShip.rejouer.getListeParties().get(BattleShip.rejouer.getIdPartieSelec()-1);
	}
}
