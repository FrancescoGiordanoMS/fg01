package francesco.giordano.fg01;

import java.time.LocalDate;

import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.ModelHardware;
import it.polito.tdp.ufo.model.Sighting;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Fg01ControllerHardware {

    @FXML
    private TextField _mModello;


    @FXML
    private TableView<Hardware> TVHardware;

    @FXML
    private TableColumn<Hardware, String> col_tipohw;

    @FXML
    private TableColumn<Hardware, String> col_modello;

    @FXML
    private TableColumn<Hardware, String> col_marca;

    @FXML
    private TableColumn<Hardware, LocalDate> col_dataacquisto;

    @FXML
    private TableColumn<Hardware, String> col_matricola;
    
    @FXML
    private TableColumn<Hardware, Float> col_prezzoacquisto;

    @FXML
    private TextField _mMarca;

    @FXML
    private TextField _mMatricola;

    @FXML
    private TextField _mPrezzo;

    @FXML
    private TextField _mTipoHw;

    @FXML
    private DatePicker _mDataAcquisto;

 
    @FXML
    private MenuItem MenuItem_Close;
    
    private Scene parentScene;
	private Stage stage;
	private ObservableList<Hardware> obs = FXCollections.observableArrayList();
	private ModelHardware model;

    
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

@FXML
void handleClose(ActionEvent event) {
	this.stage.setScene(parentScene);
	this.stage.show();
}

//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
public void setParentScene(Scene scene) {
	this.parentScene=scene;
}
public void setStage(Stage s) {
	this.stage=s;
}

public void setModel(ModelHardware m) {
	this.model=m;
}
public void setTableView() {
	ObservableList<Hardware> obs;
	obs=model.getRighe();
	this.TVHardware.setItems(obs);
}

@FXML
void initialize() {

	col_matricola.setCellValueFactory(new PropertyValueFactory<Hardware,String>("matricola"));
	col_tipohw.setCellValueFactory(new PropertyValueFactory<Hardware,String>("tipohw"));
	col_marca.setCellValueFactory(new PropertyValueFactory<Hardware,String>("marca"));
	col_modello.setCellValueFactory(new PropertyValueFactory<Hardware,String>("modello"));
	col_dataacquisto.setCellValueFactory(new PropertyValueFactory<Hardware,LocalDate>("dataacquisto"));
	col_prezzoacquisto.setCellValueFactory(new PropertyValueFactory<Hardware,Float>("prezzoacquisto"));

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
}
