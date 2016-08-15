package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import models.Account;
import models.Category;
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
}
