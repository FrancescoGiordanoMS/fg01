package francesco.giordano.fg01.db;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

import fglib.RiferimentoCampi;
import francesco.giordano.fg01.db.DBConnect;
import francesco.giordano.fg01.db.GetAutomaticField;
import francesco.giordano.fg01.model.Hardware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HardwareDAO {

	public ObservableList<Hardware> getRighe(HashMap<String, RiferimentoCampi> MapFieldValue) {
		ObservableList<Hardware> obs = FXCollections.observableArrayList();
		Hardware sig;
		GetAutomaticField ga; //= new GetAutomaticField();
		try {
			String sql2 = "SELECT * FROM Hardware";
			Connection conn = DBConnect.getConnection();
			PreparedStatement st2 = conn.prepareStatement(sql2);
			ResultSet res = st2.executeQuery() ;

			while(res.next()) {
				ga = new GetAutomaticField();
				sig=(Hardware)ga.caricaSingoloBean(new Hardware(), res);
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



	/*************************************************************************************
	 * L'update viene eseguito solo se lo hashcode salvato nel record del db è uguale a
	 * quello salvato nel record bean al momento del caricamento in tableview. Se sono 
	 * uguali significa che nessun altro utente ha nel frattempo modificato il record. 
	 * Se sono diversi significa che nel frattempo un altro utente ha modificato, quindi
	 * genero un msg di errore per impedire la modifica dei dati 
	 * @param Hardware Record - E' il record da salvare
	 * @return
	 */
	public boolean DBModify(Hardware Record) {
		boolean ret=true;
		String sqlSelect = "SELECT * FROM Hardware where matricola = ?";
		String sqlUpdate = "update hardware set "+
				"tipohw = ?, marca = ?, modello = ?, dataacquisto = ?, "+
				"prezzoacquisto = ?, immagine = ?, "+
				"savedhashcode = ? where matricola = ?";

		//Date dataAcq  = java.sql.Date.valueOf(Record.getDataacquisto()); 
		try (
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sqlSelect);
				PreparedStatement st2 = conn.prepareStatement(sqlUpdate))
		{
			conn.setAutoCommit(false);
			// Prima di tutto controllo che hasCode() del record sia valido, cioè verifico che nel frattempo
			// nessun altro abbia modificato il record nel db		
			st.setString(1, Record.getMatricola());
			ResultSet res=st.executeQuery();
			res.next();
			if (res != null) {
				if (res.getInt("savedhashcode") != Record.getSavedhashcode() && res.getInt("savedhashCode")!=0) {
					// il record è stato nel frattempo modificato da altro utente...
					// sarebbe utile qui rileggere il record da db e visualizzarlo
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("SQL Error");
					alert.setHeaderText("Si è verificato un errore durante update");
					alert.setContentText("Il record è stato modificato da un altro utente");
					alert.showAndWait();
					ret=false;
				}
			}
			if (ret) {
				// il record nel db non è stato modificato da nessuno... posso proseguire 
				st2.setString(1, Record.getTipohw());
				st2.setString(2,Record.getMarca());
				st2.setString(3,Record.getModello());
				//st2.setDate(4, java.sql.Date.valueOf(Record.getDataacquisto()));
				if (Record.getDataacquisto() == null) st2.setNull(4, java.sql.Types.DATE);
				else st2.setDate(4, java.sql.Date.valueOf(Record.getDataacquisto()));
				st2.setFloat(5, Record.getPrezzoacquisto());
				st2.setBlob(6, Record.getImmagine());
				st2.setInt(7, Record.hashCode());
				st2.setString(8, Record.getMatricola());
				st2.execute() ;
				conn.commit();
			}
			
		} catch(SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("SQL Error");
			alert.setHeaderText("Si è verificato un errore durante update");
			alert.setContentText(e.getMessage());
			alert.showAndWait();		
			//throw new RuntimeException("Database Error updating Hardware table", e);
			ret=false;
		} 
	return(ret);
	}

	
	
	
	public String DBInsert(Hardware Record) {
		boolean ret=true;
		String msgErrore = null;
		try {
			Connection conn = DBConnect.getConnection();

			String sql = "INSERT INTO hardware "+
					"(	 matricola,		tipohw,			marca,			modello, "+
					"dataacquisto, 	prezzoacquisto,	savedhashcode ) "+
					"VALUES (?,?,?,?,?,?,?)";

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, Record.getMatricola());
			st.setString(2, Record.getTipohw());
			st.setString(3,Record.getMarca());
			st.setString(4,Record.getModello());
			st.setDate(5,(Record.getDataacquisto()==null?null:java.sql.Date.valueOf(Record.getDataacquisto())));
			st.setFloat(6, Record.getPrezzoacquisto());
			st.setInt(7, Record.hashCode());
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

	public boolean DBDelete(Hardware Record) {
		//boolean ret=false;
		String sql = "DELETE FROM Hardware WHERE matricola = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, Record.getMatricola());
			st.execute() ;
			st.close();
			conn.close();
			return(true);
		} catch(SQLException e) {
			throw new RuntimeException("Database Error deleting record Hardware table", e);
			//return(false);
		}
		//return(false);
	}


	//	public boolean DBModifyNew(Hardware Record) {
	//		boolean ret=true;
	//		String TableName=Record.getClass().getName();
	//		String sql="update "+TableName+" set ";
	//		Field[] allFields = Record.getClass().getDeclaredFields();
	//		for (Field field : allFields) {
	//			if (Modifier.isPrivate(field.getModifiers()) && 
	//					(field.getName().substring(0,2).equals("_m") ) ||
	//					(field.getName().substring(0,2).equals("_k") )
	//					){
	//				
	//				sql += "tipohw = ?, marca = ?, modello = ?, dataacquisto = ?, "+
	//						"prezzoacquisto = ? where matricola = ?";
	//
	//			}
	//			}
	//	
	//		
	//		return ret;
	//	}


}


