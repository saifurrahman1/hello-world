package gui;

import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		//creating object of the mysqlconnection class
		MysqlConnection ms=new MysqlConnection();
		///creating the gui class object
		GUI gui=new GUI();
		//making it visible
		gui.setVisible(true);
	}

}
