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
	
	/**
	 * execute la requete sql sur la BD.
	 * Les exceptions sql sont traitées ici, sauf les exceptions de violation de contrainte
	 * qui sont renvoyées plus haut
	 * @return resultat de la requete
	 * @throws SQLException
	 */
	public ResultSet execute() throws SQLException {
		try {
			
			result = stmt.executeQuery(query);
			return result;
		}
		catch (SQLException e) {
			String message = e.getMessage();
			int errorCode = e.getErrorCode();
			String sqlState = e.getSQLState();
			if(sqlState == "23000"){ // integrity violation constraint
				throw e;
			}
			System.err.println("Echec de l'éxécution de la requête");
			System.out.println("Message = "+message);
			System.out.println("SQLState = "+sqlState);
			System.out.println("ErrorCode = "+errorCode);
			e.printStackTrace(System.err);
			throw new SQLException();
			//return  null; //Il faut retourner un type même si la reque est fausse.
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
