//class Product
//This class is an entity class for storing information about a software product in the system
//@author Luke Elliott
//@author Victoria Lappas
//@author Evan Hampton
//Date: Nov. 13, 2017

package entities;

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
	//This method allows for consistent formatting when writing products to the database
	//The format of the string returned is "product_id name"
	//@return - A String representation of 'this' Product
	public String toString()
	{
		return product_id + ", " + name;
	}
}
