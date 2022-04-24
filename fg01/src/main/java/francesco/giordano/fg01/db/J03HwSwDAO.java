package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03HwSw;
import francesco.giordano.fg01.model.j02Software;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class J03HwSwDAO {


	public ObservableList<j02Software> getRigheSoftware(Hardware Hrec) {
		ObservableList<j02Software> obs = FXCollections.observableArrayList();
		j02Software rec;
		GetAutomaticField ga; //= new GetAutomaticField();
		try {

			String sql ="select s.codice, s.tiposw, s.nomesw, s.versione "+
					"from hwsw as h join software as s "+
					"where (h.codice = s.codice) and h.matricola = ?";

			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, Hrec.getMatricola());
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
			Connection conn = DBConnect.getConnection();
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

}
