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

    public static void index() {
    	System.out.println("Website");
        render();
    }
    
    public static void menu() {
    	System.out.println("Menu section");
    	int menuCategoriesCount = Category.allMenuCategories().count(); 	
    	System.out.println("menu categories count"+menuCategoriesCount);
		List<Category> foodMenuCategories = null;
		if(menuCategoriesCount > 0){
			foodMenuCategories = Category.allMenuCategories().fetch();
		}
		render(foodMenuCategories);
	}
    
    public List<Category> foodMenu(){
    	System.out.println("Menu section");
    	int menuCategoriesCount = Category.allMenuCategories().count(); 	
    	System.out.println("menu categories count"+menuCategoriesCount);
		List<Category> foodMenuCategories = null;
		if(menuCategoriesCount > 0){
			foodMenuCategories = Category.allMenuCategories().fetch();
		}
		return foodMenuCategories;
    }


}