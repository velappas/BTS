/*
 * DeveloperWindow is the class that provides a GUI for a developer to interact with.
 * @author Victoria Lappas
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import controllers.*;
import entities.*;

import javax.swing.*;

public class DeveloperWindow {
	private JFrame developerFrame;
	private JTabbedPane tabbedPane;
	private BugController bugController;
	private Employee developer;
	
	//Developer window constructor
	public DeveloperWindow(Employee devIn) {
		developer = devIn;
		bugController = new BugController();
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
		DefaultListModel<Bug> listModel = new DefaultListModel<Bug>();
		Vector<Bug> bugVector = null;
		
		/*try{ //Try to display the assignments assigned to the logged in developer
			bugVector = bugController.browseAssignedBugs(developer.getID());
		}catch(FileNotFoundException fnf){
			JOptionPane err = new JOptionPane("Problem getting the bugs from the database.", JOptionPane.ERROR_MESSAGE);
		}catch(IOException ioe){
			JOptionPane err = new JOptionPane("Unexpected IOException occurred.", JOptionPane.ERROR_MESSAGE);
		}*/
		
		if(bugVector != null)
		{
			for(int i = 0; i < bugVector.size(); i++) {
				listModel.addElement(bugVector.get(i));
			}
		}
		
		JList<Bug> assignmentList = new JList<Bug>(listModel);
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
				if (reportFixedButton.isSelected())
				{
					//bugController.updateBugState(,"Fixed");
				} else if (removeButton.isSelected()) //delete
				{
					
				}
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
		
		//TODO get list of bugs from the database
		DefaultListModel<Bug> listModel = new DefaultListModel<Bug>();
		
		
		JList<Bug> bugList = new JList<Bug>(listModel);
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
		        try {
		        	if(selectedProduct.equals("All bugs"))
		        	{
			        	Vector<Bug> temp = bugController.browseAllBugs();
			        	bugList.setListData(temp);
			        }else{
			        	Vector<Bug> temp = bugController.browseProductBugs(((Product)productList.getSelectedItem()).getProductID());
						if(temp != null)
							bugList.setListData(bugController.browseProductBugs(((Product)productList.getSelectedItem()).getProductID()));
						else
							bugList.setListData(new Vector<Bug>()); //Just put in an empty vector 	
			        }    
		        }catch(IOException ioe){
		        	JOptionPane err = new JOptionPane("Problem getting the bugs from the database.", JOptionPane.ERROR_MESSAGE);
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
	
	
	public static void main(String[] args){
		DeveloperWindow developerWindow = new DeveloperWindow(new Employee("", "", ""));
	}
	
	
}
