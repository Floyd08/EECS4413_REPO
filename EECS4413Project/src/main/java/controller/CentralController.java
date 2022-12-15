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
	}

	@Override
	public String handleRequest(Map<String, String> event, Context context) {
		//Event handler has other prototypes, so if this one isn\"t right you can totally change it
		//More here: https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
		
		String eventService = event.get("Service");
		String callResponse = "";
		
		if (eventService == null) {
			callResponse = "{\"statusCode\": " + 400 + ", " + 
					"\"body\": \"Error: Service not specified.\"}";
		}
		else if (eventService.equals("Catalog")) {
			
			callResponse = toCatalog(event);	
		}
		
		else if (eventService.equals("IdentityManager")) {
			callResponse = toIdentityManager(event);	
		}
		
		else if (eventService.equals("ShoppingCart")) {
			callResponse = toShoppingCart(event);
		}
		
		else if (eventService.equals("Analytics")) {
			callResponse = toAnalytics(event);
		}
		
		else {
			callResponse = "{\"statusCode\": " + 404 + ", " + 
					"\"body\": \"Error: Service not found.\"}";
		}
		
		return callResponse;	//Just a place holder
	}
	
	public String toCatalog(Map<String, String> event) {
		
		String eventMethod = event.get("Method");
		String eventParameters = event.get("Parameters");
		String response = "";
		
		if (eventMethod == null) {
			response = "{\"statusCode\": " + 400 + ", " + 
					"\"body\": \"Error: Method not specified.\"}";
		}
		
		else if (eventMethod.equals("viewAll")) {
			String catalogResponse = Catalog.viewAll(dataModel).toString();
			//catalogResponse = jsonListBracketReplace(catalogResponse);
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + catalogResponse + "\"}";
		}
		
		else if (eventMethod.equals("viewByType")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				String catalogResponse = Catalog.viewByType(dataModel, eventParameters).toString();
				//catalogResponse = jsonListBracketReplace(catalogResponse);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + catalogResponse + "\"}";
			}
		}
		
		else if (eventMethod.equals("viewByBrand")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				String catalogResponse = Catalog.viewByBrand(dataModel, eventParameters).toString();
				//catalogResponse = jsonListBracketReplace(catalogResponse);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + catalogResponse + "\"}";
			}
		}
		
		else if (eventMethod.equals("getNameMap")) {
			String catalogResponse = Catalog.getNameMap(dataModel).toString();
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + catalogResponse + "\"}";
		}
		
		else if (eventMethod.equals("getBrandList")) {
			String catalogResponse = Catalog.getBrandList(dataModel).toString();
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + catalogResponse + "\"}";
		}
		
		else if (eventMethod.equals("getTypeList")) {
			String catalogResponse = Catalog.getTypeList(dataModel).toString();
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + catalogResponse + "\"}";
		}
		
		else if (eventMethod.equals("add")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				Catalog.add(dataModel, eventParameters);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + eventParameters + " added.\"}";
			}
		}
		
		else if (eventMethod.equals("addMany")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				// TODO
//				Catalog.addMany(dataModel, itemsToAdd);
//				response = "{\"statusCode\": " + 200 + ", " + 
//						"\"body\": \"" + eventParameters + " added.";
			}
		}
		
		else if (eventMethod.equals("get")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				String catalogResponse = Catalog.get(dataModel, eventParameters);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + catalogResponse + "\"}";
			}
		}
		
		else if (eventMethod.equals("remove")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				Catalog.remove(dataModel, eventParameters);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + eventParameters + " removed.\"}";
			}
		}
		
		else if (eventMethod.equals("addReview")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String itemID = jsonEventParameters.get("itemID").toString();
					String userID = jsonEventParameters.get("userID").toString();
					String name = jsonEventParameters.get("name").toString();
					String surName = jsonEventParameters.get("surName").toString();
					String comment = jsonEventParameters.get("comment").toString();
					
					Catalog.addReview(dataModel, itemID, userID, name, surName, comment);
					
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"The comment: " + comment + " has been added to " + itemID + ".\"}";
					
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"Error: Parameters incorrect. {\"itemID\":\"...\", "
							+ "\"userID\":\"...\","
							+ "\"name\":\"...\","
							+ "\"surName\":\"...\","
							+ "\"comment\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("deleteReview")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String itemID = jsonEventParameters.get("itemID").toString();
					String userID = jsonEventParameters.get("userID").toString();
					
					Catalog.deleteReview(dataModel, itemID, userID);
					
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"The comment for " + itemID + " has been deleted.\"}";
					
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"itemID\":\"...\", \"userID\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("getReview")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String itemID = jsonEventParameters.get("itemID").toString();
					String userID = jsonEventParameters.get("userID").toString();
					
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"" + Catalog.getReview(dataModel, itemID, userID) + "\"}";
					
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"itemID\":\"...\", \"userID\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("getAllReviewsForItem")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				String catalogResponse = Catalog.getAllReviewsForItem(dataModel, eventParameters).toString();
				//catalogResponse = jsonListBracketReplace(catalogResponse);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + catalogResponse + "\"}";
			}
		}
		
		else if (eventMethod.equals("editComment")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String itemID = jsonEventParameters.get("itemID").toString();
					String userID = jsonEventParameters.get("userID").toString();
					String newComment = jsonEventParameters.get("newComment").toString();
					
					Catalog.editComment(dataModel, itemID, userID, newComment);
					
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"The comment for " + itemID + " has been updated to: " + newComment + ".\"}";
					
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"itemID\":\"...\", \"userID\":\"...\", \"newComment\":\"...\"}";
				}
			}
		}
		
		else {
			response = "{\"statusCode\": " + 404 + ", " + 
					"\"body\": \"Error: Method not found.\"}"; 
		}
		
		response = response.replaceAll("\"", "\"");
		response = response.replaceAll("\"\\[", "\\[");
		response = response.replaceAll("\\]\"", "\\]");
		
		return response;
	}

	public String toIdentityManager(Map<String, String> event) {
		
		String eventMethod = event.get("Method");
		String eventParameters = event.get("Parameters");
		String response = "";
		
		if (eventMethod == null) {
			response = "{\"statusCode\": " + 400 + ", " + 
					"\"body\": \"Error: Method not specified.\"}";
		}
		
		else if (eventMethod.equals("userExists")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + IdentityManager.userExists(eventParameters) + "\"}";
			}
		}
		
		else if (eventMethod.equals("logIn")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String id = jsonEventParameters.get("id").toString();
					String pass = jsonEventParameters.get("pass").toString();
					response = IdentityManager.logIn(id, pass);
					response = response.replace("message", "body");
					response = response.replace("status", "statusCode");
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"id\":\"...\", \"pass\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("logOut")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				response = IdentityManager.logOut(eventParameters);
				response = response.replace("message", "body");
				response = response.replace("status", "statusCode");
			}
		}
		
		else if (eventMethod.equals("register")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					
					String id;
					
					if (jsonEventParameters.get("id") == null) {
						id = null;
					}
					else {
						id = jsonEventParameters.get("id").toString();
					}
					String nomi = jsonEventParameters.get("nomi").toString();
					String aile = jsonEventParameters.get("aile").toString();
					String postal = jsonEventParameters.get("postal").toString();
					String address = jsonEventParameters.get("address").toString();
					String pass = jsonEventParameters.get("pass").toString();
					
					if (id == null) {
						response = IdentityManager.register(nomi, aile, postal, address, pass);
					}
					else {
						response = IdentityManager.register(id, nomi, aile, postal, address, pass);
					}
					
					response = response.replace("message", "body");
					response = response.replace("status", "statusCode");
				} catch (Exception e) {
					
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"id\":\"...\" (optional), "
							+ "\"pass\":\"...\", \"nomi\":\"...\", \"aile\":\"...\", \"postal\":\"...\", \"address\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("activeUsers")) {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + dataModel.loggedIn + "\"}";
		}
		
		else if (eventMethod.equals("updateAddress")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String id = jsonEventParameters.get("id").toString();
					String street = jsonEventParameters.get("street").toString();
					String postalCode = jsonEventParameters.get("postalCode").toString();
					IdentityManager.updateAddress(id, street, postalCode);
					
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"Address of " + id + " has been updated to Street: " + street + " and Postal Code: " + postalCode + "\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"id\":\"...\", \"street\":\"...\", \"postalCode\":\"...\"}";
				}
			}
		}
		
		else {
			response = "{\"statusCode\": " + 404 + ", " + 
					"\"body\": \"Error: Method not found.\"}"; 
		}
		
		response = response.replaceAll("\"", "\"");
		response = response.replaceAll("\"\\[", "\\[");
		response = response.replaceAll("\\]\"", "\\]");
		
		return response;
	}
	
	public String toShoppingCart(Map<String, String> event) {
			
		String eventMethod = event.get("Method");
		String eventParameters = event.get("Parameters");
		String response = "";
		
		if (eventMethod == null) {
			response = "{\"statusCode\": " + 400 + ", " + 
					"\"body\": \"Error: Method not specified.\"}";
		}
		
		else if (eventMethod.equals("newShoppingCart")) {
			if (eventParameters == null) {
				CartController.initializeCart("-1");
				shoppingCart = CartController.getCart("-1");
				
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"A new Guest Shopping Cart has been created.\"}";
			}
			else {
				CartController.initializeCart(eventParameters);
				shoppingCart = CartController.getCart(eventParameters);
				
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"A new Shopping Cart has been created and " + eventParameters + " has been set as the owner.\"}";
			}
		}
		
		else if (eventMethod.equals("getOwnerID")) {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + CartController.getOwnerID(shoppingCart) + "\"}";
		}
		
		else if (eventMethod.equals("transferCart")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {				
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String oldOwner = jsonEventParameters.get("oldOwner").toString();
					String newOwner = jsonEventParameters.get("newOwner").toString();
					CartController.transferCart(oldOwner, newOwner);
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"The Shopping Cart belonging to " + oldOwner + " has been transfered to " + newOwner + ".\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"oldOwner\":\"...\", \"newOwner\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("getCart")) {
			if (eventParameters == null) {
				String userID = "-1";
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + CartController.getCart(userID) + "\"}";
			}
			else {
				String userID = eventParameters;
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + CartController.getCart(userID) + "\"}";
			}
		}
		
		else if (eventMethod.equals("getCartAsJson")) {
			if (eventParameters == null) {
				String userID = "-1";
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + CartController.getCartAsJson(userID) + "\"}";
			}
			else {
				String userID = eventParameters;
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + CartController.getCartAsJson(userID) + "\"}";
			}
		}
		
		else if (eventMethod.equals("setCart")) {
			// TODO
		}
		
		else if (eventMethod.equals("addToCart")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {				
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String ip = jsonEventParameters.get("ip").toString();
					String itemToAdd = jsonEventParameters.get("Item").toString();
					String userID = jsonEventParameters.get("userID").toString();
					CartController.addtoCart(ip, userID, itemToAdd);
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"" + ItemP.fromJSON(itemToAdd).getName() + " has been added to the shopping cart.\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"ip\":\"...\", \"Item\":\"...\", \"userID\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("removeFromCart")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {				
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String userID = jsonEventParameters.get("userID").toString();
					String itemToRemove = jsonEventParameters.get("Item").toString();
					CartController.removeFromCart(userID, itemToRemove);
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"" + ItemP.fromJSON(itemToRemove).getName() + " has been removed from the shopping cart.\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"userID\":\"...\", \"Item\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("updateCartQuant")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String userID = jsonEventParameters.get("userID").toString();
					String itemToUpdate = jsonEventParameters.get("Item").toString();
					int newQuantity = Integer.parseInt(jsonEventParameters.get("newQuantity").toString());
					CartController.updateCartQuant(userID, itemToUpdate, newQuantity);
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"" + ItemP.fromJSON(itemToUpdate).getName() + " has been updated to " + newQuantity + ".\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"userID\":\"...\", \"Item\":\"...\", \"newQuantity\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("getQuantity")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {				
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String userID = jsonEventParameters.get("userID").toString();
					String itemToCount = jsonEventParameters.get("Item").toString();
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"" + CartController.getQuantity(userID, itemToCount) + "\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"userID\":\"...\", \"Item\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("isEmpty")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + shoppingCart.isEmpty() + "\"}";
			}
		}
		
		else if (eventMethod.equals("checkOut")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {				
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonEventParameters = (JSONObject) parser.parse(eventParameters);
					String ip = jsonEventParameters.get("ip").toString();
					String userID = jsonEventParameters.get("userID").toString();
					CartController.checkOut(ip, userID);
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"" + userID + "'s Shopping Cart has been checked out.\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"ip\":\"...\", \"userID\":\"...\"}";
				}
			}
		}
		
		else {
			response = "{\"statusCode\": " + 404 + ", " + 
					"\"body\": \"Error: Method not found.\"}"; 
		}
		
		response = response.replaceAll("\"", "\"");
		response = response.replaceAll("\"\\[", "\\[");
		response = response.replaceAll("\\]\"", "\\]");
		
		return response;
	}
	
	public String toAnalytics(Map<String, String> event) {
		
		String eventMethod = event.get("Method");
		String eventParameters = event.get("Parameters");
		String response = "";
		
		if (eventMethod == null) {
			response = "{\"statusCode\": " + 400 + ", " + 
					"\"body\": \"Error: Method not specified.\"}";
		}
		
		else if (eventMethod == "getAllEvents") {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + Analytics.getAllEvents() + "\"}";
		}
		
		else if (eventMethod == "getAllSales") {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + Analytics.getAllSales() + "\"}";	
		}
				
		else if (eventMethod == "getAllViews") {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + Analytics.getAllViews() + "\"}";
		}
				
		else if (eventMethod == "getAllOrders") {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + Analytics.getAllOrders() + "\"}";
		}
				
		else if (eventMethod == "getOrdersByUser") {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + Analytics.getOrdersByUser(eventParameters) + "\"}";
			}
		}
		
		else {
			response = "{\"statusCode\": " + 404 + ", " + 
					"\"body\": \"Error: Method not found.\"}"; 
		}
		
		response = response.replaceAll("\"\\[", "\\[");
		response = response.replaceAll("\\]\"", "\\]");
		
		return response;
	}
	
	private String jsonListBracketReplace(String str) {
		String result;
		result = str.replaceAll("\\{", "'\\{");
		result = result.replaceAll("\\}", "\\}'");
		
		return result;
	}
}
