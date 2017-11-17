package entities;

import java.util.ArrayList;

public class EmployeeSingleton {
	private static EmployeeSingleton onlyInstance;
	private ArrayList<String> usernameList;
	private ArrayList<String> passwordList;
	private ArrayList<String> nameList;
	private ArrayList<Integer> idList;
	
	private EmployeeSingleton(){
		usernameList = new ArrayList<String>();
		passwordList = new ArrayList<String>();
		nameList = new ArrayList<String>();
		idList = new ArrayList<Integer>();
	}
	
	public static EmployeeSingleton getOnlyInstance(){
		if (onlyInstance == null)
		{
			onlyInstance = new EmployeeSingleton();
		}
		return onlyInstance;
	}
	
	public void addUserName(String username)
	{
		usernameList.add(username);
	}
	
	public void addPassword(String pass)
	{
		passwordList.add(pass);
	}
	
	public void addName(String name)
	{
		nameList.add(name);
	}
	
	public void addID(Integer id)
	{
		idList.add(id);
	}
}
