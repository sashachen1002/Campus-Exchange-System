package 期末報告_11102Programming_v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static Connection con;
	private static String server = "jdbc:mysql://140.119.19.73:3315/"; 
	private static String database = "108305091";
	private static String url = server + database + "?useSSL=false";
	private static String username = "108305091";
	private static String password = "o8fzq"; 
	
	public static Connection getCon() {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "108305091";
		String url = server + database + "?useSSL=false";
		String username = "108305091";
		String password = "o8fzq";
		
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}