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
import francesco.giordano.fg01.model.ModelHardware;
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

public class Fg01ControllerHardware extends MyController{

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
	private ModelHardware model;
	private J03ModelHwSw modelHwSw=new J03ModelHwSw();
	private ObservableList<Hardware> obs;
	private J03Sw j03Sw = new J03Sw();

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
		j03Sw.clear();		// pulisco la lista dei sw collegati
		popolaTableView();
		TVHardware.refresh();
	}	
	@Override
	protected void DeleteRecord() {
		Hardware rec=TVHardware.getSelectionModel().getSelectedItem();
		if (model.DBDelete(rec)) {
			obs.remove(rec);
			TVHardware.refresh();
		}
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
	public void setModel(ModelHardware m) {
		this.model=m;
	}

	public void popolaTableView() {
		obs=model.getRighe(MapFieldValue);
		this.TVHardware.setItems(obs);
		setSearch();
	}

	@FXML
	void handle_btnNuoviSoftware(ActionEvent event) throws IOException {
		if (indexTableView ==-1) return;
		j03Sw.setBeanHardware(TVHardware.getSelectionModel().getSelectedItem());
		j03Sw.btnNuoviSoftware();
	}

	/***************************************************************************************************
	 * Evento che elimina l'associazione del sw all'hardware
	 */
	public void handle_btnSganciaDaHw() {
		j03Sw.setBeanHardware(TVHardware.getSelectionModel().getSelectedItem());
		j03Sw.btnSganciaDaHw();
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
				j03Sw.SelezionaRecordSoftware(TVHardware.getSelectionModel().getSelectedItem());
			}
		});
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

		j03Sw.setTabViewSoftware(TabViewSoftware);
		j03Sw.setModelHwSw(modelHwSw);

		allFields = this.getClass().getDeclaredFields();
		Field[] allBean = Hardware.class.getDeclaredFields();
		MapFieldValue=CreaHashMap(allFields, allBean);
		//	System.out.println(MapFieldValue+"\n");


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
