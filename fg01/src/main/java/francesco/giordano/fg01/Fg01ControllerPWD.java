package francesco.giordano.fg01;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import francesco.giordano.fg01.model.Model;
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
	 private MenuItem MenuItem_hardware;

	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private Model model=null;
	private Stage stage = null;
	
    @FXML
    void handleMenuItem_hardware(ActionEvent event) throws IOException {
        BorderPane root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneFG01_Hardware.fxml")) ;
		root = loader.load();
        Fg01ControllerHardware controller = loader.getController() ;
        Model model = new Model();
        //controller.setModel(model);  
        controller.setStage(stage);
        controller.setParentScene(stage.getScene());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

	@FXML
	void handleConferma(ActionEvent event) {
			try {
		        BorderPane root;
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneFG01_Hardware.fxml")) ;
				root = loader.load();
		        Fg01ControllerHardware controller = loader.getController() ;
		        Model model = new Model();
		        Grid.setVisible(false);
		        _mMenu.setDisable(false);
		        
		        //controller.setModel(model);  
		        //controller.setStage(stage);
//		        Scene scene = new Scene(root);
//		        scene.getStylesheets().add("/styles/Styles.css");        
//		        stage.setTitle("JavaFX and Maven");
//		        stage.setScene(scene);
		        stage.show();
		        
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        

	}

	@FXML
	void d77171(ActionEvent event) {

	}
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	public void setModel(Model m) {
		this.model = m;
	}

	public void setStage(Stage s) {
		this.stage = s;
	}
}
