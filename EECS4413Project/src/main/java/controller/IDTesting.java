package controller;

import model.Model;

public class IDTesting {

	public static void main(String[] args) {
		
		Model m = Model.getInstance();
		String res;
		
		//User existence
		System.out.println("Does u004 exist? " + IdentityManager.userExists("u004"));
		System.out.println("Does u005 exist? " + IdentityManager.userExists("u005"));
		
		//Registration
		m.uDB.delete("u035");
		res = IdentityManager.register("u035", "Joe", "Smyth", "A1A 1A1", "234 fake street", "password");
		System.out.println(res);
		System.out.println("Does u035 exist? " + IdentityManager.userExists("u035"));
		String u2 = m.uDB.retrieveAsJSON("u035");
		System.out.println(u2);
		
		res = IdentityManager.logIn("u035", "password");
		System.out.println(res);
		System.out.println("is u035 in loggedIn? " + m.loggedIn.contains("u035"));
		
		res = IdentityManager.logOut("u035");
		System.out.println(res);
		System.out.println("is u035 in loggedIn? " + m.loggedIn.contains("u035"));
		
		res = IdentityManager.register("Jill", "Smoith", "A1A 1A1", "345 fake street", "password");
		System.out.println(res);
		System.out.println("Active users: " + m.loggedIn.toString());
	}

}
