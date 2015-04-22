package modele;

import jdbc.TheConnection;

public abstract class Action {
	private int idBateau;
	private int idPartie;
	private int nTour;
	private int nAction;
	
	public Action(int idBateau, int idPartie, int nTour, int nAction) {
		this.idBateau=idBateau;
		this.idPartie=idPartie;
		this.nAction = nAction;
		this.nTour = nTour;
	}
	
	/**
	 * execute l'action au niveau de la BD ie met à jour l'etat des bateaux
	 * @param theConnection
	 */
	public abstract void execute(TheConnection theConnection);
	
	/**
	 * sauvegarde l'action au niveau de la BD dans l'historique de la partie 
	 * @param theConnection
	 */
	public abstract void save(TheConnection theConnection);
	
	public int getIdBateau(){
		return idBateau;
	}
	
	public int getIdPartie(){
		return idPartie;
	}
	
	public int getNTour(){
		return nTour;
	}
	
	public int getNAction(){
		return nAction;
	}
}
