package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import models.Category;
import models.Product;
import play.mvc.Before;
import siena.Model;
import utils.Enums;
import utils.Pagination;
import controllers.CRUD.For;

@For(Category.class)
public class Categories extends controllers.CRUD {

	@Before
	public static void addDefault() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		Application.onEachController();
		session.put("currentPage", "categories");
	}

	public static boolean setArgsList() {
		List<Category> categoriesList = Category.all().fetch();
		renderArgs.put("categoriesList", categoriesList);
		return true;
	}

	public static void categoryForm(String ID) {
		Category category = Category.getByID(ID);
		if (category == null)
			category = new Category();
		render(category);
	}

	public static void saveCategory(Category category) throws IOException {
		category.categoryType_ID = Enums.categoryType.stock.ordinal();
		category.saveCategory();
		manage();
	}

	public static void manage() {
		String pageNbFromSession = session.get("currentCategoriesPage");
		int itemsCount = Category.allStockCategories().count();
		Pagination pagination = null;
		List<Category> categoriesList = null;
		if (itemsCount > 0) {
			pagination = new Pagination(pageNbFromSession, itemsCount, 25);
			session.put("currentCategoriesPage", pagination.getCurrentPage());
			categoriesList = Category.allStockCategories().fetch(pagination.getPageSize(),
					pagination.getPageStartIndex());
		}
		List<Category> allCategories = Category.allStockCategories().fetch();
		render(categoriesList, pagination, allCategories);

	}

	public static void searchByName() throws IOException {
		String categoryID = params.get("category_id");
		List<Category> categoriesList = Category.all().filter("ID", categoryID).fetch();
		boolean isFilteringMode = true;
		List<Category> allCategories = Model.all(Category.class).fetch();
		renderTemplate("Categories/manage.html", categoryID, categoriesList, allCategories, isFilteringMode);
	}

	public static void getPage(int page) throws IOException {
		session.put("currentCategoriesPage", page);
		manage();
	}

	public static void deleteCategory(String ID) {
		Category category = Category.getByID(ID);
		if (category != null) {
			category.delete();
			flash.put("deleteMessage", "The Category is deleted successfully");
		}
		manage();
	}
}
