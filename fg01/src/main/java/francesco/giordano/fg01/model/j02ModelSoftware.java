package francesco.giordano.fg01.model;

import java.util.HashMap;

import fglib.RiferimentoCampi;
import francesco.giordano.fg01.db.J04DAOUsers;
import francesco.giordano.fg01.db.j02SoftwareDAO;
import javafx.collections.ObservableList;

public class j02ModelSoftware {

	public ObservableList<j02Software> getRighe() {
		ObservableList<j02Software> obs = null;	
		j02SoftwareDAO dao=new j02SoftwareDAO();
		obs=dao.getRighe();
		return obs;
	}

	public boolean DBModify(j02Software Record) {
		boolean ret = true;
		j02SoftwareDAO dao=new j02SoftwareDAO();
		ret = dao.DBModify(Record);
		return ret;
	}

	public String DBInsert(j02Software Record) {
		//boolean ret = true;
		String msgErrore;
		j02SoftwareDAO dao=new j02SoftwareDAO();
		msgErrore = dao.DBInsert(Record);
		return msgErrore;
	}

	public boolean DBDelete(j02Software Record) {
		boolean ret = true;
		//String msgErrore;
		j02SoftwareDAO dao=new j02SoftwareDAO();
		ret = dao.DBDelete(Record);
		return ret;
	}
	
}
