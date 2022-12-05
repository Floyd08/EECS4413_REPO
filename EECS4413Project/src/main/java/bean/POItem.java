package bean;

import java.util.Objects;

public class POItem implements Comparable<POItem> {
	
	private String ID;
	private String itemID;
	private double price;
	
	//Default Constructor
	public POItem() {
		
		ID = "-1";
		this.itemID = "-1";
		this.price = 0.0;
	}
	
	public POItem(String iD, String itemID, double price) {
		
		ID = iD;
		this.itemID = itemID;
		this.price = price;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		
		return String.format("(ID:%s itemID:%s price:%s)", ID, itemID, price);
	}
	
	@Override
	public int compareTo(POItem p) {
		
		return this.ID.compareTo(p.ID);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ID, itemID, price);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof POItem))
			return false;
		
		POItem other = (POItem) obj;
		
		return Objects.equals(ID, other.ID) && Objects.equals(itemID, other.itemID)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
	}
	
	
	
}
