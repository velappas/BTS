
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import controllers.*;
import entities.*;

import javax.swing.*;
/**
 * Boundary class that presents the developer with their developer options
 * @author Luke Elliott
 *
 */
public class DeveloperWindow {
	private JFrame developerFrame;
	private JTabbedPane tabbedPane;
	private BugController bugController;
	private ProductController productController;
	private Employee developer;
	
	/**
	 * Constructs the developer window
	 * @param devID the ID of the developer who logged in, this is used to fetch the specific user's assignments
	 */
	public DeveloperWindow(Employee devID) {
		developer = devID;
		bugController = new BugController();
		productController = new ProductController();
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
	
	/**
	 * Creates the browseAssignments tab and then adds it to the tabbed pane
	 */
	public void createBrowseAssignmentsScreen() {
		
			JPanel browseAssignmentsPanel = new JPanel();
			browseAssignmentsPanel.setLayout(null);
			
			JLabel assignmentLabel = new JLabel("Current Assigned Bugs ");
			DefaultListModel<Bug> listModel = new DefaultListModel<Bug>();
			Vector<Bug> bugVector = null;
			
			 //Try to add the assignments to the list
			try{
				bugVector = bugController.browseAssignedBugs(developer.getID());
			}catch (FileNotFoundException fnf){
				JOptionPane err = new JOptionPane("File not found in the database.", JOptionPane.ERROR_MESSAGE);
			}catch (IOException ioe){
				JOptionPane err = new JOptionPane("Unexpected IOException thrown.", JOptionPane.ERROR_MESSAGE);
			}

			
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
					if (reportFixedButton.isSelected()) //report fixed
					{
						try {
							bugController.updateBugState(assignmentList.getSelectedValue().getID(),"Fixed");
							listModel.remove(assignmentList.getSelectedValue().getID());
						} catch (FileNotFoundException e1) {
							JOptionPane err = new JOptionPane("Couldn't find the file to write to.", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							JOptionPane err = new JOptionPane("Unexpected IOException occurred.", JOptionPane.ERROR_MESSAGE);
						}
					} else if (removeButton.isSelected()) //delete
					{
						try {
							bugController.removeBug(assignmentList.getSelectedValue().getID());
							assignmentList.remove(assignmentList.getSelectedValue().getID());
						} catch (FileNotFoundException e1) {
							JOptionPane err = new JOptionPane("Couldn't find the bug file to remove from.", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							JOptionPane err = new JOptionPane("Unexpected IOException occurred.", JOptionPane.ERROR_MESSAGE);
						}	
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
	
	/**
	 * Creates the browse all bugs tab and adds it to the tabbed pane
	 */
	public void createBrowseAllBugsScreen() {
		try{
			JPanel browseBugsPanel = new JPanel();
			browseBugsPanel.setLayout(null);
			
			
			
			

			
			
			JLabel productLabel = new JLabel("Product ");
			
			Vector<Product> moddedProductVector = new Vector<Product>();
			moddedProductVector.add(new Product("All bugs"));
			Vector<Product> productVector = productController.browseAllProducts();
			JComboBox<Product> productList = new JComboBox<Product>(moddedProductVector);		
			
			for (int i = 0; i < productVector.size(); i++) //add the products to the list with "all bugs" as the first option
			{
				moddedProductVector.add(productVector.elementAt(i));
			}
			productList.setSelectedIndex(0);
			
			JLabel browseBugsLabel = new JLabel("Browse Bugs ");
			
			Vector<Bug> bugVector = bugController.browseAllBugs(); //show all the bugs initially
			
			DefaultListModel<Bug> listModel = new DefaultListModel<Bug>();
			
			if(bugVector != null)
			{
				for(int i = 0; i < bugVector.size(); i++) {
					listModel.addElement(bugVector.get(i));
				}
			}
			
			JList<Bug> bugList = new JList<Bug>(listModel);
			
			productList.addActionListener(new ActionListener(){ //Listener for determining selected product and listing related bugs
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> combo = (JComboBox<String>) e.getSource(); //establish a reference to the JComboBox, "this" can't be used in anonymous inner classes
			        String selectedProduct = combo.getSelectedItem().toString();
			        try {
			        	if(productList.getSelectedIndex() == 0)
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
			
			bugList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			bugList.setLayoutOrientation(JList.VERTICAL);
			JScrollPane bugListScroller = new JScrollPane(bugList);
			
			browseBugsLabel.setBounds(80,20,150,30);
			bugListScroller.setBounds(80,60,400,200);
			productLabel.setBounds(280,20,150,30);
			productList.setBounds(330,20,150,30);
			
			browseBugsPanel.add(browseBugsLabel);
			browseBugsPanel.add(bugListScroller);
			browseBugsPanel.add(productLabel);
			browseBugsPanel.add(productList);
			
			tabbedPane.addTab("Browse Bugs", browseBugsPanel);
		
		}catch(IOException e){
			JOptionPane err = new JOptionPane("Issue reading from database.", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/*public static void main(String[] args){
		DeveloperWindow developerWindow = new DeveloperWindow(new Employee("", "", ""));
	}*/
	
	
}
