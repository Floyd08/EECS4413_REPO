package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import com.google.gson.Gson;

import model.Model;

public class ShoppingCart implements Comparable<ShoppingCart> {

	private String owner;						//the UserID that owns this shopping cart
	private HashMap<String, POItem> cart;		//The HashMap that holds all items in the shopping cart. Maps the ID attribute of the item in the database to the POItem instance
	
	//Default Constructor
	public ShoppingCart() {
		
		owner = "-1";
		cart = new HashMap<String, POItem>();
	}
	
	public ShoppingCart(String ID) {
		
		this.owner = ID;
		cart = new HashMap<String, POItem>();
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public HashMap<String, POItem> getCart() {
		return cart;
	}

	public void setCart(HashMap<String, POItem> cart) {
		this.cart = cart;
	}
	
	public String cartToJSON() {
		
		Gson gS = new Gson();
		return gS.toJson(cart);
	}
	
	public void AddToCart(String Json) {
		
		this.AddToCart(ItemP.fromJSON(Json));
	}
	
	public void AddToCart(ItemP i) {
		
		POItem p = new POItem(i);
		
		if ( this.getCart().containsKey(i.getID()) ) {
			
			int q = getQuantity(i);
			updateCartQuant(i, q + 1);
		}
		else	
			this.getCart().put(i.getID(), p);
	}
	
	public void logItemView(String ip, String itemID) {
		
		String date = java.time.LocalDateTime.now().toString();
		Model.getInstance().vDB.insertEvent(ip, date, itemID, "View");
	}
	
	public void logItemSale(String ip, String itemID) {
		
		String date = java.time.LocalDateTime.now().toString();
		Model.getInstance().vDB.insertEvent(ip, date, itemID, "View");
	}
	
	public void removeFromCart(String Json) {
		
		this.removeFromCart(ItemP.fromJSON(Json));
	}
	
	public void removeFromCart(ItemP i) {
		
		this.getCart().remove(i.getID());
	}
	
	public void updateCartQuant(String Json, int newQuant) {
		
		this.updateCartQuant(ItemP.fromJSON(Json), newQuant);
	}
	
	public void updateCartQuant(ItemP i, int newQuant) {
		
		POItem p = this.getCart().get(i.getID());
		p.setQuantity(newQuant);
		this.getCart().replace(i.getID(), p);
	}
	
	public int getQuantity(String Json) {
		
		return this.getQuantity(ItemP.fromJSON(Json));
	}
	
	public int getQuantity(ItemP i) {
		
		if ( this.getCart().containsKey(i.getID()) )
			return this.getCart().get(i.getID()).getQuantity();
		else
			return 0;
	}
	
	public boolean isEmpty() {
		return cart.isEmpty();
	}
	
	@Override 
	public String toString() {
		
		return String.format("(owner:%s cart:%s", owner, cart.toString());
	}
	
	@Override 
	public int compareTo(ShoppingCart s) {
		
		return this.owner.compareTo(s.getOwner());
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, owner);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof ShoppingCart))
			return false;
		
		ShoppingCart other = (ShoppingCart) obj;
		
		return Objects.equals(cart, other.cart) && Objects.equals(owner, other.owner);
	}
	
}
