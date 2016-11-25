package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import models.Brand;
import models.ClientOrder;
import models.Order_Product;
import models.Product;
import play.mvc.*;
import siena.Model;
import utils.Enums;
import utils.Pagination;
import utils.Enums.StatusOrder;

public class PurchaseOrder extends controllers.CRUD {

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
		List<ClientOrder> vendorOrdersList = ClientOrder.all().fetch();
		renderArgs.put("vendorOrdersList", vendorOrdersList);
		return true;
	}

	public static void manage() {
		currentPage("orders");
		String pageNbFromSession = session.get("currentClientOrdersPage");
		int itemsCount = ClientOrder.all().filter("status", Enums.StatusOrder.UnderPrepare.ordinal()).count();
		Pagination pagination = null;
		List<ClientOrder> vendorOrdersList = null;
		HashMap<String, List<Order_Product>> map = null;
		if (itemsCount > 0) {
			map = new HashMap<String, List<Order_Product>>();
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentClientOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = ClientOrder.all().filter("status", Enums.StatusOrder.UnderPrepare.ordinal())
					.order("orderDate").fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		render(vendorOrdersList, pagination);
	}

	public static void payOrders() {
		currentPage("ReadyOrders");
		String pageNbFromSession = session.get("currentPayClientOrdersPage");
		int itemsCount = ClientOrder.all().filter("status", Enums.StatusOrder.Completed.ordinal()).count();
		Pagination pagination = null;
		List<ClientOrder> vendorOrdersList = null;
		HashMap<String, List<Order_Product>> map = null;
		if (itemsCount > 0) {
			map = new HashMap<String, List<Order_Product>>();
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentPayClientOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = ClientOrder.all().filter("status", Enums.StatusOrder.Completed.ordinal())
					.order("orderDate").fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		render(vendorOrdersList, pagination);
	}

	public static void listOfPaidOrders() {
		boolean isFilteringMode = false;
		currentPage("PaidOrders");
		String pageNbFromSession = session.get("currentPaidOrdersClientOrdersPage");
		int itemsCount = ClientOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal()).count();
		Pagination pagination = null;
		List<ClientOrder> vendorOrdersList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentPaidOrdersClientOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = ClientOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal()).order("orderDate")
					.fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		render(vendorOrdersList, pagination,isFilteringMode);
	}

	public static void searchByDate() throws ParseException {
		String orderDate = params.get("orderDate");
		Date date = Application.getDateFromString(orderDate, "dd/MM/yyyy");
		int itemsCount = 0;
		String pageNbFromSession = session.get("currentPaidOrdersClientOrdersPage");
		Pagination pagination = null;
		List<ClientOrder> vendorOrdersList = null;
		Date fromDate = Application.getFirstDateInDay(date);
		Date toDate = Application.getLastDateInDay(date);
		itemsCount = ClientOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal())
				.filter("orderDate >=", fromDate).filter("orderDate<=", toDate).count();
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentPaidOrdersClientOrdersPage", pagination.getCurrentPage());
			vendorOrdersList = ClientOrder.all().filter("status", Enums.StatusOrder.Paid.ordinal())
					.filter("orderDate >=", fromDate).filter("orderDate<=", toDate).order("orderDate")
					.fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		boolean isFilteringMode = true;
		renderTemplate("ClientOrders/listOfPaidOrders.html", itemsCount, vendorOrdersList, isFilteringMode, orderDate,pagination);
	}

	public static void setReady(String id) {
		ClientOrder order = ClientOrder.getByID(id);
		order.status = Enums.StatusOrder.Completed.ordinal();
		order.saveOrder();
		payOrders();
	}

	public static void setPaid(String id) {
		ClientOrder order = ClientOrder.getByID(id);
		order.status = Enums.StatusOrder.Paid.ordinal();
		order.saveOrder();
		payOrders();
	}

	public static void getPage(int page) throws IOException {
		currentPage("orders");
		session.put("currentClientOrdersPage", page);
		listOfPaidOrders();
	}

	public static void getPayPage(int page) throws IOException {
		currentPage("ReadyOrders");
		session.put("currentPayClientOrdersPage", page);
		payOrders();
	}

	public static void getPaidOrdersPage(int page) throws IOException {
		currentPage("PaidOrders");
		session.put("currentPaidOrdersClientOrdersPage", page);
		listOfPaidOrders();
	}
}
