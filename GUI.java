package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GUI extends JFrame {
	private JTextField textField;

	public GUI() {
		//exits the program on close
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		//set dimension of gui
		this.setSize(new Dimension(600, 600));

		//setting layout
		getContentPane().setLayout(null);
		//getting dimensions to display the gui in middle of any screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		//adding label to gui
		JLabel lblSearchDependencies = new JLabel("Search Dependencies");
		lblSearchDependencies.setFont(new Font("Verdana", Font.BOLD, 12));
		lblSearchDependencies.setBounds(200, 11, 177, 30);
		getContentPane().add(lblSearchDependencies);

		//mysql connection class
		MysqlConnection con = new MysqlConnection();
		List<String> moduleslist = new ArrayList<String>();
		//getting all modules and passing the arraylist to the combobox to display it to the user
		moduleslist = con.Connect();
		
		//sorting into alphabetical order
		Collections.sort(moduleslist);

		//for(int i=0;i<moduleslist.size();i++)
			//System.out.println(moduleslist.get(i));

		//adding comboxbox to the screen
		JComboBox modulescombo = new JComboBox(moduleslist.toArray());
		modulescombo.setFont(new Font("Verdana", Font.PLAIN, 11));
		modulescombo.setBounds(109, 82, 349, 20);
		getContentPane().add(modulescombo);

		//adding label to the gui
		JLabel lblModule = new JLabel("Module");
		lblModule.setFont(new Font("Verdana", Font.BOLD, 11));
		lblModule.setBounds(42, 85, 46, 14);
		getContentPane().add(lblModule);

		//adding button to the gui
		JButton btnNewButton = new JButton("Search");

		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 11));
		btnNewButton.setBounds(223, 130, 116, 23);
		getContentPane().add(btnNewButton);

		//adding label to the gui
		JLabel lblVulnerabilities = new JLabel("Vulnerabilities:");
		lblVulnerabilities.setFont(new Font("Verdana", Font.BOLD, 11));
		lblVulnerabilities.setBounds(42, 334, 116, 14);
		getContentPane().add(lblVulnerabilities);

		//adding label module to the gui
		JLabel label = new JLabel("Module");
		label.setFont(new Font("Verdana", Font.BOLD, 11));
		label.setBounds(43, 207, 46, 14);
		getContentPane().add(label);

		//adding textfield to the gui
		textField = new JTextField();
		textField.setBounds(110, 205, 348, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		//adding button to the gui
		JButton button = new JButton("Search");
		button.setFont(new Font("Verdana", Font.BOLD, 11));
		button.setBounds(224, 255, 116, 23);
		getContentPane().add(button);

		//adding a separator to the gui
		JSeparator separator = new JSeparator();
		separator.setBounds(42, 164, 510, 14);
		getContentPane().add(separator);

		//adding another separator
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(42, 289, 510, 14);
		getContentPane().add(separator_1);

		//adding a jpanel to add the textarea to the panel
		JPanel panel = new JPanel();
		panel.setBounds(27, 359, 497, 188);
		getContentPane().add(panel);

		//adding text area to the gui
		JTextArea textArea2 = new JTextArea(8, 41);
		textArea2.setFont(new Font("Verdana", Font.PLAIN, 13));
		//adding text area to the panel
		panel.add(textArea2, BorderLayout.WEST);
		//adding a scrollbar to the textarea
		JScrollPane scroll = new JScrollPane(textArea2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel.add(scroll);

		//adding action listener to the button
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea2.setText("");
				//gets the module selected by the user in the combobox and sends to the mysql function 
				ArrayList<String> strlist = con.Connect2(modulescombo.getSelectedItem().toString());
				//if arraylist is not empty
				if (strlist.size() > 0) {
					//it prints to the text area
					textArea2.append("Name\t\tDescription\n");
					for (int i = 0; i < strlist.size(); i++) {
						textArea2.append(strlist.get(i) + "\n");
					}
				} else
					textArea2.append("This module has no known vulnerabilities.\n");//otherwise print to user

			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				textArea2.setText("");
				//get text from the textfield
				ArrayList<String> strlist = con.Connect2(textField.getText());
				//if size is greater than 0
				if (strlist.size() > 0) {
					// print the Name and description of vulnerability
					textArea2.append("Name\t\tDescription\n");
					for (int i = 0; i < strlist.size(); i++) {
						//print to the text area
						textArea2.append(strlist.get(i) + "\n");
					}
				} else
					textArea2.append("This module has no known vulnerabilities.\n");

			}

		});

	}
}

