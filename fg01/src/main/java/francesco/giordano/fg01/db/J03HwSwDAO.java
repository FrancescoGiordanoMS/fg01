package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fglib.ConfigFile;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03HwSw;
import francesco.giordano.fg01.model.j02Software;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class J03HwSwDAO {
	
	private static String connString=ConfigFile.getMySqlConnString();


	public ObservableList<j02Software> getRigheSoftware(String matricolaHw) {
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

	public void RegistraSuDB(String matricolaHardware, ObservableList<j02Software> obs) {
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


	public void EliminaDaDB(String matricolaHw, String codiceSw) {
		String sql = "Delete from HwSw where matricola = ? and codice= ?";
		int ix = 0;
		PreparedStatement st;
		try {
			Connection conn = DBConnect.getConnection(connString);
			st = conn.prepareStatement(sql);

			st.setString(1, matricolaHw);
			st.setString(2, codiceSw);
			st.execute() ;
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
