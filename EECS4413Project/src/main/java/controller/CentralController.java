package controller;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.Model;

public class CentralController implements RequestHandler<Map<String, String>, String> {
	
	//CentralController will have the only access to the Model, so when a sub controller is called,
	//it will be necessary to pass a reference to dataModel
	Model dataModel = Model.getInstance();
	
	@Override
	public String handleRequest(Map<String, String> event, Context context) {
		//Event handler has other prototypes, so if this one isn't right you can totally change it
		//More here: https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
		
		String response = "";
		int statusCode = 200;
		
		response = "{'statusCode': " + statusCode + 
				"'body': 'Hello from CentralController!'"; 
		
		return response;	//Just a place holder
	}
}
