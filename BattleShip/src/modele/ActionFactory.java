package modele;

import java.sql.ResultSet;
import java.util.ArrayList;

import jdbc.SimpleQuery;

public class ActionFactory {
	ArrayList<Action> actionsJoueur;
	//structure pour représenter les données en provenance de l'ihm
	class infoActionJoueur{
		public int idBateau;
		public String type; //Tir ou déplacement
		public int x;//Pivot ou coord tir
		public int y;//Pivot ou coord tir
		public String dir; //direction
	}
	
	
	public void actionsJoueur(ArrayList<infoActionJoueur> infos, int idPartie, int nTour){
		int i =0;
		ArrayList<Action> actionsJoueur = new ArrayList<Action>();
		while(i<infos.size()){
			infoActionJoueur infoi=infos.get(i);
			if(infoi.type=="TIR"){
				actionsJoueur.add(new Tir(infoi.idBateau, idPartie, nTour, i,infoi.x,infoi.y)); //l'indice dans la liste est le numéro de l'action
			}
			//TODO
			else if(infoi.type=="DEPLACEMENT"){
				//actionsJoueur.add(new Deplacement());
			}
		}
		this.actionsJoueur=actionsJoueur;
	}
	
	//On va éxécuter toutes les actions voulues par le joueur
	public void execute(){
		int i=0;
		while(i<this.actionsJoueur.size()){
			actionsJoueur.get(i).execute();
		}
	}
}
