package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Brand;
import models.Category;
import models.Client;
import models.Employee;
import models.Product;
import models.Reservation;
import models.Site;
import play.mvc.*;
import siena.Model;
import utils.Pagination;

public class Assets extends controllers.CRUD {

	@Before
	public static void addDefault() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		Application.onEachController();
		currentPage("reservation");
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}
 
 

	public static void manage() {
		currentPage("assetHome");
		
		render();
	}
	
	public static void sites() {
		currentPage("sites");
		int itemsCount = Site.all().count();
		List<Site> siteList = null;
		if (itemsCount > 0) {
			siteList = Site.all().fetch();
		}
		render(siteList);
	}

	
	public static void siteForm(String ID) {
		Site site = Site.getByID(ID);
		if (site == null)
			site = new Site();
	 
		render(site);
	}

	public static void savesite(Site site) throws IOException {
		site.savesite();
		sites();
	}

	 

}
