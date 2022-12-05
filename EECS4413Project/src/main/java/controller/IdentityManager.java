package controller;

import bean.User;
import model.Model;

public static class IdentityManager{
	
	//Is called by CentralController for User LogIn, LogOut, registration and so forth
	private Model dataModel = Model.getInstance();
	private String user_id;
	private String pwd;
	private String name;
	private String surName;
	private String postalCode;
	private String address;
	
	public void userExists(String id) throws IllegalArgumentException{
		User user = dataModel.uDB.retrieve(id);
		if (user == null) {
			throw new IllegalArgumentException("User with this information does not exist");
		}
	}
	public void logIn(String id, String pass) throws IllegalArgumentException {
		userExists(id);
		user_id = id;
		pwd = pass;
		
		User user = dataModel.uDB.retrieve(user_id);
		if (!((user.getID().equals(user_id) && (user.getPassword().equals(pwd))))) {
			throw new IllegalArgumentException("Password is not correct");
		}
		dataModel.makeActive(id);
	}
	
	public void logOut(String id) {
		userExists(id);
		dataModel.makeInActive(id);
	}
	
	public void register(String id, String nomi, String aile, String postal, String address, String pass) throws IllegalArgumentException {
		boolean flag = false;
		try {
			userExists(id);
			flag = true;
		}catch(IllegalArgumentException e) {
			//User object creation
			User user = new User(user_id, name, surName, postalCode, address, pwd);
			dataModel.uDB.insert(user);
			logIn(user_id, pwd);
		}

		if(flag) {
			throw new IllegalArgumentException("User with this ID already exists");
		}
	}
	
	
}

