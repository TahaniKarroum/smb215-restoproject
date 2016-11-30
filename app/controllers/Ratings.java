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
import models.RatingEmployee;
import models.RatingProduct;
import models.Site;
import play.mvc.*;
import siena.Model;
import utils.Enums;
import utils.Pagination;
import utils.Enums.categoryType;

public class Ratings extends controllers.CRUD {

	@Before
	public static void addDefault() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		Application.onEachController();
		currentPage("Ratings");
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}
	

	public static void manage() {
		currentPage("ratings");
		 
		render(1);
	}

	 

	public static void ratingProductForm(String ID) {
		RatingProduct ratingproduct = RatingProduct.getByID(ID);
		if (ratingproduct == null)
			ratingproduct = new RatingProduct();
		 
		List<Product> productList = Product.all().fetch();
		
		render(ratingproduct, productList);
	}

	public static void saveRatingProduct(RatingProduct ratingproduct) throws IOException {
		ratingproduct.save();
		ratingProducts();
	}

	public static void ratingProducts() {
		currentPage("ratings");
		int itemsCount = RatingProduct.all().count();
		List<RatingProduct> ratingProductlist = null;
		if (itemsCount > 0) {
			ratingProductlist = RatingProduct.all().fetch();
		}
		render(ratingProductlist);
	}

	
	public static void ratingEmployeeForm(String ID) {
		RatingEmployee ratingEmployee = RatingEmployee.getByID(ID);
		if (ratingEmployee == null)
			ratingEmployee = new RatingEmployee();
		 
		render(ratingEmployee);
	}

	public static void saveRatingEmployee(RatingEmployee ratingEmployee) throws IOException {
		ratingEmployee.save();
		ratingEmployees();
	}

	public static void ratingEmployees() {
		currentPage("ratings");
		int itemsCount = RatingEmployee.all().count();
		List<RatingEmployee> ratingEmployeelist = null;
		if (itemsCount > 0) {
			ratingEmployeelist = RatingEmployee.all().fetch();
		}
		render(ratingEmployeelist);
	}
	
	
public String getStars() {
		

		String res = "<div id=\"star-rating\">";
		res += "  <input type=\"radio\" name=\"example\" class=\"rating rating1\" value=\"1\" />";
				res += "    <input type=\"radio\" name=\"example\" class=\"rating rating2\" value=\"2\" />";
						res += "    <input type=\"radio\" name=\"example\" class=\"rating rating3\" value=\"3\" />";
								res += "   <input type=\"radio\" name=\"example\" class=\"rating rating4\" value=\"4\"   />";
										res += "    <input type=\"radio\" name=\"example\" class=\"rating rating5\" value=\"5\" />";
												res += "</div>";
		 
				
		return res;		
	}
	
	 
	 

}
