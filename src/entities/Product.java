//class Product
//This class is an entity class for storing information about a software product in the system
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
}
