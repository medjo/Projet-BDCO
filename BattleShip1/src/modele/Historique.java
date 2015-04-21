package modele;
import java.util.*;

public class Historique {
	String idPartie;
	ArrayList<Etat> histo;
	
	public Historique(String id) {
		this.idPartie=id;
	}
	
	public String getHistoId(){
		return this.idPartie;
	}
	
	public void HistoFactory() {
		//on parcourt la bd pour instancier la liste d'état histo
	}
	
};
