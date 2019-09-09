
package tds;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 */

public class ApplicationController implements Initializable {
   ResultSet rs=null;
   private Connection conn=null;
   
    @FXML
    private ComboBox<String> selectModuleCombo;
    @FXML
    private Button searchCombo;
    @FXML
    private TextField enteredModuleName;
    @FXML
    private ComboBox<String> yearCombo;
    @FXML
    private TableView<Vulnerability> cveTable;
    @FXML
    private TableColumn<Vulnerability, String> cveName;
    @FXML
    private TableColumn<Vulnerability, String> cveDescription;
    @FXML
    private TableColumn<Vulnerability, String> cveReference;
    
    
    private ObservableList<Vulnerability> cveData;
    
//Create object;
    DatabaseConnection dbc=new DatabaseConnection();
    Algorithms algo=new Algorithms();
    @FXML
    private Button searchCombo1;
    
    //Global list
    ObservableList<String> modulesList = FXCollections.observableArrayList(algo.getAllModules());
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
        //algo.getAllModules(selectModuleCombo);
         //ObservableList<String> modulesList = FXCollections.observableArrayList(algo.getAllModules());
       
        selectModuleCombo.setItems(null);
        selectModuleCombo.setItems(modulesList);
        selectModuleCombo.getSelectionModel().select("Select Module");
        
        //Populate the Year Combo
        ObservableList<String> yearEdited = FXCollections.observableArrayList(algo.getYears());
         yearCombo.setItems(null);
        yearCombo.setItems(yearEdited);
        yearCombo.getSelectionModel().select("Select Year");
        
        
           
        
    }    
    
    
     /*
    This is handled by the button that is located next to the  select Combobox 
    that you select the module Name from the modules in the database;
    */
    
    @FXML 
    public void loadData(ActionEvent ev){
        if(selectModuleCombo.getValue().equals("Select Module")){
             JOptionPane.showMessageDialog(null, "Please Select Module To Filter","TDS App",JOptionPane.WARNING_MESSAGE);
        
        }
        else{
        	
        //Here We Load The Data;
        //ALL CVE's
         try {
            conn=dbc.getDatabaseConnection();
            rs=conn.createStatement().executeQuery("SELECT cv.* FROM cve cv");
            cveData=FXCollections.observableArrayList();
            while(rs.next()){
                cveData.add(new Vulnerability(rs.getString("Name"), rs.getString("Description"), rs.getString("References")));
                
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex.getMessage());
        }
        
        cveName.setCellValueFactory(new PropertyValueFactory<>("vulName"));
        cveDescription.setCellValueFactory(new PropertyValueFactory<>("vulDescribe"));
        cveReference.setCellValueFactory(new PropertyValueFactory<>("vulRefer"));
       
        
        cveTable.setItems(null);
        cveTable.setItems(cveData);   
        }
    }
    
    /*
    This is handled by the button that is located next to the  textfield 
    that you enter the module Name.
    */
    
    @FXML
    public void searchByTextField(ActionEvent ev){
        String enteredModule=enteredModuleName.getText();        
        if(modulesList.contains(enteredModule)){
            try {
            conn=dbc.getDatabaseConnection();
            rs=conn.createStatement().executeQuery("SELECT cv.* FROM cve cv");
            cveData=FXCollections.observableArrayList();
            while(rs.next()){
                cveData.add(new Vulnerability(rs.getString("Name"), rs.getString("Description"), rs.getString("References")));
                
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex.getMessage());
        }
        
        cveName.setCellValueFactory(new PropertyValueFactory<>("vulName"));
        cveDescription.setCellValueFactory(new PropertyValueFactory<>("vulDescribe"));
        cveReference.setCellValueFactory(new PropertyValueFactory<>("vulRefer"));
       
        
        cveTable.setItems(null);
        cveTable.setItems(cveData);   
        
        }
        else{
            JOptionPane.showMessageDialog(null, "There are no known vulnerabilities","TDS-App",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /*
    This is handled by the filter button that is located in the left panel.
    It calls the load data method that is located below it to populate the data in the table.
    */
    
    @FXML
    public void filterVulnerabilities(ActionEvent event){        
        if(yearCombo.getValue().equals("Select Year")){
            JOptionPane.showMessageDialog(null, "Please Select Year To Filter","TDS App",JOptionPane.WARNING_MESSAGE);
        }
        else{
        this.loadData(yearCombo.getValue());
        }
    }
    
    
    
    
    
    //Method to filter data by year and populate the table.
    public void loadData(String year){
    	
        //Here We Load The Data;
        //ALL CVE's
         try {
            conn=dbc.getDatabaseConnection();
            rs=conn.createStatement().executeQuery("SELECT cv.* FROM cve cv WHERE mid(cv.Name,5,4)='"+year+"'");
            //rs=conn.createStatement().executeQuery("SELECT cv.* FROM cve cv WHERE MID(cv.Name,5,4)='"+year+"'");         
            cveData=FXCollections.observableArrayList();
            while(rs.next()){
                cveData.add(new Vulnerability(rs.getString("Name"), rs.getString("Description"), rs.getString("References")));
                
            }
        } catch (SQLException ex) {
            System.err.println("Error"+ex.getMessage());
        }
        
        cveName.setCellValueFactory(new PropertyValueFactory<>("vulName"));
        cveDescription.setCellValueFactory(new PropertyValueFactory<>("vulDescribe"));
        cveReference.setCellValueFactory(new PropertyValueFactory<>("vulRefer"));
       
        
        cveTable.setItems(null);
        cveTable.setItems(cveData);   
    }
    

    
   
    
}
