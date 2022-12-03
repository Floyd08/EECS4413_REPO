package model;

import java.util.ArrayList;

public class Model {
	
	private static Model instance;
	public ItemDAO iDB;
	public UserDAO uDB;
	public ArrayList<String> loggedIn = new ArrayList<String>();

	private Model() {
		
		iDB = new ItemDAO();
		uDB = new UserDAO();
	}
	
	public static Model getInstance() {
		
		if (instance == null) {
			
			instance = new Model();
		}
		
		return instance;
	}

	public boolean makeActive(String ID) throws NullPointerException {
		
		if ( !loggedIn.contains(ID) )
			return loggedIn.add(ID);
		
		return false;
	}
	
	public boolean makeInActive(String ID) {
		
		return loggedIn.remove(ID);
	}
}  
