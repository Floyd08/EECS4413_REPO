package controller;

import java.util.ArrayList;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.Model;

public class CentralController implements RequestHandler<Map<String, String>, String> {
	
	//CentralController will have the only access to the Model, so when a sub controller is called,
	//it will be necessary to pass a reference to dataModel
	static Model dataModel = Model.getInstance();

	public CentralController() {
	}

	@Override
	public String handleRequest(Map<String, String> event, Context context) {
		//Event handler has other prototypes, so if this one isn't right you can totally change it
		//More here: https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
		
		String eventService = event.get("Service");
		String callResponse = "";
		
		if (eventService == null) {
			callResponse = "{'statusCode': " + 400 + ", " + 
					"'body': 'Error: Service not specified.'}";
		}
		else if (eventService.equals("Catalog")) {
			
			callResponse = toCatalog(event);	
		}
		
		else if (eventService.equals("IdentityManager")) {
			callResponse = toIdentityManager(event);	
		}
		
		else {
			callResponse = "{'statusCode': " + 404 + ", " + 
					"'body': 'Error: Service not found.'}";
		}
		
		return callResponse;	//Just a place holder
	}
	
	public static String toCatalog(Map<String, String> event) {
		
		String eventMethod = event.get("Method");
		String eventParameters = event.get("Parameters");
		String response = "";
		
		if (eventMethod == null) {
			response = "{'statusCode': " + 400 + ", " + 
					"'body': 'Error: Method not specified.'}";
		}
		
		else if (eventMethod.equals("viewAll")) {
			String catalogResponse = Catalog.viewAll(dataModel).toString();
			response = "{'statusCode': " + 200 + ", " + 
					"'body': '" + catalogResponse + "'}";
		}
		
		else if (eventMethod.equals("viewByType")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				String catalogResponse = Catalog.viewByType(dataModel, eventParameters).toString();
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + catalogResponse + "'}";
			}
		}
		
		else if (eventMethod.equals("viewByBrand")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				String catalogResponse = Catalog.viewByBrand(dataModel, eventParameters).toString();
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + catalogResponse + "'}";
			}
		}
		
		else if (eventMethod.equals("getNameMap")) {
			String catalogResponse = Catalog.getNameMap(dataModel).toString();
			response = "{'statusCode': " + 200 + ", " + 
					"'body': '" + catalogResponse + "'}";
		}
		
		else if (eventMethod.equals("getBrandList")) {
			String catalogResponse = Catalog.getBrandList(dataModel).toString();
			response = "{'statusCode': " + 200 + ", " + 
					"'body': '" + catalogResponse + "'}";
		}
		
		else if (eventMethod.equals("getTypeList")) {
			String catalogResponse = Catalog.getTypeList(dataModel).toString();
			response = "{'statusCode': " + 200 + ", " + 
					"'body': '" + catalogResponse + "'}";
		}
		
		else if (eventMethod.equals("add")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				Catalog.add(dataModel, eventParameters);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + eventParameters + " added.'}";
			}
		}
		
		else if (eventMethod.equals("addMany")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				// TODO
//				Catalog.addMany(dataModel, itemsToAdd);
//				response = "{'statusCode': " + 200 + ", " + 
//						"'body': '" + eventParameters + " added.";
			}
		}
		
		else if (eventMethod.equals("get")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				String catalogResponse = Catalog.get(dataModel, eventParameters);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + catalogResponse + "'}";
			}
		}
		
		else if (eventMethod.equals("remove")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				Catalog.remove(dataModel, eventParameters);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + eventParameters + " removed.'}";
			}
		}
		else {
			response = "{'statusCode': " + 404 + ", " + 
					"'body': 'Error: Method not found.'}"; 
		}
		
		return response;
	}

	public static String toIdentityManager(Map<String, String> event) {
		
		// TODO: Implement call to identityManager
		
		String response = "{'statusCode': " + 200 + ", " + 
				"'body': 'toIdentityManager succeeded'}"; 
		
		return response;
	}
}
