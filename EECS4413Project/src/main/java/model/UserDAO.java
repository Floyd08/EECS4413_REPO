package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.google.gson.Gson;

import bean.ItemP;
import bean.User;
import modelExceptions.ItemNotFoundException;
import modelExceptions.NoPasswordMatchException;

/**
 * Data access object for User object 
 * @author Conrad Lautenschlager
 */
public class UserDAO {
	
	AmazonDynamoDB ds;
	DynamoDB ddb;
	Table table;
	
	public UserDAO() {
		
		//TO CONNECT TO DYNAMO LOCAL: comment out the line ds = ... and uncomment the other line
		AWSStaticCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAVKWM63KXGTCZ7VRE","LL8uQAja9sK3oPkSCEZzQvUjBpLEQTtugN0h9F+1"));
		ds = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(credentials).build();
		//ds = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-2")).build();
		ddb = new DynamoDB(ds);
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
		
		table.putItem(item);
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
		return item.toJSON();
	}
	
	public User retrieve(String ID) {
		
		try {
			Item item = table.getItem("ID", ID);
			
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
		
		ArrayList<User> list = new ArrayList<User>();
		
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
	
	public HashMap<String, String> extractNames() {
		
		HashMap<String, String> names = new HashMap<String, String>();
		
		Iterator<User> items = retrieveAll().iterator();
		while (items.hasNext()) {
			
			User u = items.next();
			names.put(u.getName(), u.getID());
		}
		
		return names;
	}
}
