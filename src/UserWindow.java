/*
 * UserWindow is the class that provides a GUI for the user to interact with.
 * @author Victoria Lappas
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

import controllers.BugController;
import controllers.ProductController;
import entities.Bug;
import entities.Product;

public class UserWindow{
	private JFrame userFrame;
	private JTabbedPane tabbedPane;
	private BugController bugC;
	private ProductController prodC;

	public UserWindow()
	{
		bugC = new BugController();
		prodC = new ProductController();
		
		//build user window using gui components
		userFrame = new JFrame();
		userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userFrame.setTitle("Bug Tracking System - User Window");
		userFrame.setSize(550,425);
		userFrame.setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane();

		createBrowseBugScreen();
		createSubmitBugScreen();
		createLoginScreen();
		
		userFrame.add(tabbedPane);	
		userFrame.setVisible(true);
	}
	
	//browse bug screen
	public void createBrowseBugScreen() {
		try
		{
			JPanel browseBugsPanel = new JPanel();
			browseBugsPanel.setLayout(null);
		
			JLabel selectProductLabel = new JLabel("Select Product: ");
			Vector<Product> productVector= prodC.browseAllProducts();
			JComboBox<Product> productList = new JComboBox<Product>(productVector);		
			productList.setSelectedIndex(0);
	
			JLabel bugLabel = new JLabel("Current Bugs ");
		
			Vector<Bug> bugVector = bugC.browseProductBugs(((Product)productList.getSelectedItem()).getProductID());
				
			DefaultListModel<Bug> listModel = new DefaultListModel<Bug>();
			
			if(bugVector != null)
			{
				for(int i = 0; i < bugVector.size(); i++) {
					listModel.addElement(bugVector.get(i));
				}
			}
		
			JList<Bug> bugList = new JList<Bug>(listModel);
			
			productList.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a)
				{
					try
					{
						Vector<Bug> temp = bugC.browseProductBugs(((Product)productList.getSelectedItem()).getProductID());
						if(temp != null)
							bugList.setListData(bugC.browseProductBugs(((Product)productList.getSelectedItem()).getProductID()));
						else
							bugList.setListData(new Vector<Bug>()); //Just put in an empty vector
					}
					catch(IOException b)
					{
						b.printStackTrace();
					}
				}
			}
					);
		
			bugList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			bugList.setLayoutOrientation(JList.VERTICAL);
			JScrollPane bugListScroller = new JScrollPane(bugList);
		
			selectProductLabel.setBounds(80,30,150,30);
			productList.setBounds(210,30,250,30);
			bugLabel.setBounds(90,60,150,30);
			bugListScroller.setBounds(90,90,400,200);
		
			browseBugsPanel.add(selectProductLabel);
			browseBugsPanel.add(productList);
			browseBugsPanel.add(bugLabel);
			browseBugsPanel.add(bugListScroller);
		
			tabbedPane.addTab("Browse Bugs", browseBugsPanel);
		}
			
		catch(IOException a)
		{
			JOptionPane err = new JOptionPane("Issue reading from database.", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	//submit bug screen
	public void createSubmitBugScreen() {
		JPanel submitBugPanel = new JPanel();
		submitBugPanel.setLayout(null);

		JLabel description = new JLabel("Description: ");
		JTextField descriptionField = new JTextField(60);
		
		JLabel productName = new JLabel("Product Name: ");
		String [] productStringArray = {"Sample Product 1", "Sample Product 2"};
		JComboBox<String> productList = new JComboBox<String>(productStringArray);
		productList.setSelectedIndex(0);
		
		JButton submitButton = new JButton("Submit");
		
		description.setBounds(100,80,150,30);
		descriptionField.setBounds(220,80,250,30);
		productName.setBounds(100,130,150,30);
		productList.setBounds(220,130,250,30);
		submitButton.setBounds(180,180,100,30);
		
		submitBugPanel.add(description);
		submitBugPanel.add(descriptionField);
		submitBugPanel.add(productName);
		submitBugPanel.add(productList);
		submitBugPanel.add(submitButton);
		
		tabbedPane.addTab("Submit Bug", submitBugPanel);
		
	}
	
	
	// login screen
	public void createLoginScreen() {
		JPanel loginPanel = new JPanel();
		loginPanel.setSize(300,200);
		loginPanel.setLocation(200,200);
		loginPanel.setLayout(null);
		
		JTextField userNameField = new JTextField(15);
		JPasswordField passwordField = new JPasswordField(15);
		JLabel username = new JLabel("Username:  ");
		JLabel password = new JLabel("Password:  ");
		JButton loginButton = new JButton("Login");
		
		JRadioButton developerButton = new JRadioButton("Developer");
		developerButton.setSelected(true);
		JRadioButton projectManagerButton = new JRadioButton("Project Manager");

		ButtonGroup group = new ButtonGroup();
		group.add(developerButton);
		group.add(projectManagerButton);
		
		userNameField.setBounds(220,70,150,30);
		passwordField.setBounds(220,116,150,30);
		username.setBounds(140,36,100,100);
		password.setBounds(140,56,150,150);
		loginButton.setBounds(210,195,80,25);
		developerButton.setBounds(130,155,100,30);
		projectManagerButton.setBounds(240,155,150,30);
		
		loginPanel.add(userNameField);
		loginPanel.add(passwordField);
		loginPanel.add(username);
		loginPanel.add(password);
		loginPanel.add(loginButton);
		loginPanel.add(developerButton);
		loginPanel.add(projectManagerButton);

		tabbedPane.addTab("Employee Login", loginPanel);
	}
	
	public static void main(String[] args) {
		UserWindow userWindow = new UserWindow();

	}

}
