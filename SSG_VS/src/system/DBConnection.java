package system;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	static final String url = "jdbc:h2:/data/ssgvotingdb;AUTO_SERVER=TRUE";
	static final String username = "sa";
	static final String password = "";


	public static Connection connectDB() {
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");

			conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception ex) {
			System.out.println("There were errors connecting to the database");
			return null;
		}
	}
}
