package bean;

public class modelTesting {

	public static void main(String[] args) {
		
		testItem();
		//testAddress();
	}
	
	public static void testItem() {
		
		String ID1 = "0001", ID2 = "0002", ID3 = "0001";
		ItemP i1 = new ItemP(ID1, "Oranges", "Spherical orange citrus", "Food", "FoodCo", 3,  14.99);
		ItemP i2 = new ItemP(ID2, "Books", "A thing you read", "Book", "BookCo", 58, 43.99);
		ItemP i3 = new ItemP(ID3, "Apples", "Spheroid fruit", "Food", "FoodCo", 4, 8.99);
		ItemP dumbyI = new ItemP();
		boolean b = false;
		
		System.out.println(dumbyI.toString() + "\n");
		
		System.out.println(i1.toString());
		System.out.println(i2.toString());
		
		if (i1.compareTo(i3) == 0)
			b = true;
		
		System.out.println("Is i1 equal to i3?");
		System.out.println(b);
	}
	
	public static void testAddress() {
		
		
	}
	
	public static void testPOrder() {}
	
	public static void testPOItem() {}
	
	public static void ViewEvent() {}
	

}






