package francesco.giordano.fg01.model;

import francesco.giordano.fg01.db.J04DAOUsers;
import javafx.collections.ObservableList;

public class J04ModelUsers {

	public ObservableList<J04Users> getRighe() {
		ObservableList<J04Users> obs = null;	
		J04DAOUsers dao=new J04DAOUsers();
		obs=dao.getRighe();
		return obs;
	}

	public boolean DBModify(J04Users Record) {
		boolean ret = true;
		J04DAOUsers dao=new J04DAOUsers();
		ret = dao.DBModify(Record);
		return ret;
	}

	public String DBInsert(J04Users Record) {
		//boolean ret = true;
		String msgErrore;
		J04DAOUsers dao=new J04DAOUsers();
		msgErrore = dao.DBInsert(Record);
		return msgErrore;
	}

	public boolean DBDelete(J04Users Record) {
		boolean ret = true;
		ret = J04DAOUsers.DBDelete(Record);
		return ret;
	}
	
}
