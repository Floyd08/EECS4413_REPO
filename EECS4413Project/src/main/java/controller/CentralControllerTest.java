package controller;

import java.util.Map;
import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;

import model.Model;
import bean.ItemP;

public class CentralControllerTest {
	
	Model dataModel = Model.getInstance();
	
	public static void main(String[] args) {
		
		String result = "";
		
		System.out.println("Testing for Missing Service: ");
		testMissingService();
		
		System.out.println("Testing for Service not Found: ");
		testServiceNotFound();
		
		System.out.println("Testing Catalog Methods: ");
		testToCatalogComplete();
		
		System.out.println("Testing Catalog Errors: ");
		testToCatalogErrors();
		
		System.out.println("Testing IdentityManager Methods: ");
		testToIdentityManagerComplete();
		
		System.out.println("Testing IdentityManager Errors: ");
		testToIdentityManagerErrors();
		
		System.out.println("Testing ShoppingCart Methods: ");
		testToShoppingCartComplete();
		
		System.out.println("Testing ShoppingCart Errors: ");
		testToShoppingCartErrors();
	}	
	
	public static void testMissingService() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		String response = testCentralController.handleRequest(input, testContext);
		
		System.out.println(response);
	}
	
	public static void testServiceNotFound() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "None");
		
		String response = testCentralController.handleRequest(input, testContext);
		
		System.out.println(response);
	}
	
	public static void testToCatalogComplete() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "Catalog");
		input.put("Method", "add");
		input.put("Parameters", "{\""
				+ "quantity\":\"100\","
				+ "\"price\":\"100.00\","
				+ "\"name\":\"testItem\","
				+ "\"description\":\"This is a test item\","
				+ "\"ID\":\"t001\","
				+ "\"type\":\"food\","
				+ "\"brand\":\"burnbrae\"}");
		
		String response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "viewAll");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "getNameMap");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "getBrandList");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "getTypeList");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "viewByType");
		input.put("Parameters", "food");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "viewByBrand");
		input.put("Parameters", "burnbrae");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "get");
		input.put("Parameters", "t001");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "remove");
		input.put("Parameters", "t001");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "viewAll");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
	}
	
	public static void testToCatalogErrors() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "Catalog");
		String response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "???");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "get");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
	}
	
	public static void testToIdentityManagerComplete() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "IdentityManager");
		input.put("Method", "userExists");
		input.put("Parameters", "u999");
		
		String response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "logIn");
		input.put("Parameters", "{\""
				+ "id\":\"u999\","
				+ "\"pass\":\"testPassword\"}");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "logOut");
		input.put("Parameters", "u999");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "register");
		input.put("Parameters", "{\""
				+ "id\":\"u999\","
				+ "\"pass\":\"u999password\","
				+ "\"nomi\":\"u999nomi\","
				+ "\"aile\":\"u999aile\","
				+ "\"postal\":\"u999postal\","
				+ "\"address\":\"u999address\"}");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "logOut");
		input.put("Parameters", "u999");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "logIn");
		input.put("Parameters", "{\""
				+ "id\":\"u999\","
				+ "\"pass\":\"testPassword\"}");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "userExists");
		input.put("Parameters", "u999");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "register");
		input.put("Parameters", "{\""
				+ "pass\":\"noIDpassword\","
				+ "\"nomi\":\"noIDnomi\","
				+ "\"aile\":\"noIDaile\","
				+ "\"postal\":\"noIDpostal\","
				+ "\"address\":\"noIDaddress\"}");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
	}
	
	public static void testToIdentityManagerErrors() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "IdentityManager");
		String response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "???");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "get");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
	}
	
	public static void testToShoppingCartComplete() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "ShoppingCart");
		input.put("Method", "getOwner");
		
		String response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "setOwner");
		input.put("Parameters", "u999");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "getOwner");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "getCart");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "addToCart");
		input.put("Parameters", "{\""
				+ "quantity\":\"100\","
				+ "\"price\":\"100.00\","
				+ "\"name\":\"testItem\","
				+ "\"description\":\"This is a test item\","
				+ "\"ID\":\"t001\","
				+ "\"type\":\"food\","
				+ "\"brand\":\"burnbrae\"}");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "getCart");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "updateCartQuant");
		input.put("Parameters", "{\"Item\":{\""
				+ "quantity\":\"100\","
				+ "\"price\":\"100.00\","
				+ "\"name\":\"testItem\","
				+ "\"description\":\"This is a test item\","
				+ "\"ID\":\"t001\","
				+ "\"type\":\"food\","
				+ "\"brand\":\"burnbrae\"},"
				+ "\"Quantity\":99}");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "getCart");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "getQuantity");
		input.put("Parameters", "{\""
				+ "quantity\":\"100\","
				+ "\"price\":\"100.00\","
				+ "\"name\":\"testItem\","
				+ "\"description\":\"This is a test item\","
				+ "\"ID\":\"t001\","
				+ "\"type\":\"food\","
				+ "\"brand\":\"burnbrae\"}");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "removeFromCart");
		input.put("Parameters", "{\""
				+ "quantity\":\"100\","
				+ "\"price\":\"100.00\","
				+ "\"name\":\"testItem\","
				+ "\"description\":\"This is a test item\","
				+ "\"ID\":\"t001\","
				+ "\"type\":\"food\","
				+ "\"brand\":\"burnbrae\"}");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "getCart");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
		
		input.put("Method", "isEmpty");
		response = testCentralController.handleRequest(input, testContext);		
		System.out.println(response);
	}
	
	public static void testToShoppingCartErrors() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "ShoppingCart");
		String response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "???");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
		
		input.put("Method", "get");
		response = testCentralController.handleRequest(input, testContext);
		System.out.println(response);
	}
}
