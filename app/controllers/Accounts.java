package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Category;
import models.Employee;
import models.MeasureUnit;
import models.Product;
import models.Product_MeasureUnit;
import play.Play;
import play.mvc.Before;
import siena.Model;
import siena.Query;
import utils.Enums;
import utils.Pagination;
import utils.Enums.ProductType;
import utils.Enums.categoryType;

public class Accounts extends controllers.CRUD {
	@Before
	public static void addDefaults() {
		Application.onEachController();
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}
	
	public static void displayAllAccounts() {
		currentPage("listofaccounts");
		String pageNbFromSession = session.get("currentAccountsPage");
		int itemsCount = Account.getAllAccounts().count();
		Pagination pagination = null;
		List<Account> accountsList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentAccountsPage", pagination.getCurrentPage());
			accountsList = Account.getAllAccounts().fetch(pagination.getPageSize(),
					pagination.getPageStartIndex());
		}
		List<Account> allAccounts = Account.getAllAccounts().fetch();
		render(accountsList, pagination, allAccounts);

	}

	public static void getPage(int page) throws IOException {
		session.put("currentAccountsPage", page);
		displayAllAccounts();
	}
	
	public static void updateAccountForm(String ID) {
		currentPage("listofaccounts");
		Account account = Account.getByID(ID);
		if (account == null)
			account = new Account();
		render(account);
	}
	
	public static void saveAccountChanges(Account account) throws IOException {
		currentPage("listofaccounts");
		account.saveAccount(session.get("loggedInEmpID"));
		displayAllAccounts();
	}
	
	public static void deleteAccount(String ID) {
		Account account = Account.getByID(ID);
		if (account != null) {
			account.delete();
			flash.put("deleteMessage", "The account is deleted successfully");
		}
		displayAllAccounts();
	}
	
	public static void accountsReports() {
		currentPage("accountsreports");
		render();
	}
	
	public static void findAccountReports(){
		String accountName = params.get("account_name");
		//String fromDate = params.get("from_date");
		//String toDate = params.get("to_date");
		
		if(accountName != null && accountName.equals("") == false){
			List<Account> accountsList = new ArrayList<Account>();
			List<Employee> employeeList = Employee.all().filter("name", accountName).fetch();
			int employeeCount = employeeList.size();
			if(employeeCount > 0){
				for(Employee item : employeeList){
					accountsList = Account.getAllAccounts().filter("user_ID", item.ID).fetch();
				}
			}
			renderTemplate("Accounts/accountsReports.html", employeeList, accountsList);
		}
	}
}
