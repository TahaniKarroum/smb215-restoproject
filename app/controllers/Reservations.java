package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Brand;
import models.Product;
import models.Reservation;
import play.mvc.*;
import siena.Model;
import utils.Pagination;

public class Reservations extends controllers.CRUD {

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

	public static boolean setArgsList() {
		List<Reservation> brandsList = Reservation.all().fetch();
		renderArgs.put("reservations", brandsList);
		return true;
	}

	public static void reservationForm(String ID) {
		Reservation reservation = Reservation.getByID(ID);
		if (reservation == null)
			reservation = new Reservation();
		System.out.println("hhhhhhhhhhhhh "+ID);
		System.err.println("reddddd   "+ID);
		//renderText("blablabal  "+ID+"  okkkk");
		render(reservation);
	}

	public static void saveReservation(Reservation reservation) throws IOException {
		reservation.saveReservation();
		manage();
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
