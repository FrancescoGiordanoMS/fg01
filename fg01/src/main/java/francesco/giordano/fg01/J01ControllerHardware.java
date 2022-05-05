package francesco.giordano.fg01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fglib.MyController;
import fglib.RiferimentoCampi;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.J03HwSw;
import francesco.giordano.fg01.model.J03ModelHwSw;
import francesco.giordano.fg01.model.J01ModelHardware;
import francesco.giordano.fg01.model.j02ModelSoftware;
import francesco.giordano.fg01.model.j02Software;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class J01ControllerHardware extends MyController{

	@FXML private TableView<Hardware> TVHardware;
	@FXML private TableColumn<Hardware, String> col_modello, col_marca, col_tipohw, col_matricola;
	@FXML private TableColumn<Hardware, LocalDate> col_dataacquisto;
	@FXML private TableColumn<Hardware, Float> col_prezzoacquisto;
	@FXML private TableColumn<Hardware, Integer> col_savedhashcode;

	@FXML private GridPane Grid;

	@FXML private TextField _kMatricola, _mMarca, _mModello, _mPrezzoacquisto, _mTipoHw, filterfield;

	@FXML private Label labelErrore;

	@FXML private Button btnNuoviSoftware, btnSganciaDaHw;

	@FXML private DatePicker _mDataacquisto;

	@FXML private ImageView IMV;

	@FXML private MenuBar MyMenuBar;
	@FXML private MenuItem MenuItem_Modifica,MenuItem_Inserisci,MenuItemDelete,MenuItem_Close;

	//------------------------------------------------------------------------------------------------------
	// Tabella software
	//------------------------------------------------------------------------------------------------------
	@FXML private TableView<j02Software> TabViewSoftware;
	@FXML private TableColumn<j02Software, String> col_tiposw,col_versione,col_codice,col_nomesw;

	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private J01ModelHardware model;
	private J03ModelHwSw modelHwSw=new J03ModelHwSw();
	private ObservableList<Hardware> obs;
	private ObservableList<j02Software> obsSw;
	private Azione tipoAzione;
	public enum Azione {
		ASSOCIA,
		DISSOCIA
	}

	//-----------------------------------------------------------------------------------------
	// Drag & Drop Immagini
	//-----------------------------------------------------------------------------------------
	@FXML
	void handle_DragOver(DragEvent event) {
		if (event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}
	@FXML
	void handle_DragDropped(DragEvent event) {
		List<File> files=event.getDragboard().getFiles();
		Image img;
		try {
			img = new Image(new FileInputStream(files.get(0)));
			IMV.setImage(img);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	//-----------------------------------------------------------------------------------------
	// Azioni sul database
	//-----------------------------------------------------------------------------------------	
	@Override
	protected void RefreshTableView() {
		obs.clear();
		obsSw.clear();		// pulisco la lista dei sw collegati attualmente visualizzati
		J03ModelHwSw.Refresh();	
		popolaTableView();
		TVHardware.refresh();
	}	
	/****************************************************************************************
	 * La cancellazione è eseguita dal metodo del modello J03ModelHwSw perchè è quello che
	 * gestisce l'integrità referenziale
	 */
	@Override
	protected boolean DeleteRecord() {
		boolean ret = false;
		if (super.DeleteRecord()) {		// chiedo se proseguire con la cancellazione
			Hardware rec=TVHardware.getSelectionModel().getSelectedItem();
//			if (model.DBDelete(rec)) {
			if (J03ModelHwSw.DBDeleteHwSw(rec)) {
				obs.remove(rec);
				TVHardware.refresh();
				ret=true;
			}
		}
		return ret;
	}
	@Override
	protected void SalvaModifiche() {
		Hardware rec=null;
		Field[] allFields = this.getClass().getDeclaredFields();
		rec=(Hardware) ReadModifiedFields(TVHardware.getSelectionModel().getSelectedItem(), allFields);
		rec.setImage(IMV.getImage());
		model.DBModify(rec);
		TVHardware.refresh();
	}
	@Override
	protected boolean SalvaInserimento() {
		Hardware rec=null;
		String msgErrore;
		Field[] allFields = this.getClass().getDeclaredFields();
		rec=(Hardware) ReadModifiedFields(new Hardware(), allFields);
		msgErrore=model.DBInsert(rec);
		if (msgErrore == null) {
			obs.add(rec);
			TVHardware.getSelectionModel().selectLast();
			TVHardware.refresh();
			return true;
		}
		else {
			labelErrore.setText(msgErrore);
			return false;
		}
	}

	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	public void setModel(J01ModelHardware m) {
		this.model=m;
	}

	public void popolaTableView() {
		obs=model.getRighe(MapFieldValue);
		this.TVHardware.setItems(obs);
		indexTableView=-1;
		setSearch();
	}

	@FXML
	void handle_btnNuoviSoftware(ActionEvent event) throws IOException {
		if (indexTableView ==-1) return;
		GestisciSoftwareAssociati(Azione.ASSOCIA);
	}
	/***************************************************************************************************
	 * Evento che elimina l'associazione del sw all'hardware
	 * @throws IOException 
	 */
	public void handle_btnSganciaDaHw() throws IOException {
		if (indexTableView ==-1) return;
		GestisciSoftwareAssociati(Azione.DISSOCIA);
	}

	@FXML
	void initialize() {
		col_matricola.setCellValueFactory(new PropertyValueFactory<Hardware,String>("matricola"));
		col_tipohw.setCellValueFactory(new PropertyValueFactory<Hardware,String>("tipohw"));
		col_marca.setCellValueFactory(new PropertyValueFactory<Hardware,String>("marca"));
		col_modello.setCellValueFactory(new PropertyValueFactory<Hardware,String>("modello"));
		col_dataacquisto.setCellValueFactory(new PropertyValueFactory<Hardware,LocalDate>("dataacquisto"));
		col_prezzoacquisto.setCellValueFactory(new PropertyValueFactory<Hardware,Float>("prezzoacquisto"));
		col_savedhashcode.setCellValueFactory(new PropertyValueFactory<Hardware,Integer>("savedhashcode"));

		col_codice.setCellValueFactory(new PropertyValueFactory<j02Software,String>("codice"));
		col_tiposw.setCellValueFactory(new PropertyValueFactory<j02Software,String>("tiposw"));
		col_nomesw.setCellValueFactory(new PropertyValueFactory<j02Software,String>("nomesw"));
		col_versione.setCellValueFactory(new PropertyValueFactory<j02Software,String>("versione"));

		TVHardware.getSelectionModel().selectedItemProperty().addListener((ob, oldval, newVal) -> {
			if (newVal != null) {
				_kMatricola.setText(newVal.getMatricola());
				_mTipoHw.setText(newVal.getTipohw());
				_mMarca.setText(newVal.getMarca());
				_mModello.setText(newVal.getModello());
				_mPrezzoacquisto.setText(String.valueOf(newVal.getPrezzoacquisto()));
				_mDataacquisto.setValue(newVal.getDataacquisto());
				IMV.setImage(newVal.getImage());
				indexTableView=TVHardware.getSelectionModel().getSelectedIndex();
				SelezionaRecordSoftware();		// seleziono i sw da visualizzare relativi all'hw corrente
			}
		});
		
//		_kMatricola.setMaxlength(10);
//		
//		_mTipoHw.setText(newVal.getTipohw());
//		_mMarca.setText(newVal.getMarca());
//		_mModello.setText(newVal.getModello());
//		_mPrezzoacquisto.setText(String.valueOf(newVal.getPrezzoacquisto()));
//		_mDataacquisto.setValue(newVal.getDataacquisto());

		//------------------------------------------------------------------------------
		// addListener ha come parametro una interfaccia ChangeListener che ha
		// al suo interno un solo abstract method: 
		// void changed(ObservableValue<? extends T> observable, T oldValue, T newValue);
		// Posso quindi usare l'espressione lambda...	
		_mPrezzoacquisto.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
				_mPrezzoacquisto.setText(oldValue);	
			};
		});		
		disabilitaControlli();  // super: setFormField Class
		indexTableView=-1;

		allFields = this.getClass().getDeclaredFields();
		Field[] allBean = Hardware.class.getDeclaredFields();
		MapFieldValue=CreaHashMap(allFields, allBean);

		//------------------------------------------------------------------------------

		// vedi http://dgimenes.com/blog/2014/03/06/javafx-formatting-data-in-tableview.html
		// Questo è il primo modo per formattare la colonna data
		//col_FormattedDate.setCellValueFactory(new FormattedDateValueFactory<Sighting>("FormattedDate","MM/dd/yyyy"));
		//Bindings.bindBidirectional(TFCity.textProperty(), TFCity1.textProperty());

		// Questo è il secondo modo che mi pare meno contorto... vedi formattazione date
		//	col_FormattedDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sighting, String>, ObservableValue<String>>() {
		//		public ObservableValue<String> call(TableColumn.CellDataFeatures<Sighting, String> p) {
		//			StringProperty str = p.getValue().FormattedDateProperty();
		//			return str;
		//		}
		//	});

	}    

	/**
	 * Il metodo è richiamato dal listener ad ogni aggiornamento della lista dei sw associati all'hw. 
	 * Permette di tenere aggiornato per ogni hw l'elenco dei sw che gli sono associati
	 */
	private void AggiornaSoftware() {
		obsSw=J03ModelHwSw.AggiornaSoftwareAssociato(obs.get(indexTableView).getMatricola());
		this.TabViewSoftware.setItems(obsSw);
		this.TabViewSoftware.refresh();
	}
	/**
	 * Il metodo è richiamato dal listener ad ogni cambio di riga nella tabella dell'hw. 
	 * Permette di visualizzare per ogni hw l'elenco dei sw che gli sono associati
	 */	
	private void SelezionaRecordSoftware() {
		obsSw=J03ModelHwSw.SelezionaRecordSoftware(obs.get(indexTableView).getMatricola());
		this.TabViewSoftware.setItems(obsSw);
		this.TabViewSoftware.refresh();
	}

	private void GestisciSoftwareAssociati(Azione tipoAzione) throws IOException {
		String matricola = obs.get(indexTableView).getMatricola();
		BorderPane root;
		Stage stage = new Stage();
		stage.setWidth(560); stage.setHeight(430);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/j02_software.fxml")) ;
		root = loader.load();
		j02ControllerSoftware controllerJ02 = loader.getController() ;
		j02ModelSoftware model = new j02ModelSoftware();

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");        
		stage.setTitle("Elenco dei software disponibili");
		stage.setScene(scene);

		controllerJ02.setStage(stage);
		controllerJ02.setModel(model);  
		controllerJ02.popolaTableViewSoftwareDaSelezionare(matricola,obsSw,tipoAzione);	// in caso di aggiunta di nuovi sw
		controllerJ02.setHardwareBean(obs.get(indexTableView));
		controllerJ02.init();
		controllerJ02.HideControls();
		// Questo listener controlla se la lista dei software legati all'hardware è stata variata
		// .addListener vuole come param l'iterfaccia ListChangeListener<E> che ha al suo interno un solo
		// metodo: void onChanged(ListChangeListener.Change<? extends E> c)
		controllerJ02.getControlloRecordAggiunti().addListener((ListChangeListener.Change<? extends j02Software> c) -> {
			AggiornaSoftware();
		});	
		stage.show();
	}


	/*************************************************************************************************
	 * Metodo per implementazione ricerca in tableview
	 */
	private void setSearch() {
		// Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Hardware> filteredData = new FilteredList<>(obs, b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterfield.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(hardware -> {
				// If filter text is empty, display all records						
				if (newValue == null || newValue.isEmpty()) return true;
				String lowerCaseFilter = newValue.toLowerCase();

				if (hardware.getMatricola().toLowerCase().indexOf(lowerCaseFilter) != -1 ) return true; // Filter matches first name.
				else if (hardware.getModello().toLowerCase().indexOf(lowerCaseFilter) != -1) return true; // Filter matches last name.
				else if (String.valueOf(hardware.getPrezzoacquisto()).indexOf(lowerCaseFilter)!=-1) return true;
				else return false; // Does not match.
			});
		});
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Hardware> sortedData = new SortedList<>(filteredData);
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(TVHardware.comparatorProperty());	
		// 5. Add sorted (and filtered) data to the table.
		TVHardware.setItems(sortedData);
	}	

}
