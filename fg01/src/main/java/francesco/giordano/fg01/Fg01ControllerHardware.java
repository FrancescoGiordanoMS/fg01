package francesco.giordano.fg01;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fglib.setFormFields;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.ModelHardware;
//import it.polito.tdp.ufo.FXMLController.StatoButtonSave;
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
	private TextField _mPrezzoacquisto;

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

	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------

	//	private ObservableList<Hardware> obs = FXCollections.observableArrayList();

	private ModelHardware model;
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	//	@FXML
	//	void handleClose(ActionEvent event) {
	//		this.stage.setScene(parentScene);
	//		this.stage.show();
	//	}

	@Override
	protected void SalvaModifiche() {
		Hardware rec=null;
		int index = TVHardware.getSelectionModel().getSelectedIndex(); // indice di riga tableView correntemente selezionata
		rec=(Hardware)test(index, TVHardware.getSelectionModel().getSelectedItem());
		model.DBModify(rec);
		TVHardware.refresh();


		//		int index = TVHardware.getSelectionModel().getSelectedIndex();
		//		if (index >= 0) {
		//			Hardware rec = TVHardware.getSelectionModel().getSelectedItem();
		//			rec.setModello(_mModello.getText());
		//			rec.setTipohw(_mTipoHw.getText());
		//			rec.setMarca(_mMarca.getText());
		//			rec.setPrezzoacquisto(Float.valueOf(_mPrezzoacquisto.getText()));
		//			rec.setDataacquisto(_mDataAcquisto.getValue());
		//			//rec.setImage(IMV.getImage());
		//			model.DBModify(rec);
		//			TVHardware.refresh();
		//		}		
	}

	/**
	 * @param index - Indice di riga della tableView correntemente selezionata
	 * @param rec	- Il record della classe bean interessata in cui ci sono i valori da modificare prima delle modifiche utente
	 * @return		- Il record della classe bean interessata in cui ci sono i valori modificati da salvare
	 */
	protected Object test(int index, Object rec) {
		//Hardware rec = null;
		DatePicker dp = null;
		String setMethod="", nomeField="", typeField="",nomeFieldBean="";
		if (index >= 0) {
//			rec = TVHardware.getSelectionModel().getSelectedItem();
			Class<?> c = rec.getClass();
			Class[] cArg = new Class[1];
			Method m = null;
			Object rv = null;
			TextField tf = null;
			List<Field> privateFields = new ArrayList<>();
			Field[] allFields = this.getClass().getDeclaredFields();
			for (Field field : allFields) {
				field.setAccessible(true);
				if (Modifier.isPrivate(field.getModifiers()) && 
						(field.getName().substring(0,2).equals("_m") ) ||
						(field.getName().substring(0,2).equals("_k") )){

					nomeField=field.getName();
					nomeFieldBean = nomeField.substring(2, nomeField.length()).toLowerCase();
					setMethod = "set" + nomeField.substring(2, 3).toUpperCase() + nomeField.substring(3, nomeField.length()).toLowerCase();

					Field declaredFieldBean;
					try {
						declaredFieldBean = rec.getClass().getDeclaredField(nomeFieldBean);
						typeField = declaredFieldBean.getType().getTypeName();
					} catch (NoSuchFieldException e1) {
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					}							

					try {			
						switch(typeField) {
						case "java.lang.String":
							cArg[0] = String.class;
							tf = (TextField)field.get(this);
							m=c.getMethod(setMethod,cArg);
							rv = m.invoke(rec, tf.getText());		
							break;
						case "float":
							cArg[0] = float.class;
							tf = (TextField)field.get(this);
							m=c.getMethod(setMethod,cArg);
							rv = m.invoke(rec, Float.parseFloat(tf.getText()));		
							break;
						case "java.time.LocalDate":
							cArg[0] = LocalDate.class;
							dp = (DatePicker)field.get(this);
							m=c.getMethod(setMethod,cArg);
							rv = m.invoke(rec, dp.getValue());		
							break;
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				field.setAccessible(false);
			}

		}
		return rec;
	}













	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
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
				_mPrezzoacquisto.setText(String.valueOf(newVal.getPrezzoacquisto()));
				_mDataAcquisto.setValue(newVal.getDataacquisto());
				//IMV.setImage(newVal.getImage());
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
