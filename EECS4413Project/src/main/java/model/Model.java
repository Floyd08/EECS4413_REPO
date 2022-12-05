package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.Index;

import bean.ItemP;
import bean.ShoppingCart;
import bean.User;

public class Model {
	
	private static Model instance;
	public ItemDAO iDB;
	public UserDAO uDB;
	public ArrayList<String> loggedIn = new ArrayList<String>();
	public HashMap<String, ShoppingCart> carts = new HashMap<String, ShoppingCart>();	//Maps user IDs to ShoppingCarts

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








