package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import play.mvc.Before;
import siena.Model;


public class Employees extends controllers.CRUD { 
	 @Before
	    static void addDefaults() throws IOException, ParseException {
		Application.onEachController();
	    }
  }
