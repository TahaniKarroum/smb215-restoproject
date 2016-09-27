package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import models.Category;


public class Api extends controllers.CRUD {
	
	public static String getAllCategories(){
		Gson gson=new Gson();
		List<Category> allCategoriesList=Category.allMenuCategories().fetch();
		String jsonData = gson.toJson(allCategoriesList);
		renderText(jsonData);
		return jsonData;
	}
}
