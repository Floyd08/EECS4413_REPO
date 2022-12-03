package model;

public class Model {
	
	private static Model instance;
	public ItemDAO iDB;
	public UserDAO uDB;

	private Model() {
		
		iDB = new ItemDAO();
		uDB = new UserDAO();
	}
	
	public static Model getInstance() {
		
		if (instance == null) {
			
			instance = new Model();
		}
		
		return instance;
	}

}
