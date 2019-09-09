
package tds;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class HomeController implements Initializable {

    @FXML
    private Button btnContinue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
     @FXML
    private void openApplicationArea(ActionEvent event){
    	 
        //Current Stage
        Stage currentStage;
        currentStage=(Stage)btnContinue.getScene().getWindow();
        currentStage.close();
         Parent root;        
        try {
             root= FXMLLoader.load(getClass().getResource("/tds/application.fxml"));
             Stage stage=new Stage();
             
             //stage.getIcons().add(new Image(("file:acad.png")));
             stage.setTitle("TDS-The Dependency Search");
             Scene scene = new Scene(root);             
             stage.setScene(scene);
             stage.setMaximized(true);
             stage.setResizable(false);
             stage.show();
         } catch (IOException ex) {
             Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
