package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fglib.ConfigFile;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBConnect {

	private static Connection conn = null;
	private String file=null;
	private static String database_ip = null;
	private static String database_nome = null;
	private static String database_user = null;
	private static String database_password = null;

	public static Connection getConnection()  {
		
		ConfigFile rtf = new ConfigFile("config.txt");
		try {
			rtf.read();
			database_ip = rtf.getDatabase_ip();
			database_nome= rtf.getDatabase_nome();
			database_user=rtf.getDatabase_user();
			database_password=rtf.getDatabase_password();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//String jdbcURL = "jdbc:mysql://localhost/giordano?user=root&password=pwdMySql60@";
		String jdbcURL = "jdbc:mysql://"+database_ip+"/"+database_nome+
				"?user="+database_user+"&password="+database_password;
		boolean ret=false;
		try {
			conn=DriverManager.getConnection(jdbcURL) ;
			ret=true;
		} catch (SQLException e) {
			e.printStackTrace();
			String sqlState=e.getSQLState().toString();
			if (sqlState.equals("08")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("SQL Error");
				alert.setHeaderText("DriverManager.getConnection(jdbcURL)");
				alert.setContentText("Si è verificato un errore durante la connessione: Verificare che il DB sia stato aperto correttamente");
				alert.showAndWait();		
			} 
		} 
		return conn;
	}

	public static Connection getConnectMaria() {
		String jdbcURL = "jdbc:mariadb://localhost:3306/giordano?user=root&password=pwdMySql60@";
		try {
			conn=DriverManager.getConnection(jdbcURL) ;
		} catch (SQLException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("SQL Error");
			alert.setHeaderText("DriverManager.getConnection(jdbcURL)");
			alert.setContentText("Si è verificato un errore durante la connessione: Verificare che il DB sia stato aperto correttamente");
			alert.showAndWait();		}
		return conn ;
	}

}
