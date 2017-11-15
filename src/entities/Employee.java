//class Employee
//An entity class for storing information about an employee in the system
//@author Luke Elliott
//@author Victoria Lappas
//@author Evan Hampton
//Date: Nov. 14, 2017

//TODO: Finish this class

package entities;

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
    
    @Override
    //This method allows for consistent formatting when writing products to the database
  	//The format of the string returned is "employee_id employee_type name password"
  	//@return - A String representation of 'this' Product
    public String toString()
    {
    	return employee_id + ", " + employee_type + ", " + name + ", " + password;
    }
}
