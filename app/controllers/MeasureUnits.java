package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import models.MeasureType;
import models.MeasureUnit;
import play.mvc.*;
import siena.Model;
import utils.Pagination;

public class MeasureUnits extends controllers.CRUD {
	@Before
	public static void addDefaults() throws IOException, ParseException {
		Application.checkEmployeeLogin();
		Application.onEachController();
	}

	public static void manage(String measureType_ID) {
		int itemsCount = MeasureUnit.all().filter("measureType_ID", measureType_ID).filter("isStandard", true).count();
		Pagination pagination = null;
		List<MeasureUnit> measureUnitsList = null;
		if (itemsCount > 0) {
			String pageNbFromSession = session.get("currentMeasureUnitsListPage");
			pagination = new Pagination(pageNbFromSession, itemsCount, 8);
			session.put("currentMeasureUnitsListPage", pagination.getCurrentPage());
			measureUnitsList = MeasureUnit.all().filter("measureType_ID", measureType_ID).filter("isStandard", true)
					.fetch(pagination.getPageSize(), (pagination.getCurrentPage() - 1) * pagination.getPageSize());
		}
		render(measureUnitsList, measureType_ID, pagination);
	}

	public static void getPage(int page, String measureType_ID) {
		session.put("currentMeasureUnitsListPage", page);
		manage(measureType_ID);

	}

	public static void measureUnitForm(String ID, String measureType_ID) {
		MeasureUnit measureUnit = null;
		if (ID == null) {
			measureUnit = new MeasureUnit();
		} else {
			measureUnit = Model.getByKey(MeasureUnit.class, ID);
		}
		measureUnit.measureType_ID = measureType_ID;
		List<MeasureType> measureTypesList = Model.all(MeasureType.class).fetch();
		render(measureUnit, measureType_ID, measureTypesList);
	}

	public static void saveMeasureUnit(MeasureUnit measureUnit) {
		measureUnit.isStandard = true;
		measureUnit.saveMeasureUnit();
		manage(measureUnit.measureType_ID);
	}

	public static void remove(String measureUnitID) {
		MeasureUnit measureUnit = MeasureUnit.getByID(measureUnitID);
		Model.all(MeasureUnit.class).filter("ID", measureUnitID).delete();
		manage(measureUnit.measureType_ID);
	}
}
