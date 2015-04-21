package jdbc;

public class ConnectionInfo {
	final String CONN_URL;
	final String USER;
	final String PASSWD;
	
	public ConnectionInfo(String url, String usr, String passwd) {
		CONN_URL = url; 
		USER=usr;
		PASSWD = passwd;
	}
}