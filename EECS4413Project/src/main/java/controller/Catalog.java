package controller;

import java.util.ArrayList;
import java.util.HashMap;

import bean.ItemP;
import model.Model;

public class Catalog {

	public static ArrayList<String> viewAll(Model m) {
		
		return m.iDB.retrieveAllAsJSON();
	}
	
	public static ArrayList<String> viewByType(Model m, String typeArg) {
		
		return m.iDB.retrieveByType(typeArg);
	}
	
	public static ArrayList<String> viewByBrand(Model m, String brandArg) {
		
		return m.iDB.retrieveByBrand(brandArg);
	}
	
	/**
	 * Takes a list of JSON strings extracts their IDs and names, and returns a HashMap of names to IDs
	 * @param catItems
	 * @return
	 */
	public static HashMap<String, String> getNameMap(Model m) {
		
		return m.iDB.extractNames();
	}
	
	public static ArrayList<String> getBrandList(Model m) {
		
		return m.iDB.getBrands();
	}
	
	public static ArrayList<String> getTypeList(Model m) {
		
		return m.iDB.getTypes();
	}
	/**
	 * @param newItem, a JSON String
	 */
	public static void add(Model m, String newItem) {
		
		m.iDB.insertFromJSON(newItem);
	}
	
	public static void addMany(Model m, ArrayList<String> newItems) {
		
		m.iDB.insertFromJSON(newItems);
	}
	/**
	 * @param String i, an item ID
	 */
	public static String get(Model m, String i) {
		
		return m.iDB.retrieveAsJSON(i);
	}
	
	/**
	 * @param newItem, a JSON String
	 */
	public static void remove(Model m, String i) {
		
		m.iDB.delete(i);
	}
	
	
}
