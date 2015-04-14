package modele;
import java.util.*;

public class Rejouer{
	ArrayList<InfoPartie> listeParties;
	
	public Rejouer(){
		//On construit la liste des parties jouées à partir de l'historique
		
	}
	
	public void voirPartie(String idPartie) {
		Historique h = new Historique(idPartie); //On instancie l'historique sélectionné
	}
	
}
