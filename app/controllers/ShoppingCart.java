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
		ClientOrder order = null;
		String orderID = params.get("orderID");
		if (orderID != null && orderID != "") {
			System.out.println("product order ID = "+op.order_ID);
			order = ClientOrder.getByID(op.order_ID);
			op.order_ID = orderID;
			order.addItem(op);
		} else {
			order = new ClientOrder();
			Date todaydate = new Date();
			order.orderDate = todaydate;
			order.addItem(op);
			order.saveOrder();
			
			op.order_ID = order.ID;
		}			

		double total_price = op.unitPrice * op.quantity;
		op.total = total_price;
		
		op.saveOrder_Product("");		

		WebApplication.index(order);
	}

}