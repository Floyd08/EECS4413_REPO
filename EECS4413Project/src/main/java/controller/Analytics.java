package controller;

import java.util.ArrayList;

import model.Model;

public class Analytics {

	public static ArrayList<String> getAllEvents() {
		
		return Model.getInstance().vDB.retrieveAll();
	}
	
	public static ArrayList<String> getAllSales() {
		
		return Model.getInstance().vDB.retrieveEventByType("sale");
	}
	
	public static ArrayList<String> getAllViews() {
		
		return Model.getInstance().vDB.retrieveEventByType("view");
	}
	
	public static ArrayList<String> getAllOrders() {
		
		return Model.getInstance().pDB.retrieveAll();
	}
	
	public static ArrayList<String> getOrdersByUser(String userID) {
		
		return Model.getInstance().pDB.retrieveUserOrders(userID);
	}
}
