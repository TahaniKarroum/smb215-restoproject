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
    	int cartItemsCount = 0;
    	if(order != null){
    		orderID = order.ID;
    		cartItemsCount = order.getListOrderProduct().size();
    	}
    	
    	System.out.println("Items count >> "+cartItemsCount);
    	List<Category> foodMenuCategories = foodCategories();
    	List<Product> foodMenu = foodMenu();
    	HashMap<String, String> hmap = new HashMap<String, String>();
    	for(Category cat: foodMenuCategories){
    		hmap.put(cat.ID, cat.name);
    	}
        render(foodMenuCategories, foodMenu, hmap, orderID, cartItemsCount);
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


}