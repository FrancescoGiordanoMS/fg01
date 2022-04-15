package francesco.giordano.fg01.model;

import francesco.giordano.fg01.db.HardwareDAO;
import javafx.collections.ObservableList;

public class ModelHardware {
//private ObservableList<Hardware> obs;
	
public ObservableList<Hardware> getRighe() {
	ObservableList<Hardware> obs = null;	
	HardwareDAO dao=new HardwareDAO();
	obs=dao.getRighe();
	return obs;
}

public boolean DBModify(Hardware Record) {
	boolean ret = true;
	HardwareDAO dao=new HardwareDAO();
	ret = dao.DBModify(Record);
	return ret;
}

}
