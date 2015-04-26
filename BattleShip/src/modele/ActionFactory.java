package modele;

import java.sql.ResultSet;
import java.util.ArrayList;

import jdbc.SimpleQuery;

public class ActionFactory {
	ArrayList<Action> actionsJoueur;
	//structure pour représenter les données en provenance de l'ihm
	class InfoActionJoueur{
		public int idBateau;
		public String type; //Tir ou déplacement
		public int x;//Pivot ou coord tir
		public int y;//Pivot ou coord tir
		public String typeMouvement;
		public String dir; //direction
		public String pseudo;
	}
	
	
	public void actionsJoueur(ArrayList<InfoActionJoueur> infosActions, int idPartie, int nTour) throws TirOutOfBound{
		int i =0;
		ArrayList<Action> actionsJoueur = new ArrayList<Action>();
		
		for(InfoActionJoueur infoA: infosActions){
			if(infoA.type.equals("tir")){
				//int idBateau, int idPartie, String pseudo, int nTour, int nAction, int x, int y
				actionsJoueur.add(new Tir(infoA.idBateau, idPartie, infoA.pseudo, nTour, i,infoA.x,infoA.y)); //l'indice dans la liste est le numéro de l'action
			}
			else if(infoA.type.equals("dep")){
				//int idBateau, int idPartie, String pseudo, int nTour, int nAction, TypeDeplacement type
				//actionsJoueur.add(new Deplacement());
			}
		}
		this.actionsJoueur=actionsJoueur;
	}
	
	//On va éxécuter toutes les actions voulues par le joueur
	public void execute(){
		int i=0;
		try{
		while(i<this.actionsJoueur.size()){
			actionsJoueur.get(i).execute();
		}
		}
		catch(Exception e){
			//TODO
		}
	}
}
