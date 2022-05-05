package fglib;

import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;

public class TextFieldLimited extends TextField {  
	    private int maxlength;
	    private boolean uppercase;
	    public TextFieldLimited() {
	        this.maxlength = 10;
	    }
	    public void setMaxlength(int maxlength) {
	        this.maxlength = maxlength;
	    }
	    
	    public void UpperCase(boolean upper) {
	        this.uppercase = upper;
	    }
	    
	    @Override
	    public void replaceText(int start, int end, String text) {
	        // Delete or backspace user input.
	        if (text.equals("")) {
	            super.replaceText(start, end, text);
	        } else if (getText().length() < maxlength) {
	        	if (this.uppercase) text = text.toUpperCase(); 
	            super.replaceText(start, end, text);
	        }
	    }

	    @Override
	    public void replaceSelection(String text) {
	        // Delete or backspace user input.
	    	
	    	IndexRange ir = getSelection();
	    	int inizio = ir.getStart();
	    	int fine = ir.getEnd();
	    	//String nuovoText = "";
	    	String vecchioText = getText();
	    	//String selectedText = getText().substring(inizio, fine);
	    	
	    	super.clear();
	    	text=vecchioText.substring(0,inizio)+text+vecchioText.substring(fine);
	    	text=text.substring(0,text.length() < maxlength? text.length(): maxlength);
        	if (this.uppercase) text = text.toUpperCase();
	    	super.replaceSelection(text);
	    	
//	        if (text.equals("")) {
//	            super.replaceSelection(text);
//	        } else if (getText().length() < maxlength) {
//	            // Add characters, but don't exceed maxlength.
//	            if (text.length() > maxlength - getText().length()) {
//	                text = text.substring(0, maxlength- getText().length());
//	            }
//	            super.replaceSelection(text);
//	        }
	    }
	    
//	    Inside the fxml file i added the import (of the package that the TextFieldLimited class is existing) on the top of the file and replace the TextField tag with the custom TextFieldLimited.
//
//	    <?import fx.mycontrols.*?>
//	    .  
//	    .  
//	    . 
//	    <TextFieldLimited fx:id="usernameTxtField" promptText="username" />
//	    Inside the controller class,
//
//	    on the top (property declaration),
//	    @FXML
//	    private TextFieldLimited usernameTxtField;
//
//	    inside the initialize method,
//	    usernameTxtField.setLimit(40);
	    
	    
	    
	}