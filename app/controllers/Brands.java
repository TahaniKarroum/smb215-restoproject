package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Brand;
import models.Product;
import play.mvc.*;
import siena.Model;
import utils.Pagination;

public class Brands extends controllers.CRUD {

	@Before
	public static void addDefault() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		Application.onEachController();
		currentPage("brands");
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static boolean setArgsList() {
		List<Brand> brandsList = Brand.all().fetch();
		renderArgs.put("brandsList", brandsList);
		return true;
	}

	public static void brandForm(String ID) {
		Brand brand = Brand.getByID(ID);
		if (brand == null)
			brand = new Brand();
		render(brand);
	}

	public static void saveBrand(Brand brand) throws IOException {
		List<Brand> brandsList = Brand.all().fetch();
		String brandName = brand.name.toLowerCase();
		String brandid=params.get("id");
		String message = null;
		String oldBrandName = "";
		if (brandsList != null && brandsList.size() > 0 && (brandid==null || brandid.length()==0)) {
			for (Brand b : brandsList) {
				oldBrandName = b.name.toLowerCase();
				if (oldBrandName.equals(brandName)) {
					message = "Message";
					break;
				}
			}
		}
		if (message != null && message.length() > 0)
			renderTemplate("Brands/brandForm.html", brand, message);
		else {
			brand.saveBrand();
			manage();
		}
	}

	public static void manage() {
		String pageNbFromSession = session.get("currentBrandsPage");
		int itemsCount = Brand.all().count();
		Pagination pagination = null;
		List<Brand> brandsList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentBrandsPage", pagination.getCurrentPage());
			brandsList = Brand.all().fetch(pagination.getPageSize(), pagination.getPageStartIndex());
		}
		List<Brand> allBrands = Model.all(Brand.class).fetch();
		render(brandsList, pagination, allBrands);
	}

	public static void searchByBrand() throws IOException {
		String brandID = params.get("brand_id");
		List<Brand> brandsList = Brand.all().filter("ID", brandID).fetch();
		boolean isFilteringMode = true;
		List<Brand> allBrands = Model.all(Brand.class).fetch();
		renderTemplate("Brands/manage.html", brandID, brandsList, allBrands, isFilteringMode);
	}

	public static void getPage(int page) throws IOException {
		session.put("currentBrandsPage", page);
		manage();
	}

	public static void deleteBrand(String ID) {
		Brand brand = Brand.getByID(ID);
		if (brand != null) {
			brand.delete();
		}
		manage();
	}

}
