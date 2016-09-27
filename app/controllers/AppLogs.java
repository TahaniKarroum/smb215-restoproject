package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import models.AppLog;
import models.Employee;
import play.i18n.Lang;
import play.mvc.Before;
import siena.Model;
import siena.Query;
import utils.Enums.LogType;
import utils.Pagination;

public class AppLogs extends controllers.CRUD {
	@Before
	public static void addDefault() throws IOException, ParseException, NoSuchAlgorithmException {
		if (session.get("loggedInEmpID") == null)
			Application.employeeLogin(false, true);
		Application.onEachController();
	}

	public static boolean setLatestLogs() {
		List<AppLog> logsList = AppLog.all().order("-submitDate").fetch(5);
		renderArgs.put("logsList", logsList);
		return true;
	}

	public static boolean setArgsList() {
		List<AppLog> logsList = AppLog.all().order("-submitDate").fetch();
		renderArgs.put("logsList", logsList);
		return true;
	}

	public static void correctDate() {
		for (AppLog log : AppLog.all().fetch()) {
			log.submitDate = new Date();
			log.saveLog();
		}
	}

	public static String createLog(int type, String ID) {
		StringBuilder message = new StringBuilder();
		Employee employee = Application.checkEmployeeLogin();
		int priority_id = 1;
		String link = "";
		String mes = "";
		boolean isArabic = (Lang.get() != null && Lang.get().equalsIgnoreCase("ar"));
		if (type == LogType.Product.ordinal()) {
			link = "/Products/viewDetails?ID=" + ID;
			mes = " added new Product ";
		}
		if (type == LogType.Vendor.ordinal()) {
			link = "/Vendors/viewDetails?ID=" + ID;
			mes = " added new Vendor ";
		}
		if (type == LogType.Sale.ordinal()) {
			link = "/SalesOrders/viewDetails?ID=" + ID;
			mes = " added new SalesOrder ";
		}
		if (type == LogType.PurchasedOrder.ordinal()) {
			link = "/PurchasedOrders/viewDetails?ID=" + ID;
			mes = " added new PurchasedOrder ";
		}
		if (type == LogType.Client.ordinal()) {
			link = "/Customers/viewdetails?customer_ID=" + ID;
			mes = " added new Client ";
		}

		if (type == LogType.Login.ordinal()) {
			mes = " has Loggged In ";

		}
		if (type == LogType.Logout.ordinal()) {
			if (!isArabic)
				mes = " Sign out";
			else
				mes = " قد خرج ";
		}
		message.append(employee.name + mes);
		AppLog log = new AppLog(message.toString(), type, priority_id, employee.ID);
		log.link = link;
		log.saveLog();
		return log.ID;
	}

	public static boolean deleteAppLog() {
		return true;
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static void getPage(int page) throws IOException {
		session.put("currentLogsPage", page);
		logsManage();
	}

	public static void logsManage() {
		List<AppLog> logsList = null;
		currentPage("logsList");
		boolean isFilteringMode = false;
		session.put("lastPage", null);
		String pageNbFromSession = session.get("currentLogsPage");
		Pagination pagination = null;
		int itemsCount = Model.all(AppLog.class).count();
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 30);
			session.put("currentLogsPage", pagination.getCurrentPage());
			logsList = Model.all(AppLog.class).order("-submitDate").fetch(pagination.getPageSize(),
					pagination.getPageStartIndex());
		}
		render(logsList, pagination, isFilteringMode);
	}

	public static void searchByType() throws IOException {
		String type = params.get("type");
		int intType = Integer.parseInt(type);
		if (type != null && !type.equals("")) {
			Query query = Model.all(AppLog.class).filter("module_id", intType);
			List<AppLog> logsList = query.fetch();
			boolean isFilteringMode = true;
			renderTemplate("AppLogs/logsManage.html", logsList, isFilteringMode, intType);
		} else {
			logsManage();
		}
	}
}
