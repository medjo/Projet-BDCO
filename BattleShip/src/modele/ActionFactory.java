package modele;

import java.sql.ResultSet;
import java.util.ArrayList;

import jdbc.SimpleQuery;

public class ActionFactory {
	//structure pour représenter les données en provenance de l'ihm
	class infoActionJoueur{
		public String type; //Tir ou déplacement
		public int x;//Pivot ou coord tir
		public int y;//Pivot ou coord tir
		public String dir; //direction
	}
	
	
	public ArrayList<Action> actionsJoueur(ArrayList<infoActionJoueur> infos,int idBateau, int idPartie, int nTour, int nAction){
		int i =0;
		ArrayList<Action> actionsJoueur = new ArrayList<Action>();
		while(i<infos.size()){
			infoActionJoueur infoi=infos.get(i);
			if(infoi.type=="TIR"){
				actionsJoueur.add(new Tir(idBateau, idPartie, nTour, nAction,infoi.x,infoi.y));
			}
			//TODO
			else if(infoi.type=="DEPLACEMENT"){
				actionsJoueur.add(new Deplacement());
			}
		}
		
	}
}
