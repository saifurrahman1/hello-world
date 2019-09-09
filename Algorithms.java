
package tds;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Algorithms {
	
    //Connection,PreparedStatement and ResultSet Objects;
    private   Connection conn=null;
    private   PreparedStatement cvePrepare=null;
    private   ResultSet cveRS=null;
    
    //Create Object of Connection class
    DatabaseConnection dbc=new DatabaseConnection();
    
    //Initialize Connection
    public Algorithms(){
        conn=dbc.getDatabaseConnection();
    }
    
    
    public static void main(String[] args){
        String name="cve-2019-2012";
        name.substring(4,9);
        System.out.println(name.substring(4,9));
    }
    
    //Get All Modules to an Array List
    public List<String> getAllModules(){
        List myList=new ArrayList();    
       
        try {
            cvePrepare=conn.prepareStatement("SELECT DISTINCT(module) FROM chrome");
            cveRS=cvePrepare.executeQuery();
            while(cveRS.next()){                 
                myList.add(cveRS.getString("module"));
            }
           
               
           
        } catch (SQLException ex) {
            Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myList;
        
    }
    
    //Get Vulnerabilities of Selected Modules
    public ResultSet getVulnerabilities(){
    	
        //TableModel mytableM=new DefaultTableModel();
        //mytable.setModel(mytableM);
        try {
            cvePrepare=conn.prepareStatement("SELECT cv.Name,cv.`Status`,cv.Description,cv.`References` FROM cve cv");
            cveRS=cvePrepare.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cveRS;
    }
    //This is a method to populate the year Filtering Combobox
    public List<String> getYears(){
         List yearList=new ArrayList();
       
        try {
            cvePrepare=conn.prepareStatement("SELECT Name FROM cve");
            cveRS=cvePrepare.executeQuery();
            while(cveRS.next()){ 
                String year=cveRS.getString("Name");
                String yearSub=year.substring(4,8);
                
                if(yearList.contains(yearSub)){
                    
                }
                else{
                    yearList.add(yearSub);
                }
            }
           
               
           
        } catch (SQLException ex) {
            Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return yearList;
    }
    
    
    //Check if Typed Module is Contained in the database;
    public boolean checkModule(String moduleName){
        boolean exists=false;
        try {
            cvePrepare=conn.prepareStatement("SELECT DISTINCT(module) FROM chrome WHERE module=?");
            cvePrepare.setString(1, moduleName);
            cveRS=cvePrepare.executeQuery();
            if(cveRS.next()){
                exists=true;
            }
            
            else{
                exists=false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;
        
    }
    
    //Opening a webpage;
    public void visitTheVSite(URI uri){
        try {
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (IOException ex) {
            Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
