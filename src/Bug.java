//class Bug
//An entity class to store information about a bug in a product
//@author Evan Hampton

import java.util.Date;

public class Bug 
{
	private int bugID;
	private String bug_description;
	private int productID;
	private String status;
	private Date date_submitted;
	private int assigned_developer;
	private static int IDCount;
	
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
}
