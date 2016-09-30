package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import models.Category;
import models.Product;
import net.sf.json.JSONArray;

public class Api extends controllers.CRUD {

	public static JSONArray getAllCategories() {
		Gson gson = new Gson();
		List<Category> allCategoriesList = Category.allMenuCategories().fetch();
		String jsonData = gson.toJson(allCategoriesList);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}

	public static JSONArray getAllProducts(String categoryid) {
		Gson gson = new Gson();
		List<Product> allProducts = Product.getProductByCategory(categoryid).fetch();
		String jsonData = gson.toJson(allProducts);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}

	public static JSONArray getProduct(String productid) {
		Gson gson = new Gson();
		Product pro = Product.getByID(productid);
		String jsonData = gson.toJson(pro);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}

	public static JSONArray checkAvailableProduct(String productid) {
		Gson gson = new Gson();
		Product pro = Product.getByID(productid);
		JSONArray jsonA = null;
		if (pro.qteInStock > 0) {
			String jsonData = gson.toJson(pro);
			jsonA = JSONArray.fromObject(jsonData);
		}
		return jsonA;
	}

}
