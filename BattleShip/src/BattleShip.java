import java.sql.*;
import jdbc.*;
	
public class BattleShip {
	
	public static void main (String[] args) throws SQLException {
		TheConnection theConnection = new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","guys","guys"));
		theConnection.open();
		
		//Corps
		//Exemples de requêtes avec les classes créees
		SimpleQuery req = new SimpleQuery(theConnection.getConnection(),"SELECT table_name FROM user_tables");
		req.execute();
		req.afficher();
		req.close();
		//Requête simple
		SimpleQuery req1 = new SimpleQuery(theConnection.getConnection(),"select * from emp");
		req1.execute();
		req1.afficher();
		req1.close();
		//Requête de mise à jour
		SimpleQuery req2 = new SimpleQuery(theConnection.getConnection(),"update test SET argent=argent+1");
		//req2.update();
		//attention il n'y a pas eu de commit ici
		req2.close();
		
		
		PreparedStatement stmt = theConnection.getConnection().prepareStatement
				 ("select * from emp where SAL > ?"); 

				 stmt.setInt(1, 200); // 1er parametre
				//stmt.setString(2, "L%"); // 2eme parametre 
				ResultSet rset = stmt.executeQuery();
				rset.close();
				stmt.close();
				
		
		
		
		ParamQuery req3 = new ParamQuery(theConnection.getConnection(), "select * from emp where SAL > ?");
		req3.getStatement().setInt(1,200);
		
		//req3.getStatement().setFloat(2,0);
		//req3.execute(req3.pstmt);
		req3.execute();
		
		
		theConnection.close();
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}