package controllers;

import play.*;
import play.mvc.*;
import siena.Model;
import utils.Enums.LogType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

import models.*;

public class Application extends Controller {
	public static boolean onEachController(){
		Employee employee = checkEmployeeLogin();
		renderArgs.put("loggedInEmpName", session.get("loggedInEmpName"));

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
    public static void employeeLogin(boolean isFromLogout, boolean isFirst) throws ParseException, NoSuchAlgorithmException {
		Employee employee = null;
		String username = params.get("username");
		String password = params.get("password");
    	
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
       
     String hashpass=sb.toString();
     System.out.println("mmmmm "+password+"  "+hashpass);
		String msg = "";
		if (isFromLogout == false && isFirst == false)
			msg = "loginError";
		employee = Model.all(Employee.class).filter("username", username).filter("password", hashpass).filter("isActive", true).get();
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