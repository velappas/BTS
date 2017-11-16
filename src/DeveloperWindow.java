/*
 * DeveloperWindow is the class that provides a GUI for a developer to interact with.
 * @author Victoria Lappas
 */

<<<<<<< HEAD
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controllers.*;
=======
>>>>>>> branch 'master' of https://github.com/velappas/BTS.git
import javax.swing.*;

public class DeveloperWindow {
	private JFrame developerFrame;
	private JTabbedPane tabbedPane;
	private BugController bugController;
	
	//Developer window constructor
	public DeveloperWindow() {
		developerFrame = new JFrame();
		developerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		developerFrame.setTitle("Bug Tracking System - Developer Window");
		developerFrame.setSize(550,425);
		developerFrame.setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane();
		
		createBrowseAssignmentsScreen();
		createBrowseAllBugsScreen();
		
		developerFrame.add(tabbedPane);
		developerFrame.setVisible(true);
	}
	
	//browse assignments screen
	public void createBrowseAssignmentsScreen() {
		JPanel browseAssignmentsPanel = new JPanel();
		browseAssignmentsPanel.setLayout(null);
		
		JLabel assignmentLabel = new JLabel("Current Assigned Bugs ");
		String [] assignmentStringArray = {"Assignment 1", "Assignment 2", "Assignment 3", "249025", "23859", "249025", "23859", "249025", "23859", "249025", "23859", "249025", "23859", "249025", "23859", "249025", "23859", "249025", "23859", "249025", "23859", };
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(int i = 0; i < assignmentStringArray.length; i++) {
			listModel.addElement(assignmentStringArray[i]);
		}
		
		JList<String> assignmentList = new JList<String>(listModel);
		assignmentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		assignmentList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane assignmentListScroller = new JScrollPane(assignmentList);
		
		JRadioButton reportFixedButton = new JRadioButton("Report Fixed");
		reportFixedButton.setSelected(true);
		JRadioButton removeButton = new JRadioButton("Remove");

		ButtonGroup group = new ButtonGroup();
		group.add(reportFixedButton);
		group.add(removeButton);
		
		JButton updateButton = new JButton("Update Bug Status");
		
		updateButton.addActionListener(new ActionListener(){ //Update bug status listener implemented with an anonymous inner class
			public void actionPerformed(ActionEvent e){
				System.out.println("test");
			}
		});
		
		assignmentLabel.setBounds(80,20,150,30);
		assignmentListScroller.setBounds(80,60,400,200);
		reportFixedButton.setBounds(150,270,120,30);
		removeButton.setBounds(300,270,120,30);
		updateButton.setBounds(200,310,150,30);
		
		browseAssignmentsPanel.add(assignmentLabel);
		browseAssignmentsPanel.add(assignmentListScroller);
		browseAssignmentsPanel.add(removeButton);
		browseAssignmentsPanel.add(reportFixedButton);
		browseAssignmentsPanel.add(updateButton);
		
		tabbedPane.addTab("Browse Assignments", browseAssignmentsPanel);
		
	}
	
	//browse all bugs screen
	public void createBrowseAllBugsScreen() {
		JPanel browseBugsPanel = new JPanel();
		browseBugsPanel.setLayout(null);
		
		JLabel browseBugsLabel = new JLabel("Browse Bugs ");
		
		String [] bugStringArray = {"Sample Bug 1", "Sample Bug 2", "Sample Bug 3"};
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(int i = 0; i < bugStringArray.length; i++) {
			listModel.addElement(bugStringArray[i]);
		}
		
		JList<String> bugList = new JList<String>(listModel);
		bugList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		bugList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane bugListScroller = new JScrollPane(bugList);
		
		JLabel productLabel = new JLabel("Product ");
		String [] productStringArray = {"All bugs", "Sample Product 1", "Sample Product 2"};
		JComboBox<String> productList = new JComboBox<String>(productStringArray);		
		productList.setSelectedIndex(0);
		
		productList.addActionListener(new ActionListener(){ //Listener for determining selected product and listing related bugs
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> combo = (JComboBox<String>) e.getSource(); //establish a reference to the JComboBox, "this" can't be used in anonymous inner classes
		        String selectedProduct = (String) combo.getSelectedItem();
		        if(selectedProduct.equals("All bugs"))
		        {
		        	//TODO list all the bugs
		        }else{
			        //TODO after we get the selected item, we must find the product id of the selected product and list bugs for only that product
			        
			        	
		        }
	
			}	
		});
		
		
		browseBugsLabel.setBounds(80,20,150,30);
		bugListScroller.setBounds(80,60,400,200);
		productLabel.setBounds(280,20,150,30);
		productList.setBounds(330,20,150,30);
		
		browseBugsPanel.add(browseBugsLabel);
		browseBugsPanel.add(bugListScroller);
		browseBugsPanel.add(productLabel);
		browseBugsPanel.add(productList);
		
		tabbedPane.addTab("Browse Bugs", browseBugsPanel);
	}
	
	
	//public static void main(String[] args){
	//	DeveloperWindow developerWindow = new DeveloperWindow();
	//}
	
	
}
