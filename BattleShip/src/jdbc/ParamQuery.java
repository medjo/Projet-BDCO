package jdbc;

import java.sql.*;


public class ParamQuery extends Request{
	public PreparedStatement pstmt;
	public ParamQuery(Connection conn, String query) {
		super(conn,query);
		try{
			pstmt = this.conn.prepareStatement(query);
			
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
	public PreparedStatement getStatement() {
		return pstmt;
	}
	public void setStatement(PreparedStatement st) {
		this.pstmt=st;
	}
	
	public ResultSet execute() {
		
try {
	getStatement().setInt(1,200);
			
			ResultSet result =pstmt.executeQuery(query);
			return result;
		}
		catch (SQLException e) {
			System.err.println("Echec de l'éxécution de la requête");
			e.printStackTrace(System.err);
			return  null; //Il faut retourner un type même si la reque est fausse.
		}
		
	}
	
	public int update() {
		
		return super.update(this.pstmt);
	
	}
	public void close() {
	
	super.close(this.pstmt);

	}
}
