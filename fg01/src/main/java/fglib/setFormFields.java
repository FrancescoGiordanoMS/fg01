package fglib;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class setFormFields {
	
	
	protected void disabilitaControlli() {
			
		List<Field> privateFields = new ArrayList<>();
		//Field[] allFields = questaClasse.getDeclaredFields();
		Field[] allFields = this.getClass().getDeclaredFields();
		for (Field field : allFields) {
		    if (Modifier.isPrivate(field.getModifiers()) && 
		    		(field.getName().substring(0,2).equals("_m") ) ||
		    		(field.getName().substring(0,2).equals("_k") ))
		    		{
		        Class<?> clazz = field.getType();
		        field.setAccessible(true);
	        	try {
		        switch (clazz.getName()) {
		        case "javafx.scene.control.TextField":
					TextField tf = (TextField)field.get(this);
					//tf.setEditable(false);
					tf.setDisable(true);
		        	tf.setStyle("-fx-opacity: 0.9;");
		        	break;
		        case "javafx.scene.control.DatePicker":
		        	DatePicker dp = (DatePicker)field.get(this);
		        	//dp.setEditable(false);
		        	dp.setDisable(true);
		        	dp.setStyle("-fx-opacity: 0.9;");
		        	dp.getEditor().setStyle("-fx-opacity: 1.0;");
		        	break;        
		        }
		        field.setAccessible(false); 
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        //privateFields.add(field);

		        
		        
		    }
		}
	}
	
	private static void abilita() {
		
	}
	
}
