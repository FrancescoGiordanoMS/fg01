package francesco.giordano.fg01.db;

import francesco.giordano.fg01.j02ControllerSoftware;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.j02ModelSoftware;
import francesco.giordano.fg01.model.j02Software;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class J03Sw {

	public J03Sw() {
		// TODO Auto-generated constructor stub
		BorderPane root;
		Stage stage = new Stage();
		stage.setWidth(560);
		stage.setHeight(430);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/j02_software.fxml")) ;
		root = loader.load();
		j02ControllerSoftware controller = loader.getController() ;
		j02ModelSoftware model = new j02ModelSoftware();

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");        
		stage.setTitle("Elenco dei software disponibili");
		stage.setScene(scene);

		controller.setStage(stage);
		controller.setModel(model);  
		controller.setElementiListaDaNascondere(obsSw);
		controller.popolaTableViewSoftwareDaSelezionare();
		controller.setMatricolaHardware(TVHardware.getSelectionModel().getSelectedItem().getMatricola());
		controller.init();
		controller.HideControls();
		// Questo listener controlla se la lista dei software legati all'hardware è stata variata
		// .addListener vuole come param l'iterfaccia ListChangeListener<E> che ha al suo interno un solo
		// metodo: void onChanged(ListChangeListener.Change<? extends E> c)
		controller.getControlloRecordAggiunti().addListener((ListChangeListener.Change<? extends j02Software> c) -> {
			AggiornaSoftware();
		});	
		stage.show();
	}

	
	/***************************************************************************************************
	 * Evento che elimina l'associazione del sw all'hardware
	 */
	public void handle_btnSganciaDaHw() {
		int index = TabViewSoftware.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			String matricola = TVHardware.getSelectionModel().getSelectedItem().getMatricola();
			String codiceSw = obsSw.get(index).getCodice();
			//j02Software rec = new j02Software();
			modelHwSw.EliminaDaDB(matricola,codiceSw);
			// cancello il record dalla mappa
			MapHwSw.remove(matricola);
			SelezionaRecordSoftware(TVHardware.getSelectionModel().getSelectedItem());			// 2 : forzo la rilettura dei sw associati
		}
	}
	
	private void SelezionaRecordSoftware(Hardware selectedItem) {
		//if (obsSw != null) obsSw.clear();
		obsSw=modelHwSw.getRighe(selectedItem,MapHwSw);
		this.TabViewSoftware.setItems(obsSw);
		this.TabViewSoftware.refresh();
	}
	
	/**
	 *  Viene lanciato dal listener non appena sente che è stato aggiunto nuovo software all'hardware 
	 *  correntemente selezionato
	 */
	private void AggiornaSoftware() {
		MapHwSw.remove(TVHardware.getSelectionModel().getSelectedItem().getMatricola()); 	// 1 : cancello dalla mappa la vecchia lista dei sw associati
		SelezionaRecordSoftware(TVHardware.getSelectionModel().getSelectedItem());			// 2 : forzo la rilettura dei sw associati
	}
	
	
	
	
	
	
}
