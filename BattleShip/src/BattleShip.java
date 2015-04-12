import java.sql.*;
import jdbc.*;
	
public class BattleShip {
	
	public static void main (String[] args) throws SQLException {
		TheConnection theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","guys","guys"));
		theConnection.open();
		
		//Corps
		//Exemples de requêtes avec les classes créees
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"select * from emp");
		//dumpResultSet(req.execute());
		req.close();
		
		theConnection.close();
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}