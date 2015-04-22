package modele;
import java.util.*;

public class ControleurMenu {
	
	//On change de controleur
	public void lancerHistorique(){
		ControleurHistorique histo=new ControleurHistorique();
		
	}
	
	public void lancerNouvellePartie(){
		
	}
	
	public void reprendreUnePartie(){
		
	}
	
	public void Deconnexion(){
		ControleurConnexion conn = new ControleurConnexion();
	}
}
