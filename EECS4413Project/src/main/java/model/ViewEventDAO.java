package model;

import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

public class ViewEventDAO {

	Table table;
	
	public ViewEventDAO(DynamoDB ddb) {
		
		table = ddb.getTable("events");
	}
	
	public void insertEvent(String ip, String date, String itemID, String type) {
		
		//retrieve eventCount
		String ID = generateEventID();
		
		Item item = new Item();
		item.withPrimaryKey("ID", ID);
		item.withString("ip", ip);
		item.withString("date",  date);
		item.withString("itemID", itemID);
		item.withString("type", type);
		
		table.putItem(item);
	}
	
	public String generateEventID() {
		
		try {
			Item item = table.getItem("ID", "e000");
			int count = item.getInt("eventCount");
			
			AttributeUpdate au = new AttributeUpdate("eventCount").put(count + 1);
			UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey("ID", "e000");
			update.addAttributeUpdate(au);
			table.updateItem(update);
			
			return "e" + count;
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
	
	public ArrayList<String> retrieveEventByItem(String itemID) {
		
		ArrayList<String> list = new ArrayList<String>();
		
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
	
	public ArrayList<String> retrieveEventByType(String typeArg) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		ScanFilter sF = new ScanFilter("type").eq(typeArg);
		
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
	
	public ArrayList<String> retrieveSales() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		ScanFilter sF = new ScanFilter("type").eq("sale");
		
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
	
}
