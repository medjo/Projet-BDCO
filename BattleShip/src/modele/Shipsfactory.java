package modele;
import java.sql.*;
import java.util.*;
import jdbc.*;

public class Shipsfactory {
	private TheConnection conn;
	
	public Shipsfactory(TheConnection conn) {
		this.conn=conn;
	}
	
	public ArrayList<Ship> allShips() {
		String query="SELECT * FROM";
		//Conversion de la requête en string
		return new ArrayList<Ship>();//TODO
	}
	
	public ArrayList<Ship> myShips() {
		return new ArrayList<Ship>();//TODO
	}
	
	public ArrayList<Ship> hisShips() {
		return new ArrayList<Ship>();//TODO
	}
}
