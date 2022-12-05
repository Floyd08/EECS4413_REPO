package controller;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.Model;

public class CentralController implements RequestHandler<Map<String, String>, String> {
	
	//CentralController will have the only access to the Model, so when a sub controller is called,
	//it will be necessary to pass a reference to dataModel
	Model dataModel = Model.getInstance();
	
	private static CentralController instance;
	
	private CentralController() {
	}
	
	public static CentralController getInstance() {
		
		if (instance == null) {
			
			instance = new CentralController();
		}
		
		return instance;
	}
	
	@Override
	public String handleRequest(Map<String, String> event, Context context) {
		//Event handler has other prototypes, so if this one isn't right you can totally change it
		//More here: https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
		
		String eventBody = event.get("body");
		String callResponse = "";
	
		if (event.get("Service").equals("Catalog")) {
			
			callResponse = toCatalog(eventBody);	
		}
		
		else if (event.get("Service").equals("IdentityManager")) {
			callResponse = toIdentityManager(eventBody);	
		}
		
		else {
			callResponse = "{'statusCode': " + 404 + ", " + 
					"'body': 'Service not found.'}";
		}
		
		return callResponse;	//Just a place holder
	}
	
	public static String toCatalog(String eventBody) {
		
		// TODO: Implement call to Catalog
		
		String response = "{'statusCode': " + 200 + ", " + 
				"'body': 'toCatalog succeeded'}"; 
		
		return response;
	}

	public static String toIdentityManager(String eventBody) {
		
		// TODO: Implement call to identityManager
		
		String response = "{'statusCode': " + 200 + ", " + 
				"'body': 'toIdentityManager succeeded'}"; 
		
		return response;
	}
}
