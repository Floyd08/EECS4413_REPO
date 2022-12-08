package controller;

import java.security.NoSuchAlgorithmException;

import bean.User;
import model.Model;

/**
 * 
 * @author Keshav Apparasu
 *
 */
public final class IdentityManager{
	
	//Is called by CentralController for User LogIn, LogOut, registration and so forth
	
	
	
	public static boolean userExists(String id){
		Model dataModel = Model.getInstance();
		User user = dataModel.uDB.retrieve(id);
		boolean exist = true;
		if (user == null) {
			exist = false;
		}
		return exist;
	}
	
	public static String logIn(String id, String pass) throws NoSuchAlgorithmException{
		Model dataModel = Model.getInstance();
		String res = "{\r\n"
				+ "  \"status\": 200,\r\n"
				+ "  \"message\": \"Log in successful\",\r\n";
		if(!userExists(id)) {
			res = "{\r\n"
					+ "  \"status\": 400,\r\n"
					+ "  \"message\": \"User does not exist\",\r\n";
		}
		
		User user = dataModel.uDB.retrieve(id);
		if (!((user.getID().equals(id) && (user.getPassword().equals(user.hashPassword(pass)))))) {
			res = "{\r\n"
					+ "  \"status\": 400,\r\n"
					+ "  \"message\": \"The password is wrong\",\r\n";
		}
		dataModel.makeActive(id);
		return res;
	}
	
	public static String logOut(String id) {
		Model dataModel = Model.getInstance();
		String res = "{\r\n"
				+ "  \"status\": 200,\r\n"
				+ "  \"message\": \"Log out successful\",\r\n";
		if(!userExists(id)) {
			res = "{\r\n"
					+ "  \"status\": 400,\r\n"
					+ "  \"message\": \"User does not exist\",\r\n";
		}
		dataModel.makeInActive(id);
		return res;
	}
	
	public static String register(String id, String nomi, String aile, String postal, String address, String pass) throws NoSuchAlgorithmException{
		Model dataModel = Model.getInstance();
		String res = "{\r\n"
				+ "  \"status\": 200,\r\n"
				+ "  \"message\": \"Register successful\",\r\n";
		if(userExists(id)) {
			res = "{\r\n"
					+ "  \"status\": 400,\r\n"
					+ "  \"message\": \"User already exists\",\r\n";
		}else {
			User user = new User(id, nomi, aile, postal, address, pass);
			user.changePassword(user.hashPassword(pass), pass);
			dataModel.uDB.insert(user);
			logIn(id, pass);
		}
		
		return res;
	}
	
	
}

