package francesco.giordano.fg01;

import javafx.application.Application;
import static javafx.application.Application.launch;

import francesco.giordano.fg01.Fg01ControllerPWD;
import francesco.giordano.fg01.model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
     
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SceneFG01.fxml")) ;
        Parent root = loader.load();
        
        Fg01ControllerPWD controller = loader.getController() ;
        
        
        Model model = new Model();
        controller.setModel(model);  
        controller.setStage(stage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
        //controller.setTableView();
      
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
