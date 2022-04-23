package fglib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fglib.MyController.StatoButtonSave;
//import francesco.giordano.fg01.Fg01ControllerHardware.StatoButtonSave;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.ModelHardware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MyController {

	protected HashMap<String, RiferimentoCampi> MapFieldValue = new HashMap<>();
	protected Field[] allFields; // elenco dei campi della form
	protected int indexTableView;
	protected Scene parentScene;
	protected Stage stage;
	protected Node root;
	protected Button btnSave;
	protected Button btnCancel;
	protected StatoButtonSave ButtonSave;
	//protected MenuBar myStandardMenu; //= new MyMenuBar().StandardMenu();
	protected MyMenuBar myMenuBar = new MyMenuBar();
	protected MyButton mybutton = new MyButton();
	protected enum StatoButtonSave {
		INSERT,
		MODIFY,
		INDEFINITO
	}

//	public void setRoot(Node rt) {
//		root=rt;
//	}



	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------		
	public void setParentScene(Scene scene) {
		this.parentScene=scene;
	}
	public void setStage(Stage s) {
		this.stage=s;
	}
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------	
//	@FXML
//	void handleClose(ActionEvent event) {
//		this.stage.setScene(parentScene);
//		this.stage.show();
//	}
//	@FXML
//	void handleModifica(ActionEvent event) {
//		//int index = TVHardware.getSelectionModel().getSelectedIndex(); // indice di riga tableView correntemente selezionata
//		if (indexTableView > -1) {
//			abilitaControlli();
//			ButtonSave=StatoButtonSave.MODIFY;			
//		}
//	}
//	@FXML
//	void handleMenuItemInserisci(ActionEvent event) {
//		abilitaControlli();
//		ButtonSave=StatoButtonSave.INSERT;	
//		ClearFields(allFields);
//	}
//
//	@FXML
//	void handleMenuItemDelete(ActionEvent event) {
//		if (indexTableView > -1) {
//			DeleteRecord();
//		}
//	}

