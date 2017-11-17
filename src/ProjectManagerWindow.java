/*
 * ProjectManagerWindow is the class that provides a GUI for a project manager to interact with.
 * @author Victoria Lappas
 */

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import entities.*;
import controllers.*;

public class ProjectManagerWindow {
	private JFrame PMFrame;
	private JTabbedPane tabbedPane;
	private Employee pm;
	private EmployeeController employeeController;
	private BugController bugController;
	private ProductController productController;
	
	//Project manager window constructor
	public ProjectManagerWindow(Employee pmIn) {
		employeeController = new EmployeeController();
		bugController = new BugController();
		productController = new ProductController();
		PMFrame = new JFrame();
		PMFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PMFrame.setTitle("Bug Tracking System - Project Manager Window");
		PMFrame.setSize(550,425);
		PMFrame.setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane();
		
		createAssignBugScreen();
		createModifyProductScreen();
		createModifyEmployeeScreen();
		createGenerateReportScreen();
		
		PMFrame.add(tabbedPane);
		PMFrame.setVisible(true);
		
		pm = pmIn;
	}

	//Assign and remove bug screen
	public void createAssignBugScreen() {
		try {
		JPanel assignBugPanel = new JPanel();
		assignBugPanel.setLayout(null);
		
		JLabel unassignedBugsLabel = new JLabel("Unassigned Bugs ");
		
		
		JList<Bug> unassignedBugList = new JList<Bug>(updateUnassignedBugList());
		unassignedBugList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		unassignedBugList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane unassignedBugListScroller = new JScrollPane(unassignedBugList);
		
		JLabel developerLabel = new JLabel("Select developer: ");
		Vector<Employee> employeeVector = employeeController.getAllEmployees();
		Vector<Employee> developerVector = new Vector<Employee>();
		
		//filter through employees and get only developers
		for(int i = 0; i<employeeVector.size(); i++) {
			if(employeeVector.get(i).getEmployeeType().equals("developer")) {
				developerVector.add(employeeVector.get(i));
			}
		}
		
		JComboBox<Employee> developerList = new JComboBox<Employee>(developerVector);		
		developerList.setSelectedIndex(0);
		
		JButton assignButton = new JButton("Assign Bug");
		/*
		assignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Bug temp = unassignedBugList.getSelectedValue();
					if(temp == null) {
						JOptionPane.showMessageDialog(PMFrame, "Please select a bug for assignment");
					}
					else if(developerList.getSelectedItem().equals(null)) {
						JOptionPane.showMessageDialog(PMFrame, "Please select a developer for assignment");
					}
					else {
						temp.setStatus("assigned");
						temp.setDeveloper(developerList.getSelectedItem().getID());
						unassignedBugList.setModel(updateUnassignedBugList());
					}
					
				}
				catch(IOException o) {
					JOptionPane.showMessageDialog(PMFrame, "Issue with assignment in the database");
				}
			}

		});		
		*/
		
		JButton removeBugButton = new JButton("Remove Bug");
		removeBugButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Bug temp = unassignedBugList.getSelectedValue();
					if(temp == null) {
						JOptionPane.showMessageDialog(PMFrame, "Please select a bug for removal");
					}
					else {
							bugController.removeBug(temp.getID());
							unassignedBugList.setModel(updateUnassignedBugList());
					}
					
				}
				catch(IOException o) {
					JOptionPane.showMessageDialog(PMFrame, "Issue with removal in the database");
				}
			}

		});
		
		
		unassignedBugsLabel.setBounds(80,20,150,30);
		unassignedBugListScroller.setBounds(80,60,400,150);
		removeBugButton.setBounds(360,20,125,30);
		assignButton.setBounds(200,300,150,30);
		developerLabel.setBounds(120,250,150,30);
		developerList.setBounds(245,250,235,30);
		
		assignBugPanel.add(unassignedBugsLabel);
		assignBugPanel.add(unassignedBugListScroller);
		assignBugPanel.add(removeBugButton);
		assignBugPanel.add(assignButton);
		assignBugPanel.add(developerLabel);
		assignBugPanel.add(developerList);
		
		tabbedPane.addTab("Assign Bugs", assignBugPanel);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(PMFrame, "Issue with getting developers from the database");
		}
	
	}
	
	//Modify product screen 
	public void createModifyProductScreen() {
		JPanel modifyProductPanel = new JPanel();
		modifyProductPanel.setLayout(null);
		
		JLabel productListLabel = new JLabel("Product List ");
		
		JList<Product> productList = new JList<Product>(updateProductList());
		productList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		productList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane productListScroller = new JScrollPane(productList);
		
		JLabel productNameLabel = new JLabel("Product Name: ");	
		JTextField productNameField = new JTextField();
		
		JButton removeProductButton = new JButton("Remove Product");
		removeProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Product temp = productList.getSelectedValue();
					if(temp == null) {
						JOptionPane.showMessageDialog(PMFrame, "Please select a product for removal");
					}
					else {
							productController.removeProduct(temp.getProductID());
							productList.setModel(updateProductList());
					}
					
				}
				catch(IOException o) {
					JOptionPane.showMessageDialog(PMFrame, "Issue with removal in the database");
				}
			}
		
		
		});
		
		
		
		
		JButton addProductButton = new JButton("Add Product");
		addProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(productNameField.getText().equals("")) {
						JOptionPane.showMessageDialog(PMFrame, "Please enter a project name");
					}
					else {
						String name = productNameField.getText();
						productController.createProduct(name);
						productList.setModel(updateProductList());
					}
					
				}
				catch(IOException o) {
					JOptionPane.showMessageDialog(PMFrame, "Issue with adding into the database");
				}
			}
		});
		
		
		
		productListLabel.setBounds(80,20,150,30);
		productListScroller.setBounds(80,60,400,150);
		removeProductButton.setBounds(360,20,125,30);
		addProductButton.setBounds(200,300,150,30);
		productNameLabel.setBounds(120,250,150,30);
		productNameField.setBounds(245,250,150,30);
		
		modifyProductPanel.add(productListLabel);
		modifyProductPanel.add(productListScroller);
		modifyProductPanel.add(removeProductButton);
		modifyProductPanel.add(addProductButton);
		modifyProductPanel.add(productNameLabel);
		modifyProductPanel.add(productNameField);
		
		tabbedPane.addTab("Modify Products", modifyProductPanel);
	}
	
	
	//creates the modify employee screen
	public void createModifyEmployeeScreen(){
			JPanel modifyEmployeePanel = new JPanel();
			modifyEmployeePanel.setLayout(null);
		
			JLabel employeeListLabel = new JLabel("Employee List ");

			JList<Employee> employeeList = new JList<Employee>(updateEmployeeList());
			employeeList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			employeeList.setLayoutOrientation(JList.VERTICAL);
			JScrollPane employeeListScroller = new JScrollPane(employeeList);			
			
		
			JLabel employeeNameLabel = new JLabel("Employee Name:");	
			JTextField employeeNameField = new JTextField();
		
			JLabel employeePasswordLabel = new JLabel("Employee Password:");	
			JTextField employeePasswordField = new JTextField();
		
			JLabel employeeTypeLabel = new JLabel("Employee Type: ");	
			JRadioButton developerButton = new JRadioButton("Developer");
			developerButton.setSelected(true);
			JRadioButton projectManagerButton = new JRadioButton("Project Manager");
			ButtonGroup group = new ButtonGroup();
			group.add(developerButton);
			group.add(projectManagerButton);
		
			//remove employee
			JButton removeEmployeeButton = new JButton("Remove Employee");
			removeEmployeeButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Employee temp = employeeList.getSelectedValue();
							if(temp == null) {
								JOptionPane.showMessageDialog(PMFrame, "Please select an employee for removal");
							}
							else {
									employeeController.removeEmployee(temp.getID());
									employeeList.setModel(updateEmployeeList());
							}
							
						}
						catch(IOException o) {
							JOptionPane.showMessageDialog(PMFrame, "Issue with removal in the database");
						}
					}
				
				
				});
			
			//add employee
			JButton addEmployeeButton = new JButton("Add Employee");
			addEmployeeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						String name = employeeNameField.getText();
						String password = employeePasswordField.getText();
						String type = "developer";
						if(projectManagerButton.isSelected()) {
							type = "project manager";
						}
						
						if(employeeNameField.getText().equals("")){
							JOptionPane.showMessageDialog(PMFrame, "Please enter an employee name");
						}
						else if(employeePasswordField.getText().equals("")) {
							JOptionPane.showMessageDialog(PMFrame, "Please enter a password for the employee");
						}
						else {
							employeeController.createEmployee(name,password, type);
							employeeList.setModel(updateEmployeeList());
						}
						
					}
					catch(IOException o) {
						JOptionPane.showMessageDialog(PMFrame, "Issue with adding into the database");
					}
				}
			});
			
	
		
			
			//update employee information
			JButton updateEmployeeButton = new JButton("Update Employee");
			updateEmployeeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Employee temp = employeeList.getSelectedValue();
						if(temp == null) {
							JOptionPane.showMessageDialog(PMFrame, "Please select an employee to update");
						}
						else {
								int ID = temp.getID();
								String currentType = temp.getEmployeeType();
								employeeList.setModel(updateEmployeeList());
								if(!employeeNameField.getText().equals("") && !employeePasswordField.getText().equals("")) {
									employeeController.updateEmployeeNameAndPass(ID, employeeNameField.getText(), employeePasswordField.getText());
								}
								else if(!employeeNameField.getText().equals("") && employeePasswordField.getText().equals("")) {
									JOptionPane.showMessageDialog(PMFrame, "Please also create a new password");
								}
								else if(employeeNameField.getText().equals("") && !employeePasswordField.getText().equals("")) { 
									JOptionPane.showMessageDialog(PMFrame, "Please also change the name");
								}
								
								
								String newType = "developer";
								if(projectManagerButton.isSelected()) {
									newType = "project manager";
								}
								
								if(newType != currentType) {
									employeeController.updateEmployeeType(ID, newType);
								}
								
								
								employeeList.setModel(updateEmployeeList());
								
						}
					}
					catch(IOException o) {
						JOptionPane.showMessageDialog(PMFrame, "Issue with updating the database");
					}
				}
			});
			
		
			employeeListLabel.setBounds(80,20,150,30);
			employeeListScroller.setBounds(80,60,400,150);
			removeEmployeeButton.setBounds(340,20,150,30);
			employeeNameLabel.setBounds(80,210,150,30);
			employeeNameField.setBounds(210,210,175,30);
			employeePasswordLabel.setBounds(80,245,150,30);
			employeePasswordField.setBounds(210,245,175,30);
			employeeTypeLabel.setBounds(80,280,150,30);
			developerButton.setBounds(200,280,100,30);
			projectManagerButton.setBounds(300,280,150,30);
			updateEmployeeButton.setBounds(100,320,150,30);
			addEmployeeButton.setBounds(250,320,150,30);
		
			modifyEmployeePanel.add(employeeListLabel);
			modifyEmployeePanel.add(employeeListScroller);
			modifyEmployeePanel.add(removeEmployeeButton);
			modifyEmployeePanel.add(addEmployeeButton);
			modifyEmployeePanel.add(updateEmployeeButton);
			modifyEmployeePanel.add(employeeNameLabel);
			modifyEmployeePanel.add(employeeNameField);
			modifyEmployeePanel.add(employeePasswordLabel);
			modifyEmployeePanel.add(employeePasswordField);
			modifyEmployeePanel.add(employeeTypeLabel);
			modifyEmployeePanel.add(developerButton);
			modifyEmployeePanel.add(projectManagerButton);
		
			tabbedPane.addTab("Modify Employees", modifyEmployeePanel);
	}
	
	//returns a list model for the product list when called
	DefaultListModel<Bug> updateUnassignedBugList(){
		DefaultListModel<Bug> listModel = new DefaultListModel<Bug>();
		try {
			Vector<Bug> bugVector = bugController.browseAllBugs();
	
			if(bugVector != null) {
				for(int i = 0; i < bugVector.size(); i++) {
					if(bugVector.get(i).getStatus().equals("Submitted")) {
						listModel.addElement(bugVector.get(i));
					}	
				}
			
			}
		}
		catch(IOException e) {
			JOptionPane err = new JOptionPane("Issue reading from database", JOptionPane.ERROR_MESSAGE);
		}
		return listModel;
	}
	
	
	//returns a list model for the product list when called
	DefaultListModel<Product> updateProductList(){
		DefaultListModel<Product> listModel = new DefaultListModel<Product>();
		try {
			Vector<Product> productVector = productController.browseAllProducts();
	
			if(productVector != null) {
				for(int i = 0; i < productVector.size(); i++) {
					listModel.addElement(productVector.get(i));
				}
			
			}
		}
		catch(IOException e) {
			JOptionPane err = new JOptionPane("Issue reading from database", JOptionPane.ERROR_MESSAGE);
		}
		return listModel;
	}
	
	//returns a list model for the employee list when called
	DefaultListModel<Employee> updateEmployeeList(){
		DefaultListModel<Employee> listModel = new DefaultListModel<Employee>();
		try {
			Vector<Employee> employeeVector = employeeController.getAllEmployees();
	
			if(employeeVector != null) {
				for(int i = 0; i < employeeVector.size(); i++) {
					listModel.addElement(employeeVector.get(i));
				}
			
			}
		}
		catch(IOException e) {
			JOptionPane err = new JOptionPane("Issue reading from database", JOptionPane.ERROR_MESSAGE);
		}
		return listModel;
	}
	
	
	
	// creates the generate report screen
	void createGenerateReportScreen() {
		JPanel generateReportPanel = new JPanel();
		generateReportPanel.setLayout(null);
		
		JLabel generateReportLabel1 = new JLabel("Generate a report for all bugs and their current status");
		JLabel generateReportLabel2 = new JLabel("This report will appear in your current working directory");
		
		JButton generateReportButton = new JButton("Generate Report");
		generateReportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {									
					File file = new File("Report.txt");
					FileWriter writer = new FileWriter(file);
					writer.write("Current Bugs Report \n ");
					Vector<Bug> bugList = bugController.browseAllBugs();
					for(int i = 0; i < bugList.size(); i++) {
						writer.write(bugList.get(i).toString());
						writer.write("\n");
					}
					JOptionPane.showMessageDialog(PMFrame, "Report successfully generated");
					writer.close();
					}
				catch(IOException o) {
					JOptionPane.showMessageDialog(PMFrame, "Issue with adding into the database");
				}
				finally {
				}
			}
		});
		
		generateReportLabel1.setBounds(90,50,350,30);
		generateReportLabel2.setBounds(90,80,400,30);
		generateReportButton.setBounds(200,120,150,30);
		
		generateReportPanel.add(generateReportLabel1);
		generateReportPanel.add(generateReportLabel2);
		generateReportPanel.add(generateReportButton);
		
		tabbedPane.addTab("Generate Report", generateReportPanel);
	}
	
	
}
