//class BugController
//A controller class to communicate with the 'database' and manage bugs in the system
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
		BufferedWriter fw = new BufferedWriter(new FileWriter("CurrentBugs.txt", true));	//TODO: Hard coded in. Could change.
		fw.write(toSub.toString());	//Write with toString method for consistency
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
		File currentBugs = new File("CurrentBugs.txt");
		BufferedReader fr = new BufferedReader(new FileReader(currentBugs));
		Vector<String> lines = new Vector<String>();
		
		while(true)
		{
			String tString = fr.readLine();	//Read a line from the database
			
			if(tString == null)
				break;
			
			if(Integer.parseInt(tString.split(", ")[0]) == bug_ID)	//Check if the ID in the database matches the input
				found = true;										//If it matches, don't copy the line to the temporary file
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
	//@return - Returns a vector containing objects of all bugs in the database
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	public Vector<Bug> browseAllBugs() throws FileNotFoundException, IOException
	{
		BufferedReader fr = new BufferedReader(new FileReader("CurrentBugs.txt"));
		
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
		return toReturn;
	}
//	+browseProductBugs(int product_ID): Bug [ ] 
//	+browseAssignedBugs(int developer_id): Bug []
//	+assignDeveloper(int bug_id, int developer_id): void
//	+updateBugState(String status): void
//	+generateBugReport(Bug [ ] all_bugs): File 
	
	public static void main(String [] args)
	{
		BugController test = new BugController();
		try
		{
			test.submitBug("test1", 1);
			test.submitBug("test2", 212);
			test.submitBug("test3", 132321);
			Vector<Bug> vec = test.browseAllBugs();
			for(int i = 0; i < vec.size(); i++)
				System.out.println(vec.get(i));
		}
		catch(Exception a)
		{
			System.out.println("fsdafdsa");
			a.printStackTrace();
		}
	}
}
