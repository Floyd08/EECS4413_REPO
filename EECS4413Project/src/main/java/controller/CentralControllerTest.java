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
		
		result = testMissingService();
		System.out.println(result);
		
		testToCatalogComplete();
		testToCatalogErrors();
		
		result = testToIdentityManager();
		System.out.println(result);
		
		result = testServiceNotFound();
		System.out.println(result);
	}	
	
	public static String testMissingService() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		String response = testCentralController.handleRequest(input, testContext);
		
		return response;
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
	
	public static String testToIdentityManager() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "IdentityManager");
		
		String response = testCentralController.handleRequest(input, testContext);
		
		return response;
	}
	
	public static String testServiceNotFound() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "None");
		
		String response = testCentralController.handleRequest(input, testContext);
		
		return response;
	}
}
