//class BugController
//A controller class to communicate with the 'database' and manage bugs in the system
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
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Vector;

import entities.Bug;

public class BugController 
{
	//Creates a new Bug and submits it to the database
	//@param bug_description - description of the found Bug
	//@param product_ID - ID of the product associated with the found bug
	//@throws IOException - if there is an issue writing to the database
	public void submitBug(String bug_description, int product_ID) throws IOException
	{
		Bug toSub = new Bug(bug_description, product_ID);
		BufferedWriter fw = new BufferedWriter(new FileWriter("data/CurrentBugs.txt", true));	//TODO: Hard coded in. Could change.
		fw.write(toSub.toStringDatabase());	//Write with toStringDatabse method for consistency
		fw.newLine();
		fw.flush();
		fw.close();
	}
	
	//Removes the lines in the text file associated with bug_ID
	//Will delete multiple lines if there are duplicate bug_ID's
	//@param bug_ID - ID of the bug to delete from the text file
	//@return - Returns true if a bug with a matching ID is found and removed. Returns false if no bug found
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from/writing to the database
	public boolean removeBug(int bug_ID) throws FileNotFoundException, IOException
	{
		boolean found = false;
		File currentBugs = new File("data/CurrentBugs.txt");
		BufferedReader fr = new BufferedReader(new FileReader(currentBugs));
		Vector<String> lines = new Vector<String>();
		
		while(true)
		{
			String tString = fr.readLine();	//Read a line from the database
			
			if(tString == null)
				break;
			
			if(Integer.parseInt(tString.split(", ")[0]) == bug_ID)	//Check if the ID in the database matches the input
				found = true;										//If it matches, don't copy the line to the temporary vector
			else
				lines.add(tString);
		}
		fr.close();			
		
		BufferedWriter fw = new BufferedWriter(new FileWriter(currentBugs, false));	//Make sure to write at the very beginning of the file.
		
		for(int i = 0; i < lines.size(); i++)	//Write the stored lines that aren't to be removed.
		{
			fw.write(lines.elementAt(i));
			fw.newLine();
			fw.flush();
		}

		fw.close();
		
		return found;
	}
	
	//This function scans the Bug database and returns a list of all bugs
	//@return - Returns a vector containing objects of all bugs in the database, or null if nothing found
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	public Vector<Bug> browseAllBugs() throws FileNotFoundException, IOException
	{
		BufferedReader fr = new BufferedReader(new FileReader("data/CurrentBugs.txt"));
		
		Vector<Bug> toReturn = new Vector<Bug>();
		while(true)
		{
			String temp = fr.readLine();
			
			if(temp == null)
				break;
			
			SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	//Dates in the database are formatted like this
			String arr[] = temp.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
			toReturn.add(new Bug(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), arr[3], date.parse(arr[4], new ParsePosition(0)), Integer.parseInt(arr[5])));
		}
		fr.close();
		
