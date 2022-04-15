package francesco.giordano.fg01;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.ModelHardware;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Fg01ControllerHardware {

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
	private GridPane Grid;

	@FXML
	private TextField _mMarca;

	@FXML
	private TextField _mModello;

	@FXML
	private TextField _mMatricola;

	@FXML
	private TextField _mPrezzo;

	@FXML
	private TextField _mTipoHw;

	@FXML
	private DatePicker _mDataAcquisto;

	@FXML
	private MenuItem MenuItem_Modifica;

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

	@FXML
	void handleModifica(ActionEvent event) {
		int index = TVHardware.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			Hardware rec = TVHardware.getSelectionModel().getSelectedItem();
			rec.setModello(_mModello.getText());
			rec.setTipohw(_mTipoHw.getText());
			rec.setMarca(_mMarca.getText());
			rec.setPrezzoacquisto(Float.valueOf(_mPrezzo.getText()));
			rec.setDataacquisto(_mDataAcquisto.getValue());
			//rec.setImage(IMV.getImage());
			model.DBModify(rec);
			TVHardware.refresh();
		}
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

		TVHardware.getSelectionModel().selectedItemProperty().addListener((ob, oldval, newVal) -> {
			if (newVal != null) {
				_mMatricola.setText(newVal.getMatricola());
				_mTipoHw.setText(newVal.getTipohw());
				_mMarca.setText(newVal.getMarca());
				_mModello.setText(newVal.getModello());
				_mPrezzo.setText(String.valueOf(newVal.getPrezzoacquisto()));
				_mDataAcquisto.setValue(newVal.getDataacquisto());
				//IMV.setImage(newVal.getImage());
			}
		});
		//------------------------------------------------------------------------------
		// addListener ha come parametro una interfaccia ChangeListener che ha
		// al suo interno un solo abstract method: 
		// void changed(ObservableValue<? extends T> observable, T oldValue, T newValue);
		// Posso quindi usare l'espressione lambda...	
		_mPrezzo.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
				_mPrezzo.setText(oldValue);	
			};
		});
		
		List<Field> privateFields = new ArrayList<>();
		Field[] allFields = this.getClass().getDeclaredFields();
		for (Field field : allFields) {
		    if (Modifier.isPrivate(field.getModifiers()) && field.getName().substring(0,2).equals("_m") ) {
		        privateFields.add(field);
		    }
		}
		System.out.println("Vedi lista variabili _m*");
		
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
}
