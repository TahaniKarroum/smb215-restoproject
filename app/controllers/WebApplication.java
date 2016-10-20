package controllers;

import play.*;
import play.mvc.*;
import siena.Model;
import utils.Pagination;
import utils.Enums.LogType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

import models.*;

public class WebApplication extends Controller {

    public static void index(ClientOrder order) {
    	
    	String orderID = "";
    	int shoppingCartItems = 0;
    	double totalPrice = 0.0;
    	List<Order_Product> orderProductList = null;
    	HashMap<String, Integer> inOrder = new HashMap<String, Integer>();
    	
    	if(order != null){
    		orderID = order.ID;
    		orderProductList = order.getListOrderProduct();
    		for(Order_Product p : orderProductList){
    			shoppingCartItems = shoppingCartItems + p.quantity;
    			totalPrice = totalPrice + p.total;
    			inOrder.put(p.product_ID, p.quantity);
    		}
    	}
    	
    	//System.out.println("Items count >> "+cartItemsCount);
    	List<Category> foodMenuCategories = foodCategories();
    	List<Product> foodMenu = foodMenu();
    	HashMap<String, String> hmap = new HashMap<String, String>();
    	for(Category cat: foodMenuCategories){
    		hmap.put(cat.ID, cat.name);
    	}
        render(foodMenuCategories, foodMenu, hmap, orderID, shoppingCartItems, inOrder, orderProductList, totalPrice);
    }
        
    public static List<Category> foodCategories(){
    	int menuCategoriesCount = Category.allMenuCategories().count(); 	
		List<Category> foodMenuCategories = null;
		if(menuCategoriesCount > 0){
			foodMenuCategories = Category.allMenuCategories().fetch();
		}
		return foodMenuCategories;
    }
    
    public static List<Product> foodMenu(){
    	int foodMenuCount = Product.allMenuProducts().count();
    	List<Product> foodMenu = null;
    	if(foodMenuCount > 0){
    		foodMenu = Product.allMenuProducts().fetch();
    	}
    	return foodMenu;
    }
    
    public static void removeOrderProduct(String product_id){
		List<Order_Product> productsList = Order_Product.getAllOrderProducts().filter("product_ID", product_id).fetch();
		String order_id;
		int productsCount = productsList.size();
		if(productsCount > 0){
			for(Order_Product item : productsList){				
				if(item != null){
					order_id = item.order_ID;
					item.delete();
					ClientOrder order = ClientOrder.getByID(order_id);
					WebApplication.index(order);
				}
			}					
		}		
	}
    

}