package francesco.giordano.fg01;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import fglib.MyController;
import francesco.giordano.fg01.J01ControllerHardware.Azione;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.J04ModelUsers;
import francesco.giordano.fg01.model.J04Users;
import francesco.giordano.fg01.model.J04Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import fglib.TextFieldLimited;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 * @author Francesco
 *
 */
public class J04ControllerUsers extends MyController {

	@FXML private TableView<J04Users> TVUsers;
    @FXML private TextField _kcodiceUser,_memail,_mpassword,_musername;
    @FXML private TableColumn<J04Users, String> col_codiceUser,col_email,col_password,col_username;

	//--------------------------------------------------------------------------------------------------------------
	// Controlli per la selezione dei sw da aggiungere all'hardware
	//--------------------------------------------------------------------------------------------------------------    
	@FXML private Button btnSelezionaSw,btnAnnullaSelezionaSw;
	@FXML private HBox HBoxButtons,HBoxDettaglio;
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private J04ModelUsers model;
	private ObservableList<J04Users> obs;
	private ObservableList<J04Users> obsNuoviUsers= FXCollections.<J04Users>observableArrayList();
	private ObservableList<J04Users> obsUsersAggiunti= FXCollections.<J04Users>observableArrayList();
//	private Hardware hardwareBean;
//	private String matricolaHardware;
//	private Azione tipoAzione;


	@Override
	protected boolean DeleteRecord() {
		boolean ret = false;
		if (super.DeleteRecord()) {
			J04Users rec = TVUsers.getSelectionModel().getSelectedItem();
			if (model.DBDelete(rec)) {
				obs.remove(rec);
				TVUsers.refresh();
				ret = true;
			}
		}
		return ret;
	}

	@Override
	protected void SalvaModifiche() {
		J04Users rec=null;
		Field[] allFields = this.getClass().getDeclaredFields();
		rec=(J04Users) ReadModifiedFields(TVUsers.getSelectionModel().getSelectedItem(), allFields);
		model.DBModify(rec);
		TVUsers.refresh();
	}

	@Override
	protected boolean SalvaInserimento() {
		J04Users rec=null;
		String msgErrore;
		Field[] allFields = this.getClass().getDeclaredFields();
		rec=(J04Users) ReadModifiedFields(new J04Users(), allFields);
		msgErrore=model.DBInsert(rec);
		if (msgErrore == null) {
			obs.add(rec);
			TVUsers.getSelectionModel().selectLast();
			TVUsers.refresh();
			return true;
		}
		else {
			return false;
		}
	}

	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	public void setModel(J04ModelUsers m) {
		this.model=m;
	}

	public void popolaTableView() {
		obs=model.getRighe();
		this.TVUsers.setItems(obs);
	}

