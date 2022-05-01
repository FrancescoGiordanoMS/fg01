package francesco.giordano.fg01.model;

import java.util.HashMap;

import fglib.RiferimentoCampi;
import francesco.giordano.fg01.db.HardwareDAO;
import javafx.collections.ObservableList;

public class ModelHardware {
	
public ObservableList<Hardware> getRighe(HashMap<String, RiferimentoCampi> MapFieldValue) {
	ObservableList<Hardware> obs = null;	
	//HardwareDAO dao=new HardwareDAO();
	//obs=dao.getRighe(MapFieldValue);
	obs=HardwareDAO.getRighe(MapFieldValue);
	return obs;
}

public boolean DBModify(Hardware Record) {
	boolean ret = true;
	//HardwareDAO dao=new HardwareDAO();
	//ret = dao.DBModify(Record);
	ret=HardwareDAO.DBModify(Record);

	return ret;
}

public String DBInsert(Hardware Record) {
	//boolean ret = true;
	String msgErrore;
	//HardwareDAO dao=new HardwareDAO();
	//msgErrore = dao.DBInsert(Record);
	msgErrore = HardwareDAO.DBInsert(Record);
	return msgErrore;
}

public boolean DBDelete(Hardware Record) {
	boolean ret = true;
	//String msgErrore;
	//HardwareDAO dao=new HardwareDAO();
	//ret = dao.DBDelete(Record);
	ret = HardwareDAO.DBDelete(Record);
	return ret;
}

public ModelHardware() {
//	ConfigFile.read("config.txt");
//	this.connString = "jdbc:mysql://"+ConfigFile.getDatabase_ip()+"/"+
//			ConfigFile.getDatabase_nome()+
//			"?user="+ConfigFile.getDatabase_user()+
//			"&password="+ConfigFile.getDatabase_password();
//	HardwareDAO.connString=connString;
}




}
