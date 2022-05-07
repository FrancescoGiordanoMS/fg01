package francesco.giordano.fg01.model;

import francesco.giordano.fg01.db.J04DAOUsers;
import javafx.collections.ObservableList;

public class J04ModelUsers {

	public ObservableList<J04Users> getRighe() {
		ObservableList<J04Users> obs = null;	
		//J04DAOUsers dao=new J04DAOUsers();
		obs=J04DAOUsers.getRighe();
		return obs;
	}

	public boolean DBModify(J04Users Record) {
		boolean ret = true;
		//J04DAOUsers dao=new J04DAOUsers();
		ret = J04DAOUsers.DBModify(Record);
		return ret;
	}

	public String DBInsert(J04Users Record) {
		//boolean ret = true;
		String msgErrore;
		msgErrore = J04DAOUsers.DBInsert(Record);
		return msgErrore;
	}

	public boolean DBDelete(J04Users Record) {
		boolean ret = true;
		ret = J04DAOUsers.DBDelete(Record);
		return ret;
	}
	
}
