package fglib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * @param index - Indice di riga della tableView correntemente selezionata
 * @param rec	- Il record della classe bean interessata in cui ci sono i valori da modificare prima delle modifiche utente
 * @return		- Il record della classe bean interessata in cui ci sono i valori modificati da salvare
 */
public class ReadModifiedFields {
	private int index;
	private Object rec;
	
	public ReadModifiedFields(int index, Object rec) {
		super();
		this.index = index;
		this.rec = rec;
	}
	
	public Object Read() { 
	DatePicker dp = null;
	String setMethod="", nomeField="", typeField="",nomeFieldBean="";
	if (index >= 0) {
//		rec = TVHardware.getSelectionModel().getSelectedItem();
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
}
