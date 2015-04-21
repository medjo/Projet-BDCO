package jdbc;
import java.sql.*;

public class SimpleQuery extends Request{
	private Statement stmt;
	public SimpleQuery(Connection conn, String query) {
		super(conn,query);
		try{
			stmt = this.conn.createStatement();
		}
		catch (SQLException e) {
			System.err.println("Echec à la création du statement");
			e.printStackTrace(System.err);
		}
	}
	
	public ResultSet execute() {
		try {
			
			result = stmt.executeQuery(query);
			return result;
		}
		catch (SQLException e) {
			System.err.println("Echec à l'éxécution de la requête");
			e.printStackTrace(System.err);
			return  result; //Il faut retourner un type même si la requete est fausse.
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
	public void close(){
		super.close(stmt);
	}
}
