package francesco.giordano.fg01;

import java.lang.reflect.Field;
import java.time.LocalDate;

import fglib.MyController;
import francesco.giordano.fg01.model.j02Software;
import francesco.giordano.fg01.model.ModelHardware;
import francesco.giordano.fg01.model.j02ModelSoftware;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    @FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;
	
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private j02ModelSoftware model;
	private ObservableList<j02Software> obs;

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
		obs=model.getRighe(MapFieldValue);
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
		indexTableView=-1;

		allFields = this.getClass().getDeclaredFields();
		Field[] allBean = j02Software.class.getDeclaredFields();
		MapFieldValue=CreaHashMap(allFields, allBean);
	//	System.out.println(MapFieldValue+"\n");

	}

}
