package model;

import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import bean.ItemP;
import bean.User;

public class DAOTest {
	
	//static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	//static DynamoDB dynamoDB = new DynamoDB(client);
	static AWSStaticCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAVKWM63KXGTCZ7VRE","LL8uQAja9sK3oPkSCEZzQvUjBpLEQTtugN0h9F+1"));
	static AmazonDynamoDB ds = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(credentials).build();
	//static AmazonDynamoDB ds = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-2")).build();
	static DynamoDB ddb = new DynamoDB(ds);

	public static void main(String[] args) {
	    		
		//testItemDAO(ddb);
		//testUserDAO(ddb);
		//testReviews(ddb);
		testViewEvents(ddb);
		//testCat();
		
		//listMyTables();
		//System.out.println();
		//listItems("items");
		//listItems("users");
		//listItems("reviews");
		listItems("events");
		
	}
	
	public static void testItemDAO(DynamoDB ddb) {
		
		ItemDAO iS = new ItemDAO(ddb);
		ArrayList<String> list = new ArrayList<String>();
		
		ItemP i1 = new ItemP("a006", "coffee", "brown liquid", "food", "pilot", 14, 19.99);
		ItemP i2 = new ItemP("b003", "book", "paper and words", "book", "BookCo", 3, 49.99);
		ItemP i5 = new ItemP("a016", "cheese", "femented milk", "food", "balderson", 7, 12.99);
		ItemP i6 = new ItemP("c005", "towel", "real big cloth, yo", "houseware", "towelCo", 4, 26.49);
		
		//String jTest = i1.toJSON();
		//ItemP i4 = iS.fromJSON(jTest);
		//System.out.println(i4.toString());
		
		try {
			iS.insert(i1);
			iS.insert(i2);
			iS.insert(i5);
			iS.insert(i6);
//			iS.delete("a006");
//			iS.updateQuantity("b003", 8);
//			ItemP i3 = iS.retrieve("a006");
//			System.out.println("i3: " + i3.toString());
//			String i3 = iS.retrieveAsJSON("a006");
//			System.out.println(i3);
			ArrayList<ItemP> allItems = new ArrayList<ItemP>();
			allItems = iS.retrieveAll();
			System.out.println(allItems.toString());
//			list = iS.retrieveByType("food");
//			list = iS.retrieveByBrand("BookCo");
//			list = iS.getTypes();
//			list = iS.getBrands();
//			System.out.println(list.toString());
//			iS.delete("a047");
//			iS.insertFromJSON("{\"quantity\":\"24\",\"price\":\"5.49\",\"name\":\"eggs\",\"description\":\"Protein Capsule\",\"ID\":\"a047\",\"type\":\"food\",\"brand\":\"burnbrae\"}");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
		}
	}
	
	public static void testUserDAO(DynamoDB ddb) {
		
		UserDAO uS = new UserDAO(ddb);
		
		User u1 = new User("u004", "John", "Smith", "A1A 1A1", "123 Fake Street", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8");
		
		try {
			//uS.insert(u1);
			//uS.delete("u004");
			//User u3 = uS.retrieve("u005");
			//System.out.println("u3: " + u3.toString());
			//uS.updatePassword("u004", "notpassword", "password");
			String u4 = uS.retrieveAsJSON("u004");
			System.out.println(u4);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
		}
	}
	
	public static void testReviews(DynamoDB ddb) {
		
		ReviewDAO rS = new ReviewDAO(ddb);
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			rS.insertReview("a006", "u004", "John", "Smith", "I like the way coffee tastes, because it tastes like coffee, which is nice");
			rS.insertReview("a016", "u004", "John", "Smith", "mmmmmm, cheeeese");
			//rS.deleteReview("a006", "u004");
			//String r1 = rS.getReview("a006", "u004");
			//System.out.println(r1);
			//rS.editComment("a006", "u004", "Boooo coffee, booo");
			list = rS.getAllofItem("a006");
			System.out.println(list.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
		}
	}
	
	public static void testViewEvents(DynamoDB ddb) {
		
		ViewEventDAO vS = new ViewEventDAO(ddb);
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			
//			vS.insertEvent("192.168.0.1", "10122022", "a006", "sale");
//			vS.insertEvent("192.168.0.1", "09122022", "a006", "sale");
//			vS.insertEvent("192.168.0.1", "10122022", "a016", "view");
//			vS.insertEvent("192.168.0.1", "09122022", "a016", "view");
//			vS.insertEvent("192.168.0.1", "06122022", "b003", "sale");
			list = vS.retrieveEventByItem("a016");
			System.out.println(list.toString());
			list = vS.retrieveSales();
			System.out.println(list.toString());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
		}
	}
	
	public static void listMyTables() {

		ListTablesResult tables = ds.listTables();
		System.out.println(tables);
    }
	
	public static void listItems(String table) {
		
		Table itemTable = ddb.getTable(table);
		
		ItemCollection<ScanOutcome> items = itemTable.scan();
		System.out.println("Scan of items: ");
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toJSONPretty());
        }
		
	}

}
