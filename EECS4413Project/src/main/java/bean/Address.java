package bean;

import java.util.Objects;

public class Address implements Comparable<Address> {
	
	private String ID;
	private String street;
	private String province;
	private String country;
	private String postalCode;
	private String phone;
	
	//Default Constructor
	public Address() {
		
		ID = "-1";
		this.street = "123 fake street";
		this.province = "";
		this.country = "";
		this.postalCode = "";
		this.phone = "555-1234";
	}
	
	public Address(String iD, String street, String province, String country, String postalCode, String phone) {
		
		ID = iD;
		this.street = street;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.phone = phone;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, country, phone, postalCode, province, street);
	}
	
	@Override
	public String toString() {
		
		return String.format("(ID:%s country:%s phone:%s postalCode:%s, province:%s street:%s)", this.ID, this.street, this.province, this.country, this.postalCode, this.phone);
	}
	
	@Override
	public int compareTo(Address a) {
		
		return this.ID.compareTo(a.ID);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof Address))
			return false;
		
		Address other = (Address) obj;
		
		return Objects.equals(ID, other.ID) && Objects.equals(country, other.country)
				&& Objects.equals(phone, other.phone) && Objects.equals(postalCode, other.postalCode)
				&& Objects.equals(province, other.province) && Objects.equals(street, other.street);
	}

}
