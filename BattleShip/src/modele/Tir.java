package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.*;
import modele.ActionFactory.infoActionJoueur;
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
		System.err.println("1");
		String adversaire = BattleShip.partie.getAdv();
		System.err.println("2");
		System.err.println("Adve:"+adversaire);
		
		/* on récupère la liste des bateux de l'adversaire */
		bateauxEnnemis = ShipsFactory.Ships(idPartie, adversaire);
	}
	
	@Override
	public void save() {
		//System.out.println("save");
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO Actions (iDPartie, idBateau, nTour, nAction, x, y, type)"
				+ "														VALUES ( ?, ?, ?, ?, ?, ?, ?)");
		try {
			System.out.println(getIdPartie());
			req.getStatement().setInt(1, getIdPartie());
			System.out.println(getIdBateau());
			req.getStatement().setInt(2, getIdBateau());
			System.out.println(getNTour());
			req.getStatement().setInt(3, getNTour());
			System.out.println(getNAction());
			req.getStatement().setInt(4, getNAction());
			System.out.println(this.x);
			req.getStatement().setInt(5, x);
			System.out.println(this.y);
			req.getStatement().setInt(6, y);

			//System.out.println(getIdPartie());
			req.getStatement().setString(7, "Tir");

			
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
		int idBateau = -1;
		for(Ship s : bateauxEnnemis){
			 if(x == s.getXBateau() && y == s.getYBateau()){
				 idBateau = s.getIdBateau();
			 }
		}
		/* le tir ne touche aucun bateau */
		if(idBateau == -1){
			System.out.println("Bateau non touché");
			//throw new TirMissed();
		}
		
		/*le tir touche un bateau */
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"UPDATE bateaux SET etat=etat-1 WHERE idPartie= ? AND idBateau= ?");
		try {
			req.getStatement().setInt(1, getIdPartie());
			req.getStatement().setInt(2, idBateau);
			req.execute();
		} catch (Exception e) {
			System.err.println("impossible de maj etat bateau après tir");
			e.printStackTrace(System.err);
		}
		try{
		BattleShip.theConnection.getConnection().commit();
		}catch(Exception e){
			System.out.println("pb de commit");
		}
		req.close();
	}
	
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
