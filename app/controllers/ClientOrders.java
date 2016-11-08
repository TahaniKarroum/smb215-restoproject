package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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

public class ClientOrders extends controllers.CRUD {

	@Before
	public static void addDefault() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		Application.onEachController();
		currentPage("orders");
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static boolean setArgsList() {
		List<ClientOrder> clientOrdersList = ClientOrder.all().fetch();
		renderArgs.put("clientOrdersList", clientOrdersList);
		return true;
	}

	public static void manage() {
		String pageNbFromSession = session.get("currentClientOrdersPage");
		int itemsCount = ClientOrder.all().filter("status", Enums.StatusOrder.UnderPrepare.ordinal()).count();
		Pagination pagination = null;
		List<ClientOrder> clientordersList = null;
		HashMap<String, List<Order_Product>> map = null;
		if (itemsCount > 0) {
			map = new HashMap<String, List<Order_Product>>();
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentBrandsPage", pagination.getCurrentPage());
			clientordersList = ClientOrder.all().filter("status", Enums.StatusOrder.UnderPrepare.ordinal())
					.order("orderDate").fetch(pagination.getPageSize(), pagination.getPageStartIndex());
			for (ClientOrder clientOrder : clientordersList) {
				List<Order_Product> order_products = clientOrder.getListOrderProduct();
				map.put(clientOrder.ID, order_products);
			}
		}
		render(clientordersList, pagination, map);
	}

	public static void setReady(String id) {
		ClientOrder order = ClientOrder.getByID(id);
		order.status = Enums.StatusOrder.Completed.ordinal();
		order.saveOrder();
		manage();
	}

	public static void getPage(int page) throws IOException {
		session.put("currentOrdersPage", page);
		manage();
	}
}
