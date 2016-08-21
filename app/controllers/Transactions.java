package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Category;
import models.Employee;
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

public class Transactions extends controllers.CRUD {
	@Before
	public static void addDefaults() {
		Application.onEachController();
	}
}
