package jdbc;
import java.sql.*;

public class Request {
	protected String query;
	protected Connection conn;
	protected Statement stmt;
	private ResultSet result;
	
	public Request(Connection conn, String query) {
		this.query=query;
		this.conn=conn;
	}
	public void setStatement(String query){
		this.query=query;
	}
	public String getStatement() {
		return this.query;
	}
	public void setConn(Connection conn){
		this.conn=conn;
	}
	public Connection getConnection() {
		return this.conn;
	}
	
	public ResultSet execute(){
		try {
			
			result = stmt.executeQuery(query);
			return result;
		}
		catch (SQLException e) {
			System.err.println("Echec");
			e.printStackTrace(System.err);
			return  result; //Il faut retourner un type même si la reque est fausse.
		}
	}
	
	public int updateQuery(){
		try {
			
			int nb = stmt.executeUpdate(query);
			return nb;
		}
		catch (SQLException e) {
			System.err.println("Echec à l'exécution de la mise à jour");
			e.printStackTrace(System.err);
			return  -1; //Il faut retourner un type même si la requete est fausse.
		}
	}
}
