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
		
	public static void allAccounts() {
		currentPage("listofaccounts");
		//List<Account> accountsList = Account.all().fetch();
		renderTemplate("Account/AccountManage.html");
	}
}
