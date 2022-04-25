package francesco.giordano.fg01;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fglib.MyMenuBar;
import francesco.giordano.fg01.model.ModelHardware;
import francesco.giordano.fg01.model.j02ModelSoftware;
//import francesco.giordano.fg01.model.ModelHardware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Fg01ControllerPWD {

	@FXML
	private PasswordField _mPassword;

	@FXML
	private TextField _mUtente;
	
    @FXML
    private GridPane Grid;
    
    @FXML
    private MenuBar _mMenu;

	@FXML
	private Button btnConferma;
	
	 @FXML
	 private MenuItem MenuItem_hardware,MenuItem_software;
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private ModelHardware model=null;
	private Stage stage = null;
	
    @FXML
    void handleMenuItem_hardware(ActionEvent event) throws IOException {
        BorderPane root;
    	Scene currentScene;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/j01_Hardware.fxml")) ;
		root = loader.load();
        Fg01ControllerHardware controller = loader.getController() ;
        ModelHardware model = new ModelHardware();
      	
    	currentScene=stage.getScene();
    	Scene scene = new Scene(root);
    	scene.getStylesheets().add("/styles/Styles.css");        
    	stage.setTitle("JavaFX and Maven");
    	stage.setScene(scene);
 	   	
    	controller.setParentScene(currentScene);
    	controller.setStage(stage);
    	controller.setModel(model);  
    	controller.popolaTableView();
    	controller.init();
    	stage.show();
    }

    @FXML
    void handleMenuItem_software(ActionEvent event) throws IOException {
    	BorderPane root;
    	Scene currentScene;
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/j02_software.fxml")) ;
    	root = loader.load();
    	j02ControllerSoftware controller = loader.getController() ;
    	j02ModelSoftware model = new j02ModelSoftware();

    	currentScene=stage.getScene();
    	Scene scene = new Scene(root);
    	scene.getStylesheets().add("/styles/Styles.css");        
    	stage.setTitle("JavaFX and Maven");
    	stage.setScene(scene);
 	   	
    	controller.setParentScene(currentScene);
    	controller.setStage(stage);
    	controller.setModel(model);  
    	controller.popolaTableView();
    	controller.getHBoxButtons().setVisible(false);
    	//controller.setRoot(root);	
    	controller.init();
//    	scene.getStylesheets().add("/styles/Styles.css");        
//    	stage.setTitle("JavaFX and Maven");
//    	stage.setScene(scene);
//    	//controller.setMenu();
    	stage.show();
    }
    
	@FXML
	void handleConferma(ActionEvent event) throws IOException {
		        Grid.setVisible(false);
		        _mMenu.setDisable(false);
		        stage.show();
	}

	@FXML
	void d77171(ActionEvent event) {

	}
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	public void setModel(ModelHardware m) {
		this.model = m;
	}

	public void setStage(Stage s) {
		this.stage = s;
	}
}
