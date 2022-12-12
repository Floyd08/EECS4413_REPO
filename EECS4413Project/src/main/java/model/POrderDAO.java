package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.google.gson.Gson;

import bean.POItem;
import bean.POrder;
import bean.ShoppingCart;
import bean.User;

public class POrderDAO {

	Table table;
	
	public POrderDAO(DynamoDB ddb) {
		
		table = ddb.getTable("orders");
	}
	
	public void insertOrder(POrder p) {
		
		Gson gS = new Gson();
		
		Item item = new Item();
		item.withPrimaryKey("orderID", p.getID(), "userID", p.getUserID());
		item.withString("address", p.getAddress());
		item.withString( "items", gS.toJson(p.getItems()) );
		table.putItem(item);
	}
	
	public void executeOrder/*66*/(String ip, ShoppingCart cart) {
		
		String ID = generateOrderID();
		User u = Model.getInstance().uDB.retrieve(cart.getOwner());
		String userID = u.getID();
		String address = u.getAddress();
		ArrayList<POItem> items = new ArrayList<POItem>(cart.getCart().values());
		Iterator<POItem> iterator = items.iterator();
		while(iterator.hasNext()) {
			cart.logItemSale(ip, iterator.next().getItemID());
		}
		
		POrder p = new POrder(ID, userID, address, items);
		insertOrder(p);
	}
	
	public ArrayList<String> retrieveUserOrders(String userID) {
		
		ArrayList<String> list = new ArrayList<String>();
						
		ScanFilter sF = new ScanFilter("userID").eq(userID);
		
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
	
	public String retrieveOrder(String orderID, String userID) {
		
		try {
			Item item = table.getItem("orderID", orderID, "userID", userID);
			return item.toJSON();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public ArrayList<String> retrieveAll() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			ItemCollection<ScanOutcome> items = table.scan();
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
	
	public void deleteOrder(String orderID, String userID) {
		
		try {
			table.deleteItem("orderID", orderID, "userID", userID);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public String generateOrderID() {
		
		try {
			
			Item item = table.getItem("orderID", "o000", "userID", "u000");
			int count = item.getInt("orderCount");
			
			AttributeUpdate au = new AttributeUpdate("orderCount").put(count + 1);
			UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("orderID", "o000", "userID", "u000");
			update.addAttributeUpdate(au);
			table.updateItem(update);
			
			return "o" + count;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}

}
