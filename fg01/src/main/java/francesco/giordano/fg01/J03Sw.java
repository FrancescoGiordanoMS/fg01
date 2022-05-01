package francesco.giordano.fg01;

import java.io.IOException;
import java.util.HashMap;

import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.j02ModelSoftware;
import francesco.giordano.fg01.model.j02Software;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class J03Sw {

	private ObservableList<j02Software> obsSw;
	private String matricola;
	private J03ModelHwSw modelHwSw=new J03ModelHwSw();
	private HashMap<String, ObservableList<j02Software>> MapHwSw = new HashMap<>();
	//private TableView<j02Software> tabViewSoftware;
	private Hardware hardwareBean;

	/************************************************************************************************
	 *  Viene lanciato dal listener non appena sente che è stato aggiunto nuovo software all'hardware 
	 *  correntemente selezionato
	 */
//	private void AggiornaSoftware() {
//		MapHwSw.remove(matricola); 				// 1 : cancello dalla mappa la vecchia lista dei sw associati
//		SelezionaRecordSoftware(hardwareBean);	// 2 : forzo la rilettura dei sw associati
//	}
	
//	public ObservableList<j02Software> SelezionaRecordSoftware(Hardware selectedItem) {
//		ObservableList<j02Software> obs=modelHwSw.getRighe(selectedItem,MapHwSw);
//		return obs;
//	}

	/***************************************************************************************************
	 * Lanciato dall' evento che elimina l'associazione del sw all'hardware
	 */
	public void btnSganciaDaHw() {
		int index = tabViewSoftware.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			String codiceSw = obsSw.get(index).getCodice();
			modelHwSw.EliminaDaDB(matricola,codiceSw);	// 1 : cancello il record dal db
			MapHwSw.remove(matricola);					// 2 : cancello il record dalla mappa
			SelezionaRecordSoftware(hardwareBean);		// 3 : forzo la rilettura dei sw associati
		}
	}
	
//	public void clear() {
//		MapHwSw.clear();
//		obsSw.clear();
//	}
//	
//	public J03ModelHwSw getModelHwSw() {
//		return modelHwSw;
//	}
//	public void setModelHwSw(J03ModelHwSw modelHwSw) {
//		this.modelHwSw = modelHwSw;
//	}
//	public TableView<j02Software> getTabViewSoftware() {
//		return tabViewSoftware;
//	}
//	public void setTabViewSoftware(TableView<j02Software> tabViewSoftware) {
//		this.tabViewSoftware = tabViewSoftware;
//	}
//	public Hardware getHardwareBean() {
//		return hardwareBean;
//	}
//	public void sethardwareBean(Hardware hardwareBean) {
//		this.matricola=hardwareBean.getMatricola();
//		this.hardwareBean = hardwareBean;
//	}
}
