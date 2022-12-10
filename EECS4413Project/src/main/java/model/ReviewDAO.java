package model;

import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

public class ReviewDAO {

	Table table;
	
	public ReviewDAO(DynamoDB ddb) {
		
		table = ddb.getTable("reviews");
	}
	
	public void insertReview(String itemID, String userID, String name, String surName, String comment) {
		
		Item item = new Item();
		item.withPrimaryKey("itemID", itemID, "userID", userID);
		item.withString("name", name);
		item.withString("surname", surName);
		item.withString("comment", comment);
		//System.out.println(item.toJSONPretty());
		table.putItem(item);
	}
	
	public void deleteReview(String itemID, String userID) {
		
		try {
			table.deleteItem("itemID", itemID, "userID", userID);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public String getReview(String itemID, String userID) {
		
		try {
			Item item = table.getItem("itemID", itemID, "userID", userID);
			return item.toJSON();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public ArrayList<String> getAllofItem(String itemID){
		
		ArrayList<String> list = new ArrayList<String>();
		
		//String atrName = "itemID";				
		ScanFilter sF = new ScanFilter("itemID").eq(itemID);
		
		try {
			ItemCollection<ScanOutcome> items = table.scan(sF);
			Iterator<Item> iterator = items.iterator();
			while (iterator.hasNext()) {
				list.add( iterator.next().toJSON() );
			}
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	public void editComment(String itemID, String userID, String newComment) {
		
		try {
			AttributeUpdate au = new AttributeUpdate("comment").put(newComment);
			UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("itemID", itemID, "userID", userID);
			update.addAttributeUpdate(au);
			table.updateItem(update);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
