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
		BattleShip.partie= new Partie();
		//BattleShip.partie=new Partie();
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
		
		if(BattleShip.partie.getIdDernierePartie()==BattleShip.rejouer.getIdPartieSelec()){
			//return BattleShip.rejouer.getListeParties().get(BattleShip.partie.getIdDernierePartie());
			BattleShip.rejouer.setIdPartieSelec(0);
			return BattleShip.rejouer.getListeParties().get(BattleShip.rejouer.getIdPartieSelec());
		}
		else{
			return BattleShip.rejouer.getListeParties().get(BattleShip.rejouer.getIdPartieSelec());
		}
	}
}
