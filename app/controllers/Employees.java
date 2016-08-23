package controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Employee;
import play.mvc.Before;
import siena.Model;
import utils.Pagination;

public class Employees extends controllers.CRUD {
	@Before
	static void addDefaults() throws IOException, ParseException {
		Application.onEachController();
	}
	
	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static void employeeChangePasswordForm() {
		Employee employ = Employee.getByID(session.get("loggedInEmpID"));
		render(employ);
	}

	public static void saveChangePassword() throws NoSuchAlgorithmException {
		Employee employee = Application.checkEmployeeLogin();
		String errorMsg = "";
		String oldPassword = params.get("oldPassword");
		String newPassword = params.get("password1");
		String confPassword = params.get("password2");
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(oldPassword.getBytes());
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		String hashpass = sb.toString();
		if (oldPassword.isEmpty() || newPassword.isEmpty() || confPassword.isEmpty()) {
			errorMsg = "Missing Fields";
			render("@employeeChangePasswordForm", errorMsg);
		} else {
			System.out.println("Abel el equals" + oldPassword + "  " + hashpass + "  " + employee.password);
			if (newPassword.equals(confPassword)) {
				if (hashpass.equals(employee.password)) {
					MessageDigest md2 = MessageDigest.getInstance("MD5");
					md2.update(newPassword.getBytes());
					byte byteData2[] = md2.digest();
					sb = new StringBuffer();
					for (int i = 0; i < byteData2.length; i++) {
						sb.append(Integer.toString((byteData2[i] & 0xff) + 0x100, 16).substring(1));
					}
					String hashnewpass = sb.toString();
					System.out.println(newPassword + "  " + hashnewpass + "  " + oldPassword + "  " + hashpass);
					employee.password = hashnewpass;
					employee.saveEmployee();
					errorMsg = "Password Changed Successfully";
					render("@employeeChangePasswordForm", employee, errorMsg);
				} else {
					errorMsg = "Old Password Unmatch";
					render("@employeeChangePasswordForm", errorMsg);
				}
			} else {
				errorMsg = "Unmatch New Password And Confim Password";
				render("@employeeChangePasswordForm", errorMsg);
			}
		}
	}
	
	public static void manage() {
		currentPage("listofemployees");
		int itemsCount = Employee.all().count();
		List<Employee> employeesList = null;
		if (itemsCount > 0) {
			employeesList = Employee.all().fetch();
		}
		render(employeesList);

	}
	
	public static void employeeForm(String ID) {
		currentPage("listofemployees");
		Employee employee = Employee.getByID(ID);
		if (employee == null)
			employee = new Employee();
		render(employee);
	}
	
	public static void saveEmployeeChanges(Employee employee) throws IOException {
		currentPage("listofemployees");
		employee.saveEmployee();
		manage();
	}
	
	public static void deleteEmployee(String ID) {
		Employee employee = Employee.getByID(ID);
		if (employee != null) {
			//employee.delete();
			employee.isActive = false;
			employee.update();
			flash.put("deleteMessage", "The employee is deleted successfully");
		}
		manage();
	}	
	
}
