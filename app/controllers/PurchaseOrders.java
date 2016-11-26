package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import models.Brand;
import models.PVendor;
import models.PurchaseOrder;
import models.PurchseOrderItem;
import models.Product;
import models.Product_product_Composition;
import play.mvc.*;
import siena.Model;
import utils.Enums;
import utils.Pagination;
import utils.Enums.StatusOrder;

public class PurchaseOrders extends controllers.CRUD {

	@Before
	public static void addDefault() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		Application.onEachController();

	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static boolean setArgsList() {
		List<PurchaseOrder> vendorOrdersList = PurchaseOrder.all().fetch();
		renderArgs.put("vendorOrdersList", vendorOrdersList);
		return true;
	}

	public static void manage() {
		currentPage("orders");
		String pageNbFromSession = session.get("currentPurchaseOrdersPage");
		int itemsCount = PurchaseOrder.all().filter("status", Enums.StatusOrder.UnderPrepare.ordinal()).count();
		Pagination pagination = null;
		List<PurchaseOrder> vendorOrdersList = null;
		HashMap<String, List<PurchseOrderItem>> map = null;
		if (itemsCount > 0) {
			map = new HashMap<String, List<PurchseOrderItem>>();
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentPurchaseOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = PurchaseOrder.all().filter("status", Enums.StatusOrder.UnderPrepare.ordinal())
					.order("orderDate").fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		render(vendorOrdersList, pagination);
	}

	public static void NewOrder(String vendor_ID) {
		currentPage("NewOrder");
		
		//get Vendor
		PVendor vendor = PVendor.getByID(vendor_ID);
		
		//Get All Products 
		List<Product> productsList = Product.allStockProducts().fetch();
		
		//Get Products for this vendor 
		List<Product> selectedProductsList = Vendors.getProductsByVendor(vendor_ID);
	 
		List<String> productsIDs = null;
		if (selectedProductsList != null && selectedProductsList.size() > 0) {
			productsIDs = new ArrayList<String>();
			for (Product p : selectedProductsList) {
				if (p != null)
					productsIDs.add(p.ID);
			}
		}
		render(vendor, productsList, selectedProductsList, productsIDs);
	 
	}
	
	
	public static void savePurchaseOrder() {
		 
	}
	
	
	public static void payOrders() {
		currentPage("ReadyOrders");
		String pageNbFromSession = session.get("currentPayPurchaseOrdersPage");
		int itemsCount = PurchaseOrder.all().filter("status", Enums.StatusOrder.Completed.ordinal()).count();
		Pagination pagination = null;
		List<PurchaseOrder> vendorOrdersList = null;
		HashMap<String, List<PurchseOrderItem>> map = null;
		if (itemsCount > 0) {
			map = new HashMap<String, List<PurchseOrderItem>>();
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentPayPurchaseOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = PurchaseOrder.all().filter("status", Enums.StatusOrder.Completed.ordinal())
					.order("orderDate").fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		render(vendorOrdersList, pagination);
	}

	public static void listOfPaidOrders() {
		boolean isFilteringMode = false;
		currentPage("PaidOrders");
		String pageNbFromSession = session.get("currentPaidOrdersPurchaseOrdersPage");
		int itemsCount = PurchaseOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal()).count();
		Pagination pagination = null;
		List<PurchaseOrder> vendorOrdersList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentPaidOrdersPurchaseOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = PurchaseOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal()).order("orderDate")
					.fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		render(vendorOrdersList, pagination,isFilteringMode);
	}

	public static void searchByDate() throws ParseException {
		String orderDate = params.get("orderDate");
		Date date = Application.getDateFromString(orderDate, "dd/MM/yyyy");
		int itemsCount = 0;
		String pageNbFromSession = session.get("currentPaidOrdersPurchaseOrdersPage");
		Pagination pagination = null;
		List<PurchaseOrder> vendorOrdersList = null;
		Date fromDate = Application.getFirstDateInDay(date);
		Date toDate = Application.getLastDateInDay(date);
		itemsCount = PurchaseOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal())
				.filter("orderDate >=", fromDate).filter("orderDate<=", toDate).count();
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentPaidOrdersPurchaseOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = PurchaseOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal())
					.filter("orderDate >=", fromDate).filter("orderDate<=", toDate).order("orderDate")
					.fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		boolean isFilteringMode = true;
		renderTemplate("PurchaseOrders/listOfPaidOrders.html", itemsCount, vendorOrdersList, isFilteringMode, orderDate,pagination);
	}

	public static void setReady(String id) {
		PurchaseOrder order = PurchaseOrder.getByID(id);
		order.status = Enums.StatusOrder.Completed.ordinal();
		order.saveOrder();
		payOrders();
	}

	public static void setPaid(String id) {
		PurchaseOrder order = PurchaseOrder.getByID(id);
		order.status = Enums.StatusOrder.Paid.ordinal();
		order.saveOrder();
		payOrders();
	}

	public static void getPage(int page) throws IOException {
		currentPage("orders");
		session.put("currentPurchaseOrdersPage", page);
		listOfPaidOrders();
	}

	public static void getPayPage(int page) throws IOException {
		currentPage("ReadyOrders");
		session.put("currentPayPurchaseOrdersPage", page);
		payOrders();
	}

	public static void getPaidOrdersPage(int page) throws IOException {
		currentPage("PaidOrders");
		session.put("currentPaidOrdersPurchaseOrdersPage", page);
		listOfPaidOrders();
	}
}
