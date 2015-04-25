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
		
		/*on récupère le pseudo de l'adversaire */
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"SELECT pseudo FROM Participants WHERE idPartie= ? AND pseudo <> ?");
		try {
			req.execute();
			ResultSet res = req.getResult();
			adversaire = res.getString(1);
		} catch (Exception e) {
			System.err.println("impossible de récupérer pseudo adversaire");
			e.printStackTrace(System.err);
		}
		req.close();
		
		/* on récupère la liste des bateux de l'adversaire */
		bateauxEnnemis = ShipsFactory.Ships(BattleShip.theConnection, idPartie, adversaire);
	}
	
	@Override
	public void save() {
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"INSERT INTO Actions (iDPartie, idBateau, nTour, nAction, x, y, type)"
				+ "														VALUES ( ?, ?, ?, ?, ?, ?, ?");
		try {
			req.getStatement().setInt(1, getIdPartie());
			req.getStatement().setInt(2, getIdBateau());
			req.getStatement().setInt(3, getNTour());
			req.getStatement().setInt(4, getNAction());
			req.getStatement().setInt(5, x);
			req.getStatement().setInt(6, y);
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
			throw new TirMissed();
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
		req.close();
	}
	
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
