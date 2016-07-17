package controllers;

import play.Play;
import play.mvc.Before;
import siena.Model;
import siena.Query;

public class Products extends controllers.CRUD {
	@Before
	public static void addDefaults() {
		Application.onEachController();
	}

}
