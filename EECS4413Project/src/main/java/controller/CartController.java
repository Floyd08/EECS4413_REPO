package controller;

import bean.ItemP;
import bean.ShoppingCart;
import model.Model;

public class CartController {
	
	public static void initializeCart(String userID) {
		
		Model.getInstance().carts.put(userID, new ShoppingCart(userID));
	}
	
	public static ShoppingCart getCart(String userID) {
		
		return Model.getInstance().carts.get(userID);
	}
	
	public static String getCartAsJson(String userID) {
		
		return getCart(userID).cartToJSON();
	}
	
	public static void addtoCart(String ip, String userID, String Json) {
		
		ShoppingCart c = Model.getInstance().carts.get(userID);
		c.AddToCart(Json);
		String itemID = ItemP.fromJSON(Json).getID();
		c.logItemView(ip, itemID);
	}
	
	public static void removeFromCart(String userID, String Json) {
		
		Model.getInstance().carts.get(Json).removeFromCart(Json);
	}
	
	public static void updateCartQuant(String userID, String Json, int newQuant) {
		
		Model.getInstance().carts.get(userID).updateCartQuant(Json, newQuant);
	}
	
	public static int getQuantity(String userID, String Json) {
		
		return Model.getInstance().carts.get(userID).getQuantity(Json);
	}
	
	public static void checkOut(String ip, String userID) {
		
		ShoppingCart c = Model.getInstance().carts.get(userID);
		Model.getInstance().pDB.executeOrder(ip, c);
		Model.getInstance().carts.put(userID, new ShoppingCart(userID));
	}
	
	public static boolean isCartEmpty(String userID) {
		
		return Model.getInstance().carts.get(userID).isEmpty();
	}
	
	public static String cartAsJSON(String userID) {
		
		return Model.getInstance().carts.get(userID).cartToJSON();
	}
	
	public static ShoppingCart cartFromJSON(String Json) {
		
		return ShoppingCart.cartFromJSON(Json);
	}
	
	public static String getOwnerID(ShoppingCart cart) {
		
		return cart.getOwner();
	}
	
	public static void transferCart(String oldOwner, String newOwner) {
		
		ShoppingCart c = Model.getInstance().carts.get(oldOwner);
		Model.getInstance().carts.remove(oldOwner);
		Model.getInstance().carts.put(newOwner, c);
	}
	
	
}







