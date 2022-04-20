package francesco.giordano.fg01.model;

import java.util.HashMap;

import fglib.RiferimentoCampi;
import francesco.giordano.fg01.db.HardwareDAO;
import javafx.collections.ObservableList;

public class ModelHardware {
//private ObservableList<Hardware> obs;
	
public ObservableList<Hardware> getRighe(HashMap<String, RiferimentoCampi> MapFieldValue) {
	ObservableList<Hardware> obs = null;	
	HardwareDAO dao=new HardwareDAO();
	obs=dao.getRighe(MapFieldValue);
	return obs;
}

public boolean DBModify(Hardware Record) {
	boolean ret = true;
	HardwareDAO dao=new HardwareDAO();
	ret = dao.DBModify(Record);
	return ret;
}

public boolean DBInsert(Hardware Record) {
	boolean ret = true;
	HardwareDAO dao=new HardwareDAO();
	ret = dao.DBInsert(Record);
	return ret;
}
}
