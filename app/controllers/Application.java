package controllers;

import play.*;
import play.mvc.*;
import siena.Model;
import utils.Enums.LogType;
import utils.Enums.StatusOrder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import models.*;

public class Application extends Controller {
	public static boolean onEachController() {
		Employee employee = checkEmployeeLogin();
		renderArgs.put("loggedInEmpName", session.get("loggedInEmpName"));

		return true;
	}

	public static Date getTodayDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dateNow = formatter.format(currentDate.getTime());
		return currentDate.getTime();
	}

	public static Date getFirstDateInDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		return calendar.getTime();
	}

	public static Date getLastDateInDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.AM_PM, Calendar.PM);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date getDateFromString(String dateStr, String format) throws ParseException {
		if (dateStr == null || dateStr.length() == 0)
			return null;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		c.setTime(sdf.parse(dateStr));
		return c.getTime();
	}

	public static void index() {
		onEachController();
		Date today = getTodayDate();
		Date fromDate = getFirstDateInDay(today);
		Date toDate = getLastDateInDay(today);
		List<ClientOrder> orders = Model.all(ClientOrder.class).filter("status", StatusOrder.Paid.ordinal())
				.filter("orderDate >=", fromDate).filter("orderDate<=", toDate).order("orderDate").fetch();
		double todayIncome = 0;
		if (orders != null && orders.size() > 0) {
			for (ClientOrder order : orders) {
				todayIncome += order.total;
			}
		}
		render(todayIncome);
	}

	public static void employeeLoginForm() {
		render();
	}

	public static Employee checkEmployeeLogin() {
		String ID = session.get("loggedInEmpID");
		Employee employee = Employee.getByID(ID);
		if (employee == null)
			Application.employeeLoginForm();
		return employee;
	}

	public static void employeeLogin(boolean isFromLogout, boolean isFirst)
			throws ParseException, NoSuchAlgorithmException {
		Employee employee = null;
		String username = params.get("username");
		String password = params.get("password");

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		String hashpass = sb.toString();
		System.out.println("mmmmm " + password + "  " + hashpass);
		String msg = "";
		if (isFromLogout == false && isFirst == false)
			msg = "loginError";
		employee = Model.all(Employee.class).filter("username", username).filter("password", hashpass)
				.filter("isActive", true).get();
		// renderText(employee);
		if (employee == null) {
			renderTemplate("Application/employeeLoginForm.html", msg);
		} else {
			session.put("loggedInEmpID", employee.ID);
			session.put("loggedInEmpName", employee.name);
			if (employee.isAdmin) {
				session.put("isAdmin", true);
			}
			int i = LogType.Login.ordinal();
			AppLogs.createLog(i, null);
			Application.index();
		}
	}

	public static void employeeLogout() throws ParseException {
		session.remove("loggedInEmpID");
		session.remove("loggedInEmpName");
		session.remove("isAdmin");
		Application.employeeLoginForm();
	}

}