package francesco.giordano.fg01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Fg01ControllerHardware {

    @FXML
    private TextField _mModello;

    @FXML
    private TableColumn<?, ?> col_tipohw;

    @FXML
    private TableColumn<?, ?> col_modello;

    @FXML
    private TextField _mMarca;

    @FXML
    private TextField _mMatricola;

    @FXML
    private TableColumn<?, ?> col_marca;

    @FXML
    private TableColumn<?, ?> col_dataacquisto;

    @FXML
    private TextField _mPrezzo;

    @FXML
    private TextField _mTipoHw;

    @FXML
    private TableColumn<?, ?> col_matricola;

    @FXML
    private DatePicker _mDataAcquisto;

    @FXML
    private TableColumn<?, ?> col_prezzo;

    @FXML
    private MenuItem MenuItem_Close;


    
    private Scene parentScene;
	private Stage stage;
    
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

@FXML
void handleClose(ActionEvent event) {
	this.stage.setScene(parentScene);
	this.stage.show();
}

//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
public void setParentScene(Scene scene) {
	this.parentScene=scene;
}
public void setStage(Stage s) {
	this.stage=s;
}

}
