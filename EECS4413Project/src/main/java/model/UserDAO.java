package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

import bean.User;
import modelExceptions.ItemNotFoundException;
import modelExceptions.NoPasswordMatchException;

/**
 * Data access object for User object
 * @author Conrad Lautenschlager
 */
public class UserDAO {

	Table table;

	public UserDAO(DynamoDB ddb) {

		table = ddb.getTable("users");
	}

	public void insert(User u) {

		Item item = new Item();
		item.withPrimaryKey("ID", u.getID());
		item.withString("name", u.getName());
		item.withString("surname", u.getSurName());
		item.withString("postalCode", u.getPostalCode());
		item.withString("address", u.getAddress());
		item.withString("password", u.getPassword());

		PutItemOutcome outcome = table.putItem(item);
		//System.out.println(outcome.toString());
	}

	public void delete(String ID) {

		try {
			table.deleteItem("ID", ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void delete(User u) {

		delete(u.getID());
	}

	public String retrieveAsJSON(String ID) {

		Item item = table.getItem("ID", ID);

		if (item == null)
			return null;

		return item.toJSON();
	}

	public User retrieve(String ID) {

		try {
			Item item = table.getItem("ID", ID);

			if (item == null)
				return null;

			String id = item.getString("ID");
			String name = item.getString("name");
			String surname = item.getString("surname");
			String postalCode = item.getString("postalCode");
			String address = item.getString("address");
			String password = item.getString("password");

			User u = new User(id, name, surname, postalCode, address, password);
			return u;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	public ArrayList<User> retrieveAll() {

		ArrayList<User> list = new ArrayList<>();

		try {
			ItemCollection<ScanOutcome> users = table.scan();
			Iterator<Item> iterator = users.iterator();
			while (iterator.hasNext()) {
				list.add( User.fromJSON( iterator.next().toJSON() ) );
			}
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	public void updatePassword(String ID, String newPassword, String oldPassword) throws NoPasswordMatchException, ItemNotFoundException {

		User u = retrieve(ID);

		if (u == null)
			throw new ItemNotFoundException("No user with this key is present in the table");

		if (u.changePassword(newPassword, oldPassword)) {

			try {
				AttributeUpdate au = new AttributeUpdate("password").put(u.getPassword());
				UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("ID", ID);
				update.addAttributeUpdate(au);
				table.updateItem(update);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		else
			throw new NoPasswordMatchException("Old password is invalid");
	}
	
	public void updateAddress(String userID, String street, String postalCode) {
		
		try {
			UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("ID", userID);
			AttributeUpdate au = new AttributeUpdate("address").put(street);
			update.addAttributeUpdate(au);
			au = new AttributeUpdate("postalCode").put(postalCode);
			update.addAttributeUpdate(au);
			
			table.updateItem(update);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public HashMap<String, String> extractNames() {

		HashMap<String, String> names = new HashMap<>();

		Iterator<User> items = retrieveAll().iterator();
		while (items.hasNext()) {

			User u = items.next();
			names.put(u.getName(), u.getID());
		}

		return names;
	}

	public int getUserCount() {

		try {
			Item item = table.getItem("ID", "u000");
			int count = item.getInt("userCount");

			AttributeUpdate au = new AttributeUpdate("userCount").put(count + 1);
			UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("ID", "u000");
			update.addAttributeUpdate(au);
			table.updateItem(update);

			return count;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return -1;
	}
}
