package jdbc;

import java.sql.*;


public class ParamQuery extends Request{
	private ResultSet result ;
	public ParamQuery(Connection conn, String query) {
		super(conn,query);
		try{
			stmt = this.conn.prepareStatement(query);
		}
		catch (SQLException e) {
			System.err.println("Echec à la création de la requête paramétrée");
			e.printStackTrace(System.err);
		}
	}
	/*public ResultSet execute() {
		try {
			
			result = stmt.executeQuery(query);
			return result;
		}
		catch (SQLException e) {
			System.err.println("Echec");
			e.printStackTrace(System.err);
			return  result; //Il faut retourner un type même si la reque est fausse.
		}
	}*/
}
