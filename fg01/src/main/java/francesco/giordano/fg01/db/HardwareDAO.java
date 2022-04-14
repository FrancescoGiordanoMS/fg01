package francesco.giordano.fg01.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			String sql2 = "SELECT id,city,shape, datetime, BinaryField, TypeBinary FROM Hardware";
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
}
