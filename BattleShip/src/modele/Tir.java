package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.*;

import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;

public class Tir extends Action{
	private int x,y;
	private String adversaire;
	private ArrayList<Ship> bateauxEnnemis = new ArrayList<Ship>();
	
	public Tir(int idBateau, int idPartie, String pseudo, int nTour, int nAction, int x, int y) throws TirOutOfBound {
		super(idBateau, idPartie,pseudo, nTour, nAction);
		if(x>9 || x<0 || y>9 || y<0){
			throw new TirOutOfBound();
		}
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void save() {
		//System.out.println("save");
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO Actions (iDPartie, pseudo, idBateau, nTour, nAction, x, y, type)"
				+ "														VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			req.getStatement().setInt(1, getIdPartie());
			req.getStatement().setString(2, BattleShip.user.getPseudo());
			req.getStatement().setInt(3, getIdBateau());
			req.getStatement().setInt(4, getNTour());
			req.getStatement().setInt(5, getNAction());
			req.getStatement().setInt(6, this.x);
			req.getStatement().setInt(7, this.y);
			//System.out.println(getIdPartie());
			req.getStatement().setString(8, "tir");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		req.execute();
		} catch (Exception e) {
			
		}
		req.close();
		
	}
	
	@Override
	public void execute() throws TirMissed {
		/* on récupère la liste des bateux de l'adversaire */
		this.adversaire = BattleShip.partie.getAdv();
		bateauxEnnemis = ShipsFactory.Ships(getIdPartie(), adversaire);
		
		int idBateau = -1;
		for(Ship s : bateauxEnnemis){
			if(s.getDirBateauString().equals(Direction.SUD.toString())){
				if(x==s.getXBateau() && y<(s.getYBateau()+s.getTailleBateau()) && y>=s.getYBateau()){
					idBateau = s.getIdBateau();
				}
			}
			if(s.getDirBateauString().equals(Direction.NORD.toString())){
				if(x==s.getXBateau() && y>(s.getYBateau()-s.getTailleBateau()) && y<=s.getYBateau()){
					idBateau = s.getIdBateau();
				}
			}
			if(s.getDirBateauString().equals(Direction.EST.toString())){
				System.out.println("xB " + s.getXBateau() + " yB " + + s.getYBateau());
				if(y==s.getYBateau() && x<(s.getXBateau()+s.getTailleBateau()) && x>=s.getXBateau()){
					idBateau = s.getIdBateau();
				}
			}
			if(s.getDirBateauString().equals(Direction.OUEST.toString())){
				if(y==s.getYBateau() && x>(s.getXBateau()-s.getTailleBateau()) && x<=s.getXBateau()){
					idBateau = s.getIdBateau();
				}
			}
		}
			
		/* le tir ne touche aucun bateau */
		if(idBateau == -1){
			System.out.println("Aucun bateau touché");
			return;
		}
		
		/*le tir touche un bateau */
		System.out.println("Bateau ennemi touché; idpartie:"+getIdPartie()+"idbteau"+idBateau+"pseudo:"+adversaire);
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"UPDATE bateaux SET etat=etat-1 WHERE idPartie =? AND idBateau = ? AND pseudo=?");
		try {
			req.getStatement().setInt(1, getIdPartie());
			req.getStatement().setInt(2, idBateau);
			req.getStatement().setString(3, adversaire);
			req.execute();
		} catch (Exception e) {
			System.err.println("impossible de maj etat bateau après tir");
			e.printStackTrace(System.err);
		}
		req.close();
	}
	
	
	@Override
	public void executeReplay(ArrayList<Ship> listeBateaux){
		boolean touched = false;
		for(Ship s : listeBateaux){
			//on regarde si le tir touche un bateau qui n'est pas au joueur courant
			if(!s.getPseudo().equals(getPseudo())){
				if(s.getDirBateauString().equals(Direction.NORD.toString())){
					if(x==s.getXBateau() && y<=(s.getYBateau()+s.getTailleBateau()) && y>=s.getYBateau()){
						s.decrEtat();
						touched = true;
						break;
					}
				}
				if(s.getDirBateauString().equals(Direction.SUD.toString())){
					if(x==s.getXBateau() && y>=(s.getYBateau()-s.getTailleBateau()) && y<=s.getYBateau()){
						s.decrEtat();
						touched = true;
						break;
					}
				}
				if(s.getDirBateauString().equals(Direction.EST.toString())){
					if(y==s.getXBateau() && x<=(s.getXBateau()+s.getTailleBateau()) && x>=s.getXBateau()){
						s.decrEtat();
						touched = true;
						break;
					}
				}
				if(s.getDirBateauString().equals(Direction.OUEST.toString())){
					if(y==s.getXBateau() && x>=(s.getXBateau()-s.getTailleBateau()) && x<=s.getXBateau()){
						s.decrEtat();
						touched = true;
						break;
					}
				}
			}
		}
			
		/* le tir ne touche aucun bateau */
		if(!touched){
			System.out.println("Bateau non touché");
		}
		else{
			/*le tir touche un bateau */
			System.out.println(getPseudo() + " a touché un bateau ennemi");
		}
	}
	
	
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return ("Tir en " 
				+ " x " + x
				+ " y " + y
				+ super.toString());
	}
}
