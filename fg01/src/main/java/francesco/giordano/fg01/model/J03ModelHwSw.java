package francesco.giordano.fg01.model;

import java.util.HashMap;

import francesco.giordano.fg01.db.J03HwSwDAO;
import francesco.giordano.fg01.db.j02SoftwareDAO;
import javafx.collections.ObservableList;

public class J03ModelHwSw {
	
	public J03ModelHwSw() {	}

	public ObservableList<j02Software> getRighe(Hardware Hrec, HashMap MapHwSw) {
		ObservableList<j02Software> obs = null;	
		String matricola = Hrec.getMatricola();

		obs=(ObservableList<j02Software>) MapHwSw.get(matricola);
		if (obs == null) {	
			J03HwSwDAO dao=new J03HwSwDAO();
			obs=dao.getRigheSoftware(Hrec);
			MapHwSw.put(matricola, obs);
		}
		return obs;
	}

	public void RegistraSuDB(String matricolaHardware, ObservableList<j02Software> obs) {
		if (obs != null) {	
			J03HwSwDAO dao=new J03HwSwDAO();
			dao.RegistraSuDB(matricolaHardware,obs);
		}
	}

}
