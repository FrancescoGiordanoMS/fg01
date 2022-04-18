package fglib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fglib.setFormFields.StatoButtonSave;
//import francesco.giordano.fg01.Fg01ControllerHardware.StatoButtonSave;
import francesco.giordano.fg01.model.Hardware;
import francesco.giordano.fg01.model.ModelHardware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class setFormFields {

	protected int indexTableView;
	protected Scene parentScene;
	protected Stage stage;
	protected StatoButtonSave ButtonSave;
	protected enum StatoButtonSave {
		INSERT,
		MODIFY,
		INDEFINITO
	}
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
	@FXML
	void handleClose(ActionEvent event) {
		this.stage.setScene(parentScene);
		this.stage.show();
	}
	@FXML
	void handleModifica(ActionEvent event) {
		//int index = TVHardware.getSelectionModel().getSelectedIndex(); // indice di riga tableView correntemente selezionata
		if (indexTableView > -1) {
			abilitaControlli();
			ButtonSave=StatoButtonSave.MODIFY;			
		}
	}
	@FXML
	void handlebtnCancel(ActionEvent event) {
		disabilitaControlli();
		ButtonSave=StatoButtonSave.INDEFINITO;  
	}	
	@FXML
	void handlebtnSave(ActionEvent event) {
		if (ButtonSave==StatoButtonSave.MODIFY) SalvaModifiche();
		disabilitaControlli();
		ButtonSave=StatoButtonSave.INDEFINITO;	 
	}
	protected void SalvaModifiche() {}

	protected void disabilitaControlli() {		
		List<Field> privateFields = new ArrayList<>();
		Field[] allFields = this.getClass().getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("_k") ) ||
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
					case "javafx.scene.control.Button":
						Button bt = (Button)field.get(this);
						bt.setDisable(true);
					}
					field.setAccessible(false); 
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

		List<Field> privateFields = new ArrayList<>();
		Field[] allFields = this.getClass().getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("_k") ) ||
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
					case "javafx.scene.control.Button":
						Button bt = (Button)field.get(this);
						bt.setDisable(false);
					}
					field.setAccessible(false); 
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
	 * @param 	rec=il bean dei campi da modificare
	 * @param 	allFields=l'insieme dei campi dichiarati nella form con i valori da modificare	
	 * @return	rec=il bean con i campi modificati da salvare
	 */
	public Object ReadModifiedFields(Object rec, Field[] allFields) { 
		DatePicker dp = null;
		String setMethod="", nomeField="", typeField="",nomeFieldBean="";
		Class<?> c = rec.getClass();
		Class[] cArg = new Class[1];
		Method m = null;
		Object rv = null;
		TextField tf = null;
		Field declaredFieldBean;
		for (Field field : allFields) {
			field.setAccessible(true);
			if (Modifier.isPrivate(field.getModifiers()) && 
					(field.getName().substring(0,2).equals("_m") ) ||
					(field.getName().substring(0,2).equals("_k") )){

				nomeField=field.getName();
				nomeFieldBean = nomeField.substring(2, nomeField.length()).toLowerCase();
				setMethod = "set" + nomeField.substring(2, 3).toUpperCase() + nomeField.substring(3, nomeField.length()).toLowerCase();

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
		return rec;
	}	
}
