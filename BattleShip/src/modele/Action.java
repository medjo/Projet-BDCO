package modele;

import java.util.ArrayList;

import modele.ExceptionDeplacement;
import modele.TirMissed;
import jdbc.TheConnection;

public abstract class Action {
	private int idBateau;
	private int idPartie;
	private int nTour;
	private int nAction;
	private String pseudo;
	
	public Action(int idBateau, int idPartie, String pseudo, int nTour, int nAction) {
		this.idBateau=idBateau;
		this.idPartie=idPartie;
		this.nAction = nAction;
		this.nTour = nTour;
		this.pseudo = pseudo;
	}
	
	/**
	 * execute l'action au niveau de la BD ie met à jour l'etat des bateaux
	 * @param theConnection
	 * @throws ExceptionDeplacement 
	 * @throws TirMissed 
	 */
	public abstract void execute() throws ExceptionDeplacement, TirMissed;
	
	
	/**
	 * applique les changements fait par une action lors d'une observation de partie
	 * @throws TirMissed
	 */
	public abstract void executeReplay(ArrayList<Ship> listeBateaux) throws TirMissed;
	
	
	/**
	 * sauvegarde l'action au niveau de la BD dans l'historique de la partie 
	 * @param theConnection
	 * @throws ExceptionDeplacement 
	 */
	public abstract void save();
	
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
	
	public String getPseudo(){
		return pseudo;
	}
}
