package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private static Connection conn;
	
	public static Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost/ufo_sightings?user=root&password=pwdMySql60@";
		conn=DriverManager.getConnection(jdbcURL) ;
		//return DriverManager.getConnection(jdbcURL) ;
		return conn ;
	}
	
	public static Connection getConnectMaria() throws SQLException {
		String jdbcURL = "jdbc:mariadb://localhost:3306/ufo_sightings?user=root&password=pwdMySql60@";
		conn=DriverManager.getConnection(jdbcURL) ;
		//return DriverManager.getConnection(jdbcURL) ;
		return conn ;
	}

}
