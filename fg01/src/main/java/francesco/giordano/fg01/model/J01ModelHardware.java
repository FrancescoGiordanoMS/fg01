package francesco.giordano.fg01.model;

import java.util.HashMap;

import fglib.RiferimentoCampi;
import francesco.giordano.fg01.db.J01HardwareDAO;
import javafx.collections.ObservableList;

public class J01ModelHardware {
	
public ObservableList<Hardware> getRighe(HashMap<String, RiferimentoCampi> MapFieldValue) {
	ObservableList<Hardware> obs = null;	
	//J01HardwareDAO dao=new J01HardwareDAO();
	//obs=dao.getRighe(MapFieldValue);
	obs=J01HardwareDAO.getRighe(MapFieldValue);
	return obs;
}

public boolean DBModify(Hardware Record) {
	boolean ret = true;
	//J01HardwareDAO dao=new J01HardwareDAO();
	//ret = dao.DBModify(Record);
	ret=J01HardwareDAO.DBModify(Record);

	return ret;
}

public String DBInsert(Hardware Record) {
	//boolean ret = true;
	String msgErrore;
	//J01HardwareDAO dao=new J01HardwareDAO();
	//msgErrore = dao.DBInsert(Record);
	msgErrore = J01HardwareDAO.DBInsert(Record);
	return msgErrore;
}

public boolean DBDelete(Hardware Record) {
	boolean ret = true;
	ret = J01HardwareDAO.DBDelete(Record);
	return ret;
}

public J01ModelHardware() {
//	ConfigFile.read("config.txt");
//	this.connString = "jdbc:mysql://"+ConfigFile.getDatabase_ip()+"/"+
//			ConfigFile.getDatabase_nome()+
//			"?user="+ConfigFile.getDatabase_user()+
//			"&password="+ConfigFile.getDatabase_password();
//	J01HardwareDAO.connString=connString;
}




}
