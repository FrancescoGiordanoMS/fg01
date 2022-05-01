package francesco.giordano.fg01.model;

import java.util.HashMap;

import francesco.giordano.fg01.db.J03HwSwDAO;
import francesco.giordano.fg01.db.j02SoftwareDAO;
import javafx.collections.ObservableList;

public class J03ModelHwSw {

	private HashMap<String, ObservableList<j02Software>> MapHwSw = new HashMap<>();

	public J03ModelHwSw() {	}

	/*********************************************************************************************
	 * Restituisce le righe di software associate all'hardware corrente.
	 * Controlla se la lista esiste nella mappa...Se non esiste la leggo dal db e
	 * l'aggiungo alla mappa
	 * @param hrec
	 * @param MapHwSw
	 * @return ObservableList di tipo j02Software, la lista dei sw associati all'hw
	 */
	public ObservableList<j02Software> getRighe(String matricolaHw, HashMap<String, ObservableList<j02Software>> MapHwSw) {
		ObservableList<j02Software> obs = null;	
		//String matricola = hrec.getMatricola();
		obs=(ObservableList<j02Software>) MapHwSw.get(matricolaHw);
		if (obs == null) {	
			J03HwSwDAO dao=new J03HwSwDAO();
			obs=dao.getRigheSoftware(matricolaHw);
			MapHwSw.put(matricolaHw, obs);
		}
		return obs;
	}

	public ObservableList<j02Software> AggiornaSoftwareAssociato(String matricola) {
		MapHwSw.remove(matricola); 											// 1 : cancello dalla mappa la vecchia lista dei sw associati
		ObservableList<j02Software> obs=SelezionaRecordSoftware(matricola);	// 2 : forzo la rilettura dei sw associati
		return obs;
	}

	public ObservableList<j02Software> SelezionaRecordSoftware(String matricolaHw) {
		ObservableList<j02Software> obs=getRighe(matricolaHw,MapHwSw);
		return obs;
	}

	
	public void RegistraSuDB(String matricolaHardware, ObservableList<j02Software> obs) {
		if (obs != null) {	
			J03HwSwDAO dao=new J03HwSwDAO();
			dao.RegistraSuDB(matricolaHardware,obs);	// 1: Registro su db tutti i sw selezionati dall'utente
		}
	}

	public void EliminaDaDB(String matricolaHardware, String codiceSw) {
		J03HwSwDAO dao=new J03HwSwDAO();
		dao.EliminaDaDB(matricolaHardware,codiceSw);		
	}
	
	public void Refresh() {
		MapHwSw.clear();
		//obsSw.clear();
	}

}
