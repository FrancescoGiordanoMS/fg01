package francesco.giordano.fg01;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import fglib.MyController;
import francesco.giordano.fg01.Fg01ControllerHardware.Azione;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.j02ModelSoftware;
import francesco.giordano.fg01.model.j02Software;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 * @author Francesco
 *
 */
public class j02ControllerSoftware extends MyController {

	@FXML private TableView<j02Software> TVSoftware;
	@FXML private TableColumn<j02Software, CheckBox> col_selezione ;
	@FXML private TableColumn<j02Software, String> col_nomesw, col_codice,col_versione, col_tiposw;
	@FXML private TextField _kcodice,_mversione,_mnomesw,_mtiposw;

	//--------------------------------------------------------------------------------------------------------------
	// Controlli per la selezione dei sw da aggiungere all'hardware
	//--------------------------------------------------------------------------------------------------------------    
	@FXML private Button btnSelezionaSw,btnAnnullaSelezionaSw;
	@FXML private HBox HBoxButtons,HBoxDettaglio;
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private j02ModelSoftware model;
	private ObservableList<j02Software> obs;
	private ObservableList<j02Software> obsNuoviSoftware= FXCollections.<j02Software>observableArrayList();
	private ObservableList<j02Software> obsSoftwareAggiunti= FXCollections.<j02Software>observableArrayList();
	//private HashMap<String, ObservableList<j02Software>> MapSwAssegnati = new HashMap<>();
	private Hardware hardwareBean;
	private String matricolaHardware;
	private Azione tipoAzione;


	@Override
	protected boolean DeleteRecord() {
		boolean ret = false;
		if (super.DeleteRecord()) {
			j02Software rec = TVSoftware.getSelectionModel().getSelectedItem();
			if (model.DBDelete(rec)) {
				obs.remove(rec);
				TVSoftware.refresh();
				ret = true;
			}
		}
		return ret;
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
		col_selezione.setCellValueFactory(new PropertyValueFactory<j02Software,CheckBox>("selezione"));

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
		col_selezione.setVisible(false);
		indexTableView=-1;

		allFields = this.getClass().getDeclaredFields();
		Field[] allBean = j02Software.class.getDeclaredFields();
		MapFieldValue=CreaHashMap(allFields, allBean);
		//	System.out.println(MapFieldValue+"\n");
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	// Gestione selezione asoociazione/dissociazione nuovi software
	//
	//--------------------------------------------------------------------------------------------------------------	
	@FXML
	void handle_btnAnnullaSelezionaSw(ActionEvent event) {
		this.stage.close();
	}
	/**************************************************************************************
	 *  Questo evento raccoglie la lista dei software selezionati e li passa al J03model
	 *  per aggiungere associare / dissociare nuovi software in funzione del tipo di azione
	 *  da eseguire
	 * @param event
	 */
	@FXML
	void handle_btnSelezionaSw(ActionEvent event) {
		obsNuoviSoftware.clear();
		obsNuoviSoftware=FXCollections.observableArrayList(SelezionaSwDaGestire());	
		J03ModelHwSw.GestisciSwSelezionato(matricolaHardware, obsNuoviSoftware, tipoAzione);		
		obsSoftwareAggiunti.add(new j02Software());	// è il semaforo
		this.stage.close();
	}	
	/**************************************************************************************
	 * Controlla quali sw hanno checkbox selezionata ed aggiunge l'elemento alla lista
	 * dei sw selezionati
	 * @return ObservableList<j02Software> - la lista dei sw selezionati
	 */
	private ObservableList<j02Software> SelezionaSwDaGestire() {
		ObservableList<j02Software> obs = FXCollections.<j02Software>observableArrayList();
		for(j02Software rec : TVSoftware.getItems()  ) {
			if (rec.getSelezione().isSelected()) obs.add(rec);
		}
		return obs;
	}
	/**************************************************************************************
	 * Nasconde i controlli standard della form quando questa viene chiamata
	 * come finestra per la sola scelta dei software da associare all'hardware
	 */
	public void HideControls() {
		myMenuBar.getMenuBar().setVisible(false);
		mybutton.setVisible(false);
		HBoxDettaglio.setVisible(false);
		col_selezione.setVisible(true);
	}
	/**************************************************************************************
	 * A questa funzione è stato aggiunto il listener per determinare il momento in cui
	 * qualcuno ha selezionato nuovo software da aggiungere all'hardware correntemente
	 * selezionato
	 * @return : ObservableList<j02Software> , la lista che serve solo da segnale per 
	 * 					indicare che è stato associato nuovo software all'hardware 
	 * 					correntemente selezionato
	 */
	public ObservableList<j02Software> getControlloRecordAggiunti() {
		return obsSoftwareAggiunti;		// è la lista su cui è impostato il listener
	}
	/**************************************************************************************
	 * Se il tipo di azione è ASSOCIA:
	 * Seleziona i soli software disponibili non ancora associati all'hardware, togliendo
	 * dall'elenco di tutti i sw disponibili quelli presenti nella mappa
	 * Se il tipo di azione è DISSOCIA:
	 * Popola la tabella con i sw attualmente associati all'hardware 
	 */
	public void popolaTableViewSoftwareDaSelezionare(String matricola, ObservableList<j02Software> obsSw, Azione tipoAz) {
		this.tipoAzione=tipoAz;
		obs=J03ModelHwSw.popolaTableViewSoftwareDaSelezionare(matricola, tipoAz==Azione.ASSOCIA?model.getRighe():obsSw, tipoAz);
		this.TVSoftware.setItems(obs);
	}


	public HBox getHBoxButtons() {
		return HBoxButtons;
	}

	public Hardware getHardwareBean() {
		return hardwareBean;
	}
	public void setHardwareBean(Hardware hardwareBean) {
		this.matricolaHardware= hardwareBean.getMatricola();
		this.hardwareBean = hardwareBean;
	}

}
