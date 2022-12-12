package bean;

import java.util.ArrayList;
import java.util.Objects;

public class POrder implements Comparable<POrder> {

	private String ID;
	private String userID;
	private String address;
	private ArrayList<POItem> items;
	
	//Default Constructor
	public POrder() {
		ID = "-1";
		this.userID = "-1";
		this.address = "-1";
		this.items = new ArrayList<POItem>();
	}
	
	public POrder(String iD, String userID, String address, ArrayList<POItem> items) {
		ID = iD;
		this.userID = userID;
		this.address = address;
		this.items = items;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public ArrayList<POItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<POItem> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		
		return String.format("(ID:%s userID:%s address:%s)", ID, userID, address);
	}
	
	@Override
	public int compareTo(POrder p) {
		
		return this.ID.compareTo(p.ID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, address, userID);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof POrder))
			return false;
		
		POrder other = (POrder) obj;
		
		return Objects.equals(ID, other.ID) && Objects.equals(address, other.address)
				&& Objects.equals(userID, other.userID);
	}
	
	
}
