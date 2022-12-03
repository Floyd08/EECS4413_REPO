package bean;

import java.util.Objects;

import com.google.gson.Gson;

/**
 * Data object to encapsulate catalogue items
 * @author Conrad Lautenschlager
 */
public class ItemP implements Comparable<ItemP> {

	private String ID;
	private String name;
	private String description;
	private String type;
	private String brand;
	private int quantity;
	private double price;
	
	//default Constructor
	public ItemP() {
		
		ID = "-1";
		this.name = "TestItem";
		this.description = "Empty Description";
		this.type = "";
		this.brand = "";
		this.quantity = 0;
		this.price = 0.0;
	}
	
	public ItemP(String iD, String name, String description, String type, String brand, int quantity, double price) {
		
		ID = iD;
		this.name = name;
		this.description = description;
		this.type = type;
		this.brand = brand;
		this.quantity = quantity;
		this.price = price;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toJSON() {
		
		Gson gS = new Gson();
		return gS.toJson(this);
	}
	
	public static ItemP fromJSON(String Json) {
		
		Gson gS = new Gson();
		return gS.fromJson(Json, ItemP.class);
	}
	
	@Override
	public String toString() {
		
		return String.format("(ID:%s Name:%s Description:%s Type:%s Brand:%s Price:%f)", this.ID, this.name, this.description, this.type, this.brand, this.price);
	}

	@Override
	public int compareTo(ItemP i) {
		
		return this.ID.compareTo(i.ID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, brand, description, name, price, quantity, type);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof ItemP))
			return false;
		
		ItemP other = (ItemP) obj;
		
		return Objects.equals(ID, other.ID) && Objects.equals(brand, other.brand)
				&& Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price) && quantity == other.quantity
				&& Objects.equals(type, other.type);
	}

	

}
