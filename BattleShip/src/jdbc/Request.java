package jdbc;
import java.sql.*;

public class Request {
	protected String query;
	protected Connection conn;
	private ResultSet result;
	
	public Request(Connection conn, String query) {
		this.query=query;
		this.conn=conn;
	}
	public void setQuery(String query){
		this.query=query;
	}
	public String getQuery() {
		return this.query;
	}
	public void setConn(Connection conn){
		this.conn=conn;
	}
	public Connection getConnection() {
		return this.conn;
	}
	
	public ResultSet execute(Statement st){
		try {
			
			result =st.executeQuery(query);
			return result;
		}
		catch (SQLException e) {
			System.err.println("Echec de l'éxécution de la requête");
			e.printStackTrace(System.err);
			return  result; //Il faut retourner un type même si la reque est fausse.
		}
	}
	
	public int update(Statement st){
		try {
			
			int nb = st.executeUpdate(query);
			return nb;
		}
		catch (SQLException e) {
			System.err.println("Echec à l'exécution de la mise à jour");
			e.printStackTrace(System.err);
			return  -1; //Il faut retourner un type même si la requete est fausse.
		}
	}
	
	public void close(Statement st) {
		try {
			if (result!=null) result.close();
			st.close();
			//conn.close();
		}
		catch (SQLException e) {
			System.err.println("Echec à la fermeture de la requête");
			e.printStackTrace(System.err);
		}
	}
	
	public void afficher() {
		try{
		ResultSetMetaData rsetmd = result.getMetaData();
		int i = rsetmd.getColumnCount();
		for (int k=1;k<=i;k++)
			System.out.print(rsetmd.getColumnName(k) + "\t");
		
		System.out.println();
		
		while (result.next()) {
			for (int j = 1; j <= i; j++) {
				System.out.print(result.getString(j) + "\t");
			}
			System.out.println();
		}
		} catch(SQLException e) {
			System.err.println("Echec à l'affichage du résultat de la requête");
			e.printStackTrace(System.err);
		}
	}

	public ResultSet getResult() {
		return this.result;
	}
	
}
