package francesco.giordano.fg01;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;

import fglib.MyController;
import francesco.giordano.fg01.model.j02Software;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.ModelHardware;
import francesco.giordano.fg01.model.j02ModelSoftware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class j02ControllerSoftware extends MyController {

	@FXML
	private TextField _mnomesw;

	@FXML
	private TableView<j02Software> TVSoftware;

	@FXML
	private TextField _mtiposw;

	@FXML
	private TableColumn<j02Software, String> col_tiposw;

	@FXML
	private TableColumn<j02Software, String> col_versione;

	@FXML
	private TextField _mversione;

	@FXML
	private TableColumn<j02Software, String> col_nomesw;

	@FXML
	private TextField _kcodice;

	@FXML
	private TableColumn<j02Software, String> col_codice;

	//--------------------------------------------------------------------------------------------------------------
	// Controlli per la selezione dei sw da aggiungere all'hardware
	//--------------------------------------------------------------------------------------------------------------    
	@FXML
	private Button btnSelezionaSw;
	@FXML
	private Button btnAnnullaSelezionaSw;
	@FXML
	private HBox HBoxButtons;
	@FXML
	private HBox HBoxDettaglio; 	
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private j02ModelSoftware model;
	private ObservableList<j02Software> obs;
	private ObservableList<j02Software> obsNuoviSoftware= FXCollections.<j02Software>observableArrayList();
	private ObservableList<j02Software> obsSoftwareAggiunti= FXCollections.<j02Software>observableArrayList();
	private HashMap<String, ObservableList<j02Software>> MapSwAssegnati = new HashMap<>();
	private String matricolaHardware;


	@Override
	protected void DeleteRecord() {
		j02Software rec = TVSoftware.getSelectionModel().getSelectedItem();
		if (model.DBDelete(rec)) {
			obs.remove(rec);
			TVSoftware.refresh();
		}
	}

	@Override
	protected void SalvaModifiche() {
		j02Software rec=null;
		Field[] allFields = this.getClass().getDeclaredFields();
		rec=(j02Software) ReadModifiedFields(TVSoftware.getSelectionModel().getSelectedItem(), allFields);
		model.DBModify(rec);
		TVSoftware.refresh();
	}

	@Override
	protected boolean SalvaInserimento() {
		j02Software rec=null;
		String msgErrore;
		Field[] allFields = this.getClass().getDeclaredFields();
		rec=(j02Software) ReadModifiedFields(new j02Software(), allFields);
		msgErrore=model.DBInsert(rec);
		if (msgErrore == null) {
			obs.add(rec);
			TVSoftware.getSelectionModel().selectLast();
			TVSoftware.refresh();
			return true;
		}
		else {
			return false;
		}
	}

	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	public void setModel(j02ModelSoftware m) {
		this.model=m;
	}

	public void popolaTableView() {
		obs=model.getRighe();
		this.TVSoftware.setItems(obs);
	}

	@FXML
	void initialize() {		
		col_codice.setCellValueFactory(new PropertyValueFactory<j02Software,String>("codice"));
		col_tiposw.setCellValueFactory(new PropertyValueFactory<j02Software,String>("tiposw"));
		col_nomesw.setCellValueFactory(new PropertyValueFactory<j02Software,String>("nomesw"));
		col_versione.setCellValueFactory(new PropertyValueFactory<j02Software,String>("versione"));

		TVSoftware.getSelectionModel().selectedItemProperty().addListener((ob, oldval, newVal) -> {
			if (newVal != null) {
				_kcodice.setText(newVal.getCodice());
				_mtiposw.setText(newVal.getTiposw());
				_mnomesw.setText(newVal.getNomesw());
				_mversione.setText(newVal.getVersione());

				indexTableView=TVSoftware.getSelectionModel().getSelectedIndex();
			}
		});

		disabilitaControlli();  // super: setFormField Class
		//HBoxButtons.setVisible(false);
		indexTableView=-1;

		allFields = this.getClass().getDeclaredFields();
		Field[] allBean = j02Software.class.getDeclaredFields();
		MapFieldValue=CreaHashMap(allFields, allBean);
		//	System.out.println(MapFieldValue+"\n");
	}

	//--------------------------------------------------------------------------------------------------------------
	// Gestione selezione aggiunta nuovi software
	//--------------------------------------------------------------------------------------------------------------	
	@FXML
	void handle_btnSelezionaSw(ActionEvent event) {
		obsNuoviSoftware.add(TVSoftware.getSelectionModel().getSelectedItem());
		
		J03ModelHwSw model = new J03ModelHwSw();	
		model.RegistraSuDB(matricolaHardware, obsNuoviSoftware);
		System.out.println("Prima del sync");
		obsSoftwareAggiunti.add(new j02Software());	// è il semaforo
		this.stage.close();
	}
	
	 @FXML
	    void handle_btnAnnullaSelezionaSw(ActionEvent event) {
			this.stage.close();
	    }

	public void HideControls() {
		myMenuBar.getMenuBar().setVisible(false);
		mybutton.setVisible(false);
		HBoxDettaglio.setVisible(false);
	}
	public ObservableList<j02Software> getControlloRecordAggiunti() {
		return obsSoftwareAggiunti;		// è la lista su cui è impostato il listener
	}
	public void setMatricolaHardware(String matricola) {
		this.matricolaHardware= matricola;
	}

	/**
	 * @param obs: lista dei software già assegnati
	 */
	public void setElementiListaDaNascondere(ObservableList<j02Software> obs) {
		for(j02Software o : obs) {
			MapSwAssegnati.put(o.getCodice(), obs);
		}
	}
	public void popolaTableViewSoftwareDaSelezionare() {
		obs=model.getRighe();
		int ix =0;
		while (ix < obs.size()) {
			if (MapSwAssegnati.get(obs.get(ix).getCodice()) != null)
				obs.remove(ix);
			else
				ix++;
		}
		this.TVSoftware.setItems(obs);
	}

}
