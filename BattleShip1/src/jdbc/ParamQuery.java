package jdbc;

import java.sql.*;


public class ParamQuery extends Request{
	public PreparedStatement pstmt;
	public ParamQuery(Connection conn, String query) {
		super(conn,query);
		try{
			this.pstmt = this.conn.prepareStatement(query);
		
		}
		catch (SQLException e) {
			System.err.println("Echec à la création de la requête paramétrée");
			e.printStackTrace(System.err);
		}
	}
	
	public PreparedStatement getStatement() {
		return pstmt;
	}
	public void setStatement(PreparedStatement st) {
		this.pstmt=st;
	}
	
	public ResultSet execute() {
		try {
			ResultSet result =pstmt.executeQuery();
			return result;
		}
		catch (SQLException e) {
			System.err.println("Echec de l'éxécution de la requête");
			e.printStackTrace(System.err);
			return  null; //Il faut retourner un type même si la reque est fausse.
		}
	}
	
	public int update() {
		try {
			int nb = pstmt.executeUpdate(query);
			return nb;
		}
		catch (SQLException e) {
			System.err.println("Echec à l'exécution de la mise à jour");
			e.printStackTrace(System.err);
			return  -1; //Il faut retourner un type même si la requete est fausse.
		}
	}
	public void close(){
		super.close(pstmt);
	}
}