	@FXML
	void initialize() {		
		col_codiceUser.setCellValueFactory(new PropertyValueFactory<J04Users,String>("codiceUser"));
		col_username.setCellValueFactory(new PropertyValueFactory<J04Users,String>("username"));
		col_email.setCellValueFactory(new PropertyValueFactory<J04Users,String>("email"));
		col_password.setCellValueFactory(new PropertyValueFactory<J04Users,String>("password"));

		TVUsers.getSelectionModel().selectedItemProperty().addListener((ob, oldval, newVal) -> {
			if (newVal != null) {
				_kcodiceUser.setText(String.valueOf(newVal.getCodiceUser()));
				_musername.setText(newVal.getUsername());
				_memail.setText(newVal.getEmail());
				_mpassword.setText(newVal.getPassword());

				indexTableView=TVUsers.getSelectionModel().getSelectedIndex();
			}
		});

//		_mversione.setMaxlength(10); _mversione.UpperCase(true);
//		_kcodice.setMaxlength(10); _kcodice.UpperCase(true);
//		_mtiposw.setMaxlength(45); _mtiposw.UpperCase(true);
//		_mnomesw.setMaxlength(45); _mnomesw.UpperCase(true);
		
		disabilitaControlli();  // super: setFormField Class
//		col_selezione.setVisible(false);
		indexTableView=-1;

//		allFields = this.getClass().getDeclaredFields();
//		Field[] allBean = J04Users.class.getDeclaredFields();
//		MapFieldValue=CreaHashMap(allFields, allBean);
		//	System.out.println(MapFieldValue+"\n"); 
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	// Gestione selezione asoociazione/dissociazione nuovi Users
	//
	//--------------------------------------------------------------------------------------------------------------	
//	@FXML
//	void handle_btnAnnullaSelezionaSw(ActionEvent event) {
//		this.stage.close();
//	}
//	/**************************************************************************************
//	 *  Questo evento raccoglie la lista dei Users selezionati e li passa al J03model
//	 *  per aggiungere associare / dissociare nuovi Users in funzione del tipo di azione
//	 *  da eseguire
//	 * @param event
//	 */
//	@FXML
//	void handle_btnSelezionaSw(ActionEvent event) {
//		obsNuoviUsers.clear();
//		obsNuoviUsers=FXCollections.observableArrayList(SelezionaSwDaGestire());	
//		J03ModelHwSw.GestisciSwSelezionato(matricolaHardware, obsNuoviUsers, tipoAzione);		
//		obsUsersAggiunti.add(new J04Users());	// è il semaforo
//		this.stage.close();
//	}	
//	/**************************************************************************************
//	 * Controlla quali sw hanno checkbox selezionata ed aggiunge l'elemento alla lista
//	 * dei sw selezionati
//	 * @return ObservableList<j02Users> - la lista dei sw selezionati
//	 */
//	private ObservableList<J04Users> SelezionaSwDaGestire() {
//		ObservableList<J04Users> obs = FXCollections.<J04Users>observableArrayList();
//		for(J04Users rec : TVUsers.getItems()  ) {
//			if (rec.getSelezione().isSelected()) obs.add(rec);
//		}
//		return obs;
//	}
//	/**************************************************************************************
//	 * Nasconde i controlli standard della form quando questa viene chiamata
//	 * come finestra per la sola scelta dei Users da associare all'hardware
//	 */
//	public void HideControls() {
//		myMenuBar.getMenuBar().setVisible(false);
//		mybutton.setVisible(false);
//		HBoxDettaglio.setVisible(false);
//		col_selezione.setVisible(true);
//	}
//	/**************************************************************************************
//	 * A questa funzione è stato aggiunto il listener per determinare il momento in cui
//	 * qualcuno ha selezionato nuovo Users da aggiungere all'hardware correntemente
//	 * selezionato
//	 * @return : ObservableList<j02Users> , la lista che serve solo da segnale per 
//	 * 					indicare che è stato associato nuovo Users all'hardware 
//	 * 					correntemente selezionato
//	 */
//	public ObservableList<J04Users> getControlloRecordAggiunti() {
//		return obsUsersAggiunti;		// è la lista su cui è impostato il listener
//	}
//	/**************************************************************************************
//	 * Se il tipo di azione è ASSOCIA:
//	 * Seleziona i soli Users disponibili non ancora associati all'hardware, togliendo
//	 * dall'elenco di tutti i sw disponibili quelli presenti nella mappa
//	 * Se il tipo di azione è DISSOCIA:
//	 * Popola la tabella con i sw attualmente associati all'hardware 
//	 */
//	public void popolaTableViewUsersDaSelezionare(String matricola, ObservableList<J04Users> obsSw, Azione tipoAz) {
//		this.tipoAzione=tipoAz;
//		obs=J03ModelHwSw.popolaTableViewUsersDaSelezionare(matricola, tipoAz==Azione.ASSOCIA?model.getRighe():obsSw, tipoAz);
//		this.TVUsers.setItems(obs);
//	}
//
//
//	public HBox getHBoxButtons() {
//		return HBoxButtons;
//	}
//
//	public Hardware getHardwareBean() {
//		return hardwareBean;
//	}
//	public void setHardwareBean(Hardware hardwareBean) {
//		this.matricolaHardware= hardwareBean.getMatricola();
//		this.hardwareBean = hardwareBean;
//	}

}
