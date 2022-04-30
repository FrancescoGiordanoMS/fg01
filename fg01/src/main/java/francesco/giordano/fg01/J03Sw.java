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
	private TableView<j02Software> tabViewSoftware;
	private Hardware beanHardware;


	

	public J03Sw() {
	}
	
		public void btnNuoviSoftware() throws IOException {
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
		controller.setMatricolaHardware(matricola);
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

		
		public void clear() {
			obsSw.clear();
		}
	
	/***************************************************************************************************
	 * Evento che elimina l'associazione del sw all'hardware
	 */
	public void btnSganciaDaHw() {
		int index = tabViewSoftware.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			//String matricola = TVHardware.getSelectionModel().getSelectedItem().getMatricola();
			String codiceSw = obsSw.get(index).getCodice();
			//j02Software rec = new j02Software();
			modelHwSw.EliminaDaDB(matricola,codiceSw);
			// cancello il record dalla mappa
			MapHwSw.remove(matricola);
			SelezionaRecordSoftware(beanHardware);			// 2 : forzo la rilettura dei sw associati
		}
	}
	
	public void SelezionaRecordSoftware(Hardware selectedItem) {
		obsSw=modelHwSw.getRighe(selectedItem,MapHwSw);
		this.tabViewSoftware.setItems(obsSw);
		this.tabViewSoftware.refresh();
	}
	
	/**
	 *  Viene lanciato dal listener non appena sente che è stato aggiunto nuovo software all'hardware 
	 *  correntemente selezionato
	 */
	private void AggiornaSoftware() {
		MapHwSw.remove(matricola); 	// 1 : cancello dalla mappa la vecchia lista dei sw associati
		SelezionaRecordSoftware(beanHardware);			// 2 : forzo la rilettura dei sw associati
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public J03ModelHwSw getModelHwSw() {
		return modelHwSw;
	}

	public void setModelHwSw(J03ModelHwSw modelHwSw) {
		this.modelHwSw = modelHwSw;
	}

	public HashMap<String, ObservableList<j02Software>> getMapHwSw() {
		return MapHwSw;
	}

	public void setMapHwSw(HashMap<String, ObservableList<j02Software>> mapHwSw) {
		MapHwSw = mapHwSw;
	}
	
	public TableView<j02Software> getTabViewSoftware() {
		return tabViewSoftware;
	}

	public void setTabViewSoftware(TableView<j02Software> tabViewSoftware) {
		this.tabViewSoftware = tabViewSoftware;
	}

	public Hardware getBeanHardware() {
		return beanHardware;
	}

	public void setBeanHardware(Hardware beanHardware) {
		this.beanHardware = beanHardware;
	}
	
	
	
	
}
