package bean;

import java.util.Objects;

public class VisitEvent implements Comparable<VisitEvent> {
	
	private String ipAddress;
	private String day;
	private String ID;
	private String type;
	
	//Default Constructor
	public VisitEvent() {
		
		this.ipAddress = "noAddress";
		this.day = "NA";
		ID = "-1";
		this.type = "Empty";
	}
	
	public VisitEvent(String ipAddress, String day, String iD, String type) {
		this.ipAddress = ipAddress;
		this.day = day;
		ID = iD;
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
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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
		
		return Objects.equals(ID, other.ID) && Objects.equals(day, other.day)
				&& Objects.equals(ipAddress, other.ipAddress) && Objects.equals(type, other.type);
	}
	
	
	
}
