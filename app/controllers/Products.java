package controllers;

import java.io.IOException;
import java.util.List;

import models.Category;
import models.Product;
import play.Play;
import play.mvc.Before;
import siena.Model;
import siena.Query;
import utils.Enums;
import utils.Pagination;

public class Products extends controllers.CRUD {
	@Before
	public static void addDefaults() {
		Application.onEachController();
		session.put("currentPage", "products");
	}
	public static boolean setArgsList() {
		List<Product> productsList = Product.all().fetch();
		renderArgs.put("productsList", productsList);
		return true;
	}

	public static void productForm(String ID) {
		Product product = Product.getByID(ID);
		if (product == null)
			product = new Product();
		render(product);
	}

	public static void saveProduct(Product product) throws IOException {
		product.saveProduct();
		manage();
	}

	public static void manage() {
		String pageNbFromSession = session.get("currentProductsPage");
		int itemsCount = Product.all().count();
		Pagination pagination = null;
		List<Product> productsList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentProductsPage", pagination.getCurrentPage());
			productsList = Product.all().fetch(pagination.getPageSize(),
					pagination.getPageStartIndex());
		}
		List<Product> allProducts = Product.all().fetch();
		render(productsList, pagination, allProducts);

	}

	public static void searchByName() throws IOException {
		String productID = params.get("product_id");
		List<Product> productsList = Product.all().filter("ID", productID).fetch();
		boolean isFilteringMode = true;
		List<Product> allProducts = Product.all().fetch();
		renderTemplate("Categories/manage.html", productID, productsList, allProducts, isFilteringMode);
	}

	public static void getPage(int page) throws IOException {
		session.put("currentProductsPage", page);
		manage();
	}

	public static void deleteProduct(String ID) {
		Product product = Product.getByID(ID);
		if (product != null) {
			product.delete();
			flash.put("deleteMessage", "The product is deleted successfully");
		}
		manage();
	}
}
