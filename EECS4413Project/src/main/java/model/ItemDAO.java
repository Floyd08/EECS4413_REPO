package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sql.DataSource;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.google.gson.Gson;

import bean.ItemP;
import modelExceptions.ItemNotFoundException;

/**
 * Data access object for ItemP object 
 * @author Conrad Lautenschlager
 */
public class ItemDAO {

	AmazonDynamoDB ds;
	DynamoDB ddb;
	Table table;
	
	public ItemDAO() {
		
		AWSStaticCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAVKWM63KXGTCZ7VRE","LL8uQAja9sK3oPkSCEZzQvUjBpLEQTtugN0h9F+1"));
		ds = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(credentials).build();
		//ds = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-2")).build();
		ddb = new DynamoDB(ds);
		table = ddb.getTable("items");
	}
	
	public void insert(ItemP i) {
		
		Item item = new Item();
		item.withPrimaryKey("ID", i.getID());
		item.withString("name", i.getName());
		item.withString("description", i.getDescription());
		item.withString("type", i.getDescription());
		item.withString("brand", i.getBrand());
		item.withString("quantity", String.valueOf(i.getQuantity()) );
		item.withString("price", String.valueOf( i.getPrice()) );
		
		// Write the item to the table
		PutItemOutcome outcome = table.putItem(item);
		//System.out.println(outcome.toString());
	}
	
	public void insert(ArrayList<ItemP> iList) {
		
		Iterator<ItemP> i = iList.iterator();
		while (i.hasNext())
			insert(i.next());
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
	
	public void delete(ArrayList<ItemP> iList) {
		
		Iterator<ItemP> i = iList.iterator();
		while (i.hasNext())
			delete(i.next());
	}
	
	public void delete(ItemP i) {
		
		delete(i.getID());
	}
	
	public String retrieveAsJSON(String ID) {
		
		Item item = table.getItem("ID", ID);
		return item.toJSON();		
	}
	
	public ItemP retrieve(String ID) {
		
		try {
			Item item = table.getItem("ID", ID);
			
			String id = item.getString("ID");
			String name = item.getString("name");
			String description = item.getString("description");
			String type = item.getString("type");
			String brand = item.getString("brand");
			int quantity = item.getInt("quantity");
			double price = item.getDouble("price");
			
			ItemP i = new ItemP(id, name, description, type, brand, quantity, price);
			return i;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public void updateQuantity(String ID, int quant) throws ItemNotFoundException {
		
		ItemP p = retrieve(ID);				//could be simpler, no need to actually cache the item
		
		if (p == null)
			throw new ItemNotFoundException("No item with this key is present in the table");
		
		try {
			AttributeUpdate au = new AttributeUpdate("quantity").put(String.valueOf(quant));
			//table.updateItem(ID, au);
			UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("ID", ID);
			update.addAttributeUpdate(au);
			table.updateItem(update);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public ItemP fromJSON(String Json) {
		
		Gson gS = new Gson();
		return gS.fromJson(Json, ItemP.class);
	}	
}








