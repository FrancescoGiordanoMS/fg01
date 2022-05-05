package fglib;

import javafx.scene.control.TextField;

public class TextFieldLimited extends TextField {  
	    private int maxlength;
	    public TextFieldLimited() {
	        this.maxlength = 10;
	    }
	    public void setMaxlength(int maxlength) {
	        this.maxlength = maxlength;
	    }
	    @Override
	    public void replaceText(int start, int end, String text) {
	        // Delete or backspace user input.
	        if (text.equals("")) {
	            super.replaceText(start, end, text);
	        } else if (getText().length() < maxlength) {
	            super.replaceText(start, end, text);
	        }
	    }

	    @Override
	    public void replaceSelection(String text) {
	        // Delete or backspace user input.
	        if (text.equals("")) {
	            super.replaceSelection(text);
	        } else if (getText().length() < maxlength) {
	            // Add characters, but don't exceed maxlength.
	            if (text.length() > maxlength - getText().length()) {
	                text = text.substring(0, maxlength- getText().length());
	            }
	            super.replaceSelection(text);
	        }
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