package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;

import bean.ItemP;
import bean.ShoppingCart;
import bean.User;

public class Model {
	
	private static Model instance;
	AmazonDynamoDB ds;
	DynamoDB ddb;
	
	public ItemDAO iDB;
	public UserDAO uDB;
	public ReviewDAO rDB;
	public ArrayList<String> loggedIn = new ArrayList<String>();
	public HashMap<String, ShoppingCart> carts = new HashMap<String, ShoppingCart>();	//Maps user IDs to ShoppingCarts
		
	private Model() {
		
		//TO CONNECT TO DYNAMO LOCAL: comment out the line ds = ... and uncomment the other line
		AWSStaticCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAVKWM63KXGTCZ7VRE","LL8uQAja9sK3oPkSCEZzQvUjBpLEQTtugN0h9F+1"));
		//ds = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-2")).build();
		ds = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(credentials).build();
		ddb = new DynamoDB(ds);
		
		iDB = new ItemDAO(ddb);
		uDB = new UserDAO(ddb);
		rDB = new ReviewDAO(ddb);
	}
	
	public static Model getInstance() {
		
		if (instance == null) {
			
			instance = new Model();
		}
		
		return instance;
	}

	public boolean makeActive(String ID) throws NullPointerException {
		
		if ( !loggedIn.contains(ID) )
			return loggedIn.add(ID);
		
		return false;
	}
	
	public boolean makeInActive(String ID) {
		
		return loggedIn.remove(ID);
	}
		
} 








