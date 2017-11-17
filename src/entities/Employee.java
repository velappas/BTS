//class Employee
//An entity class for storing information about an employee in the system
//@author Luke Elliott
//@author Victoria Lappas
//@author Evan Hampton
//Date: Nov. 14, 2017

//TODO: Finish this class

package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Employee 
{
	private int employee_id;
	private String employee_type;
	private String name;
	private String password;
    private static int IDCount;	//TODO: This will need to be initialized to a stored value when the system is rebooted
    
    //Constructor for an Employee
    //@param type - the type of 'this' employee
    //@param nam - the name of 'this' employee
    //@param pass - the password of 'this' employee
    public Employee(String type, String nam, String pass)
    {
    	employee_id = IDCount++;
    	employee_type = type;
    	name = nam;
    	password = pass;
    	
    	try
   		{
   			File file = new File("data/startup.txt");
   			BufferedReader f = new BufferedReader(new FileReader(file));
   			String startupinfo = f.readLine();
   			int id0 = Integer.parseInt(startupinfo.split(", ")[0]);
   			int id2 = Integer.parseInt(startupinfo.split(", ")[2]);
   			f.close();
   			
   			BufferedWriter fo = new BufferedWriter(new FileWriter(file, false));	//Make sure to write at beginning of file
   			fo.write(id0 + ", " + IDCount + ", " + id2);
   			fo.close();
   		}
   		catch(IOException a)
   		{
   			a.printStackTrace();
   		}
    }
    
    //Convenient constructor for creating an Employee from the database
    //@param id - the ID of 'this' employee
    //@param type - the type of 'this' employee
    //@param nam - the name of 'this' employee
    //@param pass - the password of 'this' employee
    public Employee(int id, String type, String nam, String pass)
    {
    	employee_id = id;
    	employee_type = type;
    	name = nam;
    	password = pass;
    }
    
    //@return - this.employee_type
    public String getEmployeeType()
    {
    	return employee_type;
    }
    
    //@return - this.name
    public String getName()
    {
    	return name;
    }
    
    //@return - this.password
    public String getPassword()
    {
    	return password;
    }
    
    /**
     * 
     * @return the ID number of this employee
     */
    public int getID()
    {
    	return employee_id;
    }
    
    @Override
    //This method allows for nice formatting in the gui
  	//@return - A String representation of 'this' Product
    public String toString()
    {
    	return "Employee name: " + name + ", Employee Password: " + password + ", Employee Type: " + employee_type + ".";
    }
    
    //This method allows for consistent formatting when writing products to the database
  	//The format of the string returned is "employee_id employee_type name password"
  	//@return - A String representation of 'this' Product
    public String toStringDatabase()
    {
    	return employee_id + ", " + employee_type + ", " + name + ", " + password;
    }
    
    //Allows IDCount to be set each time the system is booted
  	//@throws IOException - if an issue occurs when reading from the startup file
    public static void setIDCount() throws IOException
	{
		BufferedReader f = new BufferedReader(new FileReader("data/startup.txt"));
		String startupinfo = f.readLine();
		IDCount = Integer.parseInt(startupinfo.split(", ")[1]);
		f.close();
	}
}
