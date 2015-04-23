package Controleur;
import java.util.*;

public class ControleurMenu {
	
	//On change de controleur
	public void lancerHistorique(){
		ControleurHistorique histo=new ControleurHistorique();
		
	}
	
	public void jouer(){
		ControleurPartie partie=new ControleurPartie();
	}
	
	public void Deconnexion(){
		ControleurConnexion conn = new ControleurConnexion();
	}
}
