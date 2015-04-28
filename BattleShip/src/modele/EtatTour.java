package modele;

	/*Classe qui va permttre de représenter si l'on peut prendre notre tour ou pas
	 * Si init=true on est à notre étape d'initialisation
	 * Si tour=false et tour=false: on doit attendre que notre adversaire ait finie de placer ses bateaux
	 * Si tour =true, on peut jouer notre tour normal
	 */
	
	public class EtatTour{
		public boolean init;
		public boolean tour;
		public EtatTour(){
			this.init=false;
			this.tour=false;
		}
	}

