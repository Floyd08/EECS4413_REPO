package controller;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.Model;
import bean.ShoppingCart;
import bean.ItemP;

public class CentralController implements RequestHandler<Map<String, String>, String> {
	
	//CentralController will have the only access to the Model, so when a sub controller is called,
	//it will be necessary to pass a reference to dataModel
	static Model dataModel = Model.getInstance();
	ShoppingCart shoppingCart;

	public CentralController() {
		shoppingCart = new ShoppingCart();
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
	
	public String toCatalog(Map<String, String> event) {
		
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

	public String toIdentityManager(Map<String, String> event) {
		
		// TODO: Implement call to identityManager
		
		String response = "{'statusCode': " + 200 + ", " + 
				"'body': 'toIdentityManager succeeded'}"; 
		
		return response;
	}
	
	public String toShoppingCart(Map<String, String> event) {
			
		String eventMethod = event.get("Method");
		String eventParameters = event.get("Parameters");
		String response = "";
		
		if (eventMethod == null) {
			response = "{'statusCode': " + 400 + ", " + 
					"'body': 'Error: Method not specified.'}";
		}
		
		else if (eventMethod.equals("getOwner")) {
			response = "{'statusCode': " + 200 + ", " + 
					"'body': '" + shoppingCart.getOwner() + "'}";
		}
		
		else if (eventMethod.equals("setOwner")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				shoppingCart.setOwner(eventParameters);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + eventParameters + " has been set as the owner of the shopping cart.'}";
			}
		}
		
		else if (eventMethod.equals("getCart")) {
			response = "{'statusCode': " + 200 + ", " + 
					"'body': '" + shoppingCart.cartToJSON() + "'}";
		}
		
		else if (eventMethod.equals("setCart")) {
			// TODO
		}
		
		else if (eventMethod.equals("addToCart")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				ItemP itemToAdd = ItemP.fromJSON(eventParameters);
				shoppingCart.AddToCart(itemToAdd);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + itemToAdd.getName() + " has been added to the shopping cart.'}";
			}
		}
		
		else if (eventMethod.equals("removeFromCart")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				ItemP itemToRemove = ItemP.fromJSON(eventParameters);
				shoppingCart.removeFromCart(itemToRemove);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + itemToRemove.getName() + " has been removed from the shopping cart.'}";
			}
		}
		
		else if (eventMethod.equals("removeFromCart")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				ItemP itemToRemove = ItemP.fromJSON(eventParameters);
				shoppingCart.removeFromCart(itemToRemove);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + itemToRemove.getName() + " has been removed from the shopping cart.'}";
			}
		}
		
		else if (eventMethod.equals("updateCartQuant")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					ItemP itemToUpdate = ItemP.fromJSON(jsonEventParameters.get("Item").toString());
					int newQuantity = (int) jsonEventParameters.get("Quantity");
					shoppingCart.updateCartQuant(itemToUpdate, newQuantity);
					response = "{'statusCode': " + 200 + ", " + 
							"'body': '" + itemToUpdate.getName() + " has been updated to " + newQuantity + ".'}";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response = "{'statusCode': " + 400 + ", " + 
							"'body': 'Error: Parameters incorrect. {'Item':'...', 'Quantity':'...'}";
				}
			}
		}
		
		else if (eventMethod.equals("getQuantity")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				ItemP itemToCount = ItemP.fromJSON(eventParameters);
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + shoppingCart.getQuantity(itemToCount) + "'}";
			}
		}
		
		else if (eventMethod.equals("isEmpty")) {
			if (eventParameters == null) {
				response = "{'statusCode': " + 400 + ", " + 
						"'body': 'Error: Parameters missing.'}";
			}
			else {
				response = "{'statusCode': " + 200 + ", " + 
						"'body': '" + shoppingCart.isEmpty() + "'}";
			}
		}
		
		return response;
	}
	
}
