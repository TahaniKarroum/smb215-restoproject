package controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;

import models.Category;
import models.Client;
import models.ClientOrder;
import models.Order_Product;
import models.PVendor;
import models.Product;
import models.Vendor_Product;
import net.sf.json.JSONArray;
import play.mvc.Before;
import siena.Model;
import siena.Query;
import utils.Enums.ShippingMethod;
import utils.Enums.VendorType;
import utils.Enums;
import utils.Pagination;

public class ShoppingCart extends controllers.CRUD {

	public static ArrayList<Order_Product> cartItems;

	@Before
	public static void addDefault() throws IOException, ParseException, NoSuchAlgorithmException {
		if (session.get("loggedInEmpID") == null)
			Application.employeeLogin(false, true);
		Application.onEachController();
	}

	public static void addToCart(Order_Product op) throws IOException {
		ClientOrder order;
		List<Order_Product> orderItems = new ArrayList<Order_Product>();
		boolean status = false;
		String orderID = params.get("orderID");
		
		if (orderID != null && orderID.equals("") == false && !orderID.isEmpty()) {
			
			System.out.println("There is an order already opened");
			
			order = ClientOrder.getByID(orderID);			
			op.order_ID = orderID;
			
			if(orderItems.size() > 0){
				System.out.println("this order product ID "+op.product_ID);
				for(Order_Product p : orderItems){
					System.out.println("list order ID");
					if(op.product_ID.equals(p.product_ID)){
						status = true;
						System.out.println("this product already exist in the shopping cart");
						if(op.quantity == 0){
							order.removeOrderProduct(op.product_ID);					
						}
						else{
							order.updateOrderProduct(op.product_ID, op.quantity);
						}
					}
					
				}
				if(status == false){
					System.out.println("this is a new product to add to shopping cart");
					order.addItem(op);
					
					double total_price = op.unitPrice * op.quantity;
					op.total = total_price;
					
					op.saveOrder_Product("");
				}
			}
			else{
				System.out.println("the shopping cart is empty now we add a new product to it");
				order.addItem(op);
				
				double total_price = op.unitPrice * op.quantity;
				op.total = total_price;
				
				op.saveOrder_Product("");
			}
									
		} 
		else {
			
			System.out.println("No order found!!!");
			
			order = new ClientOrder();
			Date todaydate = new Date();
			order.orderDate = todaydate;
			
			order.saveOrder();
			
			op.order_ID = order.ID;
			
			double total_price = op.unitPrice * op.quantity;
			op.total = total_price;	
			
			op.saveOrder_Product("");
						
		}
			
		order.addItem(op);
		
		order.saveOrder();

		WebApplication.index(order);
	}
	
	public static void removeItem(String product_id){
		List<Order_Product> productsList = Order_Product.getAllOrderProducts().filter("product_ID", product_id).fetch();
		int productsCount = productsList.size();
		if(productsCount > 0){
			for(Order_Product item : productsList){
				if(item != null){
					item.delete();
				}
			}
		}
	}

}