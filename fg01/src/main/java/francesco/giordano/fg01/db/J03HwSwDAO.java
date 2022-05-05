package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fglib.ConfigFile;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03HwSw;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.j02Software;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class J03HwSwDAO {
	
	private static String connString=ConfigFile.getMySqlConnString();
	

	public static ObservableList<j02Software> getRigheSoftware(String matricolaHw) {
		ObservableList<j02Software> obs = FXCollections.observableArrayList();
		j02Software rec;
		GetAutomaticField ga; //= new GetAutomaticField();
		try {

			String sql ="select s.codice, s.tiposw, s.nomesw, s.versione "+
					"from hwsw as h join software as s "+
					"where (h.codice = s.codice) and h.matricola = ?";

			Connection conn = DBConnect.getConnection(connString);
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricolaHw);
			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				ga = new GetAutomaticField();
				rec=(j02Software)ga.caricaSingoloBean(new j02Software(), res);
				obs.add(rec);
			}

			st.close();
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

	public static void RegistraSuDB(String matricolaHardware, ObservableList<j02Software> obs) {
		String sql = "INSERT INTO HwSw (matricola, codice) VALUES (?,?)";
		int ix = 0;
		PreparedStatement st;
		try {
			Connection conn = DBConnect.getConnection(connString);
			st = conn.prepareStatement(sql);

			while (ix < obs.size())	 {
				st.setString(1, matricolaHardware);
				st.setString(2, obs.get(ix).getCodice());
				st.execute() ;
				ix++;
			}					
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	/********************************************************************************************
	 * Il metodo elimina tutti i record relativi ad un singolo hw se il codice sw
	 * passato come parametro è null, altrimenti elimina solo il record, relativo
	 * all'hw, il cui codice viene passato come parametro.
	 * @param matricolaHw
	 * @param codiceSw
	 * @return
	 */
	public static boolean EliminaDaDB(String matricolaHw, String codiceSw) {
		String sql=null;
		boolean ret = false;		
		PreparedStatement st;
		try {
			Connection conn = DBConnect.getConnection(connString);
			if (codiceSw==null) {
				sql = "Delete from HwSw where matricola = ?";
				st = conn.prepareStatement(sql);
				st.setString(1, matricolaHw);
			}
			else if (matricolaHw==null) {
				sql = "Delete from HwSw where codice = ?";
				st = conn.prepareStatement(sql);
				st.setString(1, codiceSw);
			}
			else {
				sql = "Delete from HwSw where matricola = ? and codice= ?";
				st = conn.prepareStatement(sql);
				st.setString(1, matricolaHw);
				st.setString(2, codiceSw);
			}
			st.execute() ;
			st.close();
			conn.close();
			ret=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	/*******************************************************************************************
	 * Questo metodo si occupa di gestire l'integrità referenziale delle varie tabelle del db
	 * a partire dalla cancellazione di un record della tabella Hardware
	 * @param Record E' il record della tabella hardware che cancelliamo
	 * @return
	 */
	public static boolean DBDeleteHwSw(Hardware Record) {
		boolean ret=false;
		String sql = "DELETE FROM Hardware WHERE matricola = ?";
		String sqlHwSw = "Delete from HwSw where matricola = ?";
		try (Connection conn = DBConnect.getConnection(connString);
				PreparedStatement st = conn.prepareStatement(sql);
				PreparedStatement stHwSw = conn.prepareStatement(sqlHwSw))
		{
			conn.setAutoCommit(false);
			st.setString(1, Record.getMatricola());
			stHwSw.setString(1, Record.getMatricola());
			st.execute() ;
			stHwSw.execute();
			conn.commit();
			conn.setAutoCommit(true);
			st.close();
			stHwSw.close();
			conn.close();
//			J03ModelHwSw.EliminaHwSW(Record.getMatricola());
			ret=true;
		} catch(SQLException e) {
			ret = false;
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eliminazione Record");
			alert.setHeaderText("Transazione non completata");
			alert.setContentText("L'hardware selezionato non è stato eliminato");
			alert.showAndWait();	
		} 
		return(ret);
	}
	
}
