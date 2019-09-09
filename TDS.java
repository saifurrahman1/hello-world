
package tds;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TDS extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Parent root; 
         try {
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(root); 
            primaryStage.setTitle("TDS-The Dependency Search Application Home Page");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.show();
         } catch (IOException ex) {
            System.out.println(ex.getMessage());
         }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
