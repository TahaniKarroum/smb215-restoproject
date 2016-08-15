package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import models.MeasureType;
import models.MeasureUnit;
import play.mvc.*;
import siena.Model;
import utils.Pagination;

public class MeasureTypes extends controllers.CRUD {
	@Before
	public static void addDefaults() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		currentPage("unitsList");
		Application.onEachController();
	}

	public static boolean currentPage(String currentPage) {
		session.put("currentPage", currentPage);
		return true;
	}

	public static void manage() {
		int itemsCount = MeasureType.all().count();
		Pagination pagination = null;
		List<MeasureType> measureTypesList = null;
		if (itemsCount > 0) {
			String pageNbFromSession = session.get("currentMeasureTypesListPage");
			pagination = new Pagination(pageNbFromSession, itemsCount, 15);
			session.put("currentMeasureTypesListPage", pagination.getCurrentPage());
			measureTypesList = MeasureType.all().fetch(pagination.getPageSize(),
					(pagination.getCurrentPage() - 1) * pagination.getPageSize());
		}
		render(measureTypesList, pagination);
	}

	public static void getPage(int page) {
		session.put("currentMeasureTypesListPage", page);
		manage();

	}

	public static void measureTypeForm(String ID) {
		MeasureType measureType = null;
		if (ID == null) {
			measureType = new MeasureType();
		} else {
			measureType = Model.getByKey(MeasureType.class, ID);
		}

		render(measureType);
	}

	public static void saveMeasureType(MeasureType measureType) {
		measureType.saveMeasureType();
		manage();
	}

	public static void remove(String measureTypeID) {
		MeasureType measureType = MeasureType.getByID(measureTypeID);
		if (measureType != null) {
			Model.all(MeasureUnit.class).filter("measureType_ID", measureTypeID).delete();
			Model.all(MeasureType.class).filter("ID", measureTypeID).delete();
		}
		manage();
	}
}
