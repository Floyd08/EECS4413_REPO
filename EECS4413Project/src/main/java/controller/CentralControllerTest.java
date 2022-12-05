package controller;

import java.util.Map;
import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.Model;

public class CentralControllerTest {
	
	Model dataModel = Model.getInstance();
	
	public static void main(String[] args) {
		
		String result = "";
		
		result = testMissingService();
		System.out.println(result);
		
		result = testToCatalog();
		System.out.println(result);
		
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
	
	
	public static String testToCatalog() {
		
		CentralController testCentralController = new CentralController();
		
		Map<String, String> input = new HashMap<String, String>();
		Context testContext = new TestContext();
		
		input.put("Service", "Catalog");
		
		String response = testCentralController.handleRequest(input, testContext);
		
		return response;
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