		if(toReturn.size() == 0)
			return null;
		else
			return toReturn;
	}
	
	//Browse bugs pertaining to a specific product
	//@param product_ID - The return value is the list of bugs associated with the product with return value product_ID
	//@return - null if no bugs found, or a Vector containing all of the bugs found associated with the specified ID
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	public Vector<Bug> browseProductBugs(int product_ID) throws FileNotFoundException, IOException
	{
		Vector<Bug> toReturn = new Vector<Bug>();
		BufferedReader fr = new BufferedReader(new FileReader("data/CurrentBugs.txt"));
		
		while(true)
		{
			String temp = fr.readLine();
			
			if(temp == null)
				break;
			
			if(Integer.parseInt(temp.split(", ")[2]) == product_ID)	//Only add the bug to the return value if the product ID matches
			{
				SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	//Dates in the database are formatted like this
				String arr[] = temp.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
				toReturn.add(new Bug(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), arr[3], date.parse(arr[4], new ParsePosition(0)), Integer.parseInt(arr[5])));
			}
		}
		
		fr.close();
		
		if(toReturn.size() == 0)
			return null;
		else
			return toReturn;
	}
	
	//Browse bugs assigned to a specific developer
	//@param developer_id - The return value is the list of bugs assigned to the developer with ID == developer_id
	//@return - null if no bugs found, or a Vector containing all of the bugs assigned to the developer with developer ID equal to developer_id
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	Vector<Bug> browseAssignedBugs(int developer_id) throws FileNotFoundException, IOException
	{
		Vector<Bug> toReturn = new Vector<Bug>();
		BufferedReader fr = new BufferedReader(new FileReader("data/CurrentBugs.txt"));
		
		while(true)
		{
			String temp = fr.readLine();
			
			if(temp == null)
				break;
			
			if(Integer.parseInt(temp.split(", ")[5]) == developer_id)	//Only add the bug to the return value if the developer ID matches
			{
				SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	//Dates in the database are formatted like this
				String arr[] = temp.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
				toReturn.add(new Bug(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), arr[3], date.parse(arr[4], new ParsePosition(0)), Integer.parseInt(arr[5])));
			}
		}
		
		fr.close();
		
		if(toReturn.size() == 0)
			return null;
		else
			return toReturn;
	}
	
	//Sets the assigned_developer field of a bug in the database
	//Will assign duplicate bugs, although there shouldn't be any in the database
	//@param bug_id - the ID of the bug to change
	//@param developer_id - the ID of the developer that will work on this bug
	//@return - false if bug with ID bug_id not found in database, otherwise true
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	boolean assignDeveloper(int bug_id, int developer_id) throws FileNotFoundException, IOException
	{
		boolean found = false;
		File currentBugs = new File("data/CurrentBugs.txt");
		BufferedReader fr = new BufferedReader(new FileReader(currentBugs));
		Vector<String> lines = new Vector<String>();
		
		while(true)
		{
			String tString = fr.readLine();	//Read a line from the database
			
			if(tString == null)
				break;
			
			if(Integer.parseInt(tString.split(", ")[0]) == bug_id)	//Check if the ID in the database matches the input
			{
				found = true;
				SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	//Dates in the database are formatted like this
				String arr[] = tString.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
				Bug temp = new Bug(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), arr[3], date.parse(arr[4], new ParsePosition(0)), developer_id);
				lines.add(temp.toStringDatabase());	//Add the edited bug. Everything will be the same except for the changed developer ID field
			}
			else
				lines.add(tString);
		}
		fr.close();			
		
		BufferedWriter fw = new BufferedWriter(new FileWriter(currentBugs, false));	//Make sure to write at the very beginning of the file.
		
		for(int i = 0; i < lines.size(); i++)	//Write all of the stored lines.
		{
			fw.write(lines.elementAt(i));
			fw.newLine();
			fw.flush();
		}

		fw.close();
		
		return found;
	}
	
	//Updates the status of a specific bug in the database
	//@param bugID - ID of the bug to modify
	//@param status - new status of the bug
	//@return - false if bug with ID bugID not found, otherwise true
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	public boolean updateBugState(int bugID, String status) throws FileNotFoundException, IOException
	{
		boolean found = false;
		File currentBugs = new File("data/CurrentBugs.txt");
		BufferedReader fr = new BufferedReader(new FileReader(currentBugs));
		Vector<String> lines = new Vector<String>();
		
		while(true)
		{
			String tString = fr.readLine();	//Read a line from the database
			
			if(tString == null)
				break;
			
			if(Integer.parseInt(tString.split(", ")[0]) == bugID)	//Check if the ID in the database matches the input
			{
				found = true;
				SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	//Dates in the database are formatted like this
				String arr[] = tString.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
				Bug temp = new Bug(Integer.parseInt(arr[0]), arr[1], Integer.parseInt(arr[2]), status, date.parse(arr[4], new ParsePosition(0)), Integer.parseInt(arr[5]));
				lines.add(temp.toStringDatabase());	//Add the edited bug. Everything will be the same except for the changed developer ID field
			}
			else
				lines.add(tString);
		}
		fr.close();			
		
		BufferedWriter fw = new BufferedWriter(new FileWriter(currentBugs, false));	//Make sure to write at the very beginning of the file.
		
		for(int i = 0; i < lines.size(); i++)	//Write all of the stored lines.
		{
			fw.write(lines.elementAt(i));
			fw.newLine();
			fw.flush();
		}

		fw.close();
		
		return found;
	}
	
	//public File generateBugReport(Bug [] all_bugs)
	//TODO: Finish this method
}
