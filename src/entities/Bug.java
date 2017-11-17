//class Bug
//An entity class to store information about a bug in a product
//@author Luke Elliott
//@author Victoria Lappas
//@author Evan Hampton
//Date: Nov. 13, 2017

package entities;

import java.util.Date;

public class Bug 
{
	private int bugID;
	private String bug_description;
	private int productID;
	private String status;
	private Date date_submitted;
	private int assigned_developer;
	private static int IDCount;	//TODO: IDCount will need to be initialized each time the system is booted.
	
	//Constructor of a Bug
	//@param description - Description of a bug in a product
	//@param prodID - ID of the product that has the bug
	public Bug(String description, int prodID)
	{
		bugID = IDCount++;
		bug_description = description;
		productID = prodID;
		status = "Submitted";	//Default status of a new bug
		date_submitted = new Date();	//This constructor allocates a new date object initialized to the time it is allocated.
		assigned_developer = -1;	//Set the developer ID to be a non-existent ID so that you can tell if the bug is assigned yet
	}
	
	//Constructor of a bug for reading out of the database
	//@param ID - ID of 'this'
	//@param description - description of 'this'
	//@param prodID - ID of the product associated with 'this'
	//@param stat - status of 'this'
	//@param dat - submission Date of 'this'
	//@param assDev - ID of the developer assigned to 'this'
	public Bug(int ID, String description, int prodID, String stat, Date dat, int assDev)
	{
		bugID = ID;
		bug_description = description;
		productID = prodID;
		status = stat;
		date_submitted = dat;
		assigned_developer = assDev;
	}
	
	//Can set the status of 'this' Bug
	//@param newStat - the new status to be set
	public void setStatus(String newStat)
	{
		status = newStat;
	}
	
	//Check to see if 'this' bug is currently assigned to a developer
	//@return false if bug not assigned, true if it is
	public boolean isAssigned()
	{
		return assigned_developer < 0 ? false : true;
	}
	
	//Set the working developer of this bug
	//@param devID - ID of the new current working developer
	public void setDeveloper(int devID)
	{
		assigned_developer = devID;
	}
	
	//@return - ID of 'this' bug
	public int getID()
	{
		return bugID;
	}
	
	//@return - Description of 'this' bug
	public String getDescription()
	{
		return bug_description;
	}
	
	//@return - the ID of the product that 'this' bug is associated with
	public int getProduct()
	{
		return productID;
	}
	
	//@return - the submission Date of 'this' Bug
	public Date getSubmissionDate()
	{
		return date_submitted;
	}
	
	@Override
	//This method allows for nice formatting in the GUI
	//@return - A String representation of 'this' bug
	public String toString()
	{
		return "Description: " + bug_description + ", " + "Status: " + status + ", " + "Date Submitted: " + date_submitted + ".";
	}
	
	//This method allows for consistent formatting when writing bugs to the database
	//The format of the string returned is "bugID bug_description productID status date_submitted assigned_developer"
	//@return - A String representation of 'this' bug
	public String toStringDatabase()
	{
		return bugID + ", " + bug_description + ", " + productID + ", " + status + ", " + date_submitted + ", " + assigned_developer;
	}
}
