package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ParamQuery;
import jdbc.TheConnection;
import jdbc.SimpleQuery;

public class Tir extends Action{
	private int x,y;
	
	public Tir(int idBateau, int idPartie, int nTour, int nAction, int x, int y) {
		super(idBateau, idPartie, nTour, nAction);
		this.x = x;
		this.y = y;
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
	public void execute() {
		ParamQuery req = new ParamQuery(BattleShip.theConnection.getConnection(),"UPDATE bateaux SET etat=etat-1 WHERE idPartie= ? AND idBateau= ?");
		try {
		req.execute();
		} catch (Exception e) {
			
		}
		req.close();
	}
	
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
