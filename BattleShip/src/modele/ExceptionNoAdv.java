package modele;

//Classe d'exception si pas d'adversaire
public class ExceptionNoAdv extends Exception{
//TODO
	public ExceptionNoAdv(){
		System.out.println("Il n'y a pas d'adversaire disponible");
	}
}
