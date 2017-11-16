/*
 * ProjectManagerWindow is the class that provides a GUI for a project manager to interact with.
 * @author Victoria Lappas
 */

import javax.swing.*;

public class ProjectManagerWindow {
	private JFrame PMFrame;
	private JTabbedPane tabbedPane;
	
	//Project manager window constructor
	public ProjectManagerWindow() {
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
		
	}

	//Assign and remove bug screen
	public void createAssignBugScreen() {
		JPanel assignBugPanel = new JPanel();
		assignBugPanel.setLayout(null);
		
		JLabel unassignedBugsLabel = new JLabel("Unassigned Bugs ");
		String [] unassignedBugArray = {"Unassigned Bug 1", "Unassigned Bug 2", "Unassigned Bug 3"};
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(int i = 0; i < unassignedBugArray.length; i++) {
			listModel.addElement(unassignedBugArray[i]);
		}
		
		JList<String> unassignedBugList = new JList<String>(listModel);
		unassignedBugList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		unassignedBugList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane unassignedBugListScroller = new JScrollPane(unassignedBugList);
		
		JLabel developerLabel = new JLabel("Select developer: ");
		String [] developerArray = {"001 - Name", "002 - Name", "003 - Name"};
		JComboBox<String> developerList = new JComboBox<String>(developerArray);		
		developerList.setSelectedIndex(0);
		
		JButton removeBugButton = new JButton("Remove Bug");
		JButton assignButton = new JButton("Assign Bug");
		
		unassignedBugsLabel.setBounds(80,20,150,30);
		unassignedBugListScroller.setBounds(80,60,400,150);
		removeBugButton.setBounds(360,20,125,30);
		assignButton.setBounds(200,300,150,30);
		developerLabel.setBounds(120,250,150,30);
		developerList.setBounds(245,250,150,30);
		
		assignBugPanel.add(unassignedBugsLabel);
		assignBugPanel.add(unassignedBugListScroller);
		assignBugPanel.add(removeBugButton);
		assignBugPanel.add(assignButton);
		assignBugPanel.add(developerLabel);
		assignBugPanel.add(developerList);
		
		tabbedPane.addTab("Assign Bugs", assignBugPanel);
		
	}
	
	//Modify product screen 
	public void createModifyProductScreen() {
		JPanel modifyProductPanel = new JPanel();
		modifyProductPanel.setLayout(null);
		
		JLabel productListLabel = new JLabel("Product List ");
		String [] productArray = {"Product 1", "Product 2", "Product 3"};
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(int i = 0; i < productArray.length; i++) {
			listModel.addElement(productArray[i]);
		}
		
		JList<String> productList = new JList<String>(listModel);
		productList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		productList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane productListScroller = new JScrollPane(productList);
		
		JLabel productNameLabel = new JLabel("Product Name: ");	
		JTextField productNameField = new JTextField();
		
		JButton removeProductButton = new JButton("Remove Product");
		JButton addProductButton = new JButton("Add Product");
		
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
		String [] employeeArray = {"Employee 1", "Employee 2", "Employee 3"};
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(int i = 0; i < employeeArray.length; i++) {
			listModel.addElement(employeeArray[i]);
		}
		
		JList<String> employeeList = new JList<String>(listModel);
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
		
		JButton removeEmployeeButton = new JButton("Remove Employee");
		JButton addEmployeeButton = new JButton("Add Employee");
		JButton updateEmployeeButton = new JButton("Update Employee");
		
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
	
	// creates the generate report screen
	void createGenerateReportScreen() {
		JPanel generateReportPanel = new JPanel();
		generateReportPanel.setLayout(null);
		
		JLabel generateReportLabel1 = new JLabel("Generate a report for all bugs and their current status");
		JLabel generateReportLabel2 = new JLabel("This report will appear in your current working directory");
		JButton generateReportButton = new JButton("Generate Report");
		
		generateReportLabel1.setBounds(90,50,350,30);
		generateReportLabel2.setBounds(90,80,400,30);
		generateReportButton.setBounds(200,120,150,30);
		
		generateReportPanel.add(generateReportLabel1);
		generateReportPanel.add(generateReportLabel2);
		generateReportPanel.add(generateReportButton);
		
		tabbedPane.addTab("Generate Report", generateReportPanel);
		
		
	}
	
	
	public static void main(String[] args){
		ProjectManagerWindow PMWindow = new ProjectManagerWindow();
	}
	
	
}