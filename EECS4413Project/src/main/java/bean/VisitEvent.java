package bean;

import java.util.Objects;

public class VisitEvent implements Comparable<VisitEvent> {
	
	private String ID;
	private String ipAddress;
	private String day;
	private String itemID;
	private String type;
	
	//Default Constructor
	public VisitEvent() {
		
		this.ID = "-1";
		this.ipAddress = "noAddress";
		this.day = "NA";
		this.itemID = "-1";
		this.type = "Empty";
	}
	
	public VisitEvent(String iD, String ipAddress, String day, String itemID, String type) {
		
		this.ID = iD;
		this.ipAddress = ipAddress;
		this.day = day;
		this.itemID = iD;
		this.type = type;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getID() {
		return itemID;
	}

	public void setID(String iD) {
		itemID = iD;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		
		return String.format("(ipAddress:%s day:%s ID:%s type:%s)");
	}
	
	@Override
	public int compareTo(VisitEvent v) {
		
		return this.ID.compareTo(v.ID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, day, ipAddress, type);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof VisitEvent))
			return false;
		
		VisitEvent other = (VisitEvent) obj;
		
		return Objects.equals(itemID, other.itemID) && Objects.equals(day, other.day)
				&& Objects.equals(ipAddress, other.ipAddress) && Objects.equals(type, other.type);
	}
	
	
	
}
