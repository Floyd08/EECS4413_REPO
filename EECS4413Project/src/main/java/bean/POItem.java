package bean;

import java.util.Objects;

public class POItem implements Comparable<POItem> {
	
	//private String ID;		//id of the item in the cart (Probably not needed)
	private String itemID;		//id of the item in the database
	private String itemName;	
	private double price;
	private int quantity;
	
	//Default Constructor
	public POItem() {
		
		//ID = "-1";
		this.itemID = "-1";
		this.price = 0.0;
	}
	
	public POItem(String itemID, String itemName, double price) {
		
		//ID = iD;
		this.itemID = itemID;
		this.itemName = itemName;
		this.price = price;
	}
	
	public POItem(ItemP i) {
		
		this.itemID = i.getID();
		this.itemName = i.getName();
		this.price = i.getPrice();
		this.quantity = 1;
	}

//	public String getID() {
//		return ID;
//	}
//
//	public void setID(String iD) {
//		ID = iD;
//	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		
		return String.format("(itemID:%s itemName:%s price:%s, quantity:%s)", itemID, itemName, price, quantity);
	}
	
	@Override
	public int compareTo(POItem p) {
		
		return this.itemID.compareTo(p.itemID);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(itemID, itemName, price, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof POItem))
			return false;
		POItem other = (POItem) obj;
		return Objects.equals(itemID, other.itemID);
	}
	
	
	
}
