package francesco.giordano.fg01;

import java.lang.reflect.Field;
//import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fglib.setFormFields;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Fg01ControllerHardware extends setFormFields{

//	public Fg01ControllerHardware() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	
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
	private TextField _kMatricola;

	@FXML
	private TextField _mPrezzo;

	@FXML
	private TextField _mTipoHw;

	@FXML
	private Label labelErrore;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnCancel;

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
				_kMatricola.setText(newVal.getMatricola());
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
		
		disabilitaControlli();  // super: setFormField Class
		//disableControl();
		System.out.println("Vedi lista variabili _m*");
		
//		for (Field field : privateFields) {
//			field.setReadonly(false);
//		}
//		_kMatricola.setDisable(false);
//		
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
	
	
//		private void disableControl() {
//			List<Field> privateFields = new ArrayList<>();
//			Field[] allFields = this.getClass().getDeclaredFields();
//			for (Field field : allFields) {
//			    if (Modifier.isPrivate(field.getModifiers()) && 
//			    		(field.getName().substring(0,2).equals("_m") ) ||
//			    		(field.getName().substring(0,2).equals("_k") ))
//			    		{
//			        Class<?> clazz = field.getType();
//		        	try {
//			        switch (clazz.getName()) {
//			        case "javafx.scene.control.TextField":
//						TextField tf = (TextField)field.get(this);
//						tf.setEditable(false);
//			        	break;
//			        case "javafx.scene.control.DatePicker":
//			        	DatePicker dp = (DatePicker)field.get(this);
//			        	//dp.setEditable(false);
//			        	dp.setDisable(true);
//			        	dp.setStyle("-fx-opacity: 1.0;");
//			        	dp.getEditor().setStyle("-fx-opacity: 1.0;");
//			        	break;        
//			        }
//					} catch (IllegalArgumentException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}     
//			        //privateFields.add(field);
//			        
//			        
//			    }
//			}
//		}
//		
//		private void enableControl() {
//			
//		}
}
