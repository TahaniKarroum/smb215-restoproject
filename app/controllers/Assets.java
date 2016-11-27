package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Brand;
import models.Category;
import models.Client;
import models.Comment;
import models.Employee;
import models.Product;
import models.Site;
import models.AssetItems;
import play.mvc.*;
import siena.Model;
import siena.Query;
import utils.Pagination;
import utils.Enums.categoryType;

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

	
	public static void items() {
		currentPage("items");
		int itemsCount = AssetItems.all().count();
		List<AssetItems> itemList = null;
		if (itemsCount > 0) {
			itemList = AssetItems.all().fetch();
		}
		render(itemList);
	}

	
	public static void itemForm(String ID) {
		AssetItems item = AssetItems.getByID(ID);
		if (item == null)
			item = new AssetItems();
		
		List<Site> siteList = Site.getAllSites().fetch();
		render(item, siteList);
 
	}

	public static void saveitem(AssetItems item) throws IOException {
		item.save();
		items();
	}

	
	//Reports 
	public static void reports() {
		currentPage("reports");
		List<Site> siteList = Site.getAllSites().fetch();
		List<AssetItems> itemList = AssetItems.getAllItems().fetch();
		render(siteList, itemList);
	}
	
	//Comments 
	public static void comments() {
		currentPage("comments");
		int itemsCount = Comment.all().count();
		List<Comment> commentList = null;
		if (itemsCount > 0) {
			commentList = Comment.all().fetch();
		}
		render(commentList);
	}

	
	public static void commentForm(String ID) {
		Comment comment = Comment.getByID(ID);
		if (comment == null)
			comment = new Comment();
	 
		render(comment);
		 
	}
	
	

	public static void savecomment(Comment comment) throws IOException {
		comment.save();
		comments();
	}
//End Comments


}
