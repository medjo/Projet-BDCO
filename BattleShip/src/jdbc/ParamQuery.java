package jdbc;

import java.sql.*;


public class ParamQuery extends Request{
	private PreparedStatement pstmt;
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
	
	/**
	 * execute la requete sql sur la BD.
	 * Les exceptions sql sont traitées ici, sauf les exceptions de violation de contrainte
	 * qui sont renvoyées plus haut
	 * @return resultat de la requete
	 * @throws SQLException
	 */
	public ResultSet execute() throws SQLException {
		try {
			ResultSet result =pstmt.executeQuery();
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
