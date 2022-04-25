package francesco.giordano.fg01;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Fg01ControllerHardware extends MyController{

	@FXML
	private TableView<Hardware> TVHardware;
	@FXML
	private TableColumn<Hardware, String> col_modello, col_marca, col_tipohw, col_matricola;
	@FXML
	private TableColumn<Hardware, LocalDate> col_dataacquisto;
	@FXML
	private TableColumn<Hardware, Float> col_prezzoacquisto;

	@FXML
	private GridPane Grid;

	@FXML
	private TextField _kMatricola, _mMarca, _mModello, _mPrezzoacquisto, _mTipoHw;

	@FXML
	private Label labelErrore;

	@FXML
	private Button btnNuoviSoftware;

	@FXML
	private DatePicker _mDataacquisto;

	@FXML
	private MenuBar MyMenuBar;
	@FXML
	private MenuItem MenuItem_Modifica,MenuItem_Inserisci,MenuItemDelete,MenuItem_Close;

	//------------------------------------------------------------------------------------------------------
	// Tabella software
	//------------------------------------------------------------------------------------------------------
	@FXML
	private TableView<j02Software> TabViewSoftware;
	@FXML
	private TableColumn<j02Software, String> col_tiposw,col_versione,col_codice,col_nomesw;
	
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private ModelHardware model;
	private J03ModelHwSw modelHwSw=new J03ModelHwSw();
	private ObservableList<Hardware> obs;
	private ObservableList<j02Software> obsSw;
	private HashMap<String, ObservableList<j02Software>> MapHwSw = new HashMap<>();

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

//		obs.addListener((Change<? extends Hardware> c) -> {
//			System.out.println("Element at position"  );
//		});	
	}


	@FXML
	void initialize() {

		col_matricola.setCellValueFactory(new PropertyValueFactory<Hardware,String>("matricola"));
		col_tipohw.setCellValueFactory(new PropertyValueFactory<Hardware,String>("tipohw"));
		col_marca.setCellValueFactory(new PropertyValueFactory<Hardware,String>("marca"));
		col_modello.setCellValueFactory(new PropertyValueFactory<Hardware,String>("modello"));
		col_dataacquisto.setCellValueFactory(new PropertyValueFactory<Hardware,LocalDate>("dataacquisto"));
		col_prezzoacquisto.setCellValueFactory(new PropertyValueFactory<Hardware,Float>("prezzoacquisto"));

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
				//IMV.setImage(newVal.getImage());
				indexTableView=TVHardware.getSelectionModel().getSelectedIndex();
				SelezionaRecordSoftware(TVHardware.getSelectionModel().getSelectedItem());
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

	/**************************************************************************************
	 * Questo evento apre la form per la selezione dei nuovi sw da associare 
	 * all'hw correntemente selezionato
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void handle_btnNuoviSoftware(ActionEvent event) throws IOException {

		if (indexTableView ==-1) return;
		BorderPane root;
		Stage stage = new Stage();
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
