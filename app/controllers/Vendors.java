package controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import models.Category;
import models.PVendor;
import models.Product;
import models.Vendor_Product;
import play.mvc.Before;
import siena.Model;
import siena.Query;
import utils.Enums.ShippingMethod;
import utils.Enums.VendorType;
import utils.Enums;
import utils.Pagination;

@CRUD.For(PVendor.class)
public class Vendors extends controllers.CRUD {
	boolean isFilteringMode = false;

	@Before
	public static void addDefault() throws IOException, ParseException, NoSuchAlgorithmException {
		if (session.get("loggedInEmpID") == null)
			Application.employeeLogin(false, true);
		Application.onEachController();
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static void manage() {
		currentPage("vendorsList");
		boolean isFilteringMode = false;
		session.put("lastPage", null);
		String pageNbFromSession = session.get("currentVendorsPage");
		int itemsCount = Model.all(PVendor.class).count();
		Pagination pagination = null;
		List<PVendor> vendorsList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 15);
			session.put("currentVendorsPage", pagination.getCurrentPage());
			vendorsList = Model.all(PVendor.class).order("vendorNumber").fetch(pagination.getPageSize(),
					pagination.getPageStartIndex());
		}
		List<Product> selectedVendorProducts = null;
		if (vendorsList != null && vendorsList.size() > 0) {
			PVendor vendor = null;
			for (PVendor vend : vendorsList) {
				vendor = PVendor.getByID(vend.ID);
				selectedVendorProducts = getVendorSelectedProducts(vendor.ID);
				if (selectedVendorProducts == null || selectedVendorProducts.size() == 0) {
					vendor.saveVendor();
				}
			}
		}
		List<PVendor> allVendorsList = Model.all(PVendor.class).fetch();
		render(vendorsList, pagination, allVendorsList, itemsCount, isFilteringMode, selectedVendorProducts);
	}

	public static void searchByVendorName() throws IOException {
		String vendorID = params.get("vendorID");
		int itemsCount = 0;
		if (vendorID != null && !vendorID.equals("")) {
			Query query = Model.all(PVendor.class).filter("ID", vendorID);
			List<PVendor> vendorsList = query.fetch();
			boolean isFilteringMode = true;
			if (vendorsList != null)
				itemsCount = vendorsList.size();
			List<PVendor> allVendorsList = Model.all(PVendor.class).fetch();
			renderTemplate("Vendors/manage.html", vendorsList, allVendorsList, itemsCount, isFilteringMode, vendorID);
		} else {
			manage();
		}
	}

	public static void getPage(int page) throws IOException {
		session.put("currentVendorsPage", page);
		manage();
	}

	public static void vendorsForm(String ID) {
		currentPage("vendorsList");
		PVendor vendor = null;
		if (ID == null) {
			vendor = new PVendor();
			vendor.shippingMethod_ID = ShippingMethod.InternalTransport.ordinal();
			vendor.vendorType_ID = VendorType.industry.ordinal();

		} else {
			vendor = PVendor.getByID(ID);
		}
		render(vendor);
	}

	public static void saveVendor(PVendor vendor) {
		vendor.saveVendor();
		vendorProductsForm(vendor.ID, null);
	}

	public static void vendorProductsForm(String ID, String categoryID) {
		currentPage("vendorsList");
		PVendor vendor = PVendor.getByID(ID);
		if (vendor == null)
			vendorsForm(null);
		List<Category> categoriesList = Category.allStockCategories().fetch();
		if (categoriesList != null && categoriesList.size() > 0 && categoryID == null)
			categoryID = Model.all(Category.class).get().ID;
		List<Product> productsList = Products.getProductsByCategory(categoryID);

		List<Vendor_Product> vpList = Model.all(Vendor_Product.class).filter("Vendor_ID", ID).fetch();
		List<Product> selectedProductsList = new ArrayList<Product>();
		for (Vendor_Product vp : vpList) {
			Product p = Model.getByKey(Product.class, vp.Product_ID);
			productsList.remove(p);
			selectedProductsList.add(p);
		}
		render(vendor, productsList, selectedProductsList, categoriesList, categoryID);
	}

	public static void saveVendorProducts(PVendor vendor) throws IOException {
		String[] productsToAdd = params.getAll("productsToAdd");
		if ((productsToAdd != null) && (productsToAdd.length > 0)) {
			Vendor_Product vp = null;
			for (String productID : productsToAdd) {
				vp = new Vendor_Product();
				vp.Product_ID = productID;
				vp.Vendor_ID = vendor.ID;
				vp.cost = 0;
				vp.shipDuration = 0;
				Model.batch(Vendor_Product.class).insert(vp);
			}
		}
		String[] productsToRemove = params.getAll("productsToRemove");
		String categoryID = params.get("categoryID");
		// renderText(categoryID);
		if (productsToRemove != null && productsToRemove.length > 0)
			for (int i = 0; i < productsToRemove.length; i++)
				System.out.println(productsToRemove[i]);
		if ((productsToRemove != null) && (productsToRemove.length > 0)) {
			for (String productID : productsToRemove) {
				Model.all(Vendor_Product.class).filter("Product_ID", productID).filter("Vendor_ID", vendor.ID).delete();
			}
		}
		vendorProductsForm(vendor.ID, categoryID);
	}

	public static void vendorSelectedProducts(String vendor_id) {
		List<Vendor_Product> vpList = Model.all(Vendor_Product.class).filter("Vendor_ID", vendor_id).fetch();
		List<Product> selectedProductsList = new ArrayList<Product>();
		for (Vendor_Product vp : vpList) {
			Product product = Model.getByKey(Product.class, vp.Product_ID);
			selectedProductsList.add(product);
		}
		render(selectedProductsList, vendor_id);
	}

	public static void addVendorProduct(String vendor_id, String productID) {
		int exist = Model.all(Vendor_Product.class).filter("Vendor_ID", vendor_id).filter("Product_ID", productID)
				.count();
		if (exist == 0) {
			Vendor_Product vp = new Vendor_Product();
			vp.Product_ID = productID;
			vp.Vendor_ID = vendor_id;
			vp.cost = 0;
			vp.shipDuration = 0;
			Model.batch(Vendor_Product.class).insert(vp);
		}
		String categoryID = params.get("categoryID");
		vendorProductsForm(vendor_id, categoryID);
	}

	public static void deleteVendorProduct(String vendor_id, String product_id) {
		Model.all(Vendor_Product.class).filter("Vendor_ID", vendor_id).filter("Product_ID", product_id).delete();
		String categoryID = params.get("categoryID");
		vendorProductsForm(vendor_id, categoryID);
	}
	public static List<Product> getVendorSelectedProducts(String ID) {
		List<Vendor_Product> vpList = Model.all(Vendor_Product.class).filter("Vendor_ID", ID).fetch();
		List<Product> selectedProductsList = new ArrayList<Product>();
		for (Vendor_Product vp : vpList) {
			Product p = Model.getByKey(Product.class, vp.Product_ID);
			selectedProductsList.add(p);
		}
		return selectedProductsList;
	}

	// Not Needed
	public static void deleteVendor(String ID) {
		PVendor vendor = PVendor.getByID(ID);
		if (vendor != null && vendor.canBeDeleted()) {
			Model.all(Vendor_Product.class).filter("Vendor_ID", vendor.ID).delete();
			vendor.delete();
			flash.put("deleteMessage", "The Vendor is deleted successfully");
		} else
			flash.put("deleteMessage", "This Vendor cannot be deleted");

		manage();
	}

	public static void activate(String ID) {
		PVendor vendor = PVendor.getByID(ID);
		vendor.isActive = true;
		vendor.saveVendor();
		manage();
	}

	public static void deactivate(String ID) {
		PVendor vendor = PVendor.getByID(ID);
		vendor.isActive = false;
		vendor.saveVendor();
		manage();
	}

	public static List<Product> getProductsByVendor(String vendor_ID) {
		List<Vendor_Product> vendorProductsList = Model.all(Vendor_Product.class).filter("Vendor_ID", vendor_ID)
				.fetch();
		List<Product> productsList = new ArrayList<Product>();
		if (vendorProductsList != null && vendorProductsList.size() > 0) {
			for (Vendor_Product vendorProduct : vendorProductsList) {
				productsList.add(Product.getByID(vendorProduct.Product_ID));
			}
		}
		return productsList;
	}

	public static List<Category> getCategoriesVendor(String vendor_ID) {
		List<Product> productsList = getProductsByVendor(vendor_ID);
		List<Category> cateoriesList = new ArrayList<Category>();
		if (productsList != null && productsList.size() > 0) {
			for (Product product : productsList) {
				if (cateoriesList.contains(Category.getByID(product.category_ID)) == false)
					cateoriesList.add(Category.getByID(product.category_ID));
			}
		}
		return cateoriesList;
	}

	public static List<Product> getProductByCategory(String vendor_ID, String category_ID) {
		List<Product> allProduct = getProductsByVendor(vendor_ID);
		List<Product> productsList = new ArrayList<Product>();
		if (allProduct != null && allProduct.size() > 0) {
			for (Product product : allProduct) {
				if (product.category_ID.equals(category_ID))
					productsList.add(product);
			}
		}
		return productsList;
	}
}