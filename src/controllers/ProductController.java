//class ProductController
//This class allows for management of products in the database
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
import entities.Product;

public class ProductController 
{
	//Creates a new product and submits it to the DataBase
	//@param name - name of the new product
	//@throws IOException - if there is an issue writing to the database
	public void createProduct(String name) throws IOException
	{
		Product toSub = new Product(name);
		BufferedWriter fw = new BufferedWriter(new FileWriter("CurrentProducts.txt", true));	//TODO: Hard coded in. Could change.
		fw.write(toSub.toString());	//Write with toString method for consistency
		fw.newLine();
		fw.flush();
		fw.close();
	}
	
	//Removes a specified product from the database
	//This method will remove lines with matching product IDs
	//@param productID - ID of the product to remove from the database
	//@return - false if product not found in database, true otherwise
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from/writing to the database
	public boolean removeProduct(int productID) throws FileNotFoundException, IOException
	{
		boolean found = false;
		File currentProducts = new File("CurrentProducts.txt");
		BufferedReader fr = new BufferedReader(new FileReader(currentProducts));
		Vector<String> lines = new Vector<String>();
		
		while(true)
		{
			String tString = fr.readLine();	//Read a line from the database
			
			if(tString == null)
				break;
			
			if(Integer.parseInt(tString.split(", ")[0]) == productID)	//Check if the ID in the database matches the input
				found = true;											//If it matches, don't copy the line to the temporary vector
			else
				lines.add(tString);
		}
		fr.close();			
		
		BufferedWriter fw = new BufferedWriter(new FileWriter(currentProducts, false));	//Make sure to write at the very beginning of the file.
		
		for(int i = 0; i < lines.size(); i++)	//Write the stored lines that aren't to be removed.
		{
			fw.write(lines.elementAt(i));
			fw.newLine();
			fw.flush();
		}

		fw.close();
		
		return found;
	}
	
	//Get a list of all products in the database
	//@returns - null if no products found, otherwise a list of all the products
	//@throws FileNotFoundException - if the database pathname could not be found
	//@throws IOException - if an IO error occurs while reading from the database
	public Vector<Product> browseAllProducts() throws FileNotFoundException, IOException
	{
		BufferedReader fr = new BufferedReader(new FileReader("CurrentProducts.txt"));
		
		Vector<Product> toReturn = new Vector<Product>();
		while(true)
		{
			String temp = fr.readLine();
			
			if(temp == null)
				break;
			
			String arr[] = temp.split(", ");	//Split the vector by spaces and commas, as that's how they are organized in the database
			toReturn.add(new Product(Integer.parseInt(arr[0]), arr[1]));
		}
		fr.close();
		
		if(toReturn.size() == 0)
			return null;
		else
			return toReturn;
	}
}
