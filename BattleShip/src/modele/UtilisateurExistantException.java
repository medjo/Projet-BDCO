package modele;

public class UtilisateurExistantException extends Exception {
	public UtilisateurExistantException() {
		super();
	}
	
	public UtilisateurExistantException(String s) {
		super(s);
	}
}