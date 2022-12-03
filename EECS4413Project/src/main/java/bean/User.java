package bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import com.google.gson.Gson;


/**
 * Data object to encapsulate user information
 * @author Conrad Lautenschlager
 */
public class User implements Comparable<User> {

	private String ID;
	private String name;
	private String surName;
	private String postalCode;
	private String address;
	private String password;
	
	public User() {
		
		ID = "-1";
		this.name = "Jack";
		this.surName = "Johnson";
		this.postalCode = "1A1 A1A";
		this.address = "-1";
		this.password = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";		//the SHA-1 hash of "password"
	}
	
	public User(String iD, String name, String surName, String postalCode, String address, String password) {
		
		ID = iD;
		this.name = name;
		this.surName = surName;
		this.postalCode = postalCode;
		this.address = address;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 
	 * @param password
	 * @return A hexadecimal representation of the SHA-1 hash
	 * @throws NoSuchAlgorithmException
	 */
	
	public String hashPassword(String password) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] hash = md.digest(password.getBytes());
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < hash.length; ++i){
			
			int tmp = hash[i] & 0xFF;
			if (tmp < 16)
				buffer.append("0");
			buffer.append(Integer.toHexString(tmp));
		}
		return buffer.toString();
	}
	/**
	 * Will change the password on file to newPassword, if and only if oldPassword is valid
	 * 
	 * @param newPassword
	 * @param oldPassword
	 * @return true if the old password hashes to the old password on record, or false otherwise
	 */
	public boolean changePassword(String newPassword, String oldPassword) {
		
		try {
			String old = hashPassword(oldPassword);
			
			//System.out.println("oldPassword: " + oldPassword);
			//System.out.println("rehash: " + old);
			//System.out.println("hash from u: " + this.getPassword());
			
			if ( old.equals(this.getPassword()) ) {
				
				this.setPassword(hashPassword(newPassword));
				return true;
			}
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public String toJSON() {
		
		Gson gS = new Gson();
		return gS.toJson(this);
	}
	
	@Override
	public String toString() {
		
		return String.format("(ID:%s name:%s surName:%s postalCode:%s address:%s, password:%s)", ID, name, surName, postalCode, address, password);
	}
	
	@Override
	public int compareTo(User u) {
		
		return this.ID.compareTo(u.ID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, address, name, password, postalCode, surName);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof User))
			return false;
		
		User other = (User) obj;
		
		return Objects.equals(ID, other.ID) && Objects.equals(address, other.address)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(surName, other.surName);
	}
	
	
}
