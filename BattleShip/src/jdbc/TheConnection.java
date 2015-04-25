package jdbc;
import java.sql.*;

public class TheConnection {
	private ConnectionInfo info;
	private Connection conn;
	public void open() {
		try{
			//Enregistrement du driver oracle
			System.out.print("Chargement du driver oracle");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Driver chargé");
			
			//Etablissement de la connexion
			System.out.print("Connection en cours à la base de données");
			conn = DriverManager.getConnection(info.CONN_URL, info.USER, info.PASSWD);
			conn.setAutoCommit(false); //on enlève l'autocommit de base
			System.out.println("Connecté");
		} catch (SQLException e) {
			System.err.println("Echec");
			e.printStackTrace(System.err);
		}
	}
	
	public void close() throws SQLException {
		System.out.println("Deconnecté");
		conn.close(); }
	
	public void rollbackPerso(){
		try{
			this.conn.rollback();
		}
		catch(Exception e){
			System.out.println("Rollback raté");
			e.printStackTrace(System.err);
		}
	}
	
	
	
	
	public Connection getConnection() {return conn;}
	public TheConnection (ConnectionInfo info) {this.info=info;}
}