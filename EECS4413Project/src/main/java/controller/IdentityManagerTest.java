package controller;

import java.security.NoSuchAlgorithmException;

import bean.User;
import model.Model;
import controller.IdentityManager;

public class IdentityManagerTest {
	
	

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		
		
		System.out.println("The results for if the user exists: "+ testUserExists() + " , login: " + testLogIn() + " , logout: " + testLogOut() + " , and registering: "+ testRegister());

	}
	
	public static boolean testUserExists() {
		boolean flag_exists, flag_nexists; 
		//Exists
		User u1 = new User("iD", "name", "surName", "postalCode", "address", "password");
		flag_exists = IdentityManager.userExists(u1.getID());
		
		//Does not exist
		flag_nexists = IdentityManager.userExists(null);
		return flag_exists && (!flag_nexists);	
	}
	
	//Maraakesh
	
	public static boolean testLogIn() throws NoSuchAlgorithmException {
		Model dataModel = Model.getInstance();
		User u2 = new User("iD", "name", "surName", "postalCode", "address", "password");
		return  (("{\r\n"
				+ "  \"status\": 200,\r\n"
				+ "  \"message\": \"Log in successful\",\r\n").equals(IdentityManager.logIn(u2.getID(),u2.getPassword()))) && dataModel.loggedIn.contains(u2.getID());
		
	}
	
	public static boolean testLogOut() {
		Model dataModel = Model.getInstance();
		User u3 = new User("iD", "name", "surName", "postalCode", "address", "password");
		return  (("{\r\n"
				+ "  \"status\": 200,\r\n"
				+ "  \"message\": \"Log out successful\",\r\n").equals(IdentityManager.logOut(u3.getID()))) && (!dataModel.loggedIn.contains(u3.getID()));
		
	}
	
	public static boolean testRegister() throws NoSuchAlgorithmException {
		Model dataModel = Model.getInstance();
		return (("{\r\n"
				+ "  \"status\": 200,\r\n"
				+ "  \"message\": \"Register successful\",\r\n").equals(IdentityManager.register("id", "name", "surName", "postalCode", "address", "password"))) && (dataModel.uDB.retrieve("id") != null);
	}

}
