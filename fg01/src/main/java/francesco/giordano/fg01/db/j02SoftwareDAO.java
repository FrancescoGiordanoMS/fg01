package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import fglib.ConfigFile;
import fglib.RiferimentoCampi;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.j02Software;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class j02SoftwareDAO {

	private static String connString=ConfigFile.getMySqlConnString();

	//public ObservableList<j02Software> getRighe(HashMap<String, RiferimentoCampi> MapFieldValue) {
	public ObservableList<j02Software> getRighe() {
		ObservableList<j02Software> obs = FXCollections.observableArrayList();
		j02Software sig;
		GetAutomaticField ga; //= new GetAutomaticField();
		try {
			String sql2 = "SELECT * FROM Software";
			Connection conn = DBConnect.getConnection(connString);
			PreparedStatement st2 = conn.prepareStatement(sql2);
			ResultSet res = st2.executeQuery() ;

			while(res.next()) {
				ga = new GetAutomaticField();
				sig=(j02Software)ga.caricaSingoloBean(new j02Software(), res);
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

	public boolean DBModify(j02Software Record) {
		boolean ret=true;
		try {
			Connection conn = DBConnect.getConnection(connString);

			String sql = "update software set "+
						"tiposw = ?, nomesw = ?, versione = ? where codice = ?";

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, Record.getTiposw());
			st.setString(2,Record.getNomesw());
			st.setString(3,Record.getVersione());
			st.setString(6, Record.getCodice());
			ret = st.execute() ;

			st.close();
			conn.close();
			return(ret);

		} catch(SQLException e) {
			throw new RuntimeException("Database Error updating Software table", e);
			//return(false);
		}
		//return(false);
	}

	public String DBInsert(j02Software Record) {
		boolean ret=true;
		String msgErrore = null;
		try {
			Connection conn = DBConnect.getConnection(connString);

			String sql = "INSERT INTO software "+
					"(	 codice,		tiposw,			nomesw,			versione) "+
						"VALUES (?,?,?,?)";
					
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, Record.getCodice());
			st.setString(2, Record.getTiposw());
			st.setString(3,Record.getNomesw());
			st.setString(4,Record.getVersione());
			st.execute() ;

			st.close();
			conn.close();
			return (msgErrore);

		} catch(SQLException e) {
			msgErrore=e.getMessage();
			return msgErrore;
			//throw new RuntimeException("Database Error insert Hardware table", e);
		}
		//return(false);
	}
	
	public boolean DBDelete(j02Software Record) {
		//boolean ret=false;
		String sql = "DELETE FROM SOFTWARE WHERE CODICE = ?";
		String sqlHwSw = "Delete from HwSw where codice = ?";
		try {
			Connection conn = DBConnect.getConnection(connString);
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(sql);
			PreparedStatement stHwSw = conn.prepareStatement(sqlHwSw);
			st.setString(1, Record.getCodice());
			stHwSw.setString(1, Record.getCodice());
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
			throw new RuntimeException("Database Error deleting record Software table", e);
			//return(false);
		}
		//return(false);
	}
	
	
}
