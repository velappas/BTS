//class Product
//This class is an entity class for storing information about a software product in the system
//@author Luke Elliott
//@author Victoria Lappas
//@author Evan Hampton
//Date: Nov. 13, 2017

package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Product 
{
	private int product_id; 
   	private String name;
   	private static int IDCount;	//TODO: IDCount will need to be initialized each time the system is booted
   	
   	//Constructor for a Product
   	//@param namein - this.name = namein
   	public Product(String namein)
   	{
   		name = namein;
   		product_id = IDCount++;
   		try
   		{
   			File file = new File("data/startup.txt");
   			BufferedReader f = new BufferedReader(new FileReader(file));
   			String startupinfo = f.readLine();
   			int id0 = Integer.parseInt(startupinfo.split(", ")[0]);
   			int id1 = Integer.parseInt(startupinfo.split(", ")[1]);
   			f.close();
   			
   			BufferedWriter fo = new BufferedWriter(new FileWriter(file, false));	//Make sure to write at beginning of file
   			fo.write(id0 + ", " + id1 + ", " + IDCount);
   			fo.close();
   		}
   		catch(IOException a)
   		{
   			a.printStackTrace();
   		}
   	}
   	
   	//Constructor for a Product to simplify "database" access
   	//@param ID - the ID of 'this' product
   	//@param namein - the name of 'this' product
   	public Product(int ID, String namein)
   	{
   		product_id = ID;
   		name = namein;
   	}
   	
   	//@return - this.product_id
   	public int getProductID()
   	{
   		return product_id;
   	}
   	
   	//@return - this.name
   	public String getName()
   	{
   		return name;
   	}
   	
   	@Override
	//This method allows for nice formatting in the GUI
	//@return - A String representation of 'this' Product
	public String toString()
	{
		return "Product Name: " + name + ".";
	}
   	
	//This method allows for consistent formatting when writing products to the database
	//The format of the string returned is "product_id name"
	//@return - A String representation of 'this' Product
	public String toStringDatabase()
	{
		return product_id + ", " + name;
	}
	
	 //Allows IDCount to be set each time the system is booted
  	//@throws IOException - if an issue occurs when reading from the startup file
    public static void setIDCount() throws IOException
	{
		BufferedReader f = new BufferedReader(new FileReader("data/startup.txt"));
		String startupinfo = f.readLine();
		IDCount = Integer.parseInt(startupinfo.split(", ")[2]);
		f.close();
	}
}
