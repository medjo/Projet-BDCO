package jdbc;
import java.sql.*;

public class Request {
	protected String query;
	protected Connection conn;
	protected ResultSet result;
	
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
	
	
	public void close(Statement st) {
		try {
			if (result!=null) result.close();
			st.close();
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
