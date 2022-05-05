package francesco.giordano.fg01.model;

import java.util.HashMap;
import java.util.Map;

import francesco.giordano.fg01.db.J01HardwareDAO;
import francesco.giordano.fg01.db.J03HwSwDAO;
import francesco.giordano.fg01.db.j02SoftwareDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import francesco.giordano.fg01.J01ControllerHardware.Azione;


public class J03ModelHwSw {

	private static HashMap<String, ObservableList<j02Software>> MapHwSw = new HashMap<>();

	/*********************************************************************************************
	 * Restituisce le righe di software associate all'hardware corrente.
	 * Controlla se la lista esiste nella mappa...Se non esiste la leggo dal db e
	 * l'aggiungo alla mappa
	 * @param hrec
	 * @param MapHwSw
	 * @return ObservableList di tipo j02Software, la lista dei sw associati all'hw
	 */
	public static ObservableList<j02Software> getRighe(String matricolaHw) { //, HashMap<String, ObservableList<j02Software>> MapHwSw) {
		ObservableList<j02Software> obs = null;	
		obs=MapHwSw.get(matricolaHw);
		if (obs == null) {	
			//J03HwSwDAO dao=new J03HwSwDAO();
			obs=J03HwSwDAO.getRigheSoftware(matricolaHw);
			MapHwSw.put(matricolaHw, obs);
		}
		return obs;
	}

	/*************************************************************************************************
	 * @param matricolaHw	E' la matricola hw cui deve essere associato il sw 
	 * @param obsAssociato	E' la lista dei sw selezionati per l'associazione/dissociazione
	 * @param tipoAzione	E' il tipo di azione: ASSOCIA / DISSOCIA
	 */
	public static void GestisciSwSelezionato(String matricolaHw, ObservableList<j02Software> obsAssociato, Azione tipoAzione) {
		if (tipoAzione==Azione.ASSOCIA) {
			RegistraSuDB(matricolaHw, obsAssociato);
			System.out.println("Prima del sync (classe j02ControllerSoftware)");
		} else if (tipoAzione==Azione.DISSOCIA) {
			EliminaDaDB(matricolaHw,obsAssociato);	// 1 : cancello il record dal db
		}
	}

	public static void RegistraSuDB(String matricolaHardware, ObservableList<j02Software> obs) {
		if (obs != null) {	
			J03HwSwDAO.RegistraSuDB(matricolaHardware,obs);	// 1: Registro su db tutti i sw selezionati dall'utente
		}
	}

	/*********************************************************************************************
	 * Cancella tutti i record della tabella HwSw con riferimento alla matricola passata come param
	 * @param matricolaHardware
	 * @param obs
	 */
	public static void EliminaDaDB(String matricolaHardware, ObservableList<j02Software> obs) {
		if (obs == null)
			J03HwSwDAO.EliminaDaDB(matricolaHardware,null);		// cancello tutti i record relativi a matricola di HwSw
		else {
			for(j02Software o : obs) {
				J03HwSwDAO.EliminaDaDB(matricolaHardware,o.getCodice());
			}
		}
		MapHwSw.remove(matricolaHardware);
	}

	/**************************************************************************************************
	 * Il metodo restituisce la lista dei sw stessa se si tratta di dissociare sw, 
	 * altrimenti restituisce la lista di tutti i sw non ancora associati con la matricola hw
	 * @param matricola		Matricola cui sono associati i sw
	 * @param tipoAzione	ASSOCIA / DISSOCIA
	 * @param obs			Elenco dei sw già associati
	 * @return
	 */
	public static ObservableList<j02Software> popolaTableViewSoftwareDaSelezionare
	(String matricola, ObservableList<j02Software> obs, Azione tipoAzione) {
		if (tipoAzione==Azione.DISSOCIA) return obs;		// se si tratta di togliare associazione sw/hw, non devo fare niente

		HashMap<String,String> mapCodiciSw=new HashMap<String,String>();
		String codiceSw;		
		int ix =0;
		while (ix < MapHwSw.get(matricola).size()) {
			codiceSw=MapHwSw.get(matricola).get(ix).getCodice();
			mapCodiciSw.put(codiceSw, codiceSw);
			ix++;
		}		
		ix =0;
		while (ix < obs.size()) {
			if (mapCodiciSw.get(obs.get(ix).getCodice()) != null)
				obs.remove(ix);
			else
				ix++;
		}
		return obs;
	}

	public static ObservableList<j02Software> AggiornaSoftwareAssociato(String matricola) {
		MapHwSw.remove(matricola); 											// 1 : cancello dalla mappa la vecchia lista dei sw associati
		ObservableList<j02Software> obs=SelezionaRecordSoftware(matricola);	// 2 : forzo la rilettura dei sw associati
		return obs;
	}

	public static ObservableList<j02Software> SelezionaRecordSoftware(String matricolaHw) {
		ObservableList<j02Software> obs=getRighe(matricolaHw); //,MapHwSw);
		return obs;
	}

	
	public static void Refresh() {
		MapHwSw.clear();
	}

	public static void EliminaHwSW(String matricolaHardware) {
		MapHwSw.remove(matricolaHardware);
	}
	
	/*******************************************************************************************
	 * Il metodo viene lanciato dalla cancellazione di un record della tabella Hardware perchè
	 * è quello che gestisce l'integrità referenziale del db
	 * 
	 * @param Record
	 * @return
	 */
	public static boolean DBDeleteHwSw(Hardware Record) {
		boolean ret = true;
		ret = J03HwSwDAO.DBDeleteHwSw(Record);
		if (ret) MapHwSw.remove(Record.getMatricola());
		return ret;
	}
	
}
