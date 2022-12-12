package model;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class AddressDAO {

	Table table;
	
	public AddressDAO(DynamoDB ddb) {
		
		table = ddb.getTable("addresses");
	}
	
	public void insertAddress(String street, String province, String country, String postalCode, String phone) {
		
		Item item = new Item();
		item.withPrimaryKey("street", street);
		item.withString("province", province);
		item.withString("country", country);
		item.withString("postalCode", postalCode);
		item.withString("phone", phone);
		
		table.putItem(item);
	}
	
	public String getAddress(String street) {
		
		try {
			Item item = table.getItem("street", street);
			return item.toJSON();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}
}
