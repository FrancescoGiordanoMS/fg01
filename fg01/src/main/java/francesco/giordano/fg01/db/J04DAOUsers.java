package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fglib.ConfigFile;
import francesco.giordano.fg01.model.J04Users;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.J04Users;
import francesco.giordano.fg01.model.J04Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	
	public static String DBInsert(J04Users Record) {
		boolean ret=true;
		String msgErrore = null;
		try {
			Connection conn = DBConnect.getConnection(connString);

			String sql = "INSERT INTO users "+
					"(	 codiceUser,	username,	email,	password) "+
						"VALUES (?,?,?,?)";
					
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Record.getCodiceuser());
			st.setString(2, Record.getUsername());
			st.setString(3,Record.getEmail());
			st.setString(4,Record.getPassword());
			st.execute() ;

			st.close();
			conn.close();
			return (msgErrore);

		} catch(SQLException e) {
			msgErrore=e.getMessage();
			return msgErrore;
			//throw new RuntimeException("Database Error insert Users table", e);
		}
		//return(false);
	}
	
	public static boolean DBDelete(J04Users Record) {
		//boolean ret=false;
		String sql = "DELETE FROM users WHERE codiceUser = ?";
		String sqlHwSw = "Delete from HwSw where codiceuser = ?";
		try {
			Connection conn = DBConnect.getConnection(connString);
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(sql);
			PreparedStatement stHwSw = conn.prepareStatement(sqlHwSw);
			st.setInt(1, Record.getCodiceuser());
			stHwSw.setInt(1, Record.getCodiceuser());
			st.execute() ;
			stHwSw.execute();
			conn.commit();
			st.close();
			stHwSw.close();
			conn.setAutoCommit(true);
			conn.close();
			J03ModelHwSw.Refresh();
			return(true);
		} catch(SQLException e) {
			throw new RuntimeException("Database Error deleting record Users table", e);
			//return(false);
		}
		//return(false);
	}
	
	
	/*************************************************************************************
	 * L'update viene eseguito solo se lo hashcode salvato nel record del db è uguale a
	 * quello salvato nel record bean al momento del caricamento in tableview. Se sono 
	 * uguali significa che nessun altro utente ha nel frattempo modificato il record. 
	 * Se sono diversi significa che nel frattempo un altro utente ha modificato, quindi
	 * genero un msg di errore per impedire la modifica dei dati 
	 * @param Users Record - E' il record da salvare
	 * @return
	 */
	public static boolean DBModify(J04Users Record) {
		boolean ret=true;
		int rowModified=0;
		String sqlUpdate = "update Users set username = ?, email = ?, password = ? "+
				"where codiceuser = ? and hash = ?";
		try (
				Connection conn = DBConnect.getConnection(connString);
				PreparedStatement st = conn.prepareStatement(sqlUpdate))
		{
			// il record nel db non è stato modificato da nessuno... posso proseguire 
			st.setString(1, Record.getUsername());
			st.setString(2,Record.getEmail());
			st.setString(3,Record.getPassword());
			st.setInt(4,Record.getCodiceuser());
			st.setInt(9, Record.getHash());
			rowModified=st.executeUpdate() ;
			if (rowModified > 0) {
				//conn.commit();
				Record.setHash(Record.getHash());
			}
		} catch(SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("SQL Error");
			alert.setHeaderText("Si è verificato un errore durante update");
			alert.setContentText(e.getMessage());
			alert.showAndWait();		
			//throw new RuntimeException("Database Error updating Users table", e);
			ret=false;
		} 
		return(ret);
	}

	
}
