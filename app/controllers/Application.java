package controllers;

import play.*;
import play.mvc.*;
import siena.Model;

import java.text.ParseException;
import java.util.*;

import models.*;

public class Application extends Controller {
	public static boolean onEachController(){
		
		Employee employee = checkEmployeeLogin();
		renderArgs.put("loggedInEmpName", session.get("loggedInEmpName"));
		session.put("from", "Inbox");

		return true;
	}

    public static void index() {
    	onEachController();
        render();
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
    public static void employeeLogin(boolean isFromLogout, boolean isFirst) throws ParseException {
		Employee employee = null;
		String username = params.get("username");
		String password = params.get("password");
		String msg = "";
		if (isFromLogout == false && isFirst == false)
			msg = "loginError";
		employee = Model.all(Employee.class).filter("username", username).filter("password", password).filter("isActive", true).get();
		// renderText(employee);
		if (employee == null) {
			renderTemplate("Application/employeeLoginForm.html", msg);
		} else {
			session.put("loggedInEmpID", employee.ID);
			session.put("loggedInEmpName", employee.name);
			if (employee.isAdmin) {
				session.put("isAdmin", true);
			}
		
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