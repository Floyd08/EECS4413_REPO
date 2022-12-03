package model;

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
	    		
		testItemDAO();
		//testUserDAO();
		
		//listMyTables();
		//System.out.println();
		//listItems("items");
		//listItems("users");
		
	}
	
	public static void testItemDAO() {
		
		ItemDAO iS = new ItemDAO();
		
		ItemP i1 = new ItemP("a006", "coffee", "brown liquid", "food", "pilot", 14, 19.99);
		ItemP i2 = new ItemP("b003", "book", "paper and words", "book", "BookCo", 3, 49.99);
		
		//String jTest = i1.toJSON();
		//ItemP i4 = iS.fromJSON(jTest);
		//System.out.println(i4.toString());
		
		try {
			//iS.insert(i1);
			//iS.insert(i2);
			//iS.delete("a006");
			//iS.updateQuantity("b003", 8);
			//ItemP i3 = iS.retrieve("a006");
			//System.out.println("i3: " + i3.toString());
			//String i3 = iS.retrieveAsJSON("a006");
			//System.out.println(i3);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
		}
	}
	
	public static void testUserDAO() {
		
		UserDAO uS = new UserDAO();
		
		User u1 = new User("u004", "John", "Smith", "A1A 1A1", "123 Fake Street", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8");
		
		try {
			//uS.insert(u1);
			//uS.delete("u004");
			//User u3 = uS.retrieve("u004");
			//System.out.println("u3: " + u3.toString());
			//uS.updatePassword("u004", "notpassword", "password");
			String u3 = uS.retrieveAsJSON("u004");
			System.out.println(u3);
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
