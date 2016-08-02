package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

public class Products extends controllers.CRUD {
	@Before
	public static void addDefaults() {
		Application.onEachController();
	}
	public static boolean setArgsList() {
		List<Product> productsList = Product.all().fetch();
		renderArgs.put("productsList", productsList);
		return true;
	}
	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static void menuProductForm(String ID) {
		currentPage("productsmenu");
		Product product = null;
		if (ID == null)
			product = new Product();
		else
			product = Product.getByID(ID);
		List<Category> menuCategoriesList = Model.all(Category.class).filter("categoryType_ID", categoryType.menu.ordinal()).fetch();
		List<Product> stockProductsList = Product.allStockProducts().fetch();
		render(product, menuCategoriesList, stockProductsList);

	}

	public static void saveMenuProductForm(Product product, File uploadImage) throws IOException {

		product.productType_ID = Enums.ProductType.menu.ordinal();
		product.isAllowAlarm = false;
		product.saveProduct();
		menuManage();
	}

	public static void menuManage() {
		currentPage("productsmenu");
		List<Product> productsList = Model.all(Product.class).filter("productType_ID", ProductType.menu.ordinal()).fetch();
		List<Product> allProducts = Model.all(Product.class).fetch();
		List<Category> categoriesList = Model.all(Category.class).filter("categoryType_ID", Enums.categoryType.menu.ordinal()).fetch();
		render(productsList, allProducts, categoriesList);
	}

}
