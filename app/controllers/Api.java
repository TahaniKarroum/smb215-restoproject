package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import models.Category;
import models.Client;
import models.ClientOrder;
import models.Order_Product;
import models.Product;
import net.sf.json.JSONArray;
import siena.Model;

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

	public static JSONArray ping(String deviceid) {
		Client cl = Client.getByDeviceId(deviceid).get();
		if (cl == null) {
			cl = new Client();
		}
		cl.device_uid = deviceid;
		cl.name = "xxx";
		cl.saveClient();
		Gson gson = new Gson();
		String jsonData = gson.toJson(cl);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}

	public static JSONArray addtocart(String deviceid, String productid, String orderid, int qty) {
		Client cl = Client.getByDeviceId(deviceid).get();
		Product pr = Product.getByID(productid);
		Order_Product op = null;
		ClientOrder order = null;
		if (orderid != null)
			order = ClientOrder.getByID(orderid);
		else
			order = new ClientOrder();
		Date todaydate = new Date();
		order.client_ID = cl.ID;
		order.orderDate = todaydate;
		order.saveOrder();
		op = Model.all(Order_Product.class).filter("order_ID", order.ID).filter("product_ID", productid).get();
		if (op != null) {
			op.quantity = qty;
			op.total = pr.price * qty;
			op.unitPrice = pr.price;
			op.order_ID = order.ID;
			op.saveOrder_Product(cl.ID);
		}
		Gson gson = new Gson();
		String jsonData = gson.toJson(order);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}
	
	public static JSONArray fillClientInformation(String deviceid,String name,String address, String phone) {
		Client cl = Client.getByDeviceId(deviceid).get();
		cl.name = name;
		cl.address = address;
		cl.phone=phone;
		cl.saveClient();
		Gson gson = new Gson();
		String jsonData = gson.toJson(cl);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}
}
