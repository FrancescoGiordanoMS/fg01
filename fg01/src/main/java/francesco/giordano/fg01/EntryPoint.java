package francesco.giordano.fg01;

import javafx.application.Application;
import static javafx.application.Application.launch;

import org.kordamp.bootstrapfx.BootstrapFX;

import francesco.giordano.fg01.Fg01ControllerPWD;
//import francesco.giordano.fg01.model.Model;
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
        controller.setStage(stage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
  
       // scene.getStylesheets().add("/styles/TestFX.css");       //(3)

             
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
      
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
