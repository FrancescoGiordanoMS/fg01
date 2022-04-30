package francesco.giordano.fg01.model;

import java.util.HashMap;

import francesco.giordano.fg01.db.J03HwSwDAO;
import francesco.giordano.fg01.db.j02SoftwareDAO;
import javafx.collections.ObservableList;

public class J03ModelHwSw {
	
	public J03ModelHwSw() {	}

	/*********************************************************************************************
	 * Restituisce le righe di software associate all'hardware corrente.
	 * Controlla se la lista esiste nella mappa...Se non esiste la leggo dal db e
	 * l'aggiungo alla mappa
	 * @param hrec
	 * @param MapHwSw
	 * @return ObservableList di tipo j02Software, la lista dei sw associati all'hw
	 */
	public ObservableList<j02Software> getRighe(Hardware hrec, HashMap<String, ObservableList<j02Software>> MapHwSw) {
		ObservableList<j02Software> obs = null;	
		String matricola = hrec.getMatricola();

		obs=(ObservableList<j02Software>) MapHwSw.get(matricola);
		if (obs == null) {	
			J03HwSwDAO dao=new J03HwSwDAO();
			obs=dao.getRigheSoftware(hrec);
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

	public void EliminaDaDB(String matricolaHardware, String codiceSw) {
		J03HwSwDAO dao=new J03HwSwDAO();
		dao.EliminaDaDB(matricolaHardware,codiceSw);		
	}
	
}
