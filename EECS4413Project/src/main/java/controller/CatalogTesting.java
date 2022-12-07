package controller;

import java.util.ArrayList;
import java.util.HashMap;

import bean.ItemP;
import bean.User;
import model.Model;
import bean.ShoppingCart;

public class CatalogTesting {

	public static void main(String[] args) {
		
		Model m = Model.getInstance();
		ArrayList<String> list;
		HashMap<String, String> names;
		
		//Catalog.add(m, "{\"quantity\":\"24\",\"price\":\"5.49\",\"name\":\"eggs\",\"description\":\"Protein Capsule\",\"ID\":\"a047\",\"type\":\"food\",\"brand\":\"burnbrae\"}");
		//list = Catalog.viewAll(m);
		//list = Catalog.viewByBrand(m, "BookCo");
		list = Catalog.viewByType(m, "food");
		//list = Catalog.getBrandList(m);
		//list = Catalog.getTypeList(m);
		//names = Catalog.getNameMap(m);
		System.out.println(list);
		//System.out.println(names);
		
		ShoppingCartTesting();
		
	}
	
	public static void ShoppingCartTesting() {
		
		Model m = Model.getInstance();
		
		User u1 = new User("u004", "John", "Smith", "A1A 1A1", "123 Fake Street", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8");
		ItemP i1 = new ItemP("a006", "coffee", "brown liquid", "food", "pilot", 14, 19.99);
		ItemP i2 = new ItemP("b003", "book", "paper and words", "book", "BookCo", 3, 49.99);
		ItemP i3 = new ItemP("a016", "cheese", "femented milk", "food", "balderson", 7, 12.99);
		ItemP i4 = new ItemP("c005", "towel", "real big cloth, yo", "houseware", "towelCo", 4, 26.49);
		
		m.carts.put(u1.getID(), new ShoppingCart(u1.getID()));
		System.out.println("Is the new cart empty? " + m.carts.get(u1.getID()).isEmpty());
		
		ShoppingCart c = m.carts.get(u1.getID());
		c.AddToCart(i1);
		System.out.println("Is the cart still empty? " + m.carts.get(u1.getID()).isEmpty());
		c.AddToCart(i2);
		c.AddToCart(i3);
		c.AddToCart(i4);
		System.out.println(c.toString());
		c.removeFromCart(i4);
		c.removeFromCart(i2);
		System.out.println(c.toString());
		c.updateCartQuant(i1, 12);
		System.out.println(c.toString());
		c.AddToCart(i3);
		System.out.println(c.toString());
	}

	
}








