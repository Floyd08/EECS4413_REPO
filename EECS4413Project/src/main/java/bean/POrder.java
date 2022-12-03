package bean;

import java.util.Objects;

public class POrder implements Comparable<POrder> {

	private String ID;
	private String name;
	private String surName;
	private String postalCode;
	private String address;
	
	//Default Constructor
	public POrder() {
		ID = "-1";
		this.name = "John";
		this.surName = "Jackson";
		this.postalCode = "1A1 A1A";
		this.address = "-1";
	}
	
	public POrder(String iD, String name, String surName, String postalCode, String address) {
		ID = iD;
		this.name = name;
		this.surName = surName;
		this.postalCode = postalCode;
		this.address = address;
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

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		
		return String.format("(ID:%s name:%s surName:%s postalCode:%s address:%s)", ID, name, surName, postalCode, address);
	}
	
	@Override
	public int compareTo(POrder p) {
		
		return this.ID.compareTo(p.ID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, address, name, postalCode, surName);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof POrder))
			return false;
		
		POrder other = (POrder) obj;
		
		return Objects.equals(ID, other.ID) && Objects.equals(address, other.address)
				&& Objects.equals(name, other.name) && Objects.equals(postalCode, other.postalCode)
				&& Objects.equals(surName, other.surName);
	}
	
	
}
