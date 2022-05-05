package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fglib.ConfigFile;
import francesco.giordano.fg01.model.J04Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class J04DAOUsers {
	
	private static String connString=ConfigFile.getMySqlConnString();

	public static ObservableList<J04Users> getRighe() {
		ObservableList<J04Users> obs = FXCollections.observableArrayList();
		J04Users sig;
		GetAutomaticField ga; //= new GetAutomaticField();
		try {
			String sql2 = "SELECT * FROM Users";
			Connection conn = DBConnect.getConnection(connString);
			PreparedStatement st2 = conn.prepareStatement(sql2);
			ResultSet res = st2.executeQuery() ;

			while(res.next()) {
				ga = new GetAutomaticField();
				sig=(J04Users)ga.caricaSingoloBean(new J04Users(), res);
				obs.add(sig);
			}

			st2.close();
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException("Database Error in countByShape", e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(obs);
	}
}