//	@FXML
//	void handlebtnCancel(ActionEvent event) {
//		disabilitaControlli();
//		ButtonSave=StatoButtonSave.INDEFINITO;  
//	}	
//
//	@FXML
//	void handlebtnSave(ActionEvent event) {
//		if (ButtonSave==StatoButtonSave.MODIFY) {
//			SalvaModifiche();
//			disabilitaControlli();
//			ButtonSave=StatoButtonSave.INDEFINITO;	 
//		}
//		if (ButtonSave==StatoButtonSave.INSERT) {
//			if (SalvaInserimento()) {
//				disabilitaControlli();
//				ButtonSave=StatoButtonSave.INDEFINITO;	 
//			}
//		}
//	}
	//------------------------------------------------------------------------------------------------------------
	// Eventi inserimento - Modifica - Cancellazione
	//------------------------------------------------------------------------------------------------------------
	public void init() {
		root=stage.getScene().getRoot();
			
		mybutton.getAnchorpane().setMaxHeight(40);
		mybutton.getAnchorpane().setMaxWidth(stage.getWidth());
		
		//if root.getStyleableNode()==
		if (root.getClass().getName().equals("javafx.scene.layout.BorderPane")) {
		((BorderPane) root).setBottom(mybutton.getAnchorpane());
		((BorderPane) root).setTop(myMenuBar.getMenuBar());
		}
		
		myMenuBar.getMenuItem_Inserisci().setOnAction((event) -> {
			abilitaControlli();
			ClearFields(allFields);
			ButtonSave=StatoButtonSave.INSERT;	
		});	
		myMenuBar.getMenuItem_Modifica().setOnAction((event) -> {
			if (indexTableView > -1) {
				abilitaControlli();
				ButtonSave=StatoButtonSave.MODIFY;			
			}
		});	
		
		
		myMenuBar.getMenuItem_Delete().setOnAction((event) -> {
			if (indexTableView > -1) {
				DeleteRecord();
			}
			ButtonSave=StatoButtonSave.INDEFINITO;	
		});	
		myMenuBar.getMenuItem_Close().setOnAction((event) -> {
			this.stage.setScene(parentScene);
			this.stage.show();
		});
		mybutton.getBtnCancel().setOnAction((event) -> {
			disabilitaControlli();
			ButtonSave=StatoButtonSave.INDEFINITO;  
		});
		mybutton.getBtnSave().setOnAction((event) -> {
			if (ButtonSave==StatoButtonSave.MODIFY) {
				SalvaModifiche();
				disabilitaControlli();
				ButtonSave=StatoButtonSave.INDEFINITO;	 
			}
			if (ButtonSave==StatoButtonSave.INSERT) {
				if (SalvaInserimento()) {
					disabilitaControlli();
					ButtonSave=StatoButtonSave.INDEFINITO;	 
				}
			}
		});
	};
	//------------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------

	protected void DeleteRecord() {}
	protected void SalvaModifiche() {}
	protected boolean SalvaInserimento() {return true;}

	protected void disabilitaControlli() {			
		myMenuBar.getMenuBar().setDisable(false);
		List<Field> privateFields = new ArrayList<>();
		Field[] allFields = this.getClass().getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("_k") ) ||
					(field.getName().substring(0,2).equals("TV") ) ||
					(field.getName().equals("MyMenuBar") ) ||
					(field.getName().equals("btnSave")) ||
					(field.getName().equals("btnCancel")) 
					){
				Class<?> clazz = field.getType();
				field.setAccessible(true);
				try {
					switch (clazz.getName()) {
					case "javafx.scene.control.TextField":
						TextField tf = (TextField)field.get(this);
						//tf.setEditable(false);
						tf.setDisable(true);
						tf.setStyle("-fx-opacity: 0.9;");
						//tf.setStyle("-fx-text-inner-color: black;");
						break;
					case "javafx.scene.control.DatePicker":
						DatePicker dp = (DatePicker)field.get(this);
						//dp.setEditable(false);
						dp.setDisable(true);
						dp.setStyle("-fx-opacity: 0.9;");
						dp.getEditor().setStyle("-fx-opacity: 1.0;");
						//dp.setStyle("-fx-text-inner-color: black;");
						break;
//					case "javafx.scene.control.Button":
//						//Button bt = (Button)field.get(this);
//						//if (bt != null) bt.setDisable(true);   // al caricamento del controller non esiste ancora...
//						if (mybutton.getBtnSave() != null) mybutton.getBtnSave().setDisable(true);
//						if (mybutton.getBtnCancel() != null) mybutton.getBtnCancel().setDisable(true);
//						break;
					case "javafx.scene.control.TableView":
						TableView tv = (TableView)field.get(this);
						tv.setDisable(false);
						break;
					case "javafx.scene.control.MenuBar":
						//MenuBar mb= (MenuBar)field.get(this);
						//mb.setDisable(false);
						if (myMenuBar != null) myMenuBar.setDisable(true);
						break;
					}
					field.setAccessible(false); 
					mybutton.getBtnSave().setDisable(true);
					mybutton.getBtnCancel().setDisable(true);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	        
			}
		}
	}

	protected void abilitaControlli() {
		myMenuBar.getMenuBar().setDisable(true);
		List<Field> privateFields = new ArrayList<>();
		Field[] allFields = this.getClass().getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("TV") ) ||
					(field.getName().equals("MyMenuBar") ) ||
					(field.getName().equals("btnSave")) ||
					(field.getName().equals("btnCancel")) 
					){
				Class<?> clazz = field.getType();
				field.setAccessible(true);
				try {
					switch (clazz.getName()) {
					case "javafx.scene.control.TextField":
						TextField tf = (TextField)field.get(this);
						tf.setDisable(false);
						tf.setStyle("-fx-opacity: 1.0;");
						//tf.setStyle("-fx-text-inner-color: blue;");
						tf.setStyle("-fx-font-weight: bold;");
						break;
					case "javafx.scene.control.DatePicker":
						DatePicker dp = (DatePicker)field.get(this);
						dp.setDisable(false);
						dp.setStyle("-fx-opacity: 1.0;");
						//dp.setStyle("-fx-text-inner-color: blue;");
						dp.setStyle("-fx-font-weight: bold;");
						break;        
//					case "javafx.scene.control.Button":
//						//Button bt = (Button)field.get(this);
//						mybutton.getBtnSave().setDisable(false);
//						mybutton.getBtnCancel().setDisable(false);
//						break;
					case "javafx.scene.control.TableView":
						TableView tv = (TableView)field.get(this);
						tv.setDisable(true);
						break;
					case "javafx.scene.control.MenuBar":
						//MenuBar mb= (MenuBar)field.get(this);
						//mb.setDisable(true);
						myMenuBar.setDisable(true);
						break;
					}
					field.setAccessible(false); 
					mybutton.getBtnSave().setDisable(false);
					mybutton.getBtnCancel().setDisable(false);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	        
			}
		}
	}

	//--------------------------------------------------------------------------------------------
	/**
	 * Legge il contenuto dei campi prima di salvarli nel  db e li registra all'interno del bean
	 * 
	 * @param 	rec=il bean dei campi da modificare
	 * @param 	allFields=l'insieme dei campi dichiarati nella form con i valori da modificare	
	 * @return	rec=il bean con i campi modificati da salvare
	 */
	public Object ReadModifiedFields(Object rec, Field[] allFields) { 
		DatePicker dp = null;
		String setMethod="", nomeField="", typeField="";
		Class<?> c = rec.getClass();
		Class[] cArg = new Class[1];
		Method m = null;
		Object rv = null, newValue=null;
		RiferimentoCampi rc;
		TextField tf = null;	

		for (Field field : allFields) {
			field.setAccessible(true);
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("_k") )){

				nomeField=field.getName().substring(2, field.getName().length());
				setMethod = "set" + nomeField.substring(0, 1).toUpperCase() + nomeField.substring(1, nomeField.length()).toLowerCase();
				rc=MapFieldValue.get(nomeField.toLowerCase());
				typeField = rc.getBeanType();

				try {			
					switch(typeField) {
					case "java.lang.String":
						cArg[0] = String.class;
						tf = (TextField)field.get(this);
						newValue=tf.getText();
						break;
					case "float":
						cArg[0] = float.class;
						tf = (TextField)field.get(this);
						if (tf.getText().equals("") || tf.getText().equals(null)) {
							tf.setText("0.0");
						}
						newValue=Float.parseFloat(tf.getText());
						break;
					case "java.time.LocalDate":
						cArg[0] = LocalDate.class;
						dp = (DatePicker)field.get(this);
						newValue=dp.getValue();
						break;
					}
					m=c.getMethod(setMethod,cArg);
					rv = m.invoke(rec, newValue);		
					rc.setValue(newValue);
					MapFieldValue.put(nomeField.toLowerCase(),rc);

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
		return rec;
	}	

	public void ClearFields(Field[] allFields) { 
		DatePicker dp = null;
		String typeField="";
		TextField tf = null;	

		for (Field field : allFields) {
			field.setAccessible(true);
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("_k") )){
				typeField = field.getType().getName();
				try {			
					switch(typeField) {
					case "javafx.scene.control.TextField":
						tf = (TextField)field.get(this);
						tf.clear();
						if(field.getName().substring(0,2).equals("_k")) {
							tf.setDisable(false);
							tf.setStyle("-fx-opacity: 1.0;");
							tf.setStyle("-fx-font-weight: bold;");
						}
						break;
					case "javafx.scene.control.DatePicker":
						dp = (DatePicker)field.get(this);
						dp.setValue(null);
						break;
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			field.setAccessible(false);
		}
	}	




	public HashMap CreaHashMap(Field[] allFields, Field[] allBean) {
		HashMap<String, RiferimentoCampi> MapFieldValue = new HashMap<>();
		String nomeFieldBean=null;
		RiferimentoCampi rc;
		for (Field field : allFields) {
			rc = new RiferimentoCampi();
			field.setAccessible(true);
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("_k") )){
				nomeFieldBean=field.getName().substring(2, field.getName().length()).toLowerCase();

				rc.setKey((field.getName().substring(0,2)));		// -k se è un campo chiave della tabella, _m altrimenti
				//rc.setBeanType(null);
				//rc.setFieldType(null);
				rc.setValue(null);
				MapFieldValue.put(nomeFieldBean, rc);
			}
		}
		Class<?> classeTipo;
		String stringTipo;
		for (int s1=0; s1 < allBean.length; s1++) {
			nomeFieldBean=allBean[s1].getName();
			classeTipo = allBean[s1].getType();
			stringTipo = classeTipo.getName();	// type della variabile del bean
			rc=MapFieldValue.get(nomeFieldBean);
			if (rc != null) {
				rc.setBeanType(stringTipo);
				MapFieldValue.put(nomeFieldBean, rc);
			}
		}
		return MapFieldValue;
	}

}
