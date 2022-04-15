package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import francesco.giordano.fg01.db.DBConnect;
import francesco.giordano.fg01.db.GetAutomaticField;
import francesco.giordano.fg01.model.Hardware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HardwareDAO {

	public ObservableList<Hardware> getRighe() {
		ObservableList<Hardware> obs = FXCollections.observableArrayList();
		Hardware sig;
		GetAutomaticField ga = new GetAutomaticField();
		try {
			String sql2 = "SELECT * FROM Hardware";
			Connection conn = DBConnect.getConnection();
			PreparedStatement st2 = conn.prepareStatement(sql2);
			ResultSet res = st2.executeQuery() ;

			while(res.next()) {
				ga = new GetAutomaticField();
				sig=(Hardware)ga.getField(new Hardware(), res);
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

	public boolean DBModify(Hardware Record) {
		boolean ret=true;
		try {
			Connection conn = DBConnect.getConnection();

			String sql = "update hardware set "+
						"tipohw = ?, marca = ?, modello = ?, dataacquisto = ?, "+
						"prezzoacquisto = ? where matricola = ?";

			PreparedStatement st2 = conn.prepareStatement(sql);
			st2.setString(1, Record.getTipohw());
			st2.setString(2,Record.getMarca());
			st2.setString(3,Record.getModello());
			st2.setDate(4, java.sql.Date.valueOf(Record.getDataacquisto()));
			st2.setFloat(5, Record.getPrezzoacquisto());
			st2.setString(6, Record.getMatricola());
			ret = st2.execute() ;

			st2.close();
			conn.close();
			return(ret);

		} catch(SQLException e) {
			throw new RuntimeException("Database Error updating Hardware table", e);
			//return(false);
		}
		//return(false);
	}

}


