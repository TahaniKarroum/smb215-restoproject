package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Brand;
import models.Category;
import models.MeasureType;
import models.MeasureUnit;
import models.Product;
import models.Product_MeasureUnit;
import models.Product_product_Composition;
import models.Unit;
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
		List<Category> menuCategoriesList = Model.all(Category.class)
				.filter("categoryType_ID", categoryType.menu.ordinal()).fetch();
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
		List<Product> productsList = Model.all(Product.class).filter("productType_ID", ProductType.menu.ordinal())
				.fetch();
		List<Product> allProducts = Model.all(Product.class).fetch();
		List<Category> categoriesList = Model.all(Category.class)
				.filter("categoryType_ID", Enums.categoryType.menu.ordinal()).fetch();
		render(productsList, allProducts, categoriesList);
	}

	public static void deleteProduct(String ID) {

	}

	public static void manage() {
		currentPage("newProductStock");
		String pageNbFromSession = session.get("currentProductsPage");
		int itemsCount = Product.allStockProducts().count();
		Pagination pagination = null;
		List<Product> productsList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentProductsPage", pagination.getCurrentPage());
			productsList = Product.allStockProducts().order("-number").fetch(pagination.getPageSize(),
					pagination.getPageStartIndex());
		}
		List<Product> allProducts = Product.allStockProducts().fetch();
		List<Category> categoriesList = Category.allStockCategories().fetch();
		render(productsList, itemsCount, categoriesList, pagination, allProducts);
	}

	public static void viewDetails(String ID) {
		currentPage("newProductStock");
		Product product = Product.getByID(ID);
		if (product == null)
			manage();
		render(product);
	}

	public static void getPage(int page) {
		session.put("currentProductsPage", page);
		manage();
	}

	public static void productForm(String ID) {
		currentPage("newProductStock");
		Product product = null;
		if (ID == null)
			product = new Product();
		else
			product = Product.getByID(ID);
		List<Category> categoriesList = Category.allStockCategories().fetch();
		List<MeasureUnit> unitsList=MeasureUnit.all().fetch();
		render(product, ID, categoriesList,unitsList);
	}
	
	public static void saveProductForm(Product product) throws IOException {
		product.productType_ID = Enums.ProductType.stock.ordinal();
		product.isAllowAlarm = false;
		product.saveProduct();
		manage();
	}
	
	public static void productComposition(String product_ID) {
		Product product = Product.getByID(product_ID);
		List<Product> productsList = Product.allStockProducts().fetch();
		List<Product> selectedProductsList = product.getProductComposite();
		List<String> productsIDs = null;
		if (selectedProductsList != null && selectedProductsList.size() > 0) {
			productsIDs = new ArrayList<String>();
			for (Product p : selectedProductsList) {
				if (p != null)
					productsIDs.add(p.ID);
			}
		}
		render(product, productsList, selectedProductsList, productsIDs);
	}

	public static void saveProductComposite() {
		String product_ID = params.get("productID");
		String[] productsToAdd = params.getAll("productsToAdd");
		Product product = null;
		if ((productsToAdd != null) && (productsToAdd.length > 0)) {
			for (String productID : productsToAdd) {
				product = Product.getByID(productID);
				Product_product_Composition ppc = Model.all(Product_product_Composition.class).filter("principalProduct_ID", product_ID)
						.filter("product_ID", productID).get();
				if (ppc == null)
					ppc = new Product_product_Composition();
				ppc.product_ID = productID;
				ppc.principalProduct_ID = product_ID;
				ppc.measureUnit_ID = params.get("unit_" + productID);
				String qty = params.get("qty_" + productID);
				if (qty != null && qty.length() > 0)
					ppc.quantity = Double.parseDouble(qty);
				ppc.saveProductComposition();

			}
		} else {
			product = Product.getByID(product_ID);
			List<Product> productsCompositesList = product.getProductComposite();
			if (productsCompositesList != null && productsCompositesList.size() > 0) {
				for (Product pr : productsCompositesList) {
					Product_product_Composition ppc = Model.all(Product_product_Composition.class).filter("principalProduct_ID", product_ID)
							.filter("product_ID", pr.ID).get();
					ppc.measureUnit_ID = params.get("unit_" + pr.ID);
					String qty = params.get("qty_" + pr.ID);
					if (qty != null && qty.length() > 0)
						ppc.quantity = Double.parseDouble(qty);
					ppc.saveProductComposition();
				}
			}
		}
		String submitType = params.get("submitTypeInput");
		if (submitType != null && submitType.equals("finish"))
			menuManage();
		else {
			productComposition(product_ID);
		}
	}

	public static void deleteCompositeProduct(String productComposite_ID, String product_ID) {
		Model.all(Product_product_Composition.class).filter("ID", productComposite_ID).delete();
		productComposition(product_ID);
	}

}
