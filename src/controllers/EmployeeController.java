//class EmployeeController
//This class allows for management of employees in the database
//@author Luke Elliott
//@author Victoria Lappas
//@author Evan Hampton
//Date: Nov. 13, 2017

package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import entities.Employee;

public class EmployeeController 
{
	//Create a new Employee and enter it to the database
	//@param name - name of the employee to be entered to the database
	//@param password - password of the employee to be entered to the database
	//@param employee_type - type of the employee being entered to the database
	public void createEmployee(String name, String password, String employee_type) throws FileNotFoundException, IOException
	{
		Employee toSub = new Employee(employee_type, name, password);
		BufferedWriter fw = new BufferedWriter(new FileWriter("data/CurrentEmployees.txt", true));	//TODO: Hard coded in. Could change.
		fw.write(toSub.toStringDatabase());	//Write with toStringDatabase method for consistency
		fw.newLine();
		fw.flush();
		fw.close();
	}
	
	//Removes a specified employee from the database
	//This method will remove lines with matching employee IDs
	//@param emploee_id - ID of the employee to remove from the database
	//@return - false if employee not found in database, true otherwise
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from/writing to the database
	public boolean removeEmployee(int employee_id) throws FileNotFoundException, IOException
	{
		boolean found = false;
		File currentEmployees = new File("data/CurrentEmployees.txt");
		BufferedReader fr = new BufferedReader(new FileReader(currentEmployees));
		Vector<String> lines = new Vector<String>();
		
		while(true)
		{
			String tString = fr.readLine();	//Read a line from the database
			
			if(tString == null)
				break;
			
			if(Integer.parseInt(tString.split(", ")[0]) == employee_id)	//Check if the ID in the database matches the input
				found = true;											//If it matches, don't copy the line to the temporary vector
			else
				lines.add(tString);
		}
		fr.close();			
		
		BufferedWriter fw = new BufferedWriter(new FileWriter(currentEmployees, false));	//Make sure to write at the very beginning of the file.
		
		for(int i = 0; i < lines.size(); i++)	//Write the stored lines that aren't to be removed.
		{
			fw.write(lines.elementAt(i));
			fw.newLine();
			fw.flush();
		}

		fw.close();
		
		return found;
	}
	
	//Get a list of all employees in the database
	//@returns - null if no employees found, otherwise a list of all the employees
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	public Vector<Employee> getAllEmployees() throws FileNotFoundException, IOException
	{
		BufferedReader fr = new BufferedReader(new FileReader("data/CurrentEmployees.txt"));
		
		Vector<Employee> toReturn = new Vector<Employee>();
		
		while(true)
		{
			String temp = fr.readLine();
			
			if(temp == null)
				break;
			
			String arr[] = temp.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
			toReturn.add(new Employee(Integer.parseInt(arr[0]), arr[1], arr[2], arr[3]));
		}
		fr.close();
		
		if(toReturn.size() == 0)
			return null;
		else
			return toReturn;
	}
	
		//Updates the status of a specific employee in the database
		//@param employeeID - ID of the employee to modify
		//@param status - new status of the employee
		//@return - false if employee with ID employeeID not found, otherwise true
		//@throws FileNotFoundException - if the database pathname could not be found
		//@throws IOException - if an IO error occurs while reading from the database
		public boolean updateEmployeeType(int employeeID, String status) throws FileNotFoundException, IOException
		{
			boolean found = false;
			File currentEmployees = new File("data/CurrentEmployees.txt");
			BufferedReader fr = new BufferedReader(new FileReader(currentEmployees));
			Vector<String> lines = new Vector<String>();
			
			while(true)
			{
				String tString = fr.readLine();	//Read a line from the database
				
				if(tString == null)
					break;
				
				if(Integer.parseInt(tString.split(", ")[0]) == employeeID)	//Check if the ID in the database matches the input
				{
					found = true;
					String arr[] = tString.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
					Employee temp = new Employee(Integer.parseInt(arr[0]), status, arr[2], arr[3]);
					lines.add(temp.toStringDatabase());	//Add the edited employee. Everything will be the same except for the employee type field
				}
				else
					lines.add(tString);
			}
			fr.close();			
			
			BufferedWriter fw = new BufferedWriter(new FileWriter(currentEmployees, false));	//Make sure to write at the very beginning of the file.
			
			for(int i = 0; i < lines.size(); i++)	//Write all of the stored lines.
			{
				fw.write(lines.elementAt(i));
				fw.newLine();
				fw.flush();
			}

			fw.close();
			
			return found;
		}
		
		//Updates the status of a specific employee in the database
		//@param employeeID - ID of the employee to modify
		//@param newName - new name of the employee in the database
		//@param newPass - new password of the employee in the database
		//@return - false if employee with ID employeeID not found, otherwise true
		//@throws FileNotFoundException - if the database pathname could not be found
		//@throws IOException - if an IO error occurs while reading from the database
		public boolean updateEmployeeNameAndPass(int employeeID, String newName, String newPass) throws FileNotFoundException, IOException
		{
			boolean found = false;
			File currentEmployees = new File("data/CurrentEmployees.txt");
			BufferedReader fr = new BufferedReader(new FileReader(currentEmployees));
			Vector<String> lines = new Vector<String>();
					
			while(true)
			{
				String tString = fr.readLine();	//Read a line from the database
						
				if(tString == null)
					break;
						
				if(Integer.parseInt(tString.split(", ")[0]) == employeeID)	//Check if the ID in the database matches the input
				{
					found = true;
					String arr[] = tString.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
					Employee temp = new Employee(Integer.parseInt(arr[0]), arr[1], newName, newPass);
					lines.add(temp.toStringDatabase());	//Add the edited employee.
				}
				else
					lines.add(tString);
			}
			fr.close();			
					
			BufferedWriter fw = new BufferedWriter(new FileWriter(currentEmployees, false));	//Make sure to write at the very beginning of the file.
					
			for(int i = 0; i < lines.size(); i++)	//Write all of the stored lines.
			{
				fw.write(lines.elementAt(i));
				fw.newLine();
				fw.flush();
			}

			fw.close();
					
			return found;
		}
}
