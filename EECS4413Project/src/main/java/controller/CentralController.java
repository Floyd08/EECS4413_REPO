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
				shoppingCart = new ShoppingCart();
				
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"A new Shopping Cart has been created.\"}";
			}
			else {
				shoppingCart = new ShoppingCart(eventParameters);
				
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"A new Shopping Cart has been created and " + eventParameters + " has been set as the owner.\"}";
			}
		}
		
		else if (eventMethod.equals("getOwner")) {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + shoppingCart.getOwner() + "\"}";
		}
		
		else if (eventMethod.equals("setOwner")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				shoppingCart.setOwner(eventParameters);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + eventParameters + " has been set as the owner of the shopping cart.\"}";
			}
		}
		
		else if (eventMethod.equals("getCart")) {
			response = "{\"statusCode\": " + 200 + ", " + 
					"\"body\": \"" + shoppingCart.cartToJSON() + "\"}";
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
				ItemP itemToAdd = ItemP.fromJSON(eventParameters);
				shoppingCart.AddToCart(itemToAdd);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + itemToAdd.getName() + " has been added to the shopping cart.\"}";
			}
		}
		
		else if (eventMethod.equals("removeFromCart")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				ItemP itemToRemove = ItemP.fromJSON(eventParameters);
				shoppingCart.removeFromCart(itemToRemove);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + itemToRemove.getName() + " has been removed from the shopping cart.\"}";
			}
		}
		
		else if (eventMethod.equals("removeFromCart")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				ItemP itemToRemove = ItemP.fromJSON(eventParameters);
				shoppingCart.removeFromCart(itemToRemove);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + itemToRemove.getName() + " has been removed from the shopping cart.\"}";
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
					ItemP itemToUpdate = ItemP.fromJSON(jsonEventParameters.get("Item").toString());
					int newQuantity = Integer.parseInt(jsonEventParameters.get("Quantity").toString());
					shoppingCart.updateCartQuant(itemToUpdate, newQuantity);
					response = "{\"statusCode\": " + 200 + ", " + 
							"\"body\": \"" + itemToUpdate.getName() + " has been updated to " + newQuantity + ".\"}";
				} catch (Exception e) {
					
					e.printStackTrace();
					response = "{\"statusCode\": " + 400 + ", " + 
							"\"body\": \"Error: Parameters incorrect. {\"Item\":\"...\", \"Quantity\":\"...\"}";
				}
			}
		}
		
		else if (eventMethod.equals("getQuantity")) {
			if (eventParameters == null) {
				response = "{\"statusCode\": " + 400 + ", " + 
						"\"body\": \"Error: Parameters missing.\"}";
			}
			else {
				ItemP itemToCount = ItemP.fromJSON(eventParameters);
				response = "{\"statusCode\": " + 200 + ", " + 
						"\"body\": \"" + shoppingCart.getQuantity(itemToCount) + "\"}";
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
		
		else {
			response = "{\"statusCode\": " + 404 + ", " + 
					"\"body\": \"Error: Method not found.\"}"; 
		}
		
		response = response.replaceAll("\"", "\"");
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
