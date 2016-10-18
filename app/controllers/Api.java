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

	public static JSONArray getlistItemsByOrder(String orderid) {
		if (orderid != null && orderid.length() > 0) {
			ClientOrder order = ClientOrder.getByID(orderid);
			List<Order_Product> items = order.getListOrderProduct();
			Gson gson = new Gson();
			String jsonData = gson.toJson(items);
			JSONArray jsonA = JSONArray.fromObject(jsonData);
			return jsonA;
		} else
			return null;

	}

	public static JSONArray ping(String deviceid) {
		Client cl = Client.getByDeviceId(deviceid).get();
		if (cl == null) {
			cl = new Client();
			cl.device_uid = deviceid;
			cl.name = "Blabla";
		}
		cl.saveClient();
		List<Client> newCl = new ArrayList<Client>();
		newCl.add(Client.getByID(cl.ID));
		Gson gson = new Gson();
		String jsonData = gson.toJson(newCl);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}

	public static JSONArray addtocart(String deviceid, String productid, String orderid, int qty) {
		Client cl = Client.getByDeviceId(deviceid).get();
		if (cl == null) {
			cl = new Client();
			cl.device_uid = deviceid;
			cl.name = "Blabla";
			cl.saveClient();
		}

		Product pr = Model.all(Product.class).filter("ID", productid).get();
		Order_Product op = null;
		ClientOrder order = null;
		Date todaydate = new Date();
		if (orderid != null)
			order = ClientOrder.getByID(orderid);
		if (order == null) {
			order = new ClientOrder();
			order.client_ID = cl.ID;
			order.orderDate = todaydate;
			order.saveOrder();
		}
		op = Model.all(Order_Product.class).filter("order_ID", order.ID).filter("product_ID", productid).get();
		if (op == null)
			op = new Order_Product();

		op.product_ID = productid;
		op.quantity = qty;
		op.productname = pr.name;
		op.image = pr.imagePath;
		op.total = pr.price * qty;
		op.unitPrice = pr.price;
		op.order_ID = order.ID;
		op.saveOrder_Product(cl.ID);
		if (qty == 0)
			op.delete();
		List<Order_Product> list = new ArrayList<Order_Product>();
		list.add(op);
		Gson gson = new Gson();
		String jsonData = gson.toJson(list);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}

	public static JSONArray fillClientInformation(String deviceid, String name, String address, String phone) {
		Client cl = Client.getByDeviceId(deviceid).get();
		cl.name = name;
		cl.address = address;
		cl.phone = phone;
		cl.saveClient();
		Gson gson = new Gson();
		String jsonData = gson.toJson(cl);
		JSONArray jsonA = JSONArray.fromObject(jsonData);
		return jsonA;
	}

}
