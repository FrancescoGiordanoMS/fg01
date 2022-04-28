package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBConnect {

	private static Connection conn = null;

	public static Connection getConnection()  {
		String jdbcURL = "jdbc:mysql://localhost/giordano?user=root&password=pwdMySql60@";
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
